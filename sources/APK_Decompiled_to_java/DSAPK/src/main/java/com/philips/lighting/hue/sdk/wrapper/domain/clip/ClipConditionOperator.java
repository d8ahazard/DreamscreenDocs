package com.philips.lighting.hue.sdk.wrapper.domain.clip;

public enum ClipConditionOperator {
    UNKNOWN(-1),
    NONE(0),
    EQ(1),
    GT(2),
    LT(3),
    DX(4),
    DDX(5),
    STABLE(6),
    NOT_STABLE(7),
    IN(8),
    NOT_IN(9);
    
    private int value;

    private ClipConditionOperator(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static ClipConditionOperator fromValue(int value2) {
        ClipConditionOperator[] values;
        for (ClipConditionOperator op : values()) {
            if (op.getValue() == value2) {
                return op;
            }
        }
        return UNKNOWN;
    }
}
