package com.amazonaws.internal;

import com.amazonaws.AbortedException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.logging.LogFactory;

public abstract class SdkInputStream extends InputStream implements MetricAware {
    /* access modifiers changed from: protected */
    public abstract InputStream getWrappedInputStream();

    @Deprecated
    public final boolean isMetricActivated() {
        InputStream in = getWrappedInputStream();
        if (in instanceof MetricAware) {
            return ((MetricAware) in).isMetricActivated();
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public final void abortIfNeeded() {
        if (Thread.interrupted()) {
            try {
                abort();
            } catch (IOException e) {
                LogFactory.getLog(getClass()).debug("FYI", e);
            }
            throw new AbortedException();
        }
    }

    /* access modifiers changed from: protected */
    public void abort() throws IOException {
    }
}
