package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers.DateJsonUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class CredentialsJsonUnmarshaller implements Unmarshaller<Credentials, JsonUnmarshallerContext> {
    private static CredentialsJsonUnmarshaller instance;

    CredentialsJsonUnmarshaller() {
    }

    public Credentials unmarshall(JsonUnmarshallerContext context) throws Exception {
        AwsJsonReader reader = context.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        Credentials credentials = new Credentials();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("AccessKeyId")) {
                credentials.setAccessKeyId(StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("SecretKey")) {
                credentials.setSecretKey(StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("SessionToken")) {
                credentials.setSessionToken(StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Expiration")) {
                credentials.setExpiration(DateJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return credentials;
    }

    public static CredentialsJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CredentialsJsonUnmarshaller();
        }
        return instance;
    }
}
