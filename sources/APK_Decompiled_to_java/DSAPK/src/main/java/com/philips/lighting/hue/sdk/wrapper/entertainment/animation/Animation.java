package com.philips.lighting.hue.sdk.wrapper.entertainment.animation;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;

public class Animation extends WrapperObject {
    public static final double INFINITE = Double.MAX_VALUE;

    public final native Animation clone();

    /* access modifiers changed from: protected */
    public native void create(AnimationDelegate animationDelegate);

    /* access modifiers changed from: protected */
    public native void delete();

    public native AnimationDelegate getDelegate();

    public final native double getLengthMs();

    public final native double getMarker();

    public final native double getPositionFromValue(double d);

    public final native double getTotalLength();

    public final native String getTypeName();

    public final native double getValue();

    public final native boolean isEndless();

    public final native boolean isPlaying();

    public final native void rewind();

    public final native void setMarker(double d);

    public final native void setValue(double d);

    public final native double updateValue(double d);

    public Animation(AnimationDelegate delegate) throws IllegalArgumentException {
        if (delegate == null) {
            throw new IllegalArgumentException("Not valid delegate object!");
        }
        create(delegate);
    }

    protected Animation(Scope scope) {
    }
}
