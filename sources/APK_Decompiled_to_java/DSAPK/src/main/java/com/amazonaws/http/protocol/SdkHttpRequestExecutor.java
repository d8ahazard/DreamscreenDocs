package com.amazonaws.http.protocol;

import com.amazonaws.metrics.MetricType;
import com.amazonaws.util.AWSRequestMetrics;
import com.amazonaws.util.AWSRequestMetrics.Field;
import java.io.IOException;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestExecutor;

public class SdkHttpRequestExecutor extends HttpRequestExecutor {
    /* access modifiers changed from: protected */
    public HttpResponse doSendRequest(HttpRequest request, HttpClientConnection conn, HttpContext context) throws IOException, HttpException {
        AWSRequestMetrics awsRequestMetrics = (AWSRequestMetrics) context.getAttribute(AWSRequestMetrics.class.getSimpleName());
        if (awsRequestMetrics == null) {
            return SdkHttpRequestExecutor.super.doSendRequest(request, conn, context);
        }
        awsRequestMetrics.startEvent((MetricType) Field.HttpClientSendRequestTime);
        try {
            return SdkHttpRequestExecutor.super.doSendRequest(request, conn, context);
        } finally {
            awsRequestMetrics.endEvent((MetricType) Field.HttpClientSendRequestTime);
        }
    }

    /* access modifiers changed from: protected */
    public HttpResponse doReceiveResponse(HttpRequest request, HttpClientConnection conn, HttpContext context) throws HttpException, IOException {
        AWSRequestMetrics awsRequestMetrics = (AWSRequestMetrics) context.getAttribute(AWSRequestMetrics.class.getSimpleName());
        if (awsRequestMetrics == null) {
            return SdkHttpRequestExecutor.super.doReceiveResponse(request, conn, context);
        }
        awsRequestMetrics.startEvent((MetricType) Field.HttpClientReceiveResponseTime);
        try {
            return SdkHttpRequestExecutor.super.doReceiveResponse(request, conn, context);
        } finally {
            awsRequestMetrics.endEvent((MetricType) Field.HttpClientReceiveResponseTime);
        }
    }
}
