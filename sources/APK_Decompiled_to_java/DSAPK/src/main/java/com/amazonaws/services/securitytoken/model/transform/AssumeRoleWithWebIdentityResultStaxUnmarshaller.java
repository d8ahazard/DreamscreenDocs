package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.services.securitytoken.model.AssumeRoleWithWebIdentityResult;
import com.amazonaws.transform.SimpleTypeStaxUnmarshallers.IntegerStaxUnmarshaller;
import com.amazonaws.transform.SimpleTypeStaxUnmarshallers.StringStaxUnmarshaller;
import com.amazonaws.transform.StaxUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

public class AssumeRoleWithWebIdentityResultStaxUnmarshaller implements Unmarshaller<AssumeRoleWithWebIdentityResult, StaxUnmarshallerContext> {
    private static AssumeRoleWithWebIdentityResultStaxUnmarshaller instance;

    public AssumeRoleWithWebIdentityResult unmarshall(StaxUnmarshallerContext context) throws Exception {
        AssumeRoleWithWebIdentityResult assumeRoleWithWebIdentityResult = new AssumeRoleWithWebIdentityResult();
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
                    assumeRoleWithWebIdentityResult.setCredentials(CredentialsStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("SubjectFromWebIdentityToken", targetDepth)) {
                    assumeRoleWithWebIdentityResult.setSubjectFromWebIdentityToken(StringStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("AssumedRoleUser", targetDepth)) {
                    assumeRoleWithWebIdentityResult.setAssumedRoleUser(AssumedRoleUserStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("PackedPolicySize", targetDepth)) {
                    assumeRoleWithWebIdentityResult.setPackedPolicySize(IntegerStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("Provider", targetDepth)) {
                    assumeRoleWithWebIdentityResult.setProvider(StringStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("Audience", targetDepth)) {
                    assumeRoleWithWebIdentityResult.setAudience(StringStaxUnmarshaller.getInstance().unmarshall(context));
                }
            } else {
                break;
            }
        }
        return assumeRoleWithWebIdentityResult;
    }

    public static AssumeRoleWithWebIdentityResultStaxUnmarshaller getInstance() {
        if (instance == null) {
            instance = new AssumeRoleWithWebIdentityResultStaxUnmarshaller();
        }
        return instance;
    }
}
