package com.amazonaws.mobileconnectors.cognitoauth.exceptions;

public class AuthInvalidGrantException extends AuthClientException {
    private static final long serialVersionUID = -666047864690460870L;

    public AuthInvalidGrantException(String message, Throwable t) {
        super(message, t);
    }

    public AuthInvalidGrantException(String message) {
        super(message);
    }
}
