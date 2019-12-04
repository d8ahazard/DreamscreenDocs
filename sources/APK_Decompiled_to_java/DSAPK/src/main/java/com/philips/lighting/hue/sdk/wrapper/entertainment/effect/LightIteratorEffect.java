package com.philips.lighting.hue.sdk.wrapper.entertainment.effect;

public class LightIteratorEffect extends ColorAnimationEffect {

    public enum Mode {
        Single,
        Cycle,
        Bounce
    }

    public enum Order {
        LeftRight,
        FrontBack,
        Clockwise,
        InOut,
        Random,
        Group
    }

    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void create2(String str, int i, Order order, Mode mode, double d, boolean z, boolean z2);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native Mode getMode();

    public final native double getOffset();

    public final native Order getOrder();

    public final native boolean hasInvertedIterationOrder();

    public final native boolean hasPreamble();

    public final native void setInvertedIterationOrder(boolean z);

    public final native void setMode(Mode mode);

    public final native void setOffset(double d);

    public final native void setOrder(Order order);

    public final native void setPreamble(boolean z);

    public LightIteratorEffect() {
        super(Scope.Internal);
        create();
    }

    public LightIteratorEffect(String name, int layer, Order order, Mode mode, double offset, boolean preamble, boolean invertOrder) {
        super(Scope.Internal);
        create2(name, layer, order, mode, offset, preamble, invertOrder);
    }

    public EffectDelegate getDelegate() {
        return null;
    }

    protected LightIteratorEffect(Scope scope) {
        super(scope);
    }
}
