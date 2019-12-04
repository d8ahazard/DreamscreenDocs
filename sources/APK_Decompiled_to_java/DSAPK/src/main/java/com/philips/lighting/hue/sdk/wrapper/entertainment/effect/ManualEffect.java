package com.philips.lighting.hue.sdk.wrapper.entertainment.effect;

import com.philips.lighting.hue.sdk.wrapper.entertainment.Color;

public class ManualEffect extends Effect {
    /* access modifiers changed from: protected */
    public native void create(String str, int i);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native void setLightToColor(String str, Color color);

    public ManualEffect() {
        super(Scope.Internal);
        create("", 0);
    }

    public ManualEffect(String name, int priority) {
        super(Scope.Internal);
        create(name, priority);
    }

    public EffectDelegate getDelegate() {
        return null;
    }

    protected ManualEffect(Scope scope) {
        super(scope);
    }
}
