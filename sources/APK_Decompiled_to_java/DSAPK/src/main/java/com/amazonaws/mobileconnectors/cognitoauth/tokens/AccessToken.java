package com.amazonaws.mobileconnectors.cognitoauth.tokens;

import com.amazonaws.mobileconnectors.cognitoauth.util.JWTParser;
import java.util.Date;

public class AccessToken extends UserToken {
    private long SEC_IN_MILLSEC = 1000;

    public AccessToken(String jwtToken) {
        super(jwtToken);
    }

    public String getJWTToken() {
        return super.getToken();
    }

    public Date getExpiration() throws Exception {
        try {
            String claim = JWTParser.getClaim(super.getToken(), "exp");
            if (claim == null) {
                return null;
            }
            return new Date(Long.parseLong(claim) * this.SEC_IN_MILLSEC);
        } catch (Exception e) {
            throw e;
        }
    }

    public String getUsername() throws Exception {
        return JWTParser.getClaim(super.getToken(), "username");
    }
}
