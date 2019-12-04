package com.amazonaws.http;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonServiceException.ErrorType;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Request;
import com.amazonaws.RequestClientOptions;
import com.amazonaws.RequestClientOptions.Marker;
import com.amazonaws.Response;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.handlers.CredentialsRequestHandler;
import com.amazonaws.handlers.RequestHandler2;
import com.amazonaws.internal.CRC32MismatchException;
import com.amazonaws.metrics.MetricType;
import com.amazonaws.metrics.RequestMetricCollector;
import com.amazonaws.retry.RetryPolicy;
import com.amazonaws.util.AWSRequestMetrics;
import com.amazonaws.util.AWSRequestMetrics.Field;
import com.amazonaws.util.DateUtils;
import com.amazonaws.util.TimingInfo;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AmazonHttpClient {
    private static final String HEADER_SDK_RETRY_INFO = "aws-sdk-retry";
    private static final String HEADER_SDK_TRANSACTION_ID = "aws-sdk-invocation-id";
    private static final String HEADER_USER_AGENT = "User-Agent";
    private static final int HTTP_STATUS_MULTIPLE_CHOICES = 300;
    private static final int HTTP_STATUS_OK = 200;
    private static final int HTTP_STATUS_REQ_TOO_LONG = 413;
    private static final int HTTP_STATUS_SERVICE_UNAVAILABLE = 503;
    private static final int HTTP_STATUS_TEMP_REDIRECT = 307;
    private static final Log REQUEST_LOG = LogFactory.getLog("com.amazonaws.request");
    private static final int TIME_MILLISEC = 1000;
    static final Log log = LogFactory.getLog(AmazonHttpClient.class);
    final ClientConfiguration config;
    final HttpClient httpClient;
    private final HttpRequestFactory requestFactory;
    private final RequestMetricCollector requestMetricCollector;

    public AmazonHttpClient(ClientConfiguration config2) {
        this(config2, (HttpClient) new UrlHttpClient(config2));
    }

    @Deprecated
    public AmazonHttpClient(ClientConfiguration config2, RequestMetricCollector requestMetricCollector2) {
        this(config2, new UrlHttpClient(config2), requestMetricCollector2);
    }

    public AmazonHttpClient(ClientConfiguration config2, HttpClient httpClient2) {
        this.requestFactory = new HttpRequestFactory();
        this.config = config2;
        this.httpClient = httpClient2;
        this.requestMetricCollector = null;
    }

    @Deprecated
    public AmazonHttpClient(ClientConfiguration config2, HttpClient httpClient2, RequestMetricCollector requestMetricCollector2) {
        this.requestFactory = new HttpRequestFactory();
        this.config = config2;
        this.httpClient = httpClient2;
        this.requestMetricCollector = requestMetricCollector2;
    }

    @Deprecated
    public ResponseMetadata getResponseMetadataForRequest(AmazonWebServiceRequest request) {
        return null;
    }

    public <T> Response<T> execute(Request<?> request, HttpResponseHandler<AmazonWebServiceResponse<T>> responseHandler, HttpResponseHandler<AmazonServiceException> errorResponseHandler, ExecutionContext executionContext) {
        if (executionContext == null) {
            throw new AmazonClientException("Internal SDK Error: No execution context parameter specified.");
        }
        List<RequestHandler2> requestHandler2s = requestHandler2s(request, executionContext);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        Response<T> response = null;
        try {
            response = executeHelper(request, responseHandler, errorResponseHandler, executionContext);
            afterResponse(request, requestHandler2s, response, awsRequestMetrics.getTimingInfo().endTiming());
            return response;
        } catch (AmazonClientException e) {
            afterError(request, response, requestHandler2s, e);
            throw e;
        }
    }

    /* access modifiers changed from: 0000 */
    public void afterError(Request<?> request, Response<?> response, List<RequestHandler2> requestHandler2s, AmazonClientException e) {
        for (RequestHandler2 handler2 : requestHandler2s) {
            handler2.afterError(request, response, e);
        }
    }

    /* access modifiers changed from: 0000 */
    public <T> void afterResponse(Request<?> request, List<RequestHandler2> requestHandler2s, Response<T> response, TimingInfo timingInfo) {
        for (RequestHandler2 handler2 : requestHandler2s) {
            handler2.afterResponse(request, response);
        }
    }

    /* access modifiers changed from: 0000 */
    public List<RequestHandler2> requestHandler2s(Request<?> request, ExecutionContext executionContext) {
        List<RequestHandler2> requestHandler2s = executionContext.getRequestHandler2s();
        if (requestHandler2s == null) {
            return Collections.emptyList();
        }
        for (RequestHandler2 requestHandler2 : requestHandler2s) {
            if (requestHandler2 instanceof CredentialsRequestHandler) {
                ((CredentialsRequestHandler) requestHandler2).setCredentials(executionContext.getCredentials());
            }
            requestHandler2.beforeRequest(request);
        }
        return requestHandler2s;
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: type inference failed for: r33v0 */
    /* JADX WARNING: type inference failed for: r33v1 */
    /* JADX WARNING: type inference failed for: r33v2 */
    /* JADX WARNING: type inference failed for: r14v0, types: [java.lang.Throwable, com.amazonaws.AmazonClientException] */
    /* JADX WARNING: type inference failed for: r33v3 */
    /* JADX WARNING: type inference failed for: r33v4 */
    /* JADX WARNING: type inference failed for: r9v0, types: [java.lang.Throwable, com.amazonaws.AmazonServiceException, com.amazonaws.AmazonClientException, java.lang.Exception] */
    /* JADX WARNING: type inference failed for: r33v5 */
    /* JADX WARNING: type inference failed for: r33v6 */
    /* JADX WARNING: type inference failed for: r1v13, types: [com.amazonaws.AmazonClientException] */
    /* JADX WARNING: type inference failed for: r33v7 */
    /* JADX WARNING: type inference failed for: r33v8 */
    /* JADX WARNING: type inference failed for: r33v9 */
    /* JADX WARNING: type inference failed for: r33v10 */
    /* JADX WARNING: type inference failed for: r33v11 */
    /* JADX WARNING: type inference failed for: r33v12 */
    /* JADX WARNING: type inference failed for: r33v13 */
    /* JADX WARNING: type inference failed for: r33v14 */
    /* JADX WARNING: type inference failed for: r33v15 */
    /* JADX WARNING: type inference failed for: r33v16 */
    /* JADX WARNING: type inference failed for: r33v17 */
    /* JADX WARNING: type inference failed for: r33v18 */
    /* JADX WARNING: type inference failed for: r33v19 */
    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x0067, code lost:
        r33 = r33;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x0067, code lost:
        r33 = r33;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r33v2
  assigns: []
  uses: []
  mth insns count: 366
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 9 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> com.amazonaws.Response<T> executeHelper(com.amazonaws.Request<?> r37, com.amazonaws.http.HttpResponseHandler<com.amazonaws.AmazonWebServiceResponse<T>> r38, com.amazonaws.http.HttpResponseHandler<com.amazonaws.AmazonServiceException> r39, com.amazonaws.http.ExecutionContext r40) {
        /*
            r36 = this;
            r26 = 0
            com.amazonaws.util.AWSRequestMetrics r17 = r40.getAwsRequestMetrics()
            com.amazonaws.util.AWSRequestMetrics$Field r6 = com.amazonaws.util.AWSRequestMetrics.Field.ServiceName
            java.lang.String r7 = r37.getServiceName()
            r0 = r17
            r0.addProperty(r6, r7)
            com.amazonaws.util.AWSRequestMetrics$Field r6 = com.amazonaws.util.AWSRequestMetrics.Field.ServiceEndpoint
            java.net.URI r7 = r37.getEndpoint()
            r0 = r17
            r0.addProperty(r6, r7)
            r36.setUserAgent(r37)
            java.lang.String r6 = "aws-sdk-invocation-id"
            java.util.UUID r7 = java.util.UUID.randomUUID()
            java.lang.String r7 = r7.toString()
            r0 = r37
            r0.addHeader(r6, r7)
            r10 = 0
            r24 = 0
            r31 = 0
            r33 = 0
            java.util.LinkedHashMap r29 = new java.util.LinkedHashMap
            java.util.Map r6 = r37.getParameters()
            r0 = r29
            r0.<init>(r6)
            java.util.HashMap r28 = new java.util.HashMap
            java.util.Map r6 = r37.getHeaders()
            r0 = r28
            r0.<init>(r6)
            java.io.InputStream r27 = r37.getContent()
            if (r27 == 0) goto L_0x005d
            boolean r6 = r27.markSupported()
            if (r6 == 0) goto L_0x005d
            r6 = -1
            r0 = r27
            r0.mark(r6)
        L_0x005d:
            com.amazonaws.auth.AWSCredentials r19 = r40.getCredentials()
            r34 = 0
            r22 = 0
            r21 = 0
        L_0x0067:
            int r10 = r10 + 1
            com.amazonaws.util.AWSRequestMetrics$Field r6 = com.amazonaws.util.AWSRequestMetrics.Field.RequestCount
            long r12 = (long) r10
            r0 = r17
            r0.setCounter(r6, r12)
            r6 = 1
            if (r10 <= r6) goto L_0x0089
            r0 = r37
            r1 = r29
            r0.setParameters(r1)
            r0 = r37
            r1 = r28
            r0.setHeaders(r1)
            r0 = r37
            r1 = r27
            r0.setContent(r1)
        L_0x0089:
            if (r31 == 0) goto L_0x00c8
            java.net.URI r6 = r37.getEndpoint()
            if (r6 != 0) goto L_0x00c8
            java.lang.String r6 = r37.getResourcePath()
            if (r6 != 0) goto L_0x00c8
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = r31.getScheme()
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = "://"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = r31.getAuthority()
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            java.net.URI r6 = java.net.URI.create(r6)
            r0 = r37
            r0.setEndpoint(r6)
            java.lang.String r6 = r31.getPath()
            r0 = r37
            r0.setResourcePath(r6)
        L_0x00c8:
            r6 = 1
            if (r10 <= r6) goto L_0x00fc
            com.amazonaws.util.AWSRequestMetrics$Field r6 = com.amazonaws.util.AWSRequestMetrics.Field.RetryPauseTime     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r17
            r0.startEvent(r6)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            com.amazonaws.AmazonWebServiceRequest r6 = r37.getOriginalRequest()     // Catch:{ all -> 0x01d9 }
            r0 = r36
            com.amazonaws.ClientConfiguration r7 = r0.config     // Catch:{ all -> 0x01d9 }
            com.amazonaws.retry.RetryPolicy r7 = r7.getRetryPolicy()     // Catch:{ all -> 0x01d9 }
            r0 = r36
            r1 = r33
            long r24 = r0.pauseBeforeNextRetry(r6, r1, r10, r7)     // Catch:{ all -> 0x01d9 }
            com.amazonaws.util.AWSRequestMetrics$Field r6 = com.amazonaws.util.AWSRequestMetrics.Field.RetryPauseTime     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r17
            r0.endEvent(r6)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.io.InputStream r18 = r37.getContent()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            if (r18 == 0) goto L_0x00fc
            boolean r6 = r18.markSupported()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            if (r6 == 0) goto L_0x00fc
            r18.reset()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
        L_0x00fc:
            java.lang.String r6 = "aws-sdk-retry"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r7.<init>()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            int r8 = r10 + -1
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.lang.String r8 = "/"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r24
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.lang.String r7 = r7.toString()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r37
            r0.addHeader(r6, r7)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            if (r34 != 0) goto L_0x012a
            java.net.URI r6 = r37.getEndpoint()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r40
            com.amazonaws.auth.Signer r34 = r0.getSignerByURI(r6)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
        L_0x012a:
            if (r34 == 0) goto L_0x0145
            if (r19 == 0) goto L_0x0145
            com.amazonaws.util.AWSRequestMetrics$Field r6 = com.amazonaws.util.AWSRequestMetrics.Field.RequestSigningTime     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r17
            r0.startEvent(r6)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r34
            r1 = r37
            r2 = r19
            r0.sign(r1, r2)     // Catch:{ all -> 0x026c }
            com.amazonaws.util.AWSRequestMetrics$Field r6 = com.amazonaws.util.AWSRequestMetrics.Field.RequestSigningTime     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r17
            r0.endEvent(r6)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
        L_0x0145:
            org.apache.commons.logging.Log r6 = REQUEST_LOG     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            boolean r6 = r6.isDebugEnabled()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            if (r6 == 0) goto L_0x0169
            org.apache.commons.logging.Log r6 = REQUEST_LOG     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r7.<init>()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.lang.String r8 = "Sending Request: "
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.lang.String r8 = r37.toString()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.lang.String r7 = r7.toString()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r6.debug(r7)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
        L_0x0169:
            r0 = r36
            com.amazonaws.http.HttpRequestFactory r6 = r0.requestFactory     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r36
            com.amazonaws.ClientConfiguration r7 = r0.config     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r37
            r1 = r40
            com.amazonaws.http.HttpRequest r21 = r6.createHttpRequest(r0, r7, r1)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r33 = 0
            com.amazonaws.util.AWSRequestMetrics$Field r6 = com.amazonaws.util.AWSRequestMetrics.Field.HttpRequestTime     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r17
            r0.startEvent(r6)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r36
            com.amazonaws.http.HttpClient r6 = r0.httpClient     // Catch:{ all -> 0x0283 }
            r0 = r21
            com.amazonaws.http.HttpResponse r22 = r6.execute(r0)     // Catch:{ all -> 0x0283 }
            com.amazonaws.util.AWSRequestMetrics$Field r6 = com.amazonaws.util.AWSRequestMetrics.Field.HttpRequestTime     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r17
            r0.endEvent(r6)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r36
            r1 = r22
            boolean r6 = r0.isRequestSuccessful(r1)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            if (r6 == 0) goto L_0x02a6
            com.amazonaws.util.AWSRequestMetrics$Field r6 = com.amazonaws.util.AWSRequestMetrics.Field.StatusCode     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            int r7 = r22.getStatusCode()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r17
            r0.addProperty(r6, r7)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            boolean r26 = r38.needsConnectionLeftOpen()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r36
            r1 = r37
            r2 = r38
            r3 = r22
            r4 = r40
            java.lang.Object r32 = r0.handleResponse(r1, r2, r3, r4)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            com.amazonaws.Response r6 = new com.amazonaws.Response     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r32
            r1 = r22
            r6.<init>(r0, r1)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            if (r26 != 0) goto L_0x01d8
            if (r22 == 0) goto L_0x01d8
            java.io.InputStream r7 = r22.getRawContent()     // Catch:{ IOException -> 0x029a }
            if (r7 == 0) goto L_0x01d8
            java.io.InputStream r7 = r22.getRawContent()     // Catch:{ IOException -> 0x029a }
            r7.close()     // Catch:{ IOException -> 0x029a }
        L_0x01d8:
            return r6
        L_0x01d9:
            r6 = move-exception
            com.amazonaws.util.AWSRequestMetrics$Field r7 = com.amazonaws.util.AWSRequestMetrics.Field.RetryPauseTime     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r17
            r0.endEvent(r7)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            throw r6     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
        L_0x01e2:
            r23 = move-exception
            org.apache.commons.logging.Log r6 = log     // Catch:{ all -> 0x0259 }
            boolean r6 = r6.isDebugEnabled()     // Catch:{ all -> 0x0259 }
            if (r6 == 0) goto L_0x0209
            org.apache.commons.logging.Log r6 = log     // Catch:{ all -> 0x0259 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0259 }
            r7.<init>()     // Catch:{ all -> 0x0259 }
            java.lang.String r8 = "Unable to execute HTTP request: "
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0259 }
            java.lang.String r8 = r23.getMessage()     // Catch:{ all -> 0x0259 }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0259 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0259 }
            r0 = r23
            r6.debug(r7, r0)     // Catch:{ all -> 0x0259 }
        L_0x0209:
            com.amazonaws.util.AWSRequestMetrics$Field r6 = com.amazonaws.util.AWSRequestMetrics.Field.Exception     // Catch:{ all -> 0x0259 }
            r0 = r17
            r0.incrementCounter(r6)     // Catch:{ all -> 0x0259 }
            com.amazonaws.util.AWSRequestMetrics$Field r6 = com.amazonaws.util.AWSRequestMetrics.Field.Exception     // Catch:{ all -> 0x0259 }
            r0 = r17
            r1 = r23
            r0.addProperty(r6, r1)     // Catch:{ all -> 0x0259 }
            com.amazonaws.util.AWSRequestMetrics$Field r6 = com.amazonaws.util.AWSRequestMetrics.Field.AWSRequestID     // Catch:{ all -> 0x0259 }
            r7 = 0
            r0 = r17
            r0.addProperty(r6, r7)     // Catch:{ all -> 0x0259 }
            com.amazonaws.AmazonClientException r14 = new com.amazonaws.AmazonClientException     // Catch:{ all -> 0x0259 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0259 }
            r6.<init>()     // Catch:{ all -> 0x0259 }
            java.lang.String r7 = "Unable to execute HTTP request: "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x0259 }
            java.lang.String r7 = r23.getMessage()     // Catch:{ all -> 0x0259 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x0259 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0259 }
            r0 = r23
            r14.<init>(r6, r0)     // Catch:{ all -> 0x0259 }
            com.amazonaws.AmazonWebServiceRequest r12 = r37.getOriginalRequest()     // Catch:{ all -> 0x0259 }
            java.io.InputStream r13 = r21.getContent()     // Catch:{ all -> 0x0259 }
            r0 = r36
            com.amazonaws.ClientConfiguration r6 = r0.config     // Catch:{ all -> 0x0259 }
            com.amazonaws.retry.RetryPolicy r16 = r6.getRetryPolicy()     // Catch:{ all -> 0x0259 }
            r11 = r36
            r15 = r10
            boolean r6 = r11.shouldRetry(r12, r13, r14, r15, r16)     // Catch:{ all -> 0x0259 }
            if (r6 != 0) goto L_0x038b
            throw r14     // Catch:{ all -> 0x0259 }
        L_0x0259:
            r6 = move-exception
            if (r26 != 0) goto L_0x026b
            if (r22 == 0) goto L_0x026b
            java.io.InputStream r7 = r22.getRawContent()     // Catch:{ IOException -> 0x03b5 }
            if (r7 == 0) goto L_0x026b
            java.io.InputStream r7 = r22.getRawContent()     // Catch:{ IOException -> 0x03b5 }
            r7.close()     // Catch:{ IOException -> 0x03b5 }
        L_0x026b:
            throw r6
        L_0x026c:
            r6 = move-exception
            com.amazonaws.util.AWSRequestMetrics$Field r7 = com.amazonaws.util.AWSRequestMetrics.Field.RequestSigningTime     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r17
            r0.endEvent(r7)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            throw r6     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
        L_0x0275:
            r20 = move-exception
            r0 = r36
            r1 = r20
            r2 = r17
            java.lang.Throwable r6 = r0.handleUnexpectedFailure(r1, r2)     // Catch:{ all -> 0x0259 }
            java.lang.RuntimeException r6 = (java.lang.RuntimeException) r6     // Catch:{ all -> 0x0259 }
            throw r6     // Catch:{ all -> 0x0259 }
        L_0x0283:
            r6 = move-exception
            com.amazonaws.util.AWSRequestMetrics$Field r7 = com.amazonaws.util.AWSRequestMetrics.Field.HttpRequestTime     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r17
            r0.endEvent(r7)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            throw r6     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
        L_0x028c:
            r20 = move-exception
            r0 = r36
            r1 = r20
            r2 = r17
            java.lang.Throwable r6 = r0.handleUnexpectedFailure(r1, r2)     // Catch:{ all -> 0x0259 }
            java.lang.Error r6 = (java.lang.Error) r6     // Catch:{ all -> 0x0259 }
            throw r6     // Catch:{ all -> 0x0259 }
        L_0x029a:
            r20 = move-exception
            org.apache.commons.logging.Log r7 = log
            java.lang.String r8 = "Cannot close the response content."
            r0 = r20
            r7.warn(r8, r0)
            goto L_0x01d8
        L_0x02a6:
            boolean r6 = isTemporaryRedirect(r22)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            if (r6 == 0) goto L_0x0321
            java.util.Map r6 = r22.getHeaders()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.lang.String r7 = "Location"
            java.lang.Object r30 = r6.get(r7)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.lang.String r30 = (java.lang.String) r30     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            org.apache.commons.logging.Log r6 = log     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r7.<init>()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.lang.String r8 = "Redirecting to: "
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r30
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.lang.String r7 = r7.toString()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r6.debug(r7)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.net.URI r31 = java.net.URI.create(r30)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r6 = 0
            r0 = r37
            r0.setEndpoint(r6)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r6 = 0
            r0 = r37
            r0.setResourcePath(r6)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            com.amazonaws.util.AWSRequestMetrics$Field r6 = com.amazonaws.util.AWSRequestMetrics.Field.StatusCode     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            int r7 = r22.getStatusCode()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r17
            r0.addProperty(r6, r7)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            com.amazonaws.util.AWSRequestMetrics$Field r6 = com.amazonaws.util.AWSRequestMetrics.Field.RedirectLocation     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r17
            r1 = r30
            r0.addProperty(r6, r1)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            com.amazonaws.util.AWSRequestMetrics$Field r6 = com.amazonaws.util.AWSRequestMetrics.Field.AWSRequestID     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r7 = 0
            r0 = r17
            r0.addProperty(r6, r7)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
        L_0x0302:
            if (r26 != 0) goto L_0x0067
            if (r22 == 0) goto L_0x0067
            java.io.InputStream r6 = r22.getRawContent()     // Catch:{ IOException -> 0x0315 }
            if (r6 == 0) goto L_0x0067
            java.io.InputStream r6 = r22.getRawContent()     // Catch:{ IOException -> 0x0315 }
            r6.close()     // Catch:{ IOException -> 0x0315 }
            goto L_0x0067
        L_0x0315:
            r20 = move-exception
            org.apache.commons.logging.Log r6 = log
            java.lang.String r7 = "Cannot close the response content."
            r0 = r20
            r6.warn(r7, r0)
            goto L_0x0067
        L_0x0321:
            boolean r26 = r39.needsConnectionLeftOpen()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r36
            r1 = r37
            r2 = r39
            r3 = r22
            com.amazonaws.AmazonServiceException r9 = r0.handleErrorResponse(r1, r2, r3)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            com.amazonaws.util.AWSRequestMetrics$Field r6 = com.amazonaws.util.AWSRequestMetrics.Field.AWSRequestID     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.lang.String r7 = r9.getRequestId()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r17
            r0.addProperty(r6, r7)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            com.amazonaws.util.AWSRequestMetrics$Field r6 = com.amazonaws.util.AWSRequestMetrics.Field.AWSErrorCode     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.lang.String r7 = r9.getErrorCode()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r17
            r0.addProperty(r6, r7)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            com.amazonaws.util.AWSRequestMetrics$Field r6 = com.amazonaws.util.AWSRequestMetrics.Field.StatusCode     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            int r7 = r9.getStatusCode()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r17
            r0.addProperty(r6, r7)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            com.amazonaws.AmazonWebServiceRequest r7 = r37.getOriginalRequest()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            java.io.InputStream r8 = r21.getContent()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r0 = r36
            com.amazonaws.ClientConfiguration r6 = r0.config     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            com.amazonaws.retry.RetryPolicy r11 = r6.getRetryPolicy()     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            r6 = r36
            boolean r6 = r6.shouldRetry(r7, r8, r9, r10, r11)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            if (r6 != 0) goto L_0x036f
            throw r9     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
        L_0x036f:
            r33 = r9
            boolean r6 = com.amazonaws.retry.RetryUtils.isClockSkewError(r9)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            if (r6 == 0) goto L_0x0382
            r0 = r36
            r1 = r22
            int r35 = r0.parseClockSkewOffset(r1, r9)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            com.amazonaws.SDKGlobalConfiguration.setGlobalTimeOffset(r35)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
        L_0x0382:
            r0 = r36
            r1 = r37
            r0.resetRequestAfterError(r1, r9)     // Catch:{ IOException -> 0x01e2, RuntimeException -> 0x0275, Error -> 0x028c }
            goto L_0x0302
        L_0x038b:
            r33 = r14
            r0 = r36
            r1 = r37
            r2 = r23
            r0.resetRequestAfterError(r1, r2)     // Catch:{ all -> 0x0259 }
            if (r26 != 0) goto L_0x0067
            if (r22 == 0) goto L_0x0067
            java.io.InputStream r6 = r22.getRawContent()     // Catch:{ IOException -> 0x03a9 }
            if (r6 == 0) goto L_0x0067
            java.io.InputStream r6 = r22.getRawContent()     // Catch:{ IOException -> 0x03a9 }
            r6.close()     // Catch:{ IOException -> 0x03a9 }
            goto L_0x0067
        L_0x03a9:
            r20 = move-exception
            org.apache.commons.logging.Log r6 = log
            java.lang.String r7 = "Cannot close the response content."
            r0 = r20
            r6.warn(r7, r0)
            goto L_0x0067
        L_0x03b5:
            r20 = move-exception
            org.apache.commons.logging.Log r7 = log
            java.lang.String r8 = "Cannot close the response content."
            r0 = r20
            r7.warn(r8, r0)
            goto L_0x026b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.http.AmazonHttpClient.executeHelper(com.amazonaws.Request, com.amazonaws.http.HttpResponseHandler, com.amazonaws.http.HttpResponseHandler, com.amazonaws.http.ExecutionContext):com.amazonaws.Response");
    }

    private <T extends Throwable> T handleUnexpectedFailure(T t, AWSRequestMetrics awsRequestMetrics) {
        awsRequestMetrics.incrementCounter((MetricType) Field.Exception);
        awsRequestMetrics.addProperty((MetricType) Field.Exception, (Object) t);
        return t;
    }

    /* access modifiers changed from: 0000 */
    public void resetRequestAfterError(Request<?> request, Exception cause) {
        if (request.getContent() != null) {
            if (!request.getContent().markSupported()) {
                throw new AmazonClientException("Encountered an exception and stream is not resettable", cause);
            }
            try {
                request.getContent().reset();
            } catch (IOException e) {
                throw new AmazonClientException("Encountered an exception and couldn't reset the stream to retry", cause);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void setUserAgent(Request<?> request) {
        String userAgent = ClientConfiguration.DEFAULT_USER_AGENT;
        AmazonWebServiceRequest awsreq = request.getOriginalRequest();
        if (awsreq != null) {
            RequestClientOptions opts = awsreq.getRequestClientOptions();
            if (opts != null) {
                String userAgentMarker = opts.getClientMarker(Marker.USER_AGENT);
                if (userAgentMarker != null) {
                    userAgent = createUserAgentString(userAgent, userAgentMarker);
                }
            }
        }
        if (!ClientConfiguration.DEFAULT_USER_AGENT.equals(this.config.getUserAgent())) {
            userAgent = createUserAgentString(userAgent, this.config.getUserAgent());
        }
        request.addHeader("User-Agent", userAgent);
    }

    static String createUserAgentString(String existingUserAgentString, String userAgent) {
        return existingUserAgentString.contains(userAgent) ? existingUserAgentString : existingUserAgentString.trim() + " " + userAgent.trim();
    }

    public void shutdown() {
        this.httpClient.shutdown();
    }

    private boolean shouldRetry(AmazonWebServiceRequest originalRequest, InputStream inputStream, AmazonClientException exception, int requestCount, RetryPolicy retryPolicy) {
        int retries = requestCount - 1;
        int maxErrorRetry = this.config.getMaxErrorRetry();
        if (maxErrorRetry < 0 || !retryPolicy.isMaxErrorRetryInClientConfigHonored()) {
            maxErrorRetry = retryPolicy.getMaxErrorRetry();
        }
        if (retries >= maxErrorRetry) {
            return false;
        }
        if (inputStream == null || inputStream.markSupported()) {
            return retryPolicy.getRetryCondition().shouldRetry(originalRequest, exception, retries);
        }
        if (!log.isDebugEnabled()) {
            return false;
        }
        log.debug("Content not repeatable");
        return false;
    }

    private static boolean isTemporaryRedirect(HttpResponse response) {
        String location = (String) response.getHeaders().get(HttpHeader.LOCATION);
        return response.getStatusCode() == HTTP_STATUS_TEMP_REDIRECT && location != null && !location.isEmpty();
    }

    private boolean isRequestSuccessful(HttpResponse response) {
        int statusCode = response.getStatusCode();
        return statusCode >= 200 && statusCode < HTTP_STATUS_MULTIPLE_CHOICES;
    }

    /* access modifiers changed from: 0000 */
    public <T> T handleResponse(Request<?> request, HttpResponseHandler<AmazonWebServiceResponse<T>> responseHandler, HttpResponse response, ExecutionContext executionContext) throws IOException {
        AWSRequestMetrics awsRequestMetrics;
        try {
            awsRequestMetrics = executionContext.getAwsRequestMetrics();
            awsRequestMetrics.startEvent((MetricType) Field.ResponseProcessingTime);
            AmazonWebServiceResponse<? extends T> awsResponse = (AmazonWebServiceResponse) responseHandler.handle(response);
            awsRequestMetrics.endEvent((MetricType) Field.ResponseProcessingTime);
            if (awsResponse == null) {
                throw new RuntimeException("Unable to unmarshall response metadata. Response Code: " + response.getStatusCode() + ", Response Text: " + response.getStatusText());
            }
            if (REQUEST_LOG.isDebugEnabled()) {
                REQUEST_LOG.debug("Received successful response: " + response.getStatusCode() + ", AWS Request ID: " + awsResponse.getRequestId());
            }
            awsRequestMetrics.addProperty((MetricType) Field.AWSRequestID, (Object) awsResponse.getRequestId());
            return awsResponse.getResult();
        } catch (CRC32MismatchException e) {
            throw e;
        } catch (IOException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new AmazonClientException("Unable to unmarshall response (" + e3.getMessage() + "). Response Code: " + response.getStatusCode() + ", Response Text: " + response.getStatusText(), e3);
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) Field.ResponseProcessingTime);
            throw th;
        }
    }

    /* access modifiers changed from: 0000 */
    public AmazonServiceException handleErrorResponse(Request<?> request, HttpResponseHandler<AmazonServiceException> errorResponseHandler, HttpResponse response) throws IOException {
        AmazonServiceException exception;
        int status = response.getStatusCode();
        try {
            exception = (AmazonServiceException) errorResponseHandler.handle(response);
            REQUEST_LOG.debug("Received error response: " + exception.toString());
        } catch (Exception e) {
            if (status == HTTP_STATUS_REQ_TOO_LONG) {
                exception = new AmazonServiceException("Request entity too large");
                exception.setServiceName(request.getServiceName());
                exception.setStatusCode(HTTP_STATUS_REQ_TOO_LONG);
                exception.setErrorType(ErrorType.Client);
                exception.setErrorCode("Request entity too large");
            } else if (status == HTTP_STATUS_SERVICE_UNAVAILABLE && "Service Unavailable".equalsIgnoreCase(response.getStatusText())) {
                exception = new AmazonServiceException("Service unavailable");
                exception.setServiceName(request.getServiceName());
                exception.setStatusCode(HTTP_STATUS_SERVICE_UNAVAILABLE);
                exception.setErrorType(ErrorType.Service);
                exception.setErrorCode("Service unavailable");
            } else if (e instanceof IOException) {
                throw ((IOException) e);
            } else {
                throw new AmazonClientException("Unable to unmarshall error response (" + e.getMessage() + "). Response Code: " + status + ", Response Text: " + response.getStatusText(), e);
            }
        }
        exception.setStatusCode(status);
        exception.setServiceName(request.getServiceName());
        exception.fillInStackTrace();
        return exception;
    }

    private long pauseBeforeNextRetry(AmazonWebServiceRequest originalRequest, AmazonClientException previousException, int requestCount, RetryPolicy retryPolicy) {
        int retries = (requestCount - 1) - 1;
        long delay = retryPolicy.getBackoffStrategy().delayBeforeNextRetry(originalRequest, previousException, retries);
        if (log.isDebugEnabled()) {
            log.debug("Retriable error detected, will retry in " + delay + "ms, attempt number: " + retries);
        }
        try {
            Thread.sleep(delay);
            return delay;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AmazonClientException(e.getMessage(), e);
        }
    }

    private String getServerDateFromException(String body) {
        int endPos;
        int startPos = body.indexOf("(");
        if (body.contains(" + 15")) {
            endPos = body.indexOf(" + 15");
        } else {
            endPos = body.indexOf(" - 15");
        }
        return body.substring(startPos + 1, endPos);
    }

    /* access modifiers changed from: 0000 */
    public int parseClockSkewOffset(HttpResponse response, AmazonServiceException exception) {
        Date serverDate;
        Date deviceDate = new Date();
        String serverDateStr = null;
        String responseDateHeader = (String) response.getHeaders().get(HttpHeader.DATE);
        if (responseDateHeader != null) {
            try {
                if (!responseDateHeader.isEmpty()) {
                    serverDateStr = responseDateHeader;
                    serverDate = DateUtils.parseRFC822Date(serverDateStr);
                    return (int) ((deviceDate.getTime() - serverDate.getTime()) / 1000);
                }
            } catch (RuntimeException e) {
                log.warn("Unable to parse clock skew offset from response: " + serverDateStr, e);
                return 0;
            }
        }
        serverDate = DateUtils.parseCompressedISO8601Date(getServerDateFromException(exception.getMessage()));
        return (int) ((deviceDate.getTime() - serverDate.getTime()) / 1000);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        shutdown();
        super.finalize();
    }

    public RequestMetricCollector getRequestMetricCollector() {
        return this.requestMetricCollector;
    }
}
