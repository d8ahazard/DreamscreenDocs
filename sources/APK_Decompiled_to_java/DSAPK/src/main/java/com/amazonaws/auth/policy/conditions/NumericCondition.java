package com.amazonaws.auth.policy.conditions;

import com.amazonaws.auth.policy.Condition;
import java.util.Arrays;

public class NumericCondition extends Condition {

    public enum NumericComparisonType {
        NumericEquals,
        NumericGreaterThan,
        NumericGreaterThanEquals,
        NumericLessThan,
        NumericLessThanEquals,
        NumericNotEquals
    }

    public NumericCondition(NumericComparisonType type, String key, String value) {
        this.type = type.toString();
        this.conditionKey = key;
        this.values = Arrays.asList(new String[]{value});
    }
}
