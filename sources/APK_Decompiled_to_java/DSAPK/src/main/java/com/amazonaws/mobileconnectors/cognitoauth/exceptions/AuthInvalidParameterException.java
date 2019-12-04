package com.amazonaws.mobileconnectors.cognitoauth.exceptions;

public class AuthInvalidParameterException extends AuthClientException {
    private static final long serialVersionUID = 4714089830668096023L;

    public AuthInvalidParameterException(String message) {
        super(message);
    }

    public AuthInvalidParameterException(String message, Throwable t) {
        super(message, t);
    }
}
