package com.philips.lighting.hue.sdk.wrapper.entertainment.effect;

import com.philips.lighting.hue.sdk.wrapper.entertainment.animation.Animation;
import java.util.List;

public class AnimationEffect extends Effect {
    /* access modifiers changed from: protected */
    public native void create(String str, int i, AnimationEffectDelegate animationEffectDelegate);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native List<Animation> getAnimations();

    public native EffectDelegate getDelegate();

    public final native double getLength();

    public final native boolean isEndless();

    public final native void setSpeedAnimation(Animation animation);

    public AnimationEffect(AnimationEffectDelegate delegate) throws IllegalArgumentException {
        super(Scope.Internal);
        if (delegate == null) {
            throw new IllegalArgumentException("Not valid delegate object!");
        }
        create("", 0, delegate);
    }

    public AnimationEffect(String name, int layer, AnimationEffectDelegate delegate) throws IllegalArgumentException {
        super(Scope.Internal);
        if (delegate == null) {
            throw new IllegalArgumentException("Not valid delegate object!");
        }
        create(name, layer, delegate);
    }

    protected AnimationEffect(Scope scope) {
        super(scope);
    }
}
