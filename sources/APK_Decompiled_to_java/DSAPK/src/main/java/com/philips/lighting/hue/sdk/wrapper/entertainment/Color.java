package com.philips.lighting.hue.sdk.wrapper.entertainment;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;

public final class Color extends WrapperObject {
    public final native void applyBrightness(double d);

    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void create(double d, double d2, double d3);

    /* access modifiers changed from: protected */
    public native void create(double d, double d2, double d3, double d4);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native double getAlpha();

    public final native double getBlue();

    public final native double getCurrentBrightness();

    public final native double getGreen();

    public final native double getRed();

    public final native void setAlpha(double d);

    public final native void setBlue(double d);

    public final native void setGreen(double d);

    public final native void setRed(double d);

    public Color() {
        create();
    }

    public Color(double red, double green, double blue) {
        create(red, green, blue);
    }

    public Color(double red, double green, double blue, double alpha) {
        create(red, green, blue, alpha);
    }

    protected Color(Scope scope) {
    }
}
