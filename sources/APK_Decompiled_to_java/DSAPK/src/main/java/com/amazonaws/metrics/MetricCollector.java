package com.amazonaws.metrics;

public abstract class MetricCollector {
    public static final MetricCollector NONE = new MetricCollector() {
        public boolean start() {
            return true;
        }

        public boolean stop() {
            return true;
        }

        public boolean isEnabled() {
            return false;
        }

        public RequestMetricCollector getRequestMetricCollector() {
            return RequestMetricCollector.NONE;
        }

        public ServiceMetricCollector getServiceMetricCollector() {
            return ServiceMetricCollector.NONE;
        }
    };

    public interface Factory {
        MetricCollector getInstance();
    }

    public abstract RequestMetricCollector getRequestMetricCollector();

    public abstract ServiceMetricCollector getServiceMetricCollector();

    public abstract boolean isEnabled();

    public abstract boolean start();

    public abstract boolean stop();
}
