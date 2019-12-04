package com.philips.lighting.hue.sdk.wrapper.connection;

public class BridgeConnectionOptionsProvider {
    public static LocalBridgeConnectionOptions provideLocalConnectionOptions(String host, String appName, String deviceName, BridgeConnectionProtocol bridgeConnectionProtocol) {
        return new LocalBridgeConnectionOptions(host, appName, deviceName, bridgeConnectionProtocol);
    }

    public static LocalBridgeConnectionOptions provideLocalConnectionOptions(String host, String appName, String deviceName) {
        return new LocalBridgeConnectionOptions(host, appName, deviceName, BridgeConnectionProtocol.HTTP);
    }
}
