package com.philips.lighting.hue.sdk.wrapper.uicallback;

import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;

public abstract class BridgeResponseNonUICallback extends BridgeResponseCallback {
    public BridgeResponseNonUICallback(BridgeResponseCallback callback) {
        this.runOnUI = false;
    }
}
