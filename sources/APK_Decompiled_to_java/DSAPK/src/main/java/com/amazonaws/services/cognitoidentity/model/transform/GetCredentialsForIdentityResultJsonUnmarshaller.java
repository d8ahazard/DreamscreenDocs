package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class GetCredentialsForIdentityResultJsonUnmarshaller implements Unmarshaller<GetCredentialsForIdentityResult, JsonUnmarshallerContext> {
    private static GetCredentialsForIdentityResultJsonUnmarshaller instance;

    public GetCredentialsForIdentityResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetCredentialsForIdentityResult getCredentialsForIdentityResult = new GetCredentialsForIdentityResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("IdentityId")) {
                getCredentialsForIdentityResult.setIdentityId(StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Credentials")) {
                getCredentialsForIdentityResult.setCredentials(CredentialsJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return getCredentialsForIdentityResult;
    }

    public static GetCredentialsForIdentityResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetCredentialsForIdentityResultJsonUnmarshaller();
        }
        return instance;
    }
}
