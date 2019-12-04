package com.amazonaws.http;

import com.amazonaws.Request;
import com.amazonaws.metrics.MetricInputStreamEntity;
import com.amazonaws.metrics.ServiceMetricType;
import com.amazonaws.metrics.ThroughputMetricType;
import com.amazonaws.metrics.internal.ServiceMetricTypeGuesser;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.InputStreamEntity;

class RepeatableInputStreamRequestEntity extends BasicHttpEntity {
    private static final Log log = LogFactory.getLog(AmazonHttpClient.class);
    private InputStream content;
    private boolean firstAttempt = true;
    private InputStreamEntity inputStreamRequestEntity;
    private IOException originalException;

    RepeatableInputStreamRequestEntity(Request<?> request) {
        setChunked(false);
        long contentLength = -1;
        try {
            String contentLengthString = (String) request.getHeaders().get(HttpHeader.CONTENT_LENGTH);
            if (contentLengthString != null) {
                contentLength = Long.parseLong(contentLengthString);
            }
        } catch (NumberFormatException e) {
            log.warn("Unable to parse content length from request.  Buffering contents in memory.");
        }
        String contentType = (String) request.getHeaders().get("Content-Type");
        ThroughputMetricType type = ServiceMetricTypeGuesser.guessThroughputMetricType(request, ServiceMetricType.UPLOAD_THROUGHPUT_NAME_SUFFIX, ServiceMetricType.UPLOAD_BYTE_COUNT_NAME_SUFFIX);
        if (type == null) {
            this.inputStreamRequestEntity = new InputStreamEntity(request.getContent(), contentLength);
        } else {
            this.inputStreamRequestEntity = new MetricInputStreamEntity(type, request.getContent(), contentLength);
        }
        this.inputStreamRequestEntity.setContentType(contentType);
        this.content = request.getContent();
        setContent(this.content);
        setContentType(contentType);
        setContentLength(contentLength);
    }

    public boolean isChunked() {
        return false;
    }

    public boolean isRepeatable() {
        return this.content.markSupported() || this.inputStreamRequestEntity.isRepeatable();
    }

    public void writeTo(OutputStream output) throws IOException {
        try {
            if (!this.firstAttempt && isRepeatable()) {
                this.content.reset();
            }
            this.firstAttempt = false;
            this.inputStreamRequestEntity.writeTo(output);
        } catch (IOException ioe) {
            if (this.originalException == null) {
                this.originalException = ioe;
            }
            throw this.originalException;
        }
    }
}
