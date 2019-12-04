package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.GetIdResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class GetIdResultJsonUnmarshaller implements Unmarshaller<GetIdResult, JsonUnmarshallerContext> {
    private static GetIdResultJsonUnmarshaller instance;

    public GetIdResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetIdResult getIdResult = new GetIdResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("IdentityId")) {
                getIdResult.setIdentityId(StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return getIdResult;
    }

    public static GetIdResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetIdResultJsonUnmarshaller();
        }
        return instance;
    }
}
