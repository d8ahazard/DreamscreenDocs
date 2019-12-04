package com.philips.lighting.hue.sdk.wrapper.domain.clip;

public enum ClipDataType {
    UNDEFINED(0),
    BOOLEAN(1),
    INTEGER(2),
    TIMEPATTERN(3);
    
    private int value;

    private ClipDataType(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static ClipDataType fromValue(int value2) {
        ClipDataType[] values;
        for (ClipDataType type : values()) {
            if (type.getValue() == value2) {
                return type;
            }
        }
        return UNDEFINED;
    }
}
