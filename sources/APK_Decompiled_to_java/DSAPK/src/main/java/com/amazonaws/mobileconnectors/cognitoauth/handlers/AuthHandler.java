package com.amazonaws.mobileconnectors.cognitoauth.handlers;

import com.amazonaws.mobileconnectors.cognitoauth.AuthUserSession;

public interface AuthHandler {
    void onFailure(Exception exc);

    void onSignout();

    void onSuccess(AuthUserSession authUserSession);
}
