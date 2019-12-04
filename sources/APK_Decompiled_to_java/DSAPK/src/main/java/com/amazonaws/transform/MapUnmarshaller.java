package com.amazonaws.transform;

import com.amazonaws.util.json.AwsJsonReader;
import com.amazonaws.util.json.AwsJsonToken;
import java.util.HashMap;
import java.util.Map;

public class MapUnmarshaller<V> implements Unmarshaller<Map<String, V>, JsonUnmarshallerContext> {
    private final Unmarshaller<V, JsonUnmarshallerContext> valueUnmarshaller;

    public MapUnmarshaller(Unmarshaller<V, JsonUnmarshallerContext> valueUnmarshaller2) {
        this.valueUnmarshaller = valueUnmarshaller2;
    }

    public Map<String, V> unmarshall(JsonUnmarshallerContext context) throws Exception {
        AwsJsonReader reader = context.getReader();
        if (reader.peek() == AwsJsonToken.VALUE_NULL) {
            reader.skipValue();
            return null;
        }
        Map<String, V> map = new HashMap<>();
        reader.beginObject();
        while (reader.hasNext()) {
            map.put(reader.nextName(), this.valueUnmarshaller.unmarshall(context));
        }
        reader.endObject();
        return map;
    }
}
