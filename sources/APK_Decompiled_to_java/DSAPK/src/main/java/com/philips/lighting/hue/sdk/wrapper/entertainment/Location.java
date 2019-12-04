package com.philips.lighting.hue.sdk.wrapper.entertainment;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;

public final class Location extends WrapperObject {
    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void create(double d, double d2);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native double getX();

    public final native double getY();

    public final native void setX(double d);

    public final native void setY(double d);

    public Location() {
        create();
    }

    public Location(double x, double y) {
        create(x, y);
    }

    protected Location(Scope scope) {
    }
}
