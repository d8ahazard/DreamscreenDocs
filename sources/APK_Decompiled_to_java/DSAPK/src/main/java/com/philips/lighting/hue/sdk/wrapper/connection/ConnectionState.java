package com.philips.lighting.hue.sdk.wrapper.connection;

public enum ConnectionState {
    NO_VALUE(-1),
    DISCONNECTED(0),
    CONNECTING(1),
    DISCONNECTING(2),
    AUTHENTICATING(3),
    AUTHENTICATED(4),
    WAITING_FOR_AUTHENTICATION(5),
    RESTORING_CONNECTION(6);
    
    private int stateCode;

    private ConnectionState(int stateCode2) {
        this.stateCode = stateCode2;
    }

    public int getValue() {
        return this.stateCode;
    }

    public static ConnectionState fromValue(int val) {
        ConnectionState[] values;
        for (ConnectionState e : values()) {
            if (e.getValue() == val) {
                return e;
            }
        }
        return NO_VALUE;
    }
}
