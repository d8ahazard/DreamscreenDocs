package com.amazonaws.metrics;

import java.util.concurrent.TimeUnit;

class ByteThroughputHelper extends ByteThroughputProvider {
    private static final int REPORT_INTERVAL_SECS = 10;

    ByteThroughputHelper(ThroughputMetricType type) {
        super(type);
    }

    /* access modifiers changed from: 0000 */
    public long startTiming() {
        if (TimeUnit.NANOSECONDS.toSeconds(getDurationNano()) > 10) {
            reportMetrics();
        }
        return System.nanoTime();
    }

    /* access modifiers changed from: 0000 */
    public void reportMetrics() {
        if (getByteCount() > 0) {
            AwsSdkMetrics.getServiceMetricCollector().collectByteThroughput(this);
            reset();
        }
    }

    public void increment(int bytesDelta, long startTimeNano) {
        super.increment(bytesDelta, startTimeNano);
    }
}
