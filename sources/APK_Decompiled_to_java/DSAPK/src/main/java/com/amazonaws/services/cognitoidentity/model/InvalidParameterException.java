package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonServiceException;

public class InvalidParameterException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public InvalidParameterException(String message) {
        super(message);
    }
}
