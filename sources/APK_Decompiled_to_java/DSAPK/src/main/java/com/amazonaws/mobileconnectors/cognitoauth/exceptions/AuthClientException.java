package com.amazonaws.mobileconnectors.cognitoauth.exceptions;

public class AuthClientException extends RuntimeException {
    private static final long serialVersionUID = -2748057344647987093L;

    public AuthClientException(String message, Throwable t) {
        super(message, t);
    }

    public AuthClientException(String message) {
        super(message);
    }

    public boolean isRetryable() {
        return true;
    }
}
