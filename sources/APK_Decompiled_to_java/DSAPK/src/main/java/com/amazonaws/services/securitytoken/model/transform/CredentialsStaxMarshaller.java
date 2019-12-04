package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.Request;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.util.StringUtils;

class CredentialsStaxMarshaller {
    private static CredentialsStaxMarshaller instance;

    CredentialsStaxMarshaller() {
    }

    public void marshall(Credentials _credentials, Request<?> request, String _prefix) {
        if (_credentials.getAccessKeyId() != null) {
            request.addParameter(_prefix + "AccessKeyId", StringUtils.fromString(_credentials.getAccessKeyId()));
        }
        if (_credentials.getSecretAccessKey() != null) {
            request.addParameter(_prefix + "SecretAccessKey", StringUtils.fromString(_credentials.getSecretAccessKey()));
        }
        if (_credentials.getSessionToken() != null) {
            request.addParameter(_prefix + "SessionToken", StringUtils.fromString(_credentials.getSessionToken()));
        }
        if (_credentials.getExpiration() != null) {
            request.addParameter(_prefix + "Expiration", StringUtils.fromDate(_credentials.getExpiration()));
        }
    }

    public static CredentialsStaxMarshaller getInstance() {
        if (instance == null) {
            instance = new CredentialsStaxMarshaller();
        }
        return instance;
    }
}
