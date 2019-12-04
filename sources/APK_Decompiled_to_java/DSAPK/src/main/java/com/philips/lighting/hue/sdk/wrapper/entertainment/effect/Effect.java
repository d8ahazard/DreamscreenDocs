package com.philips.lighting.hue.sdk.wrapper.entertainment.effect;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;
import com.philips.lighting.hue.sdk.wrapper.entertainment.Color;
import com.philips.lighting.hue.sdk.wrapper.entertainment.Light;

public class Effect extends WrapperObject {
    /* access modifiers changed from: protected */
    public native void create(String str, int i, EffectDelegate effectDelegate);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native void disable();

    public final native void enable();

    public final native void finish();

    public final native Color getColor(Light light);

    public native EffectDelegate getDelegate();

    public final native int getLayer();

    public final native String getName();

    public final native String getTypeName();

    public final native boolean isDisabled();

    public final native boolean isEnabled();

    public final native boolean isFinished();

    public final native void render();

    public final native void setLayer(int i);

    public final native void setName(String str);

    public Effect(String name, int layer, EffectDelegate delegate) throws IllegalArgumentException {
        if (delegate == null) {
            throw new IllegalArgumentException("Not valid delegate object!");
        }
        create(name, layer, delegate);
    }

    protected Effect(Scope scope) {
    }
}
