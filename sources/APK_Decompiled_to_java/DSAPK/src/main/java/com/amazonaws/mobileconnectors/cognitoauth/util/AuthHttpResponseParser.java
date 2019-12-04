package com.amazonaws.mobileconnectors.cognitoauth.util;

import com.amazonaws.mobileconnectors.cognitoauth.AuthUserSession;
import com.amazonaws.mobileconnectors.cognitoauth.exceptions.AuthClientException;
import com.amazonaws.mobileconnectors.cognitoauth.exceptions.AuthInvalidGrantException;
import com.amazonaws.mobileconnectors.cognitoauth.exceptions.AuthInvalidParameterException;
import com.amazonaws.mobileconnectors.cognitoauth.exceptions.AuthServiceException;
import com.amazonaws.mobileconnectors.cognitoauth.tokens.AccessToken;
import com.amazonaws.mobileconnectors.cognitoauth.tokens.IdToken;
import com.amazonaws.mobileconnectors.cognitoauth.tokens.RefreshToken;
import org.json.JSONObject;

public class AuthHttpResponseParser {
    private AuthHttpResponseParser() {
    }

    public static final AuthUserSession parseHttpResponse(String responseStr) {
        if (responseStr == null || responseStr.isEmpty()) {
            throw new AuthInvalidParameterException("Invalid (null) response from Amazon Cognito Auth endpoint");
        }
        AccessToken accessToken = new AccessToken(null);
        IdToken idToken = new IdToken(null);
        RefreshToken refreshToken = new RefreshToken(null);
        try {
            JSONObject responseJson = new JSONObject(responseStr);
            if (responseJson.has(ClientConstants.DOMAIN_QUERY_PARAM_ERROR)) {
                String errorText = responseJson.getString(ClientConstants.DOMAIN_QUERY_PARAM_ERROR);
                if (ClientConstants.HTTP_RESPONSE_INVALID_GRANT.equals(errorText)) {
                    throw new AuthInvalidGrantException(errorText);
                }
                throw new AuthServiceException(errorText);
            }
            if (responseJson.has(ClientConstants.HTTP_RESPONSE_ACCESS_TOKEN)) {
                accessToken = new AccessToken(responseJson.getString(ClientConstants.HTTP_RESPONSE_ACCESS_TOKEN));
            }
            if (responseJson.has(ClientConstants.HTTP_RESPONSE_ID_TOKEN)) {
                idToken = new IdToken(responseJson.getString(ClientConstants.HTTP_RESPONSE_ID_TOKEN));
            }
            if (responseJson.has("refresh_token")) {
                refreshToken = new RefreshToken(responseJson.getString("refresh_token"));
            }
            return new AuthUserSession(idToken, accessToken, refreshToken);
        } catch (AuthInvalidGrantException invg) {
            throw invg;
        } catch (AuthServiceException seve) {
            throw seve;
        } catch (Exception e) {
            throw new AuthClientException(e.getMessage(), e);
        }
    }
}
