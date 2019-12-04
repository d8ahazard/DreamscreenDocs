package com.amazonaws.services.lambda;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceClient;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.handlers.HandlerChainFactory;
import com.amazonaws.http.ExecutionContext;
import com.amazonaws.http.HttpClient;
import com.amazonaws.http.HttpResponseHandler;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.http.JsonResponseHandler;
import com.amazonaws.http.UrlHttpClient;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.metrics.MetricType;
import com.amazonaws.metrics.RequestMetricCollector;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.model.transform.EC2AccessDeniedExceptionUnmarshaller;
import com.amazonaws.services.lambda.model.transform.EC2ThrottledExceptionUnmarshaller;
import com.amazonaws.services.lambda.model.transform.EC2UnexpectedExceptionUnmarshaller;
import com.amazonaws.services.lambda.model.transform.ENILimitReachedExceptionUnmarshaller;
import com.amazonaws.services.lambda.model.transform.InvalidParameterValueExceptionUnmarshaller;
import com.amazonaws.services.lambda.model.transform.InvalidRequestContentExceptionUnmarshaller;
import com.amazonaws.services.lambda.model.transform.InvalidSecurityGroupIDExceptionUnmarshaller;
import com.amazonaws.services.lambda.model.transform.InvalidSubnetIDExceptionUnmarshaller;
import com.amazonaws.services.lambda.model.transform.InvalidZipFileExceptionUnmarshaller;
import com.amazonaws.services.lambda.model.transform.InvokeRequestMarshaller;
import com.amazonaws.services.lambda.model.transform.InvokeResultJsonUnmarshaller;
import com.amazonaws.services.lambda.model.transform.KMSAccessDeniedExceptionUnmarshaller;
import com.amazonaws.services.lambda.model.transform.KMSDisabledExceptionUnmarshaller;
import com.amazonaws.services.lambda.model.transform.KMSInvalidStateExceptionUnmarshaller;
import com.amazonaws.services.lambda.model.transform.KMSNotFoundExceptionUnmarshaller;
import com.amazonaws.services.lambda.model.transform.RequestTooLargeExceptionUnmarshaller;
import com.amazonaws.services.lambda.model.transform.ResourceNotFoundExceptionUnmarshaller;
import com.amazonaws.services.lambda.model.transform.ServiceExceptionUnmarshaller;
import com.amazonaws.services.lambda.model.transform.SubnetIPAddressLimitReachedExceptionUnmarshaller;
import com.amazonaws.services.lambda.model.transform.TooManyRequestsExceptionUnmarshaller;
import com.amazonaws.services.lambda.model.transform.UnsupportedMediaTypeExceptionUnmarshaller;
import com.amazonaws.transform.JsonErrorUnmarshaller;
import com.amazonaws.util.AWSRequestMetrics;
import com.amazonaws.util.AWSRequestMetrics.Field;
import java.util.ArrayList;
import java.util.List;

public class AWSLambdaClient extends AmazonWebServiceClient implements AWSLambda {
    private AWSCredentialsProvider awsCredentialsProvider;
    protected List<JsonErrorUnmarshaller> jsonErrorUnmarshallers;

    @Deprecated
    public AWSLambdaClient() {
        this((AWSCredentialsProvider) new DefaultAWSCredentialsProviderChain(), new ClientConfiguration());
    }

    @Deprecated
    public AWSLambdaClient(ClientConfiguration clientConfiguration) {
        this((AWSCredentialsProvider) new DefaultAWSCredentialsProviderChain(), clientConfiguration);
    }

    public AWSLambdaClient(AWSCredentials awsCredentials) {
        this(awsCredentials, new ClientConfiguration());
    }

    public AWSLambdaClient(AWSCredentials awsCredentials, ClientConfiguration clientConfiguration) {
        this((AWSCredentialsProvider) new StaticCredentialsProvider(awsCredentials), clientConfiguration);
    }

    public AWSLambdaClient(AWSCredentialsProvider awsCredentialsProvider2) {
        this(awsCredentialsProvider2, new ClientConfiguration());
    }

