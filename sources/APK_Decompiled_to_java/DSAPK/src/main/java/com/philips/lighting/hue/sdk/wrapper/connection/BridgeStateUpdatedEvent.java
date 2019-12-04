package com.philips.lighting.hue.sdk.wrapper.connection;

public enum BridgeStateUpdatedEvent {
    UNKNOWN(-1),
    INITIALIZED(0),
    FULL_CONFIG(1),
    BRIDGE_CONFIG(2),
    LIGHTS_AND_GROUPS(3),
    SCENES(4),
    SENSORS_AND_SWITCHES(5),
    RULES(6),
    SCHEDULES_AND_TIMERS(7),
    RESOURCE_LINKS(8),
    DEVICE_SEARCH_STATUS(9);
    
    private int value;

    private BridgeStateUpdatedEvent(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static BridgeStateUpdatedEvent fromValue(int value2) {
        BridgeStateUpdatedEvent[] values;
        for (BridgeStateUpdatedEvent event : values()) {
            if (event.getValue() == value2) {
                return event;
            }
        }
        return UNKNOWN;
    }
}
