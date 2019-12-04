package com.amazonaws.auth.policy.conditions;

import com.amazonaws.auth.policy.Condition;
import java.util.Arrays;

public class StringCondition extends Condition {

    public enum StringComparisonType {
        StringEquals,
        StringEqualsIgnoreCase,
        StringLike,
        StringNotEquals,
        StringNotEqualsIgnoreCase,
        StringNotLike
    }

    public StringCondition(StringComparisonType type, String key, String value) {
        this.type = type.toString();
        this.conditionKey = key;
        this.values = Arrays.asList(new String[]{value});
    }
}
