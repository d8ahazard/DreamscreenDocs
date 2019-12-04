package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.transform.SimpleTypeStaxUnmarshallers.IntegerStaxUnmarshaller;
import com.amazonaws.transform.StaxUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

public class AssumeRoleResultStaxUnmarshaller implements Unmarshaller<AssumeRoleResult, StaxUnmarshallerContext> {
    private static AssumeRoleResultStaxUnmarshaller instance;

    public AssumeRoleResult unmarshall(StaxUnmarshallerContext context) throws Exception {
        AssumeRoleResult assumeRoleResult = new AssumeRoleResult();
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
                    assumeRoleResult.setCredentials(CredentialsStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("AssumedRoleUser", targetDepth)) {
                    assumeRoleResult.setAssumedRoleUser(AssumedRoleUserStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("PackedPolicySize", targetDepth)) {
                    assumeRoleResult.setPackedPolicySize(IntegerStaxUnmarshaller.getInstance().unmarshall(context));
                }
            } else {
                break;
            }
        }
        return assumeRoleResult;
    }

    public static AssumeRoleResultStaxUnmarshaller getInstance() {
        if (instance == null) {
            instance = new AssumeRoleResultStaxUnmarshaller();
        }
        return instance;
    }
}
