package com.philips.lighting.hue.sdk.wrapper.utilities;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;

public final class OperationImpl extends WrapperObject implements Operation {
    public final native void cancel();

    /* access modifiers changed from: protected */
    public native void delete();

    public final native boolean isCancelable();

    public final native void waitFinished();

    protected OperationImpl(Scope scope) {
    }
}
