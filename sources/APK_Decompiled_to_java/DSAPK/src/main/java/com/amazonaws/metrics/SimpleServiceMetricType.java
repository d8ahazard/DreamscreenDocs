package com.amazonaws.metrics;

public class SimpleServiceMetricType extends SimpleMetricType implements ServiceMetricType {
    private final String name;
    private final String serviceName;

    public SimpleServiceMetricType(String name2, String serviceName2) {
        this.name = name2;
        this.serviceName = serviceName2;
    }

    public String name() {
        return this.name;
    }

    public String getServiceName() {
        return this.serviceName;
    }
}
