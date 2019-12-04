package com.philips.lighting.hue.sdk.wrapper.entertainment.animation;

import com.philips.lighting.hue.sdk.wrapper.entertainment.TweenType;

public class TweenAnimation extends Animation {
    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void create(double d, double d2, double d3, TweenType tweenType);

    /* access modifiers changed from: protected */
    public native void create(double d, double d2, TweenType tweenType);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native double getBeginValue();

    public final native double getEndValue();

    public final native double getTime();

    public final native TweenType getTweenType();

    public final native boolean hasBeginValue();

    public final native void setBeginValue(double d);

    public final native void setEndValue(double d);

    public final native void setHasBeginValue(boolean z);

    public final native void setTime(double d);

    public final native void setTweenType(TweenType tweenType);

    public TweenAnimation() {
        super(Scope.Internal);
        create();
    }

    public TweenAnimation(double endValue, double timeMs, TweenType tweenType) {
        super(Scope.Internal);
        create(endValue, timeMs, tweenType);
    }

    public TweenAnimation(double beginValue, double endValue, double timeMs, TweenType tweenType) {
        super(Scope.Internal);
        create(beginValue, endValue, timeMs, tweenType);
    }

    public AnimationDelegate getDelegate() {
        return null;
    }

    protected TweenAnimation(Scope scope) {
        super(scope);
    }
}
