package com.philips.lighting.hue.sdk.wrapper.domain.device.light;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;
import com.philips.lighting.hue.sdk.wrapper.utilities.Color.XY;

public final class CustomStartUpSettingsImpl extends WrapperObject implements CustomStartUpSettings {
    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void create(Integer num);

    /* access modifiers changed from: protected */
    public native void create(Integer num, XY xy);

    /* access modifiers changed from: protected */
    public native void create(Integer num, Integer num2);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native Integer getBri();

    public final native Integer getCt();

    public final native XY getXy();

    public final native void setBri(Integer num);

    public final native void setCt(Integer num);

    public final native void setXy(XY xy);

    public CustomStartUpSettingsImpl() {
        create();
    }

    public CustomStartUpSettingsImpl(Integer bri) {
        create(bri);
    }

    public CustomStartUpSettingsImpl(Integer bri, Integer ct) {
        create(bri, ct);
    }

    public CustomStartUpSettingsImpl(Integer bri, XY xy) {
        create(bri, xy);
    }

    protected CustomStartUpSettingsImpl(Scope scope) {
    }
}
