package com.amazonaws.mobileconnectors.cognitoauth.util;

import android.util.Base64;
import com.amazonaws.mobileconnectors.cognitoauth.exceptions.AuthInvalidParameterException;
import org.json.JSONObject;

public class JWTParser {
    private static int HEADER = 0;
    private static int JWT_PARTS = 3;
    private static int PAYLOAD = 1;
    private static int SIGNATURE = 2;

    public static JSONObject getHeader(String JWT) {
        try {
            validateJWT(JWT);
            return new JSONObject(new String(Base64.decode(JWT.split("\\.")[HEADER], 8), "UTF-8"));
        } catch (Exception e) {
            throw new AuthInvalidParameterException("error while parsing JSON", e);
        }
    }

    public static JSONObject getPayload(String JWT) {
        try {
            validateJWT(JWT);
            return new JSONObject(new String(Base64.decode(JWT.split("\\.")[PAYLOAD], 8), "UTF-8"));
        } catch (Exception e) {
            throw new AuthInvalidParameterException("error while parsing JSON", e);
        }
    }

    public static String getSignature(String JWT) {
        try {
            validateJWT(JWT);
            return new String(Base64.decode(JWT.split("\\.")[SIGNATURE], 8), "UTF-8");
        } catch (Exception e) {
            throw new AuthInvalidParameterException("error while parsing JSON", e);
        }
    }

    public static String getClaim(String JWT, String claim) {
        try {
            Object claimValue = getPayload(JWT).get(claim);
            if (claimValue != null) {
                return claimValue.toString();
            }
            return null;
        } catch (Exception e) {
            throw new AuthInvalidParameterException("error while parsing JSON", e);
        }
    }

    public static void validateJWT(String JWT) {
        if (JWT.split("\\.").length < JWT_PARTS) {
            throw new AuthInvalidParameterException("Not a JSON Web Token");
        }
    }
}
