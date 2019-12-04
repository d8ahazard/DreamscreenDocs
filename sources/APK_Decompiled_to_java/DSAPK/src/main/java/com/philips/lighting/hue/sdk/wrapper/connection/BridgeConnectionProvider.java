package com.philips.lighting.hue.sdk.wrapper.connection;

import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;

public class BridgeConnectionProvider {
    public static BridgeConnection getBridgeConnection(BridgeConnectionOptions connectionOptions, Bridge bridge) {
        BridgeConnection connection = null;
        switch (connectionOptions.getConnectionType()) {
            case LOCAL:
                connection = new LocalBridgeConnection((LocalBridgeConnectionOptions) connectionOptions);
                break;
            case REMOTE:
                connection = new RemoteBridgeConnection((RemoteBridgeConnectionOptions) connectionOptions);
                break;
        }
        bridge.addBridgeConnection(connection);
        return connection;
    }
}
