package com.philips.lighting.hue.sdk.wrapper.domain.device.light;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;

public final class LightStartUpStateImpl extends WrapperObject implements LightStartUpState {
    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void create(CustomStartUpSettings customStartUpSettings, Boolean bool);

    /* access modifiers changed from: protected */
    public native void create(StartUpMode startUpMode, Boolean bool);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native CustomStartUpSettings getCustomSettings();

    public final native StartUpMode getMode();

    public final native Boolean isConfigured();

    public final native void setCustomSettings(CustomStartUpSettings customStartUpSettings);

    public final native void setMode(StartUpMode startUpMode);

    public LightStartUpStateImpl() {
        create();
    }

    public LightStartUpStateImpl(StartUpMode mode, Boolean isConfigured) {
        create(mode, isConfigured);
    }

    public LightStartUpStateImpl(CustomStartUpSettings settings, Boolean isConfigured) {
        create(settings, isConfigured);
    }

    protected LightStartUpStateImpl(Scope scope) {
    }
}
