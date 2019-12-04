package com.philips.lighting.hue.sdk.wrapper.entertainment.animation;

import com.philips.lighting.hue.sdk.wrapper.entertainment.TweenType;

public class RandomAnimation extends Animation {
    public static final double INFINITE_LENGTH = Double.MAX_VALUE;

    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void create(double d, double d2, double d3, double d4, TweenType tweenType, double d5);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native double getLength();

    public final native double getMaxInterval();

    public final native double getMaxValue();

    public final native double getMinInterval();

    public final native double getMinValue();

    public final native double getTweenType();

    public final native void setLength(double d);

    public final native void setMaxInterval(double d);

    public final native void setMaxValue(double d);

    public final native void setMinInterval(double d);

    public final native void setMinValue(double d);

    public final native void setTweenType(TweenType tweenType);

    public RandomAnimation() {
        super(Scope.Internal);
        create();
    }

    public RandomAnimation(double minValue, double maxValue, double minInterval, double maxInterval, TweenType tweenType, double length) {
        super(Scope.Internal);
        create(minValue, maxValue, minInterval, maxInterval, tweenType, length);
    }

    public AnimationDelegate getDelegate() {
        return null;
    }

    protected RandomAnimation(Scope scope) {
        super(scope);
    }
}
