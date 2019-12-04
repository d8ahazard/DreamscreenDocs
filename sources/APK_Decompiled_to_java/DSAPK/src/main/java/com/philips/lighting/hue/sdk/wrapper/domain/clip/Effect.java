package com.philips.lighting.hue.sdk.wrapper.domain.clip;

public enum Effect {
    NO_VALUE(-1),
    NONE(0),
    COLORLOOP(1);
    
    private int value;

    private Effect(int val) {
        this.value = val;
    }

    public int getValue() {
        return this.value;
    }

    public static Effect fromValue(int val) {
        Effect[] values;
        for (Effect effect : values()) {
            if (effect.getValue() == val) {
                return effect;
            }
        }
        return NO_VALUE;
    }
}
