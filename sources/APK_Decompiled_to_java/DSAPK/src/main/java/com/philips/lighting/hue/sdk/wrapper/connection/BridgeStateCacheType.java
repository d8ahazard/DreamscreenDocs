package com.philips.lighting.hue.sdk.wrapper.connection;

public enum BridgeStateCacheType {
    UNKNOWN(-1),
    FULL_CONFIG(0),
    BRIDGE_CONFIG(1),
    LIGHTS_AND_GROUPS(2),
    SCENES(3),
    SENSORS_AND_SWITCHES(4),
    RULES(5),
    SCHEDULES_AND_TIMERS(6),
    RESOURCE_LINKS(7),
    DEVICE_SEARCH_STATUS(8);
    
    private int value;

    private BridgeStateCacheType(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static BridgeStateCacheType fromValue(int value2) {
        BridgeStateCacheType[] values;
        for (BridgeStateCacheType type : values()) {
            if (type.getValue() == value2) {
                return type;
            }
        }
        return UNKNOWN;
    }

    public BridgeStateUpdatedEvent getMatchingEvent() {
        if (getValue() < 0) {
            return null;
        }
        return BridgeStateUpdatedEvent.fromValue(getValue() + 1);
    }
}
