package com.amazonaws.services.lambda.model;

import com.amazonaws.AmazonServiceException;

public class RequestTooLargeException extends AmazonServiceException {
    private static final long serialVersionUID = 1;
    private String type;

    public RequestTooLargeException(String message) {
        super(message);
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type2) {
        this.type = type2;
    }
}
