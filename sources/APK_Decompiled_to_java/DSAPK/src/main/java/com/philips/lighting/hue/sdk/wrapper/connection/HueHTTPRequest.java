package com.philips.lighting.hue.sdk.wrapper.connection;

import com.philips.lighting.hue.sdk.wrapper.SessionObject;

public class HueHTTPRequest extends SessionObject {
    private byte[] body;
    private String method;
    private String url;

    public native void cancel();

    public HueHTTPRequest(String url2, String method2, byte[] body2) {
        this.url = url2;
        this.method = method2;
        this.body = body2;
    }

    private HueHTTPRequest() {
    }

    private HueHTTPRequest(long sessionKey) {
        super(sessionKey);
    }

    public void syncNative() {
    }

    public String getUrl() {
        return this.url;
    }

    public String getMethod() {
        return this.method;
    }

    public byte[] getBody() {
        return this.body;
    }
}
