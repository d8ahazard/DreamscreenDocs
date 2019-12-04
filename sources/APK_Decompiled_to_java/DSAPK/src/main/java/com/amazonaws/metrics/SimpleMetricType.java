package com.amazonaws.metrics;

public abstract class SimpleMetricType implements MetricType {
    public abstract String name();

    public final int hashCode() {
        return name().hashCode();
    }

    public final boolean equals(Object o) {
        if (!(o instanceof MetricType)) {
            return false;
        }
        return name().equals(((MetricType) o).name());
    }

    public final String toString() {
        return name();
    }
}
