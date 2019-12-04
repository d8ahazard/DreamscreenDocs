package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonServiceException;

public class ResourceConflictException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public ResourceConflictException(String message) {
        super(message);
    }
}
