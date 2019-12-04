package com.philips.lighting.hue.sdk.wrapper.domain;

public class BridgeProvider {
    public static Bridge getBridge() {
        return new BridgeImpl();
    }

    public static Bridge getBridge(String bridgeId) {
        return new BridgeImpl(bridgeId);
    }
}
