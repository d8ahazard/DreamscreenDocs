package com.amazonaws.metrics;

public interface ThroughputMetricType extends ServiceMetricType {
    ServiceMetricType getByteCountMetricType();
}
