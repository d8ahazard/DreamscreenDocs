package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.services.securitytoken.model.FederatedUser;
import com.amazonaws.transform.SimpleTypeStaxUnmarshallers.StringStaxUnmarshaller;
import com.amazonaws.transform.StaxUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

class FederatedUserStaxUnmarshaller implements Unmarshaller<FederatedUser, StaxUnmarshallerContext> {
    private static FederatedUserStaxUnmarshaller instance;

    FederatedUserStaxUnmarshaller() {
    }

    public FederatedUser unmarshall(StaxUnmarshallerContext context) throws Exception {
        FederatedUser federatedUser = new FederatedUser();
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
                } else if (context.testExpression("FederatedUserId", targetDepth)) {
                    federatedUser.setFederatedUserId(StringStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("Arn", targetDepth)) {
                    federatedUser.setArn(StringStaxUnmarshaller.getInstance().unmarshall(context));
                }
            } else {
                break;
            }
        }
        return federatedUser;
    }

    public static FederatedUserStaxUnmarshaller getInstance() {
        if (instance == null) {
            instance = new FederatedUserStaxUnmarshaller();
        }
        return instance;
    }
}
