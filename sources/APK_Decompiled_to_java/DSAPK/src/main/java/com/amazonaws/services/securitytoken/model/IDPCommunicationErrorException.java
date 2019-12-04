package com.amazonaws.services.securitytoken.model;

import com.amazonaws.AmazonServiceException;

public class IDPCommunicationErrorException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public IDPCommunicationErrorException(String message) {
        super(message);
    }
}
