package com.philips.lighting.hue.sdk.wrapper.entertainment;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;

public final class Light extends WrapperObject {
    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void create(String str, Location location);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native Color getColor();

    public final native String getId();

    public final native Location getLocation();

    public final native void setColor(Color color);

    public final native void setId(String str);

    public final native void setLocation(Location location);

    public Light() {
        create();
    }

    public Light(String id, Location location) {
        create(id, location);
    }

    protected Light(Scope scope) {
    }
}
