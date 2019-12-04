package com.philips.lighting.hue.sdk.wrapper.utilities;

public interface Operation {
    void cancel();

    boolean isCancelable();

    void waitFinished();
}
