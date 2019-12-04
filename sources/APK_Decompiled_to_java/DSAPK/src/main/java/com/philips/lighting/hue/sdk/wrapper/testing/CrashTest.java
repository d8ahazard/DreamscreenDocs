package com.philips.lighting.hue.sdk.wrapper.testing;

public class CrashTest {
    public static native void invokeDirectJavaExceptionOnWrapper();

    public static native void invokeIndirectJavaExceptionOnWrapper();

    public static native void invokeSigAbrtOnCore();

    public static native void invokeSigAbrtOnWrapper();

    public static native void invokeSigSegVOnCore();

    public static native void invokeSigSegVOnWrapper();
}
