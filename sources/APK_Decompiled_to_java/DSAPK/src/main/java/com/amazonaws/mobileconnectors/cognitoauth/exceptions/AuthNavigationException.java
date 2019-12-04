package com.amazonaws.mobileconnectors.cognitoauth.exceptions;

public class AuthNavigationException extends AuthClientException {
    private static final long serialVersionUID = -7116786198762963314L;

    public AuthNavigationException(String message, Throwable t) {
        super(message, t);
    }

    public AuthNavigationException(String message) {
        super(message);
    }
}
