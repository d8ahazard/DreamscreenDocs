package com.amazonaws.mobileconnectors.cognitoauth.exceptions;

public class AuthServiceException extends AuthClientException {
    private static final long serialVersionUID = -5574630014204561805L;

    public AuthServiceException(String message) {
        super(message);
    }

    public AuthServiceException(String message, Throwable t) {
        super(message, t);
    }
}
