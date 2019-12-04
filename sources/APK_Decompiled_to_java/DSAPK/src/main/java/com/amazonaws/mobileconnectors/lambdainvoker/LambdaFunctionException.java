package com.amazonaws.mobileconnectors.lambdainvoker;

import com.amazonaws.AmazonClientException;

public class LambdaFunctionException extends AmazonClientException {
    private static final long serialVersionUID = 6674259958957646199L;
    private final String details;

    public LambdaFunctionException(String message, String details2) {
        super(message);
        this.details = details2;
    }

    public String getDetails() {
        return this.details;
    }
}
