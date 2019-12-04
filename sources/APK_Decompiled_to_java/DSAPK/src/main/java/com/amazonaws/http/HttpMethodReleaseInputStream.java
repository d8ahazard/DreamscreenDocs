package com.amazonaws.http;

import com.amazonaws.internal.SdkInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.AbortableHttpRequest;

public class HttpMethodReleaseInputStream extends SdkInputStream {
    private static final Log log = LogFactory.getLog(HttpMethodReleaseInputStream.class);
    private boolean alreadyReleased;
    private HttpEntityEnclosingRequest httpRequest;
    private InputStream in;
    private boolean underlyingStreamConsumed;

    public HttpMethodReleaseInputStream(HttpEntityEnclosingRequest httpMethod) {
        this.httpRequest = httpMethod;
        try {
            this.in = httpMethod.getEntity().getContent();
        } catch (IOException e) {
            if (log.isWarnEnabled()) {
                log.warn("Unable to obtain HttpMethod's response data stream", e);
            }
            try {
                httpMethod.getEntity().getContent().close();
            } catch (Exception e2) {
            }
            this.in = new ByteArrayInputStream(new byte[0]);
        }
    }

    public HttpEntityEnclosingRequest getHttpRequest() {
        return this.httpRequest;
    }

    /* access modifiers changed from: protected */
    public void releaseConnection() throws IOException {
        if (!this.alreadyReleased) {
            if (!this.underlyingStreamConsumed && (this.httpRequest instanceof AbortableHttpRequest)) {
                this.httpRequest.abort();
            }
            this.in.close();
            this.alreadyReleased = true;
        }
    }

    public int read() throws IOException {
        try {
            int read = this.in.read();
            if (read == -1) {
                this.underlyingStreamConsumed = true;
                if (!this.alreadyReleased) {
                    releaseConnection();
                    if (log.isDebugEnabled()) {
                        log.debug("Released HttpMethod as its response data stream is fully consumed");
                    }
                }
            }
            return read;
        } catch (IOException e) {
            releaseConnection();
            if (log.isDebugEnabled()) {
                log.debug("Released HttpMethod as its response data stream threw an exception", e);
            }
            throw e;
        }
    }

    public int read(byte[] b, int off, int len) throws IOException {
        try {
            int read = this.in.read(b, off, len);
            if (read == -1) {
                this.underlyingStreamConsumed = true;
                if (!this.alreadyReleased) {
                    releaseConnection();
                    if (log.isDebugEnabled()) {
                        log.debug("Released HttpMethod as its response data stream is fully consumed");
                    }
                }
            }
            return read;
        } catch (IOException e) {
            releaseConnection();
            if (log.isDebugEnabled()) {
                log.debug("Released HttpMethod as its response data stream threw an exception", e);
            }
            throw e;
        }
    }

    public int available() throws IOException {
        try {
            return this.in.available();
        } catch (IOException e) {
            releaseConnection();
            if (log.isDebugEnabled()) {
                log.debug("Released HttpMethod as its response data stream threw an exception", e);
            }
            throw e;
        }
    }

    public void close() throws IOException {
        if (!this.alreadyReleased) {
            releaseConnection();
            if (log.isDebugEnabled()) {
                log.debug("Released HttpMethod as its response data stream is closed");
            }
        }
        this.in.close();
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        if (!this.alreadyReleased) {
            if (log.isWarnEnabled()) {
                log.warn("Attempting to release HttpMethod in finalize() as its response data stream has gone out of scope. This attempt will not always succeed and cannot be relied upon! Please ensure S3 response data streams are always fully consumed or closed to avoid HTTP connection starvation.");
            }
            releaseConnection();
            if (log.isWarnEnabled()) {
                log.warn("Successfully released HttpMethod in finalize(). You were lucky this time... Please ensure S3 response data streams are always fully consumed or closed.");
            }
        }
        super.finalize();
    }

    /* access modifiers changed from: protected */
    public InputStream getWrappedInputStream() {
        return this.in;
    }
}
