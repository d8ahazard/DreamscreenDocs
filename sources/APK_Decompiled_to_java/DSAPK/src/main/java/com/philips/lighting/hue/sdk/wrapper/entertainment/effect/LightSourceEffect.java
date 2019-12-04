package com.philips.lighting.hue.sdk.wrapper.entertainment.effect;

import com.philips.lighting.hue.sdk.wrapper.entertainment.animation.Animation;

public class LightSourceEffect extends ColorAnimationEffect {
    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void create(String str, int i);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native Animation getRadiusAnimation();

    public final native Animation getXAnimation();

    public final native Animation getYAnimation();

    public final native void setPositionAnimation(Animation animation, Animation animation2);

    public final native void setRadiusAnimation(Animation animation);

    public final native void setXAnimation(Animation animation);

    public final native void setYAnimation(Animation animation);

    public LightSourceEffect() {
        super(Scope.Internal);
        create();
    }

    public LightSourceEffect(String name, int layer) {
        super(Scope.Internal);
        create(name, layer);
    }

    public EffectDelegate getDelegate() {
        return null;
    }

    LightSourceEffect(Scope scope) {
        super(scope);
    }
}
