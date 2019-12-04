package com.amazonaws.metrics;

import com.amazonaws.Request;
import com.amazonaws.Response;

public abstract class RequestMetricCollector {
    public static final RequestMetricCollector NONE = new RequestMetricCollector() {
        public void collectMetrics(Request<?> request, Response<?> response) {
        }

        public boolean isEnabled() {
            return false;
        }
    };

    public interface Factory {
        RequestMetricCollector getRequestMetricCollector();
    }

    public abstract void collectMetrics(Request<?> request, Response<?> response);

    public boolean isEnabled() {
        return true;
    }
}
