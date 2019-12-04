package com.philips.lighting.hue.sdk.wrapper.uicallback;

import com.philips.lighting.hue.sdk.wrapper.connection.RequestCallback;

public abstract class RequestNonUICallback extends RequestCallback {
    public RequestNonUICallback(RequestCallback callback) {
        this.runOnUI = false;
    }
}
