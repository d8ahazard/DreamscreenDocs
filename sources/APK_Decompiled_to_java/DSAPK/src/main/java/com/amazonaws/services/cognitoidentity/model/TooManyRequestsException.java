package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonServiceException;

public class TooManyRequestsException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public TooManyRequestsException(String message) {
        super(message);
    }
}
