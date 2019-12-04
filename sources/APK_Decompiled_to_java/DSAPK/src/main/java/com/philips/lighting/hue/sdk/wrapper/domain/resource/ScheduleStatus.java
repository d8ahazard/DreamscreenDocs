package com.philips.lighting.hue.sdk.wrapper.domain.resource;

public enum ScheduleStatus {
    UNKNOWN(-1),
    NONE(0),
    ENABLED(1),
    DISABLED(2),
    ERROR(3);
    
    private int value;

    private ScheduleStatus(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static ScheduleStatus fromValue(int value2) {
        ScheduleStatus[] values;
        for (ScheduleStatus status : values()) {
            if (status.getValue() == value2) {
                return status;
            }
        }
        return UNKNOWN;
    }
}
