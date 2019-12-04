package com.philips.lighting.hue.sdk.wrapper.entertainment.effect;

import com.philips.lighting.hue.sdk.wrapper.entertainment.Color;
import com.philips.lighting.hue.sdk.wrapper.entertainment.Location;

public class ExplosionEffect extends LightSourceEffect {
    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void create(String str, int i);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native void prepareEffect(Color color, Location location, double d, double d2, double d3, double d4);

    public ExplosionEffect() {
        super(Scope.Internal);
        create();
    }

    public ExplosionEffect(String name, int layer) {
        super(Scope.Internal);
        create(name, layer);
    }

    public EffectDelegate getDelegate() {
        return null;
    }

    protected ExplosionEffect(Scope scope) {
    }
}
