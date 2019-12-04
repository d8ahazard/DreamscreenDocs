package com.amazonaws.transform;

import com.amazonaws.util.json.AwsJsonReader;
import com.amazonaws.util.json.AwsJsonToken;
import java.util.ArrayList;
import java.util.List;

public class ListUnmarshaller<T> implements Unmarshaller<List<T>, JsonUnmarshallerContext> {
    private final Unmarshaller<T, JsonUnmarshallerContext> itemUnmarshaller;

    public ListUnmarshaller(Unmarshaller<T, JsonUnmarshallerContext> itemUnmarshaller2) {
        this.itemUnmarshaller = itemUnmarshaller2;
    }

    public List<T> unmarshall(JsonUnmarshallerContext context) throws Exception {
        AwsJsonReader reader = context.getReader();
        if (reader.peek() == AwsJsonToken.VALUE_NULL) {
            reader.skipValue();
            return null;
        }
        List<T> list = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            list.add(this.itemUnmarshaller.unmarshall(context));
        }
        reader.endArray();
        return list;
    }
}
