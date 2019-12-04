package com.philips.lighting.hue.sdk.wrapper.knownbridges;

import com.philips.lighting.hue.sdk.wrapper.domain.BridgeState;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import java.util.List;

public class KnownBridges {
    public static native int forgetAllBridgesNative();

    public static native int forgetBridgeNative(String str);

    public static native List<KnownBridge> getAll();

    private static native int removeBridgeStateNative(String str);

    private static native int removeWhitelistEntryNative(String str);

    public static native BridgeState retrieveBridgeState(String str);

    public static native String retrieveWhitelistEntry(String str);

    public static ReturnCode forgetBridge(String uniqueId) {
        return ReturnCode.fromValue(forgetBridgeNative(uniqueId));
    }

    public static ReturnCode forgetAllBridges() {
        return ReturnCode.fromValue(forgetAllBridgesNative());
    }

    public static ReturnCode removeBridgeState(String uniqueId) {
        return ReturnCode.fromValue(removeBridgeStateNative(uniqueId));
    }

    public static ReturnCode removeWhitelistEntry(String uniqueId) {
        return ReturnCode.fromValue(removeWhitelistEntryNative(uniqueId));
    }
}
