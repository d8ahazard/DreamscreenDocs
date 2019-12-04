package com.amazonaws.transform;

import com.amazonaws.http.HttpResponse;
import com.amazonaws.util.json.AwsJsonReader;

public class JsonUnmarshallerContext {
    private final HttpResponse httpResponse;
    private final AwsJsonReader reader;

    public JsonUnmarshallerContext(AwsJsonReader reader2) {
        this(reader2, null);
    }

    public JsonUnmarshallerContext(AwsJsonReader reader2, HttpResponse httpResponse2) {
        this.reader = reader2;
        this.httpResponse = httpResponse2;
    }

    public AwsJsonReader getReader() {
        return this.reader;
    }

    public String getHeader(String header) {
        if (this.httpResponse == null) {
            return null;
        }
        return (String) this.httpResponse.getHeaders().get(header);
    }

    public HttpResponse getHttpResponse() {
        return this.httpResponse;
    }
}
