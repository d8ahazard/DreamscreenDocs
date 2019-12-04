package com.amazonaws;

import com.amazonaws.auth.RegionAwareSigner;
import com.amazonaws.auth.Signer;
import com.amazonaws.auth.SignerFactory;
import com.amazonaws.handlers.RequestHandler;
import com.amazonaws.handlers.RequestHandler2;
import com.amazonaws.http.AmazonHttpClient;
import com.amazonaws.http.ExecutionContext;
import com.amazonaws.http.HttpClient;
import com.amazonaws.http.UrlHttpClient;
import com.amazonaws.metrics.AwsSdkMetrics;
import com.amazonaws.metrics.MetricType;
import com.amazonaws.metrics.RequestMetricCollector;
import com.amazonaws.regions.Region;
import com.amazonaws.util.AWSRequestMetrics;
import com.amazonaws.util.AWSRequestMetrics.Field;
import com.amazonaws.util.AwsHostNameUtils;
import com.amazonaws.util.Classes;
import com.amazonaws.util.StringUtils;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AmazonWebServiceClient {
    private static final String AMAZON = "Amazon";
    private static final String AWS = "AWS";
    private static final Log LOG = LogFactory.getLog(AmazonWebServiceClient.class);
    public static final boolean LOGGING_AWS_REQUEST_METRIC = true;
    protected AmazonHttpClient client;
    protected ClientConfiguration clientConfiguration;
    protected volatile URI endpoint;
    protected volatile String endpointPrefix;
    protected final List<RequestHandler2> requestHandler2s;
    private volatile String serviceName;
    private volatile Signer signer;
    private volatile String signerRegionOverride;
    protected int timeOffset;

    protected AmazonWebServiceClient(ClientConfiguration clientConfiguration2) {
        this(clientConfiguration2, (HttpClient) new UrlHttpClient(clientConfiguration2));
    }

    @Deprecated
    protected AmazonWebServiceClient(ClientConfiguration clientConfiguration2, RequestMetricCollector requestMetricCollector) {
        this(clientConfiguration2, new UrlHttpClient(clientConfiguration2), null);
    }

    protected AmazonWebServiceClient(ClientConfiguration clientConfiguration2, HttpClient httpClient) {
        this.clientConfiguration = clientConfiguration2;
        this.client = new AmazonHttpClient(clientConfiguration2, httpClient);
        this.requestHandler2s = new CopyOnWriteArrayList();
    }

    @Deprecated
    protected AmazonWebServiceClient(ClientConfiguration clientConfiguration2, HttpClient httpClient, RequestMetricCollector requestMetricCollector) {
        this.clientConfiguration = clientConfiguration2;
        this.client = new AmazonHttpClient(clientConfiguration2, httpClient, requestMetricCollector);
        this.requestHandler2s = new CopyOnWriteArrayList();
    }

    /* access modifiers changed from: protected */
    public Signer getSigner() {
        return this.signer;
    }

    public void setEndpoint(String endpoint2) {
        URI uri = toURI(endpoint2);
        Signer signer2 = computeSignerByURI(uri, this.signerRegionOverride, false);
        synchronized (this) {
            this.endpoint = uri;
            this.signer = signer2;
        }
    }

    public String getEndpoint() {
        String uri;
        synchronized (this) {
            uri = this.endpoint.toString();
        }
        return uri;
    }

    public String getEndpointPrefix() {
        return this.endpointPrefix;
    }

    private URI toURI(String endpoint2) {
        if (!endpoint2.contains("://")) {
            endpoint2 = this.clientConfiguration.getProtocol().toString() + "://" + endpoint2;
        }
        try {
            return new URI(endpoint2);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Deprecated
    public void setEndpoint(String endpoint2, String serviceName2, String regionId) {
        URI uri = toURI(endpoint2);
        Signer signer2 = computeSignerByServiceRegion(serviceName2, regionId, regionId, true);
        synchronized (this) {
            this.signer = signer2;
            this.endpoint = uri;
            this.signerRegionOverride = regionId;
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void configSigner(URI uri) {
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void configSigner(String serviceName2, String regionId) {
    }

    public Signer getSignerByURI(URI uri) {
        return computeSignerByURI(uri, this.signerRegionOverride, true);
    }

    private Signer computeSignerByURI(URI uri, String signerRegionOverride2, boolean isRegionIdAsSignerParam) {
        if (uri == null) {
            throw new IllegalArgumentException("Endpoint is not set. Use setEndpoint to set an endpoint before performing any request.");
        }
        String service = getServiceNameIntern();
        return computeSignerByServiceRegion(service, AwsHostNameUtils.parseRegionName(uri.getHost(), service), signerRegionOverride2, isRegionIdAsSignerParam);
    }

    private Signer computeSignerByServiceRegion(String serviceName2, String regionId, String signerRegionOverride2, boolean isRegionIdAsSignerParam) {
        Signer signer2;
        String signerType = this.clientConfiguration.getSignerOverride();
        if (signerType == null) {
            signer2 = SignerFactory.getSigner(serviceName2, regionId);
        } else {
            signer2 = SignerFactory.getSignerByTypeAndService(signerType, serviceName2);
        }
        if (signer2 instanceof RegionAwareSigner) {
            RegionAwareSigner regionAwareSigner = (RegionAwareSigner) signer2;
            if (signerRegionOverride2 != null) {
                regionAwareSigner.setRegionName(signerRegionOverride2);
            } else if (regionId != null && isRegionIdAsSignerParam) {
                regionAwareSigner.setRegionName(regionId);
            }
        }
        return signer2;
    }

    public void setRegion(Region region) {
        String serviceEndpoint;
        if (region == null) {
            throw new IllegalArgumentException("No region provided");
        }
        String serviceName2 = getServiceNameIntern();
        if (region.isServiceSupported(serviceName2)) {
            serviceEndpoint = region.getServiceEndpoint(serviceName2);
            int protocolIdx = serviceEndpoint.indexOf("://");
            if (protocolIdx >= 0) {
                serviceEndpoint = serviceEndpoint.substring("://".length() + protocolIdx);
            }
        } else {
            serviceEndpoint = String.format("%s.%s.%s", new Object[]{getEndpointPrefix(), region.getName(), region.getDomain()});
        }
        URI uri = toURI(serviceEndpoint);
        Signer signer2 = computeSignerByServiceRegion(serviceName2, region.getName(), this.signerRegionOverride, false);
        synchronized (this) {
            this.endpoint = uri;
            this.signer = signer2;
        }
    }

    @Deprecated
    public void setConfiguration(ClientConfiguration clientConfiguration2) {
        AmazonHttpClient existingClient = this.client;
        RequestMetricCollector requestMetricCollector = null;
        if (existingClient != null) {
            requestMetricCollector = existingClient.getRequestMetricCollector();
            existingClient.shutdown();
        }
        this.clientConfiguration = clientConfiguration2;
        this.client = new AmazonHttpClient(clientConfiguration2, requestMetricCollector);
    }

    public void shutdown() {
        this.client.shutdown();
    }

    @Deprecated
    public void addRequestHandler(RequestHandler requestHandler) {
        this.requestHandler2s.add(RequestHandler2.adapt(requestHandler));
    }

    public void addRequestHandler(RequestHandler2 requestHandler2) {
        this.requestHandler2s.add(requestHandler2);
    }

    @Deprecated
    public void removeRequestHandler(RequestHandler requestHandler) {
        this.requestHandler2s.remove(RequestHandler2.adapt(requestHandler));
    }

    public void removeRequestHandler(RequestHandler2 requestHandler2) {
        this.requestHandler2s.remove(requestHandler2);
    }

    /* access modifiers changed from: protected */
    public ExecutionContext createExecutionContext(AmazonWebServiceRequest req) {
        return new ExecutionContext(this.requestHandler2s, isRequestMetricsEnabled(req) || isProfilingEnabled(), this);
    }

    /* access modifiers changed from: protected */
    public final ExecutionContext createExecutionContext(Request<?> req) {
        return createExecutionContext(req.getOriginalRequest());
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public final ExecutionContext createExecutionContext() {
        return new ExecutionContext(this.requestHandler2s, isRMCEnabledAtClientOrSdkLevel() || isProfilingEnabled(), this);
    }

    @Deprecated
    protected static boolean isProfilingEnabled() {
        return System.getProperty(SDKGlobalConfiguration.PROFILING_SYSTEM_PROPERTY) != null;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public final boolean isRequestMetricsEnabled(AmazonWebServiceRequest req) {
        RequestMetricCollector c = req.getRequestMetricCollector();
        if (c == null || !c.isEnabled()) {
            return isRMCEnabledAtClientOrSdkLevel();
        }
        return true;
    }

    @Deprecated
    private boolean isRMCEnabledAtClientOrSdkLevel() {
        RequestMetricCollector c = requestMetricCollector();
        return c != null && c.isEnabled();
    }

    public void setTimeOffset(int timeOffset2) {
        this.timeOffset = timeOffset2;
    }

    public AmazonWebServiceClient withTimeOffset(int timeOffset2) {
        setTimeOffset(timeOffset2);
        return this;
    }

    public int getTimeOffset() {
        return this.timeOffset;
    }

    @Deprecated
    public RequestMetricCollector getRequestMetricsCollector() {
        return this.client.getRequestMetricCollector();
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public RequestMetricCollector requestMetricCollector() {
        RequestMetricCollector mc = this.client.getRequestMetricCollector();
        return mc == null ? AwsSdkMetrics.getRequestMetricCollector() : mc;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public final RequestMetricCollector findRequestMetricCollector(Request<?> req) {
        RequestMetricCollector mc = req.getOriginalRequest().getRequestMetricCollector();
        if (mc != null) {
            return mc;
        }
        RequestMetricCollector mc2 = getRequestMetricsCollector();
        return mc2 == null ? AwsSdkMetrics.getRequestMetricCollector() : mc2;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public final void endClientExecution(AWSRequestMetrics awsRequestMetrics, Request<?> request, Response<?> response) {
        endClientExecution(awsRequestMetrics, request, response, false);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public final void endClientExecution(AWSRequestMetrics awsRequestMetrics, Request<?> request, Response<?> response, boolean loggingAwsRequestMetrics) {
        if (request != null) {
            awsRequestMetrics.endEvent((MetricType) Field.ClientExecuteTime);
            awsRequestMetrics.getTimingInfo().endTiming();
            findRequestMetricCollector(request).collectMetrics(request, response);
        }
        if (loggingAwsRequestMetrics) {
            awsRequestMetrics.log();
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public String getServiceAbbreviation() {
        return getServiceNameIntern();
    }

    public String getServiceName() {
        return getServiceNameIntern();
    }

    /* access modifiers changed from: protected */
    public String getServiceNameIntern() {
        if (this.serviceName == null) {
            synchronized (this) {
                if (this.serviceName == null) {
                    this.serviceName = computeServiceName();
                    String str = this.serviceName;
                    return str;
                }
            }
        }
        return this.serviceName;
    }

    public final void setServiceNameIntern(String serviceName2) {
        this.serviceName = serviceName2;
    }

    private String computeServiceName() {
        int len;
        String httpClientName = Classes.childClassOf(AmazonWebServiceClient.class, this).getSimpleName();
        String service = ServiceNameFactory.getServiceName(httpClientName);
        if (service != null) {
            return service;
        }
        int j = httpClientName.indexOf("JavaClient");
        if (j == -1) {
            j = httpClientName.indexOf("Client");
            if (j == -1) {
                throw new IllegalStateException("Unrecognized suffix for the AWS http client class name " + httpClientName);
            }
        }
        int i = httpClientName.indexOf(AMAZON);
        if (i == -1) {
            i = httpClientName.indexOf(AWS);
            if (i == -1) {
                throw new IllegalStateException("Unrecognized prefix for the AWS http client class name " + httpClientName);
            }
            len = AWS.length();
        } else {
            len = AMAZON.length();
        }
        if (i < j) {
            return StringUtils.lowerCase(httpClientName.substring(i + len, j));
        }
        throw new IllegalStateException("Unrecognized AWS http client class name " + httpClientName);
    }

    public final String getSignerRegionOverride() {
        return this.signerRegionOverride;
    }

    public final void setSignerRegionOverride(String signerRegionOverride2) {
        Signer signer2 = computeSignerByURI(this.endpoint, signerRegionOverride2, true);
        synchronized (this) {
            this.signer = signer2;
            this.signerRegionOverride = signerRegionOverride2;
        }
    }
}
