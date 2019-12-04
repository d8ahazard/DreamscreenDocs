package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.services.securitytoken.model.GetFederationTokenResult;
import com.amazonaws.transform.SimpleTypeStaxUnmarshallers.IntegerStaxUnmarshaller;
import com.amazonaws.transform.StaxUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

public class GetFederationTokenResultStaxUnmarshaller implements Unmarshaller<GetFederationTokenResult, StaxUnmarshallerContext> {
    private static GetFederationTokenResultStaxUnmarshaller instance;

    public GetFederationTokenResult unmarshall(StaxUnmarshallerContext context) throws Exception {
        GetFederationTokenResult getFederationTokenResult = new GetFederationTokenResult();
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
                } else if (context.testExpression("Credentials", targetDepth)) {
                    getFederationTokenResult.setCredentials(CredentialsStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("FederatedUser", targetDepth)) {
                    getFederationTokenResult.setFederatedUser(FederatedUserStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("PackedPolicySize", targetDepth)) {
                    getFederationTokenResult.setPackedPolicySize(IntegerStaxUnmarshaller.getInstance().unmarshall(context));
                }
            } else {
                break;
            }
        }
        return getFederationTokenResult;
    }

    public static GetFederationTokenResultStaxUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetFederationTokenResultStaxUnmarshaller();
        }
        return instance;
    }
}
