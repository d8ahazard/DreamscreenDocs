package com.philips.lighting.hue.sdk.wrapper.domain.clip;

public enum PortalConnectionState {
    NONE(0),
    DISCONNECTED(1),
    CONNECTING(2),
    CONNECTED(3);
    
    private int value;

    private PortalConnectionState(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static PortalConnectionState fromValue(int value2) {
        PortalConnectionState[] values;
        for (PortalConnectionState state : values()) {
            if (state.getValue() == value2) {
                return state;
            }
        }
        return NONE;
    }
}
