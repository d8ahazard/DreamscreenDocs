package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonServiceException;

public class NotAuthorizedException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public NotAuthorizedException(String message) {
        super(message);
    }
}
