package com.philips.lighting.hue.sdk.wrapper.entertainment.animation;

public class ConstantAnimation extends Animation {
    /* access modifiers changed from: protected */
    public native void create(double d);

    /* access modifiers changed from: protected */
    public native void create(double d, double d2);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native double getLength();

    public ConstantAnimation(double value, double length) {
        super(Scope.Internal);
        create(value, length);
    }

    public ConstantAnimation(double value) {
        super(Scope.Internal);
        create(value);
    }

    public AnimationDelegate getDelegate() {
        return null;
    }

    protected ConstantAnimation(Scope scope) {
        super(scope);
    }
}
