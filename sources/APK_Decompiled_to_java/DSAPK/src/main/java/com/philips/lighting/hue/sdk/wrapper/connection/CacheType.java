package com.philips.lighting.hue.sdk.wrapper.connection;

public enum CacheType {
    NORMAL(1),
    DIRECT(2);
    
    private int value;

    private CacheType(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static CacheType fromValue(int value2) {
        CacheType[] values;
        for (CacheType type : values()) {
            if (type.getValue() == value2) {
                return type;
            }
        }
        return NORMAL;
    }
}
