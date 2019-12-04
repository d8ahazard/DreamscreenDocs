package com.philips.lighting.hue.sdk.wrapper.domain;

public enum ReturnCode {
    SUCCESS(0),
    ERROR(-1),
    UNKNOWN(-2),
    INTERNAL(-3),
    NOT_SUPPORTED(-4),
    BUSY(-5),
    ERROR_LOCAL(-6),
    ERROR_REMOTE(-7),
    NULL_PARAMETER(-101),
    WRONG_PARAMETER(-102),
    UNSUPPORTED_PARAMETER_TYPE(-103),
    ITEM_NOT_FOUND(-104),
    NO_PARENT_SET(-105),
    NOT_ALLOWED(-106),
    BRIDGE_NOT_SET(-107),
    CONNECTION_FAILED(-201),
    NO_CONNECTION_AVAILABLE(-202),
    CONNECTION_TYPE_NOT_ALLOWED(-203),
    CONNECTION_NOT_AUTHENTICATED(-204),
    ALREADY_CONNECTED(-205),
    INVALID_CONNECTION_OPTIONS(-206),
    CONNECTION_NOT_LINKED_TO_BRIDGE(-207),
    RESPONSE_CORRUPT(-208),
    DOES_NOT_EXIST(-250),
    REQUEST_TIMED_OUT(-301),
    CANCELED(-302),
    STOPPED(-303),
    MISSING_DISCOVERY_METHODS(-401),
    BRIDGE_QUEUE_FULL(-501),
    NOT_EXECUTED(-502),
    REQUEST_CANCELED(-503),
    REQUEST_DISCARDED_BY_QUEUE(-504),
    RETRY(-505),
    NO_RESPONSE(-506),
    INVALID_REQUEST(-602),
    INVALID_CLIENT(-603),
    AUTHORIZATION_CODE_EXPIRED(-604),
    STATE_MISMATCH(-605),
    PARSER_NOT_FOUND(-606),
    COULD_NOT_PARSE_RESPONSE(-607),
    SERIALIZER_NOT_FOUND(-608),
    INVALID_RESPONSE(-611),
    ACCESS_TOKEN_MISSING(-612),
    ACCESS_TOKEN_BAD(-613),
    ACCESS_TOKEN_EXPIRED(-614),
    LOGIN_REQUIRED(-615),
    AUTHORIZATION_FAILED(-616),
    ACCESS_TOKEN_BRIDGE_MISMATCH(-617),
    RATE_LIMIT_QUOTA_VIOLATION(-618),
    ACCESS_TOKEN_REFRESH_ATTEMPTED(-619),
    TOKEN_UNKNOWN(-620),
    FIRMWARE_UPLOADING_FAILED(-701),
    MIGRATION_FAILED(-801),
    BACKUP_FAILED(-802),
    NO_BACKUP_DATA_FOUND(-803),
    RESTORE_FAILED(-804),
    NOT_IMPLEMENTED(-999),
    UNKNOWN_CODE(-998);
    
    private int value;

    private ReturnCode(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static ReturnCode fromValue(int value2) {
        ReturnCode[] values;
        for (ReturnCode code : values()) {
            if (code.getValue() == value2) {
                return code;
            }
        }
        return UNKNOWN_CODE;
    }
}
