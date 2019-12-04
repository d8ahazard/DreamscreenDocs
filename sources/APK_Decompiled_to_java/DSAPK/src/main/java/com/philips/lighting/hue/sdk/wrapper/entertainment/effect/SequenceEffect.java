package com.philips.lighting.hue.sdk.wrapper.entertainment.effect;

import com.philips.lighting.hue.sdk.wrapper.entertainment.Area;
import com.philips.lighting.hue.sdk.wrapper.entertainment.Color;

public class SequenceEffect extends Effect {

    public enum Mode {
        All,
        Round,
        Back,
        Right,
        Last
    }

    public final native boolean addArea(Area area);

    public final native void addColor(Color color);

    /* access modifiers changed from: protected */
    public native void create(String str, int i);

    public final native void decreaseBrightness(double d);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native double getBrightness();

    public final native Mode getMode();

    public final native void inceaseBrightness(double d);

    public final native void setBrightness(double d);

    public final native void setMode(Mode mode);

    public final native void step();

    public SequenceEffect() {
        super(Scope.Internal);
        create("", 0);
    }

    public SequenceEffect(String name, int layer) {
        super(Scope.Internal);
        create(name, layer);
    }

    public EffectDelegate getDelegate() {
        return null;
    }

    protected SequenceEffect(Scope scope) {
        super(scope);
    }
}
