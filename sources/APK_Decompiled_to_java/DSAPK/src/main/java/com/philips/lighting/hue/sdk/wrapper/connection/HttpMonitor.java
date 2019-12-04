package com.philips.lighting.hue.sdk.wrapper.connection;

public class HttpMonitor {
    public static native long registerCallback(HttpMonitorCallback httpMonitorCallback);

    public static native void unregisterCallback(long j);
}
