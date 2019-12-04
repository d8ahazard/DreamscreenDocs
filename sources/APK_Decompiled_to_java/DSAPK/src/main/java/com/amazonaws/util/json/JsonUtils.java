package com.amazonaws.util.json;

import com.amazonaws.AmazonClientException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class JsonUtils {
    private static volatile AwsJsonFactory factory = new GsonFactory();

    public enum JsonEngine {
        Gson,
        Jackson
    }

    public static void setJsonEngine(JsonEngine jsonEngine) {
        switch (jsonEngine) {
            case Gson:
                factory = new GsonFactory();
                return;
            case Jackson:
                factory = new JacksonFactory();
                return;
            default:
                throw new RuntimeException("Unsupported json engine");
        }
    }

    static void setJsonEngine(AwsJsonFactory factory2) {
        if (factory2 == null) {
            throw new IllegalArgumentException("factory can't be null");
        }
        factory = factory2;
    }

    public static AwsJsonReader getJsonReader(Reader in) {
        if (factory != null) {
            return factory.getJsonReader(in);
        }
        throw new IllegalStateException("Json engine is unavailable.");
    }

    public static AwsJsonWriter getJsonWriter(Writer out) {
        if (factory != null) {
            return factory.getJsonWriter(out);
        }
        throw new IllegalStateException("Json engine is unavailable.");
    }

    public static Map<String, String> jsonToMap(Reader in) {
        AwsJsonReader reader = getJsonReader(in);
        try {
            if (reader.peek() == null) {
                return Collections.EMPTY_MAP;
            }
            Map<String, String> map = new HashMap<>();
            reader.beginObject();
            while (reader.hasNext()) {
                String key = reader.nextName();
                if (reader.isContainer()) {
                    reader.skipValue();
                } else {
                    map.put(key, reader.nextString());
                }
            }
            reader.endObject();
            reader.close();
            return Collections.unmodifiableMap(map);
        } catch (IOException e) {
            throw new AmazonClientException("Unable to parse JSON String.", e);
        }
    }

    public static Map<String, String> jsonToMap(String json) {
        if (json == null || json.isEmpty()) {
            return Collections.EMPTY_MAP;
        }
        return jsonToMap((Reader) new StringReader(json));
    }

    public static String mapToString(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return "{}";
        }
        try {
            StringWriter out = new StringWriter();
            AwsJsonWriter writer = getJsonWriter(out);
            writer.beginObject();
            for (Entry<String, String> entry : map.entrySet()) {
                writer.name((String) entry.getKey()).value((String) entry.getValue());
            }
            writer.endObject();
            writer.close();
            return out.toString();
        } catch (IOException e) {
            throw new AmazonClientException("Unable to serialize to JSON String.", e);
        }
    }

    private static boolean isClassAvailable(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
