package com.philips.lighting.hue.sdk.wrapper.entertainment.effect;

import com.philips.lighting.hue.sdk.wrapper.entertainment.Area;

public class AreaEffect extends ColorAnimationEffect {
    static final boolean HAS_OPACITY_BOUND_TO_INTENSITY_DEFAULT = false;

    public final native void addArea(Area area);

    /* access modifiers changed from: protected */
    public native void create(String str, int i, boolean z);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native void setArea(Area area);

    public AreaEffect() {
        super(Scope.Internal);
        create("", 0, false);
    }

    public AreaEffect(String name, int layer) {
        super(Scope.Internal);
        create(name, layer, false);
    }

    public AreaEffect(String name, int layer, boolean hasOpacityBoundToIntensity) {
        super(Scope.Internal);
        create(name, layer, hasOpacityBoundToIntensity);
    }

    public EffectDelegate getDelegate() {
        return null;
    }

    public AreaEffect(Scope scope) {
        super(scope);
    }
}
