package com.philips.lighting.hue.sdk.wrapper.utilities;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;

public final class SubscriptionImpl extends WrapperObject implements Subscription {
    /* access modifiers changed from: protected */
    public native void delete();

    public final native void disable();

    public final native void enable();

    protected SubscriptionImpl(Scope scope) {
    }
}
