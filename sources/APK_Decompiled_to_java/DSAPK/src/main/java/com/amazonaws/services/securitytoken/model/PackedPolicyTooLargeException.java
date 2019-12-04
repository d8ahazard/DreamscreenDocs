package com.amazonaws.services.securitytoken.model;

import com.amazonaws.AmazonServiceException;

public class PackedPolicyTooLargeException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public PackedPolicyTooLargeException(String message) {
        super(message);
    }
}
