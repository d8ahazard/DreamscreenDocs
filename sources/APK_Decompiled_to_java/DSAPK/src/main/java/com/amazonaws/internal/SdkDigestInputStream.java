package com.amazonaws.internal;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class SdkDigestInputStream extends DigestInputStream implements MetricAware {
    static final /* synthetic */ boolean $assertionsDisabled = (!SdkDigestInputStream.class.desiredAssertionStatus());
    private static final int SKIP_BUF_SIZE = 2048;

    public SdkDigestInputStream(InputStream stream, MessageDigest digest) {
        super(stream, digest);
    }

    @Deprecated
    public final boolean isMetricActivated() {
        if (this.in instanceof MetricAware) {
            return ((MetricAware) this.in).isMetricActivated();
        }
        return false;
    }

    public final long skip(long n) throws IOException {
        if (n <= 0) {
            return n;
        }
        byte[] b = new byte[((int) Math.min(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH, n))];
        long m = n;
        while (m > 0) {
            int len = read(b, 0, (int) Math.min(m, (long) b.length));
            if (len == -1) {
                return m == n ? -1 : n - m;
            }
            m -= (long) len;
        }
        if ($assertionsDisabled || m == 0) {
            return n;
        }
        throw new AssertionError();
    }
}
