package com.amazonaws.metrics;

public abstract class ServiceMetricCollector {
    public static final ServiceMetricCollector NONE = new ServiceMetricCollector() {
        public void collectByteThroughput(ByteThroughputProvider provider) {
        }

        public void collectLatency(ServiceLatencyProvider provider) {
        }

        public boolean isEnabled() {
            return false;
        }
    };

    public interface Factory {
        ServiceMetricCollector getServiceMetricCollector();
    }

    public abstract void collectByteThroughput(ByteThroughputProvider byteThroughputProvider);

    public abstract void collectLatency(ServiceLatencyProvider serviceLatencyProvider);

    public boolean isEnabled() {
        return true;
    }
}
