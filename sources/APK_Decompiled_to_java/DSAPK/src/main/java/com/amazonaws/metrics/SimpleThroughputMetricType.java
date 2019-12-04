package com.amazonaws.metrics;

public class SimpleThroughputMetricType extends SimpleServiceMetricType implements ThroughputMetricType {
    private final ServiceMetricType byteCountMetricType;

    public SimpleThroughputMetricType(String name, String serviceName, String byteCountMetricName) {
        super(name, serviceName);
        this.byteCountMetricType = new SimpleServiceMetricType(byteCountMetricName, serviceName);
    }

    public ServiceMetricType getByteCountMetricType() {
        return this.byteCountMetricType;
    }
}
