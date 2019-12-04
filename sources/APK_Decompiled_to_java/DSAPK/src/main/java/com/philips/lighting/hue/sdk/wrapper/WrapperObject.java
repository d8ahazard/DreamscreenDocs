package com.philips.lighting.hue.sdk.wrapper;

public abstract class WrapperObject {
    private long reference = 0;

    protected enum Scope {
        Internal
    }

    /* access modifiers changed from: protected */
    public abstract void delete();

    public void setReference(long reference2) {
        this.reference = reference2;
    }

    public long getReference() {
        return this.reference;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }
}
