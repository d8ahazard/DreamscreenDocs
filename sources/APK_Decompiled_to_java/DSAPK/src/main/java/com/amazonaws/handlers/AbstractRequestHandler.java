package com.amazonaws.handlers;

import com.amazonaws.Request;
import com.amazonaws.util.TimingInfo;

@Deprecated
public abstract class AbstractRequestHandler implements RequestHandler {
    public void beforeRequest(Request<?> request) {
    }

    public void afterResponse(Request<?> request, Object response, TimingInfo timingInfo) {
    }

    public void afterError(Request<?> request, Exception e) {
    }
}
