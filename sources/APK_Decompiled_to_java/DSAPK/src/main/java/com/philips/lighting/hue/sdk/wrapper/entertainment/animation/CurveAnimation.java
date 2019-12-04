package com.philips.lighting.hue.sdk.wrapper.entertainment.animation;

import com.philips.lighting.hue.sdk.wrapper.entertainment.Curve;

public class CurveAnimation extends RepeatableAnimation {
    public final native void append(Curve curve);

    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void create(double d, Curve curve);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native Curve getCurve();

    public final native void setCurve(Curve curve);

    public CurveAnimation() {
        super(Scope.Internal);
        create();
    }

    public CurveAnimation(Curve curve) {
        super(Scope.Internal);
        create(0.0d, curve);
    }

    public CurveAnimation(double repeatCount, Curve curve) {
        super(Scope.Internal);
        create(repeatCount, curve);
    }

    public AnimationDelegate getDelegate() {
        return null;
    }

    protected CurveAnimation(Scope scope) {
        super(scope);
    }
}
