package com.philips.lighting.hue.sdk.wrapper.connection;

import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.uicallback.CallbackWrapper;

public abstract class BridgeStateUpdatedCallback extends CallbackWrapper implements BridgeStateUpdatedInternalCallback {
    public abstract void onBridgeStateUpdated(Bridge bridge, BridgeStateUpdatedEvent bridgeStateUpdatedEvent);

    public final void onInternalBridgeStateUpdated(final Bridge bridge, final BridgeStateUpdatedEvent event) {
        post(new Runnable() {
            public void run() {
                BridgeStateUpdatedCallback.this.onBridgeStateUpdated(bridge, event);
            }
        });
    }
}
