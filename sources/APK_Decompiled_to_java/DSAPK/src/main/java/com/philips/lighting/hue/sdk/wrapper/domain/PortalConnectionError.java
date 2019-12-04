package com.philips.lighting.hue.sdk.wrapper.domain;

public class PortalConnectionError extends HttpError {
    private Error error;

    public enum Error {
        UNKNOWN(0),
        NOT_ALLOWED(1),
        BAD_REQUEST(2),
        LOGIN_REQUIRED(3),
        INVALID_REQUEST(4),
        INVALID_CLIENT(5),
        AUTHORIZATION_CODE_EXPIRED(6),
        RATE_LIMIT_QUOTA_VIOLATION(7),
        ACCESS_TOKEN_MISSING(8),
        ACCESS_TOKEN_BAD(9),
        ACCESS_TOKEN_EXPIRED(10),
        ACCESS_TOKEN_BRIDGE_MISMATCH(11);
        
        private int value;

        private Error(int value2) {
            this.value = value2;
        }

        public int getValue() {
            return this.value;
        }

        public static Error fromValue(int value2) {
            Error[] values;
            for (Error e : values()) {
                if (e.getValue() == value2) {
                    return e;
                }
            }
            return UNKNOWN;
        }
    }

    protected PortalConnectionError(int code, String message, int enumCode) {
        super(code, message);
        this.error = Error.fromValue(enumCode);
    }

    public Error getError() {
        return this.error;
    }

    public String toString() {
        String m_msg = "<unknown>";
        if (getMessage() != null) {
            m_msg = getMessage();
        }
        return "Remote Connection Error: " + this.error + " (code " + getCode() + "), " + m_msg;
    }
}
