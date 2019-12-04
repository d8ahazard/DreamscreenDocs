package com.philips.lighting.hue.sdk.wrapper.uicallback;

import com.philips.lighting.hue.sdk.wrapper.connection.FoundDevicesCallback;

public abstract class FoundDevicesNonUICallback extends FoundDevicesCallback {
    public FoundDevicesNonUICallback(FoundDevicesCallback callback) {
        this.runOnUI = false;
    }
}
