package com.philips.lighting.hue.sdk.wrapper;

import com.philips.lighting.hue.sdk.wrapper.domain.BridgeConfiguration;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;

public abstract class SDK {
    public static native void clearProxySettings();

    public static native String getBridgeDiscoveryURL();

    public static native BridgeConfiguration getBridgeInformation(String str);

    public static native String getProxyAddress();

    public static native int getProxyPort();

    public static native String getRemoteAPIURL();

    public static native String getVersion();

    public static native boolean isProxySet();

    private static native int setBridgeDiscoveryURLNative(String str);

    private static native int setProxyNative(String str, int i);

    private static native int setRemoteAPIURLNative(String str);

    public static ReturnCode setBridgeDiscoveryURL(String bridgeDiscoveryUrl) {
        return ReturnCode.fromValue(setBridgeDiscoveryURLNative(bridgeDiscoveryUrl));
    }

    public static ReturnCode setRemoteAPIURL(String remoteConnectionApi) {
        return ReturnCode.fromValue(setRemoteAPIURLNative(remoteConnectionApi));
    }

    public static ReturnCode setProxy(String address, int port) {
        return ReturnCode.fromValue(setProxyNative(address, port));
    }
}
