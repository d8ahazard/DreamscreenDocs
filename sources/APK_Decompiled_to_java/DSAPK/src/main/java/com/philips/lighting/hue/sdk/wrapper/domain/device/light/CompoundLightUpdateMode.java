package com.philips.lighting.hue.sdk.wrapper.domain.device.light;

public enum CompoundLightUpdateMode {
    STREAMING(0),
    UNICAST(1);
    
    private int value;

    private CompoundLightUpdateMode(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static CompoundLightUpdateMode fromValue(int value2) {
        CompoundLightUpdateMode[] values;
        for (CompoundLightUpdateMode mode : values()) {
            if (mode.getValue() == value2) {
                return mode;
            }
        }
        return STREAMING;
    }
}
