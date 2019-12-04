package com.philips.lighting.hue.sdk.wrapper.domain.resource.rule;

public enum RuleStatus {
    UNKNOWN(-1),
    NONE(0),
    ENABLED(1),
    DISABLED(2),
    LOOP_ERROR(3),
    RESOURCE_DELETED(4),
    ERRORS(5);
    
    private int value;

    private RuleStatus(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static RuleStatus fromValue(int value2) {
        RuleStatus[] values;
        for (RuleStatus rs : values()) {
            if (rs.getValue() == value2) {
                return rs;
            }
        }
        return UNKNOWN;
    }
}
