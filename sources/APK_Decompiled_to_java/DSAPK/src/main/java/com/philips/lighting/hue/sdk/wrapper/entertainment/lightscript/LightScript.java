package com.philips.lighting.hue.sdk.wrapper.entertainment.lightscript;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;
import com.philips.lighting.hue.sdk.wrapper.entertainment.Location;
import java.util.List;

public class LightScript extends WrapperObject {
    public final native void addAction(Action action);

    public final native void bindTimeline(Timeline timeline);

    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void create2(String str, long j);

    /* access modifiers changed from: protected */
    public native void create3(String str, long j, List<Location> list);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native void finish();

    public final native List<Action> getActions();

    public final native List<Location> getIdealSetup();

    public final native long getLength();

    public final native String getName();

    public final native void load(String str);

    public final native void setActions(List<Action> list);

    public final native void setIdealSetup(List<Location> list);

    public final native void setLength(long j);

    public final native void setName(String str);

    public final native String toString();

    public LightScript() {
        create();
    }

    public LightScript(String name, long length) {
        create2(name, length);
    }

    public LightScript(String name, long length, List<Location> idealSetup) {
        create3(name, length, idealSetup);
    }

    public static LightScript fromString(String content) {
        LightScript returnValue = new LightScript();
        returnValue.load(content);
        return returnValue;
    }

    protected LightScript(Scope scope) {
    }
}
