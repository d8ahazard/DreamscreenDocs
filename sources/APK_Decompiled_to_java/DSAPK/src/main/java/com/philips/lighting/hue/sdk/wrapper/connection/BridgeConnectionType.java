package com.philips.lighting.hue.sdk.wrapper.connection;

public enum BridgeConnectionType {
    NONE(0),
    LOCAL(1),
    REMOTE(2),
    LOCAL_REMOTE(3),
    REMOTE_LOCAL(4);
    
    private int type;

    private BridgeConnectionType(int type2) {
        this.type = type2;
    }

    public int getValue() {
        return this.type;
    }

    public static BridgeConnectionType fromValue(int value) {
        BridgeConnectionType[] values;
        for (BridgeConnectionType type2 : values()) {
            if (type2.getValue() == value) {
                return type2;
            }
        }
        return NONE;
    }
}
