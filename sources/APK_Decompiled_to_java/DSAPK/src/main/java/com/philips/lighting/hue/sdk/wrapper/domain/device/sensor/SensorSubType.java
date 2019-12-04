package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor;

public enum SensorSubType {
    UNKNOWN(-1),
    CLIP(0),
    ZLL(1),
    ZGP(2);
    
    private int value;

    private SensorSubType(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static SensorSubType fromValue(int value2) {
        SensorSubType[] values;
        for (SensorSubType type : values()) {
            if (type.getValue() == value2) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
