package com.amazonaws.mobileconnectors.cognitoauth;

import com.amazonaws.mobileconnectors.cognitoauth.tokens.AccessToken;
import com.amazonaws.mobileconnectors.cognitoauth.tokens.IdToken;
import com.amazonaws.mobileconnectors.cognitoauth.tokens.RefreshToken;
import com.amazonaws.mobileconnectors.cognitoauth.util.AuthClientConfig;
import java.util.Date;

public class AuthUserSession {
    private AccessToken accessToken;
    private IdToken idToken;
    private RefreshToken refreshToken;

    public AuthUserSession(IdToken idToken2, AccessToken accessToken2, RefreshToken refreshToken2) {
        this.idToken = idToken2;
        this.accessToken = accessToken2;
        this.refreshToken = refreshToken2;
    }

    public IdToken getIdToken() {
        return this.idToken;
    }

    public AccessToken getAccessToken() {
        return this.accessToken;
    }

    public RefreshToken getRefreshToken() {
        return this.refreshToken;
    }

    public boolean isValid() {
        boolean z = false;
        if (this.accessToken == null || this.accessToken.getJWTToken() == null) {
            return z;
        }
        try {
            return new Date().before(this.accessToken.getExpiration());
        } catch (Exception e) {
            return z;
        }
    }

    public boolean isValidForThreshold() {
        if (this.accessToken == null || this.accessToken.getJWTToken() == null) {
            return false;
        }
        try {
            if (this.accessToken.getExpiration().getTime() - System.currentTimeMillis() > AuthClientConfig.getRefreshThreshold()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername() {
        String str = null;
        if (this.accessToken == null) {
            return str;
        }
        try {
            return this.accessToken.getUsername();
        } catch (Exception e) {
            return str;
        }
    }
}
