package com.amazonaws.handlers;

import com.amazonaws.Request;
import com.amazonaws.Response;

public abstract class RequestHandler2 {
    public abstract void afterError(Request<?> request, Response<?> response, Exception exc);

    public abstract void afterResponse(Request<?> request, Response<?> response);

    public abstract void beforeRequest(Request<?> request);

    public static RequestHandler2 adapt(RequestHandler old) {
        return new RequestHandler2Adaptor(old);
    }
}
