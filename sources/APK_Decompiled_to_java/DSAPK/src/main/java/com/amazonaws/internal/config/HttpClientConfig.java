package com.amazonaws.internal.config;

public class HttpClientConfig {
    private final String serviceName;

    HttpClientConfig(String serviceName2) {
        this.serviceName = serviceName2;
    }

    public String toString() {
        return "serviceName: " + this.serviceName;
    }

    public String getServiceName() {
        return this.serviceName;
    }
}
