package com.amazonaws.internal;

import java.io.FilterOutputStream;
import java.io.OutputStream;

@Deprecated
public class SdkFilterOutputStream extends FilterOutputStream implements MetricAware {
    public SdkFilterOutputStream(OutputStream out) {
        super(out);
    }

    public boolean isMetricActivated() {
        if (this.out instanceof MetricAware) {
            return ((MetricAware) this.out).isMetricActivated();
        }
        return false;
    }
}
