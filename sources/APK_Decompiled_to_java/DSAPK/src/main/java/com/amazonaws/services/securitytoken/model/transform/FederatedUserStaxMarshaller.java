package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.Request;
import com.amazonaws.services.securitytoken.model.FederatedUser;
import com.amazonaws.util.StringUtils;

class FederatedUserStaxMarshaller {
    private static FederatedUserStaxMarshaller instance;

    FederatedUserStaxMarshaller() {
    }

    public void marshall(FederatedUser _federatedUser, Request<?> request, String _prefix) {
        if (_federatedUser.getFederatedUserId() != null) {
            request.addParameter(_prefix + "FederatedUserId", StringUtils.fromString(_federatedUser.getFederatedUserId()));
        }
        if (_federatedUser.getArn() != null) {
            request.addParameter(_prefix + "Arn", StringUtils.fromString(_federatedUser.getArn()));
        }
    }

    public static FederatedUserStaxMarshaller getInstance() {
        if (instance == null) {
            instance = new FederatedUserStaxMarshaller();
        }
        return instance;
    }
}
