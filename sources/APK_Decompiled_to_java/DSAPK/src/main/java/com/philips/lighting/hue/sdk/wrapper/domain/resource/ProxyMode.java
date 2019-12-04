package com.philips.lighting.hue.sdk.wrapper.domain.resource;

public enum ProxyMode {
    UNKNOWN(-1),
    NONE(0),
    AUTO(1),
    MANUAL(2);
    
    private int value;

    private ProxyMode(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static ProxyMode fromValue(int value2) {
        ProxyMode[] values;
        for (ProxyMode proxyMode : values()) {
            if (proxyMode.getValue() == value2) {
                return proxyMode;
            }
        }
        return UNKNOWN;
    }
}
