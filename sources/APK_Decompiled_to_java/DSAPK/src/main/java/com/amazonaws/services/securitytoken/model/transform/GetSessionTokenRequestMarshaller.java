package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

public class GetSessionTokenRequestMarshaller implements Marshaller<Request<GetSessionTokenRequest>, GetSessionTokenRequest> {
    public Request<GetSessionTokenRequest> marshall(GetSessionTokenRequest getSessionTokenRequest) {
        if (getSessionTokenRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetSessionTokenRequest)");
        }
        Request<GetSessionTokenRequest> request = new DefaultRequest<>(getSessionTokenRequest, "AWSSecurityTokenService");
        request.addParameter(JsonDocumentFields.ACTION, "GetSessionToken");
        request.addParameter(JsonDocumentFields.VERSION, "2011-06-15");
        if (getSessionTokenRequest.getDurationSeconds() != null) {
            request.addParameter("DurationSeconds", StringUtils.fromInteger(getSessionTokenRequest.getDurationSeconds()));
        }
        if (getSessionTokenRequest.getSerialNumber() != null) {
            request.addParameter("SerialNumber", StringUtils.fromString(getSessionTokenRequest.getSerialNumber()));
        }
        if (getSessionTokenRequest.getTokenCode() != null) {
            request.addParameter("TokenCode", StringUtils.fromString(getSessionTokenRequest.getTokenCode()));
        }
        return request;
    }
}
