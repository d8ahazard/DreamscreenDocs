package com.philips.lighting.hue.sdk.wrapper.domain.clip;

public enum ConnectionState {
    NO_VALUE(-1),
    DISCONNECTED(0),
    CONNECTED(1);
    
    private int value;

    private ConnectionState(int val) {
        this.value = val;
    }

    public int getValue() {
        return this.value;
    }

    public static ConnectionState fromValue(int val) {
        ConnectionState[] values;
        for (ConnectionState cs : values()) {
            if (cs.getValue() == val) {
                return cs;
            }
        }
        return NO_VALUE;
    }
}
