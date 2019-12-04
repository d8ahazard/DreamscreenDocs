package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.Request;
import com.amazonaws.services.securitytoken.model.AssumedRoleUser;
import com.amazonaws.util.StringUtils;

class AssumedRoleUserStaxMarshaller {
    private static AssumedRoleUserStaxMarshaller instance;

    AssumedRoleUserStaxMarshaller() {
    }

    public void marshall(AssumedRoleUser _assumedRoleUser, Request<?> request, String _prefix) {
        if (_assumedRoleUser.getAssumedRoleId() != null) {
            request.addParameter(_prefix + "AssumedRoleId", StringUtils.fromString(_assumedRoleUser.getAssumedRoleId()));
        }
        if (_assumedRoleUser.getArn() != null) {
            request.addParameter(_prefix + "Arn", StringUtils.fromString(_assumedRoleUser.getArn()));
        }
    }

    public static AssumedRoleUserStaxMarshaller getInstance() {
        if (instance == null) {
            instance = new AssumedRoleUserStaxMarshaller();
        }
        return instance;
    }
}
