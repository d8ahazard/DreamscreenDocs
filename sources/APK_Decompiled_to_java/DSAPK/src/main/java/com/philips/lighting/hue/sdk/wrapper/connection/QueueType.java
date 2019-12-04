package com.philips.lighting.hue.sdk.wrapper.connection;

public enum QueueType {
    FIFO(0),
    SMART(1);
    
    private int value;

    private QueueType(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static QueueType fromValue(int value2) {
        QueueType[] values;
        for (QueueType type : values()) {
            if (type.getValue() == value2) {
                return type;
            }
        }
        return FIFO;
    }
}
