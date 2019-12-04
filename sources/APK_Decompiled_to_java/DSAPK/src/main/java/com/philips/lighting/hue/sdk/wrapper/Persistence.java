package com.philips.lighting.hue.sdk.wrapper;

import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;

public class Persistence {
    public static native String getStorageLocation();

    private static native int setStorageLocationNative(String str, String str2);

    public static ReturnCode setStorageLocation(String storageLocation, String deviceId) {
        return ReturnCode.fromValue(setStorageLocationNative(storageLocation, deviceId));
    }
}
