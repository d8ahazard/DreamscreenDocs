package com.amazonaws.auth.policy.conditions;

import com.amazonaws.auth.policy.Condition;
import com.amazonaws.util.DateUtils;
import java.util.Arrays;
import java.util.Date;

public class DateCondition extends Condition {

    public enum DateComparisonType {
        DateEquals,
        DateGreaterThan,
        DateGreaterThanEquals,
        DateLessThan,
        DateLessThanEquals,
        DateNotEquals
    }

    public DateCondition(DateComparisonType type, Date date) {
        this.type = type.toString();
        this.values = Arrays.asList(new String[]{DateUtils.formatISO8601Date(date)});
    }
}
