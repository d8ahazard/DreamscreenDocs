package com.philips.lighting.hue.sdk.wrapper.entertainment.animation;

public class TriggerableAnimation extends RepeatableAnimation {
    /* access modifiers changed from: protected */
    public native void create(double d, TriggerableAnimationDelegate triggerableAnimationDelegate);

    /* access modifiers changed from: protected */
    public native void delete();

    public native AnimationDelegate getDelegate();

    public final native double getOffset();

    public final native void trigger(String str);

    public final native void triggerOnLevel(String str);

    public TriggerableAnimation(TriggerableAnimationDelegate delegate) throws IllegalArgumentException {
        super(Scope.Internal);
        if (delegate == null) {
            throw new IllegalArgumentException("Not valid delegate object!");
        }
        create(0.0d, delegate);
    }

    public TriggerableAnimation(double repeatCount, TriggerableAnimationDelegate delegate) throws IllegalArgumentException {
        super(Scope.Internal);
        if (delegate == null) {
            throw new IllegalArgumentException("Not valid delegate object!");
        }
        create(repeatCount, delegate);
    }

    protected TriggerableAnimation(Scope scope) {
        super(scope);
    }
}
