package com.philips.lighting.hue.sdk.wrapper.uicallback;

import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateUpdatedCallback;

public abstract class BridgeStateUpdatedNonUICallback extends BridgeStateUpdatedCallback {
    public BridgeStateUpdatedNonUICallback(BridgeStateUpdatedCallback callback) {
        this.runOnUI = false;
    }
}
