package com.amazonaws.mobileconnectors.cognitoauth.tokens;

public class RefreshToken extends UserToken {
    public RefreshToken(String token) {
        super(token);
    }

    public String getToken() {
        return super.getToken();
    }
}
