package com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;

public class TimePatternFactory {
    protected static native TimePattern fromStringNative(byte[] bArr);

    public static TimePattern fromString(String timePatternString) {
        if (timePatternString != null) {
            return fromStringNative(NativeTools.StringToBytes(timePatternString));
        }
        return null;
    }
}
