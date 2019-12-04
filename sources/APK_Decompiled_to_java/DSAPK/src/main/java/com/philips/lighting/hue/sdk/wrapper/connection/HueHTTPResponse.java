package com.philips.lighting.hue.sdk.wrapper.connection;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;

public class HueHTTPResponse {
    private byte[] body;
    private int statusCode;

    public HueHTTPResponse(int code, String body2) {
        this.statusCode = code;
        setBody(body2);
    }

    protected HueHTTPResponse(int code, byte[] body2) {
        this.statusCode = code;
        this.body = body2;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int code) {
        this.statusCode = code;
    }

    public String getBody() {
        return NativeTools.BytesToString(this.body);
    }

    public void setBody(String body2) {
        this.body = NativeTools.StringToBytes(body2);
    }

    public int hashCode() {
        return ((Arrays.hashCode(this.body) + 31) * 31) + this.statusCode;
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
        HueHTTPResponse other = (HueHTTPResponse) obj;
        if (!Arrays.equals(this.body, other.body)) {
            return false;
        }
        if (this.statusCode != other.statusCode) {
            return false;
        }
        return true;
    }
}
