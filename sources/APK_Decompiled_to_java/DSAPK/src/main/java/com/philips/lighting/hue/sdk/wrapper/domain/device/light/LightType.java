package com.philips.lighting.hue.sdk.wrapper.domain.device.light;

public enum LightType {
    UNKNOWN(-1),
    ON_OFF(1),
    DIMMABLE(2),
    COLOR(3),
    COLOR_TEMPERATURE(4),
    EXTENDED_COLOR(5);
    
    private int value;

    private LightType(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static LightType fromValue(int value2) {
        LightType[] values;
        for (LightType t : values()) {
            if (t.getValue() == value2) {
                return t;
            }
        }
        return UNKNOWN;
    }
}
