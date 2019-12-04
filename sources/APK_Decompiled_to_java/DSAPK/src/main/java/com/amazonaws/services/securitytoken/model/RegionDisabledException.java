package com.amazonaws.services.securitytoken.model;

import com.amazonaws.AmazonServiceException;

public class RegionDisabledException extends AmazonServiceException {
    private static final long serialVersionUID = 1;

    public RegionDisabledException(String message) {
        super(message);
    }
}
