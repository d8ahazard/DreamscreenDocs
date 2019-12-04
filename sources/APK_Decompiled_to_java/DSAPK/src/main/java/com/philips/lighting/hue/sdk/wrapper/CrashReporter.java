package com.philips.lighting.hue.sdk.wrapper;

public class CrashReporter {
    private static CrashReporterCallback callbackInstance = null;

    public static native void deinit();

    public static native String getLatestDumpFilePath();

    private static native void initNative(CrashReporterCallback crashReporterCallback, String str);

    public static void init(CrashReporterCallback callback, String crashReportPath) {
        callbackInstance = callback;
        initNative(callbackInstance, crashReportPath);
    }
}
