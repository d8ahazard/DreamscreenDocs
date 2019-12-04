package com.amazonaws.http;

import com.amazonaws.AmazonWebServiceClient;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.Signer;
import com.amazonaws.handlers.RequestHandler2;
import com.amazonaws.util.AWSRequestMetrics;
import com.amazonaws.util.AWSRequestMetricsFullSupport;
import java.net.URI;
import java.util.List;

public class ExecutionContext {
    private final AmazonWebServiceClient awsClient;
    private final AWSRequestMetrics awsRequestMetrics;
    private String contextUserAgent;
    private AWSCredentials credentials;
    private final List<RequestHandler2> requestHandler2s;

    @Deprecated
    public ExecutionContext(boolean isMetricEnabled) {
        this(null, isMetricEnabled, null);
    }

    public ExecutionContext() {
        this(null, false, null);
    }

    public ExecutionContext(List<RequestHandler2> requestHandler2s2, boolean isMetricEnabled, AmazonWebServiceClient awsClient2) {
        this.requestHandler2s = requestHandler2s2;
        this.awsRequestMetrics = isMetricEnabled ? new AWSRequestMetricsFullSupport() : new AWSRequestMetrics();
        this.awsClient = awsClient2;
    }

    public String getContextUserAgent() {
        return this.contextUserAgent;
    }

    public void setContextUserAgent(String contextUserAgent2) {
        this.contextUserAgent = contextUserAgent2;
    }

    public List<RequestHandler2> getRequestHandler2s() {
        return this.requestHandler2s;
    }

    @Deprecated
    public AWSRequestMetrics getAwsRequestMetrics() {
        return this.awsRequestMetrics;
    }

    public void setSigner(Signer signer) {
    }

    public Signer getSignerByURI(URI uri) {
        if (this.awsClient == null) {
            return null;
        }
        return this.awsClient.getSignerByURI(uri);
    }

    public AWSCredentials getCredentials() {
        return this.credentials;
    }

    public void setCredentials(AWSCredentials credentials2) {
        this.credentials = credentials2;
    }
}
