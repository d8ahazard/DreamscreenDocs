package com.amazonaws.services.securitytoken.model;

import com.amazonaws.AmazonServiceException;

public class ExpiredTokenException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public ExpiredTokenException(String message) {
        super(message);
    }
}
