package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

public class GetOpenIdTokenRequestMarshaller implements Marshaller<Request<GetOpenIdTokenRequest>, GetOpenIdTokenRequest> {
    public Request<GetOpenIdTokenRequest> marshall(GetOpenIdTokenRequest getOpenIdTokenRequest) {
        if (getOpenIdTokenRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetOpenIdTokenRequest)");
        }
        Request<GetOpenIdTokenRequest> request = new DefaultRequest<>(getOpenIdTokenRequest, "AmazonCognitoIdentity");
        request.addHeader("X-Amz-Target", "AWSCognitoIdentityService.GetOpenIdToken");
        request.setHttpMethod(HttpMethodName.POST);
        request.setResourcePath("/");
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            jsonWriter.beginObject();
            if (getOpenIdTokenRequest.getIdentityId() != null) {
                String identityId = getOpenIdTokenRequest.getIdentityId();
                jsonWriter.name("IdentityId");
                jsonWriter.value(identityId);
            }
            if (getOpenIdTokenRequest.getLogins() != null) {
                Map<String, String> logins = getOpenIdTokenRequest.getLogins();
                jsonWriter.name("Logins");
                jsonWriter.beginObject();
                for (Entry<String, String> loginsEntry : logins.entrySet()) {
                    String loginsValue = (String) loginsEntry.getValue();
                    if (loginsValue != null) {
                        jsonWriter.name((String) loginsEntry.getKey());
                        jsonWriter.value(loginsValue);
                    }
                }
                jsonWriter.endObject();
            }
            jsonWriter.endObject();
            jsonWriter.close();
            String snippet = stringWriter.toString();
            byte[] content = snippet.getBytes(StringUtils.UTF8);
            request.setContent(new StringInputStream(snippet));
            request.addHeader(HttpHeader.CONTENT_LENGTH, Integer.toString(content.length));
            if (!request.getHeaders().containsKey("Content-Type")) {
                request.addHeader("Content-Type", "application/x-amz-json-1.1");
            }
            return request;
        } catch (Throwable t) {
            throw new AmazonClientException("Unable to marshall request to JSON: " + t.getMessage(), t);
        }
    }
}
