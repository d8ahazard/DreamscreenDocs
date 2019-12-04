package com.philips.lighting.hue.sdk.wrapper.entertainment.animation;

public class RepeatableAnimation extends Animation {
    /* access modifiers changed from: protected */
    public native void create(double d, AnimationDelegate animationDelegate);

    /* access modifiers changed from: protected */
    public native void delete();

    public native AnimationDelegate getDelegate();

    public final native double getRepeatCount();

    public final native void setRepeatCount(double d);

    public RepeatableAnimation(AnimationDelegate delegate) throws IllegalArgumentException {
        super(Scope.Internal);
        if (delegate == null) {
            throw new IllegalArgumentException("Not valid delegate object!");
        }
        create(0.0d, delegate);
    }

    public RepeatableAnimation(double repeatCount, AnimationDelegate delegate) throws IllegalArgumentException {
        super(Scope.Internal);
        if (delegate == null) {
            throw new IllegalArgumentException("Not valid delegate object!");
        }
        create(repeatCount, delegate);
    }

    protected RepeatableAnimation(Scope scope) {
        super(scope);
    }
}
