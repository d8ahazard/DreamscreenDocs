package com.philips.lighting.hue.sdk.wrapper.domain;

public enum HttpRequestErrorCode {
    NO_VALUE(-1),
    SUCCESS(0),
    UNDEFINED(1),
    CANCELED(2),
    CANNOT_RESOLVE_HOST(3),
    CANNOT_CONNECT(4),
    TIMEOUT(5),
    SERVER_RESET(6),
    INVALID_CERTIFICATE(7),
    GATEWAY_TIMEOUT(8);
    
    private int value;

    private HttpRequestErrorCode(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static HttpRequestErrorCode fromValue(int value2) {
        HttpRequestErrorCode[] values;
        for (HttpRequestErrorCode code : values()) {
            if (code.getValue() == value2) {
                return code;
            }
        }
        return NO_VALUE;
    }
}
