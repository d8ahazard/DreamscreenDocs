package com.amazonaws.metrics;

import com.amazonaws.util.TimingInfo;
import org.apache.commons.logging.LogFactory;

public class ServiceLatencyProvider {
    private long endNano = this.startNano;
    private final ServiceMetricType serviceMetricType;
    private final long startNano = System.nanoTime();

    public ServiceLatencyProvider(ServiceMetricType type) {
        this.serviceMetricType = type;
    }

    public ServiceMetricType getServiceMetricType() {
        return this.serviceMetricType;
    }

    public ServiceLatencyProvider endTiming() {
        if (this.endNano != this.startNano) {
            throw new IllegalStateException();
        }
        this.endNano = System.nanoTime();
        return this;
    }

    public double getDurationMilli() {
        if (this.endNano == this.startNano) {
            LogFactory.getLog(getClass()).debug("Likely to be a missing invocation of endTiming().");
        }
        return TimingInfo.durationMilliOf(this.startNano, this.endNano);
    }

    public String getProviderId() {
        return super.toString();
    }

    public String toString() {
        return String.format("providerId=%s, serviceMetricType=%s, startNano=%d, endNano=%d", new Object[]{getProviderId(), this.serviceMetricType, Long.valueOf(this.startNano), Long.valueOf(this.endNano)});
    }
}
