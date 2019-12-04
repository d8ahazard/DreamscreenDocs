package com.philips.lighting.hue.sdk.wrapper.utilities;

public class WifiUtilFactory {
    private static WifiUtil getWifiUtil() {
        return new WifiUtil(InitSdk.getApplicationContext());
    }
}
