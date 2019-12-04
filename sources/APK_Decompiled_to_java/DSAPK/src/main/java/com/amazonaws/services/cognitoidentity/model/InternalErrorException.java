package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonServiceException;

public class InternalErrorException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public InternalErrorException(String message) {
        super(message);
    }
}
