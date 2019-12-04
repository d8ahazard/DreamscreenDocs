package com.philips.lighting.hue.sdk.wrapper.entertainment;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;
import com.philips.lighting.hue.sdk.wrapper.entertainment.lightscript.Timeline;

public class TimelineMessageData extends WrapperObject implements MessageData {
    /* access modifiers changed from: protected */
    public native void delete();

    public final native Timeline getTimeline();

    protected TimelineMessageData(Scope scope) {
    }
}
