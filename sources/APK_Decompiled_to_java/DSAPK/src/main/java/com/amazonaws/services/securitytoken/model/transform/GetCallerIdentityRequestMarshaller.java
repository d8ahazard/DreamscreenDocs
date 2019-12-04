package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.securitytoken.model.GetCallerIdentityRequest;
import com.amazonaws.transform.Marshaller;

public class GetCallerIdentityRequestMarshaller implements Marshaller<Request<GetCallerIdentityRequest>, GetCallerIdentityRequest> {
    public Request<GetCallerIdentityRequest> marshall(GetCallerIdentityRequest getCallerIdentityRequest) {
        if (getCallerIdentityRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetCallerIdentityRequest)");
        }
        Request<GetCallerIdentityRequest> request = new DefaultRequest<>(getCallerIdentityRequest, "AWSSecurityTokenService");
        request.addParameter(JsonDocumentFields.ACTION, "GetCallerIdentity");
        request.addParameter(JsonDocumentFields.VERSION, "2011-06-15");
        return request;
    }
}
