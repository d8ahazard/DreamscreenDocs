package com.amazonaws.util;

import com.amazonaws.metrics.MetricType;
import com.amazonaws.metrics.RequestMetricType;
import java.util.Collections;
import java.util.List;

@Deprecated
public class AWSRequestMetrics {
    protected final TimingInfo timingInfo;

    public enum Field implements RequestMetricType {
        AWSErrorCode,
        AWSRequestID,
        BytesProcessed,
        ClientExecuteTime,
        CredentialsRequestTime,
        Exception,
        HttpRequestTime,
        RedirectLocation,
        RequestMarshallTime,
        RequestSigningTime,
        ResponseProcessingTime,
        RequestCount,
        RetryCount,
        HttpClientRetryCount,
        HttpClientSendRequestTime,
        HttpClientReceiveResponseTime,
        HttpClientPoolAvailableCount,
        HttpClientPoolLeasedCount,
        HttpClientPoolPendingCount,
        RetryPauseTime,
        ServiceEndpoint,
        ServiceName,
        StatusCode
    }

    public AWSRequestMetrics() {
        this.timingInfo = TimingInfo.startTiming();
    }

    protected AWSRequestMetrics(TimingInfo timingInfo2) {
        this.timingInfo = timingInfo2;
    }

    public final TimingInfo getTimingInfo() {
        return this.timingInfo;
    }

    public boolean isEnabled() {
        return false;
    }

    public void startEvent(String eventName) {
    }

    public void startEvent(MetricType f) {
    }

    public void endEvent(String eventName) {
    }

    public void endEvent(MetricType f) {
    }

    public void incrementCounter(String event) {
    }

    public void incrementCounter(MetricType f) {
    }

    public void setCounter(String counterName, long count) {
    }

    public void setCounter(MetricType f, long count) {
    }

    public void addProperty(String propertyName, Object value) {
    }

    public void addProperty(MetricType f, Object value) {
    }

    public void log() {
    }

    public List<Object> getProperty(String propertyName) {
        return Collections.emptyList();
    }

    public List<Object> getProperty(MetricType f) {
        return Collections.emptyList();
    }
}
