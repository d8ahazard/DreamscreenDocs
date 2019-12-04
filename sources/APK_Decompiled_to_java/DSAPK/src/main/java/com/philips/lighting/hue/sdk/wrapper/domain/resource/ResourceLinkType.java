package com.philips.lighting.hue.sdk.wrapper.domain.resource;

public enum ResourceLinkType {
    UNKNOWN(-1),
    NONE(0),
    LINK(1);
    
    private int value;

    private ResourceLinkType(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static ResourceLinkType fromValue(int value2) {
        ResourceLinkType[] values;
        for (ResourceLinkType type : values()) {
            if (type.getValue() == value2) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
