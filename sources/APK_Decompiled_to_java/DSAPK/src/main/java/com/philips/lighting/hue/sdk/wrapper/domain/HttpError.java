package com.philips.lighting.hue.sdk.wrapper.domain;

public class HttpError extends HueError {
    private int code;
    private String message;

    protected HttpError(int code2, String message2) {
        this.code = code2;
        this.message = message2;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        String m_msg = "<unknown>";
        if (this.message != null) {
            m_msg = getMessage();
        }
        return "Http Error: code " + this.code + ", " + m_msg;
    }

    public int hashCode() {
        return ((this.code + 31) * 31) + (this.message == null ? 0 : this.message.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        HttpError other = (HttpError) obj;
        if (this.code != other.code) {
            return false;
        }
        if (this.message == null) {
            if (other.message != null) {
                return false;
            }
            return true;
        } else if (!this.message.equals(other.message)) {
            return false;
        } else {
            return true;
        }
    }
}
