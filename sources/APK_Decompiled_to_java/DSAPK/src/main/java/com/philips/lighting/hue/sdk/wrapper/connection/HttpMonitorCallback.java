package com.philips.lighting.hue.sdk.wrapper.connection;

public interface HttpMonitorCallback {
    void onRequestPerformed(HueHTTPRequest hueHTTPRequest, int i);

    void onResponseReceived(HueHTTPResponse hueHTTPResponse);
}