    public AWSLambdaClient(AWSCredentialsProvider awsCredentialsProvider2, ClientConfiguration clientConfiguration) {
        this(awsCredentialsProvider2, clientConfiguration, (HttpClient) new UrlHttpClient(clientConfiguration));
    }

    @Deprecated
    public AWSLambdaClient(AWSCredentialsProvider awsCredentialsProvider2, ClientConfiguration clientConfiguration, RequestMetricCollector requestMetricCollector) {
        super(adjustClientConfiguration(clientConfiguration), requestMetricCollector);
        this.awsCredentialsProvider = awsCredentialsProvider2;
        init();
    }

    public AWSLambdaClient(AWSCredentialsProvider awsCredentialsProvider2, ClientConfiguration clientConfiguration, HttpClient httpClient) {
        super(adjustClientConfiguration(clientConfiguration), httpClient);
        this.awsCredentialsProvider = awsCredentialsProvider2;
        init();
    }

    private void init() {
        this.jsonErrorUnmarshallers = new ArrayList();
        this.jsonErrorUnmarshallers.add(new EC2AccessDeniedExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new EC2ThrottledExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new EC2UnexpectedExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new ENILimitReachedExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new InvalidParameterValueExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new InvalidRequestContentExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new InvalidSecurityGroupIDExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new InvalidSubnetIDExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new InvalidZipFileExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new KMSAccessDeniedExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new KMSDisabledExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new KMSInvalidStateExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new KMSNotFoundExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new RequestTooLargeExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new ResourceNotFoundExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new ServiceExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new SubnetIPAddressLimitReachedExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new TooManyRequestsExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new UnsupportedMediaTypeExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new JsonErrorUnmarshaller());
        setEndpoint("lambda.us-east-1.amazonaws.com");
        this.endpointPrefix = "lambda";
        HandlerChainFactory chainFactory = new HandlerChainFactory();
        this.requestHandler2s.addAll(chainFactory.newRequestHandlerChain("/com/amazonaws/services/lambda/request.handlers"));
        this.requestHandler2s.addAll(chainFactory.newRequestHandler2Chain("/com/amazonaws/services/lambda/request.handler2s"));
    }

    private static ClientConfiguration adjustClientConfiguration(ClientConfiguration orig) {
        return orig;
    }

    public InvokeResult invoke(InvokeRequest invokeRequest) throws AmazonServiceException, AmazonClientException {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) invokeRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent((MetricType) Field.ClientExecuteTime);
        Request<InvokeRequest> request = null;
        Response<InvokeResult> response = null;
        try {
            awsRequestMetrics.startEvent((MetricType) Field.RequestMarshallTime);
            request = new InvokeRequestMarshaller().marshall(invokeRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) Field.RequestMarshallTime);
            response = invoke(request, new JsonResponseHandler<>(new InvokeResultJsonUnmarshaller<>()), executionContext);
            InvokeResult invokeResult = (InvokeResult) response.getAwsResponse();
            endClientExecution(awsRequestMetrics, request, response, true);
            return invokeResult;
        } catch (Throwable th) {
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    @Deprecated
    public ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest request) {
        return this.client.getResponseMetadataForRequest(request);
    }

    /* JADX INFO: finally extract failed */
    private <X, Y extends AmazonWebServiceRequest> Response<X> invoke(Request<Y> request, HttpResponseHandler<AmazonWebServiceResponse<X>> responseHandler, ExecutionContext executionContext) {
        request.setEndpoint(this.endpoint);
        request.setTimeOffset(this.timeOffset);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent((MetricType) Field.CredentialsRequestTime);
        try {
            AWSCredentials credentials = this.awsCredentialsProvider.getCredentials();
            awsRequestMetrics.endEvent((MetricType) Field.CredentialsRequestTime);
            AmazonWebServiceRequest originalRequest = request.getOriginalRequest();
            if (!(originalRequest == null || originalRequest.getRequestCredentials() == null)) {
                credentials = originalRequest.getRequestCredentials();
            }
            executionContext.setCredentials(credentials);
            return this.client.execute(request, responseHandler, new JsonErrorResponseHandler(this.jsonErrorUnmarshallers), executionContext);
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) Field.CredentialsRequestTime);
            throw th;
        }
    }
}
