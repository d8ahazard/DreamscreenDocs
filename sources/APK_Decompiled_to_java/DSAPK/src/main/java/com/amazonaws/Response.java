package com.amazonaws;

import com.amazonaws.http.HttpResponse;

public final class Response<T> {
    private final HttpResponse httpResponse;
    private final T response;

    public Response(T response2, HttpResponse httpResponse2) {
        this.response = response2;
        this.httpResponse = httpResponse2;
    }

    public T getAwsResponse() {
        return this.response;
    }

    public HttpResponse getHttpResponse() {
        return this.httpResponse;
    }
}
