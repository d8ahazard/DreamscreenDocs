package com.philips.lighting.hue.sdk.wrapper.entertainment.lightscript;

import com.philips.lighting.hue.sdk.wrapper.entertainment.effect.AnimationEffect;
import com.philips.lighting.hue.sdk.wrapper.entertainment.effect.Effect;
import com.philips.lighting.hue.sdk.wrapper.entertainment.effect.EffectDelegate;

public class Action extends Effect {
    static final long AUTOMATICALLY_DETERMINED_ENDPOSITION = -1;

    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void create(String str, int i, AnimationEffect animationEffect, long j, long j2);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native AnimationEffect getEffect();

    public final native long getEndPosition();

    public final native long getStartPosition();

    public final native void setEffect(AnimationEffect animationEffect);

    public final native void setEndPosition(long j);

    public final native void setStartPosition(long j);

    public Action() {
        super(Scope.Internal);
        create();
    }

    public Action(String name, int layer, AnimationEffect effect, long startPosition) {
        super(Scope.Internal);
        create(name, layer, effect, startPosition, -1);
    }

    public Action(String name, int layer, AnimationEffect effect, long startPosition, long endPosition) {
        super(Scope.Internal);
        create(name, layer, effect, startPosition, endPosition);
    }

    public EffectDelegate getDelegate() {
        return null;
    }

    protected Action(Scope scope) {
        super(scope);
    }
}
