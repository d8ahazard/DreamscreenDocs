package com.philips.lighting.hue.sdk.wrapper.domain.clip;

public enum ClipActionType {
    UNKNOWN(0),
    UPDATE_DEVICE_STATE(1),
    UPDATE_DEVICE_CONFIGURATION(2),
    DELETE_DEVICE(3),
    SET_GROUP_STATE(4),
    RECALL_RESOURCE(5),
    CREATE_RESOURCE(6),
    DELETE_RESOURCE(7),
    UPDATE_RESOURCE(8);
    
    private int value;

    private ClipActionType(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static ClipActionType fromValue(int value2) {
        ClipActionType[] values;
        for (ClipActionType type : values()) {
            if (type.getValue() == value2) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
