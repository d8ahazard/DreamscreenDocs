package com.amazonaws.mobileconnectors.cognitoauth.tokens;

public class UserToken {
    private String token;

    public UserToken(String token2) {
        this.token = token2;
    }

    /* access modifiers changed from: protected */
    public String getToken() {
        return this.token;
    }
}
