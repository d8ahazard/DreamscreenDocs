package com.amazonaws.handlers;

import com.amazonaws.Request;
import com.amazonaws.util.TimingInfo;

@Deprecated
public interface RequestHandler {
    void afterError(Request<?> request, Exception exc);

    void afterResponse(Request<?> request, Object obj, TimingInfo timingInfo);

    void beforeRequest(Request<?> request);
}
