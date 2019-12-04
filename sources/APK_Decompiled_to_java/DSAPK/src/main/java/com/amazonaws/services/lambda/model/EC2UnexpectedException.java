package com.amazonaws.services.lambda.model;

import com.amazonaws.AmazonServiceException;

public class EC2UnexpectedException extends AmazonServiceException {
    private static final long serialVersionUID = 1;
    private String eC2ErrorCode;
    private String type;

    public EC2UnexpectedException(String message) {
        super(message);
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public String getEC2ErrorCode() {
        return this.eC2ErrorCode;
    }

    public void setEC2ErrorCode(String eC2ErrorCode2) {
        this.eC2ErrorCode = eC2ErrorCode2;
    }
}
