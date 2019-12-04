package com.philips.lighting.hue.sdk.wrapper.connection;

import com.philips.lighting.hue.sdk.wrapper.domain.HueError;
import com.philips.lighting.hue.sdk.wrapper.uicallback.CallbackWrapper;
import java.util.List;

public abstract class BridgeConnectionCallback extends CallbackWrapper implements BridgeConnectionInternalCallback {
    public abstract void onConnectionError(BridgeConnection bridgeConnection, List<HueError> list);

    public abstract void onConnectionEvent(BridgeConnection bridgeConnection, ConnectionEvent connectionEvent);

    public final void onInternalConnectionEvent(final BridgeConnection connection, final ConnectionEvent event) {
        post(new Runnable() {
            public void run() {
                BridgeConnectionCallback.this.onConnectionEvent(connection, event);
            }
        });
    }

    public final void onInternalConnectionError(final BridgeConnection connection, final List<HueError> errors) {
        post(new Runnable() {
            public void run() {
                BridgeConnectionCallback.this.onConnectionError(connection, errors);
            }
        });
    }
}
