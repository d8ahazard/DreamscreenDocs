package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonServiceException;

public class ExternalServiceException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public ExternalServiceException(String message) {
        super(message);
    }
}
