package com.amazonaws.services.securitytoken.model;

import com.amazonaws.AmazonServiceException;

public class MalformedPolicyDocumentException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public MalformedPolicyDocumentException(String message) {
        super(message);
    }
}
