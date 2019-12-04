package com.philips.lighting.hue.sdk.wrapper.domain;

public enum BridgeBackupStatus {
    IDLE(0),
    START_MIGRATION(1),
    FILEREADY_DISABLED(2),
    PREPARE_RESTORE(3),
    RESTORING(4);
    
    private int value;

    private BridgeBackupStatus(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static BridgeBackupStatus fromValue(int value2) {
        BridgeBackupStatus[] values;
        for (BridgeBackupStatus status : values()) {
            if (status.getValue() == value2) {
                return status;
            }
        }
        return IDLE;
    }
}
