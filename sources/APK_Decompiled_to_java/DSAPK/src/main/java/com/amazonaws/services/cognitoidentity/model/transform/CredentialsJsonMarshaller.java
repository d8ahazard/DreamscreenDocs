package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Date;

class CredentialsJsonMarshaller {
    private static CredentialsJsonMarshaller instance;

    CredentialsJsonMarshaller() {
    }

    public void marshall(Credentials credentials, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (credentials.getAccessKeyId() != null) {
            String accessKeyId = credentials.getAccessKeyId();
            jsonWriter.name("AccessKeyId");
            jsonWriter.value(accessKeyId);
        }
        if (credentials.getSecretKey() != null) {
            String secretKey = credentials.getSecretKey();
            jsonWriter.name("SecretKey");
            jsonWriter.value(secretKey);
        }
        if (credentials.getSessionToken() != null) {
            String sessionToken = credentials.getSessionToken();
            jsonWriter.name("SessionToken");
            jsonWriter.value(sessionToken);
        }
        if (credentials.getExpiration() != null) {
            Date expiration = credentials.getExpiration();
            jsonWriter.name("Expiration");
            jsonWriter.value(expiration);
        }
        jsonWriter.endObject();
    }

    public static CredentialsJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new CredentialsJsonMarshaller();
        }
        return instance;
    }
}
