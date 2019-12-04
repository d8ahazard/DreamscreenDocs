package com.philips.lighting.hue.sdk.wrapper.entertainment.lightscript;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;

public final class Timeline extends WrapperObject {
    /* access modifiers changed from: protected */
    public native void create(int i);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native boolean equals(Object obj);

    public final native long getLength();

    public final native boolean isRunning();

    public final native long now();

    public final native void pause();

    public final native void resume();

    public final native void setLength(long j);

    public final native void setPosition(long j);

    public final native void start();

    public final native void stop();

    public Timeline(int latencyCompensation) {
        create(latencyCompensation);
    }

    protected Timeline(Scope scope) {
    }
}
