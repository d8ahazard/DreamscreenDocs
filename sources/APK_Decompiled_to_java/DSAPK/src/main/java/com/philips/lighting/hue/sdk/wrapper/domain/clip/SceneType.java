package com.philips.lighting.hue.sdk.wrapper.domain.clip;

public enum SceneType {
    NO_VALUE(-1),
    LIGHT(1),
    GROUP(2);
    
    private int value;

    private SceneType(int val) {
        this.value = val;
    }

    public int getValue() {
        return this.value;
    }

    public static SceneType fromValue(int val) {
        SceneType[] values;
        for (SceneType SceneType : values()) {
            if (SceneType.getValue() == val) {
                return SceneType;
            }
        }
        return NO_VALUE;
    }
}
