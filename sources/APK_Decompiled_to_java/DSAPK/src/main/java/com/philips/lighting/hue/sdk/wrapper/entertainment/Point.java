package com.philips.lighting.hue.sdk.wrapper.entertainment;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;

public final class Point extends WrapperObject {
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

    public Point() {
        create();
    }

    public Point(double x, double y) {
        create(x, y);
    }

    Point(Scope scope) {
    }
}
