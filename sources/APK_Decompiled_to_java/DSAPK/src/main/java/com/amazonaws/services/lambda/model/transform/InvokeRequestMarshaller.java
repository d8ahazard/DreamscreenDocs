package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.BinaryUtils;
import com.amazonaws.util.StringUtils;

public class InvokeRequestMarshaller implements Marshaller<Request<InvokeRequest>, InvokeRequest> {
    public Request<InvokeRequest> marshall(InvokeRequest invokeRequest) {
        String fromString;
        if (invokeRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(InvokeRequest)");
        }
        Request<InvokeRequest> request = new DefaultRequest<>(invokeRequest, "AWSLambda");
        request.setHttpMethod(HttpMethodName.POST);
        if (invokeRequest.getInvocationType() != null) {
            request.addHeader("X-Amz-Invocation-Type", StringUtils.fromString(invokeRequest.getInvocationType()));
        }
        if (invokeRequest.getLogType() != null) {
            request.addHeader("X-Amz-Log-Type", StringUtils.fromString(invokeRequest.getLogType()));
        }
        if (invokeRequest.getClientContext() != null) {
            request.addHeader("X-Amz-Client-Context", StringUtils.fromString(invokeRequest.getClientContext()));
        }
        String uriResourcePath = "/2015-03-31/functions/{FunctionName}/invocations";
        String str = "{FunctionName}";
        if (invokeRequest.getFunctionName() == null) {
            fromString = "";
        } else {
            fromString = StringUtils.fromString(invokeRequest.getFunctionName());
        }
        String uriResourcePath2 = uriResourcePath.replace(str, fromString);
        if (invokeRequest.getQualifier() != null) {
            request.addParameter("Qualifier", StringUtils.fromString(invokeRequest.getQualifier()));
        }
        request.setResourcePath(uriResourcePath2);
        request.addHeader(HttpHeader.CONTENT_LENGTH, Integer.toString(invokeRequest.getPayload().remaining()));
        request.setContent(BinaryUtils.toStream(invokeRequest.getPayload()));
        if (!request.getHeaders().containsKey("Content-Type")) {
            request.addHeader("Content-Type", "application/x-amz-json-1.0");
        }
        return request;
    }
}
