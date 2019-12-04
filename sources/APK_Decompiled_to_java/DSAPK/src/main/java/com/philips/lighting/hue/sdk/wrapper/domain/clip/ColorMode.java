package com.philips.lighting.hue.sdk.wrapper.domain.clip;

public enum ColorMode {
    NO_VALUE(-1),
    HUE_SATURATION(1),
    XY(2),
    COLOR_TEMPERATURE(3);
    
    private int value;

    private ColorMode(int val) {
        this.value = val;
    }

    public int getValue() {
        return this.value;
    }

    public static ColorMode fromValue(int val) {
        ColorMode[] values;
        for (ColorMode mode : values()) {
            if (mode.getValue() == val) {
                return mode;
            }
        }
        return NO_VALUE;
    }
}
