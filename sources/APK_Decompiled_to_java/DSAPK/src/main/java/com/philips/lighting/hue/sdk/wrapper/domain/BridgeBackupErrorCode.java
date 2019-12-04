package com.philips.lighting.hue.sdk.wrapper.domain;

public enum BridgeBackupErrorCode {
    NONE(0),
    IMPORT_FAILED(1),
    EXPORT_FAILED(2);
    
    private int value;

    private BridgeBackupErrorCode(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static BridgeBackupErrorCode fromValue(int value2) {
        BridgeBackupErrorCode[] values;
        for (BridgeBackupErrorCode code : values()) {
            if (code.getValue() == value2) {
                return code;
            }
        }
        return NONE;
    }
}
