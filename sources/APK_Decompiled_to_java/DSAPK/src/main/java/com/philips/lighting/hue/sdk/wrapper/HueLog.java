package com.philips.lighting.hue.sdk.wrapper;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;

public class HueLog {

    public class LogComponent {
        public static final int ALL = 2047;
        public static final int APPCORE = 512;
        public static final int CLIENT = 32;
        public static final int COLORCONVERSION = 64;
        public static final int CORE = 2;
        public static final int NETWORK = 8;
        public static final int STREAM = 128;
        public static final int STREAMDTLS = 256;
        public static final int SUPPORT = 4;
        public static final int UNKNOWN = 1;
        public static final int WRAPPER = 16;

        public LogComponent() {
        }
    }

    public enum LogLevel {
        OFF(0),
        ERROR(1),
        WARN(2),
        INFO(3),
        DEBUG(4);
        
        private int value;

        private LogLevel(int level) {
            this.value = level;
        }

        public int getValue() {
            return this.value;
        }
    }

    private static native void logNative(int i, byte[] bArr, byte[] bArr2);

    private static native void setConsoleLogNative(int i, int i2);

    private static native void setFileLogNative(int i, int i2);

    public static void setConsoleLogLevel(LogLevel level) {
        setConsoleLogNative(level.getValue(), LogComponent.ALL);
    }

    public static void setConsoleLogLevel(LogLevel level, int enabledComponents) {
        setConsoleLogNative(level.getValue(), enabledComponents);
    }

    public static void setFileLogLevel(LogLevel level, int enabledComponents) {
        setFileLogNative(level.getValue(), enabledComponents);
    }

    public static void d(String tag, String message) {
        logNative(LogLevel.DEBUG.getValue(), NativeTools.StringToBytes(tag), NativeTools.StringToBytes(message));
    }

    public static void i(String tag, String message) {
        logNative(LogLevel.INFO.getValue(), NativeTools.StringToBytes(tag), NativeTools.StringToBytes(message));
    }

    public static void e(String tag, String message) {
        logNative(LogLevel.ERROR.getValue(), NativeTools.StringToBytes(tag), NativeTools.StringToBytes(message));
    }

    public static void w(String tag, String message) {
        logNative(LogLevel.WARN.getValue(), NativeTools.StringToBytes(tag), NativeTools.StringToBytes(message));
    }
}
