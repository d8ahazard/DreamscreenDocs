package com.amazonaws.metrics;

import android.support.v4.media.session.PlaybackStateCompat;
import com.amazonaws.internal.MetricAware;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.entity.InputStreamEntity;

public class MetricInputStreamEntity extends InputStreamEntity {
    private static final int BUFFER_SIZE = 2048;
    private final ByteThroughputHelper helper;

    public MetricInputStreamEntity(ThroughputMetricType metricType, InputStream instream, long length) {
        super(instream, length);
        this.helper = new ByteThroughputHelper(metricType);
    }

    public void writeTo(OutputStream outstream) throws IOException {
        if (!(outstream instanceof MetricAware) || !((MetricAware) outstream).isMetricActivated()) {
            writeToWithMetrics(outstream);
        } else {
            MetricInputStreamEntity.super.writeTo(outstream);
        }
    }

    private void writeToWithMetrics(OutputStream outstream) throws IOException {
        if (outstream == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        }
        InputStream content = getContent();
        long length = getContentLength();
        InputStream instream = content;
        try {
            byte[] buffer = new byte[2048];
            if (length < 0) {
                while (true) {
                    int l = instream.read(buffer);
                    if (l == -1) {
                        break;
                    }
                    long startNano = this.helper.startTiming();
                    outstream.write(buffer, 0, l);
                    this.helper.increment(l, startNano);
                }
            } else {
                long remaining = length;
                while (remaining > 0) {
                    int l2 = instream.read(buffer, 0, (int) Math.min(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH, remaining));
                    if (l2 == -1) {
                        break;
                    }
                    long startNano2 = this.helper.startTiming();
                    outstream.write(buffer, 0, l2);
                    this.helper.increment(l2, startNano2);
                    remaining -= (long) l2;
                }
            }
        } finally {
            this.helper.reportMetrics();
            instream.close();
        }
    }
}
