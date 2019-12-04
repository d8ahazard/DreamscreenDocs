package com.philips.lighting.hue.sdk.wrapper.entertainment;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;
import java.util.List;

public class RenderMessageData extends WrapperObject implements MessageData {
    /* access modifiers changed from: protected */
    public native void delete();

    public final native List<Light> getLights();

    protected RenderMessageData(Scope scope) {
    }
}
