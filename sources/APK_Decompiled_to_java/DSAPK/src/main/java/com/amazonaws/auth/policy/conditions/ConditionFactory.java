package com.amazonaws.auth.policy.conditions;

import com.amazonaws.auth.policy.Condition;
import com.amazonaws.auth.policy.conditions.ArnCondition.ArnComparisonType;
import com.amazonaws.auth.policy.conditions.StringCondition.StringComparisonType;

public final class ConditionFactory {
    public static final String CURRENT_TIME_CONDITION_KEY = "aws:CurrentTime";
    public static final String EPOCH_TIME_CONDITION_KEY = "aws:EpochTime";
    public static final String REFERER_CONDITION_KEY = "aws:Referer";
    public static final String SECURE_TRANSPORT_CONDITION_KEY = "aws:SecureTransport";
    public static final String SOURCE_ARN_CONDITION_KEY = "aws:SourceArn";
    public static final String SOURCE_IP_CONDITION_KEY = "aws:SourceIp";
    public static final String USER_AGENT_CONDITION_KEY = "aws:UserAgent";

    private ConditionFactory() {
    }

    public static Condition newSourceArnCondition(String arnPattern) {
        return new ArnCondition(ArnComparisonType.ArnLike, SOURCE_ARN_CONDITION_KEY, arnPattern);
    }

    public static Condition newSecureTransportCondition() {
        return new BooleanCondition(SECURE_TRANSPORT_CONDITION_KEY, true);
    }

    public static Condition newUserAgentCondition(StringComparisonType comparisonType, String value) {
        return new StringCondition(comparisonType, USER_AGENT_CONDITION_KEY, value);
    }

    public static Condition newRefererCondition(StringComparisonType comparisonType, String value) {
        return new StringCondition(comparisonType, REFERER_CONDITION_KEY, value);
    }
}
