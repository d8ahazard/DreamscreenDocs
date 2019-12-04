package com.philips.lighting.hue.sdk.wrapper.domain;

public class HttpRequestError extends HueError {
    private HttpRequestErrorCode code;
    private String message;

    private HttpRequestError(int code2, String message2) {
        this.code = HttpRequestErrorCode.fromValue(code2);
        this.message = message2;
    }

    public HttpRequestErrorCode getCode() {
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
        return "Http Request Error: code " + this.code + ", " + m_msg;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((this.code == null ? 0 : this.code.hashCode()) + 31) * 31;
        if (this.message != null) {
            i = this.message.hashCode();
        }
        return hashCode + i;
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
        HttpRequestError other = (HttpRequestError) obj;
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
