package com.amazonaws.http.impl.client;

import com.amazonaws.metrics.MetricType;
import com.amazonaws.util.AWSRequestMetrics;
import com.amazonaws.util.AWSRequestMetrics.Field;
import java.io.IOException;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

public final class SdkHttpRequestRetryHandler extends DefaultHttpRequestRetryHandler {
    public static final SdkHttpRequestRetryHandler Singleton = new SdkHttpRequestRetryHandler();

    private SdkHttpRequestRetryHandler() {
    }

    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        boolean retry = SdkHttpRequestRetryHandler.super.retryRequest(exception, executionCount, context);
        if (retry) {
            AWSRequestMetrics awsRequestMetrics = (AWSRequestMetrics) context.getAttribute(AWSRequestMetrics.class.getSimpleName());
            if (awsRequestMetrics != null) {
                awsRequestMetrics.incrementCounter((MetricType) Field.HttpClientRetryCount);
            }
        }
        return retry;
    }
}
