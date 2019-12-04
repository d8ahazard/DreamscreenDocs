package com.amazonaws;

import com.amazonaws.http.HttpMethodName;
import com.amazonaws.util.AWSRequestMetrics;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultRequest<T> implements Request<T> {
    private InputStream content;
    private URI endpoint;
    private final Map<String, String> headers;
    private HttpMethodName httpMethod;
    private AWSRequestMetrics metrics;
    private final AmazonWebServiceRequest originalRequest;
    private final Map<String, String> parameters;
    private String resourcePath;
    private String serviceName;
    private boolean streaming;
    private int timeOffset;

    public DefaultRequest(AmazonWebServiceRequest originalRequest2, String serviceName2) {
        this.streaming = false;
        this.parameters = new LinkedHashMap();
        this.headers = new HashMap();
        this.httpMethod = HttpMethodName.POST;
        this.serviceName = serviceName2;
        this.originalRequest = originalRequest2;
    }

    public DefaultRequest(String serviceName2) {
        this(null, serviceName2);
    }

    public AmazonWebServiceRequest getOriginalRequest() {
        return this.originalRequest;
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public void setResourcePath(String resourcePath2) {
        this.resourcePath = resourcePath2;
    }

    public String getResourcePath() {
        return this.resourcePath;
    }

    public void addParameter(String name, String value) {
        this.parameters.put(name, value);
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public Request<T> withParameter(String name, String value) {
        addParameter(name, value);
        return this;
    }

    public HttpMethodName getHttpMethod() {
        return this.httpMethod;
    }

    public void setHttpMethod(HttpMethodName httpMethod2) {
        this.httpMethod = httpMethod2;
    }

    public void setEndpoint(URI endpoint2) {
        this.endpoint = endpoint2;
    }

    public URI getEndpoint() {
        return this.endpoint;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public InputStream getContent() {
        return this.content;
    }

    public void setContent(InputStream content2) {
        this.content = content2;
    }

    public void setHeaders(Map<String, String> headers2) {
        this.headers.clear();
        this.headers.putAll(headers2);
    }

    public void setParameters(Map<String, String> parameters2) {
        this.parameters.clear();
        this.parameters.putAll(parameters2);
    }

    public int getTimeOffset() {
        return this.timeOffset;
    }

    public void setTimeOffset(int timeOffset2) {
        this.timeOffset = timeOffset2;
    }

    public Request<T> withTimeOffset(int timeOffset2) {
        setTimeOffset(timeOffset2);
        return this;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getHttpMethod()).append(" ");
        builder.append(getEndpoint()).append(" ");
        String resourcePath2 = getResourcePath();
        if (resourcePath2 == null) {
            builder.append("/");
        } else {
            if (!resourcePath2.startsWith("/")) {
                builder.append("/");
            }
            builder.append(resourcePath2);
        }
        builder.append(" ");
        if (!getParameters().isEmpty()) {
            builder.append("Parameters: (");
            for (String key : getParameters().keySet()) {
                builder.append(key).append(": ").append((String) getParameters().get(key)).append(", ");
            }
            builder.append(") ");
        }
        if (!getHeaders().isEmpty()) {
            builder.append("Headers: (");
            for (String key2 : getHeaders().keySet()) {
                builder.append(key2).append(": ").append((String) getHeaders().get(key2)).append(", ");
            }
            builder.append(") ");
        }
        return builder.toString();
    }

    @Deprecated
    public AWSRequestMetrics getAWSRequestMetrics() {
        return this.metrics;
    }

    @Deprecated
    public void setAWSRequestMetrics(AWSRequestMetrics metrics2) {
        if (this.metrics == null) {
            this.metrics = metrics2;
            return;
        }
        throw new IllegalStateException("AWSRequestMetrics has already been set on this request");
    }

    public boolean isStreaming() {
        return this.streaming;
    }

    public void setStreaming(boolean streaming2) {
        this.streaming = streaming2;
    }
}
