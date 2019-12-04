package com.amazonaws.auth.policy.conditions;

import com.amazonaws.auth.policy.Condition;
import java.util.Arrays;

public class IpAddressCondition extends Condition {

    public enum IpAddressComparisonType {
        IpAddress,
        NotIpAddress
    }

    public IpAddressCondition(String ipAddressRange) {
        this(IpAddressComparisonType.IpAddress, ipAddressRange);
    }

    public IpAddressCondition(IpAddressComparisonType type, String ipAddressRange) {
        this.type = type.toString();
        this.conditionKey = ConditionFactory.SOURCE_IP_CONDITION_KEY;
        this.values = Arrays.asList(new String[]{ipAddressRange});
    }
}
