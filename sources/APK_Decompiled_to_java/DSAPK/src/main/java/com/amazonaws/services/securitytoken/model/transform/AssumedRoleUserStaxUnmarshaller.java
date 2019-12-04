package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.services.securitytoken.model.AssumedRoleUser;
import com.amazonaws.transform.SimpleTypeStaxUnmarshallers.StringStaxUnmarshaller;
import com.amazonaws.transform.StaxUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

class AssumedRoleUserStaxUnmarshaller implements Unmarshaller<AssumedRoleUser, StaxUnmarshallerContext> {
    private static AssumedRoleUserStaxUnmarshaller instance;

    AssumedRoleUserStaxUnmarshaller() {
    }

    public AssumedRoleUser unmarshall(StaxUnmarshallerContext context) throws Exception {
        AssumedRoleUser assumedRoleUser = new AssumedRoleUser();
        int originalDepth = context.getCurrentDepth();
        int targetDepth = originalDepth + 1;
        if (context.isStartOfDocument()) {
            targetDepth += 2;
        }
        while (true) {
            int xmlEvent = context.nextEvent();
            if (xmlEvent != 1) {
                if (xmlEvent != 2) {
                    if (xmlEvent == 3 && context.getCurrentDepth() < originalDepth) {
                        break;
                    }
                } else if (context.testExpression("AssumedRoleId", targetDepth)) {
                    assumedRoleUser.setAssumedRoleId(StringStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("Arn", targetDepth)) {
                    assumedRoleUser.setArn(StringStaxUnmarshaller.getInstance().unmarshall(context));
                }
            } else {
                break;
            }
        }
        return assumedRoleUser;
    }

    public static AssumedRoleUserStaxUnmarshaller getInstance() {
        if (instance == null) {
            instance = new AssumedRoleUserStaxUnmarshaller();
        }
        return instance;
    }
}
