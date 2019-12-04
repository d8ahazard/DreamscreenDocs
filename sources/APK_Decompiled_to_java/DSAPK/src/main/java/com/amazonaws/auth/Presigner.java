package com.amazonaws.auth;

import com.amazonaws.Request;
import java.util.Date;

public interface Presigner {
    void presignRequest(Request<?> request, AWSCredentials aWSCredentials, Date date);
}
