package com.philips.lighting.hue.sdk.wrapper.entertainment.effect;

import com.philips.lighting.hue.sdk.wrapper.entertainment.Color;
import com.philips.lighting.hue.sdk.wrapper.entertainment.animation.Animation;

public class ColorAnimationEffect extends AnimationEffect {
    /* access modifiers changed from: protected */
    public native void create(String str, int i, boolean z, ColorAnimationEffectDelegate colorAnimationEffectDelegate);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native Animation getBlueAnimation();

    public native EffectDelegate getDelegate();

    public final native Animation getGreenAnimation();

    public final native Animation getIntensityAnimation();

    public final native Animation getOpacityAnimation();

    public final native Animation getRedAnimation();

    public final native boolean hasOpacityBoundToIntensity();

    public final native void setBlueAnimaiton(Animation animation);

    public final native void setColorAnimation(Animation animation, Animation animation2, Animation animation3);

    public final native void setFixedColor(Color color);

    public final native void setFixedOpacity(double d);

    public final native void setGreenAnimation(Animation animation);

    public final native void setIntensityAnimation(Animation animation);

    public final native void setOpacityAnimation(Animation animation);

    public final native void setOpacityBoundToIntensity(boolean z);

    public final native void setRedAnimation(Animation animation);

    public ColorAnimationEffect(ColorAnimationEffectDelegate delegate) throws IllegalArgumentException {
        super(Scope.Internal);
        if (delegate == null) {
            throw new IllegalArgumentException("Not valid delegate object!");
        }
        create("", 0, false, delegate);
    }

    public ColorAnimationEffect(String name, int layer, boolean opacityBoundToIntensity, ColorAnimationEffectDelegate delegate) throws IllegalArgumentException {
        super(Scope.Internal);
        if (delegate == null) {
            throw new IllegalArgumentException("Not valid delegate object!");
        }
        create(name, 0, opacityBoundToIntensity, delegate);
    }

    protected ColorAnimationEffect(Scope scope) {
        super(scope);
    }
}
