package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class GetOpenIdTokenResultJsonUnmarshaller implements Unmarshaller<GetOpenIdTokenResult, JsonUnmarshallerContext> {
    private static GetOpenIdTokenResultJsonUnmarshaller instance;

    public GetOpenIdTokenResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetOpenIdTokenResult getOpenIdTokenResult = new GetOpenIdTokenResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("IdentityId")) {
                getOpenIdTokenResult.setIdentityId(StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Token")) {
                getOpenIdTokenResult.setToken(StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return getOpenIdTokenResult;
    }

    public static GetOpenIdTokenResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetOpenIdTokenResultJsonUnmarshaller();
        }
        return instance;
    }
}
