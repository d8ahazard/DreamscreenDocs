package com.amazonaws.metrics;

public abstract class ByteThroughputProvider {
    private int byteCount;
    private long duration;
    private final ThroughputMetricType throughputType;

    protected ByteThroughputProvider(ThroughputMetricType type) {
        this.throughputType = type;
    }

    public ThroughputMetricType getThroughputMetricType() {
        return this.throughputType;
    }

    public int getByteCount() {
        return this.byteCount;
    }

    public long getDurationNano() {
        return this.duration;
    }

    public String getProviderId() {
        return super.toString();
    }

    /* access modifiers changed from: protected */
    public void increment(int bytesDelta, long startTimeNano) {
        this.byteCount += bytesDelta;
        this.duration += System.nanoTime() - startTimeNano;
    }

    /* access modifiers changed from: protected */
    public void reset() {
        this.byteCount = 0;
        this.duration = 0;
    }

    public String toString() {
        return String.format("providerId=%s, throughputType=%s, byteCount=%d, duration=%d", new Object[]{getProviderId(), this.throughputType, Integer.valueOf(this.byteCount), Long.valueOf(this.duration)});
    }
}
