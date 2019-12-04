package com.philips.lighting.hue.sdk.wrapper.entertainment;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;
import com.philips.lighting.hue.sdk.wrapper.entertainment.animation.Animation;

public final class Channel extends WrapperObject {
    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void create(Location location, Animation animation, Animation animation2, Animation animation3, Animation animation4);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native Animation getAlpha();

    public final native Animation getB();

    public final native Animation getG();

    public final native Location getPosition();

    public final native Animation getR();

    public final native void setAlpha(Animation animation);

    public final native void setB(Animation animation);

    public final native void setG(Animation animation);

    public final native void setPosition(Location location);

    public final native void setR(Animation animation);

    public Channel() {
        create();
    }

    public Channel(Location position, Animation r, Animation g, Animation b, Animation alpha) {
        create(position, r, g, b, alpha);
    }
}
