package com.amazonaws.mobileconnectors.cognitoauth.tokens;

import com.amazonaws.mobileconnectors.cognitoauth.util.JWTParser;
import java.util.Date;

public class IdToken extends UserToken {
    private long SEC_IN_MILLSEC = 1000;

    public IdToken(String jwtToken) {
        super(jwtToken);
    }

    public String getJWTToken() {
        return super.getToken();
    }

    public Date getExpiration() throws Exception {
        String claim = JWTParser.getClaim(super.getToken(), "exp");
        if (claim == null) {
            return null;
        }
        return new Date(Long.parseLong(claim) * this.SEC_IN_MILLSEC);
    }

    public Date getNotBefore() throws Exception {
        String claim = JWTParser.getClaim(super.getToken(), "nbf");
        if (claim == null) {
            return null;
        }
        return new Date(Long.parseLong(claim) * this.SEC_IN_MILLSEC);
    }

    public Date getIssuedAt() throws Exception {
        String claim = JWTParser.getClaim(super.getToken(), "iat");
        if (claim == null) {
            return null;
        }
        return new Date(Long.parseLong(claim) * this.SEC_IN_MILLSEC);
    }

    public String getCognitoUsername() throws Exception {
        return JWTParser.getClaim(super.getToken(), "cognito:username");
    }
}
