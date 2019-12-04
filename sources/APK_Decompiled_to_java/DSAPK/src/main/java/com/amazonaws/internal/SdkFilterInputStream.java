package com.amazonaws.internal;

import com.amazonaws.AbortedException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SdkFilterInputStream extends FilterInputStream implements MetricAware {
    protected SdkFilterInputStream(InputStream in) {
        super(in);
    }

    @Deprecated
    public boolean isMetricActivated() {
        if (this.in instanceof MetricAware) {
            return ((MetricAware) this.in).isMetricActivated();
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public final void abortIfNeeded() {
        if (Thread.interrupted()) {
            abort();
            throw new AbortedException();
        }
    }

    /* access modifiers changed from: protected */
    public void abort() {
    }

    public int read() throws IOException {
        abortIfNeeded();
        return this.in.read();
    }

    public int read(byte[] b, int off, int len) throws IOException {
        abortIfNeeded();
        return this.in.read(b, off, len);
    }

    public long skip(long n) throws IOException {
        abortIfNeeded();
        return this.in.skip(n);
    }

    public int available() throws IOException {
        abortIfNeeded();
        return this.in.available();
    }

    public void close() throws IOException {
        this.in.close();
        abortIfNeeded();
    }

    public synchronized void mark(int readlimit) {
        abortIfNeeded();
        this.in.mark(readlimit);
    }

    public synchronized void reset() throws IOException {
        abortIfNeeded();
        this.in.reset();
    }

    public boolean markSupported() {
        abortIfNeeded();
        return this.in.markSupported();
    }
}
