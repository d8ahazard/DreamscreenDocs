package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.securitytoken.model.GetFederationTokenRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

public class GetFederationTokenRequestMarshaller implements Marshaller<Request<GetFederationTokenRequest>, GetFederationTokenRequest> {
    public Request<GetFederationTokenRequest> marshall(GetFederationTokenRequest getFederationTokenRequest) {
        if (getFederationTokenRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetFederationTokenRequest)");
        }
        Request<GetFederationTokenRequest> request = new DefaultRequest<>(getFederationTokenRequest, "AWSSecurityTokenService");
        request.addParameter(JsonDocumentFields.ACTION, "GetFederationToken");
        request.addParameter(JsonDocumentFields.VERSION, "2011-06-15");
        if (getFederationTokenRequest.getName() != null) {
            request.addParameter("Name", StringUtils.fromString(getFederationTokenRequest.getName()));
        }
        if (getFederationTokenRequest.getPolicy() != null) {
            request.addParameter("Policy", StringUtils.fromString(getFederationTokenRequest.getPolicy()));
        }
        if (getFederationTokenRequest.getDurationSeconds() != null) {
            request.addParameter("DurationSeconds", StringUtils.fromInteger(getFederationTokenRequest.getDurationSeconds()));
        }
        return request;
    }
}
