package com.philips.lighting.hue.sdk.wrapper.connection;

import com.philips.lighting.hue.sdk.wrapper.domain.HueError;
import java.util.List;

interface BridgeConnectionInternalCallback {
    void onInternalConnectionError(BridgeConnection bridgeConnection, List<HueError> list);

    void onInternalConnectionEvent(BridgeConnection bridgeConnection, ConnectionEvent connectionEvent);
}
