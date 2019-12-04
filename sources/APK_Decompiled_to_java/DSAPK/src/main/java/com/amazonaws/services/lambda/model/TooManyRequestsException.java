package com.amazonaws.services.lambda.model;

import com.amazonaws.AmazonServiceException;

public class TooManyRequestsException extends AmazonServiceException {
    private static final long serialVersionUID = 1;
    private String reason;
    private String retryAfterSeconds;
    private String type;

    public TooManyRequestsException(String message) {
        super(message);
    }

    public String getRetryAfterSeconds() {
        return this.retryAfterSeconds;
    }

    public void setRetryAfterSeconds(String retryAfterSeconds2) {
        this.retryAfterSeconds = retryAfterSeconds2;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason2) {
        this.reason = reason2;
    }
}
