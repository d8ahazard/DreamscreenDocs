package com.philips.lighting.hue.sdk.wrapper.entertainment;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;

class ObserverImpl extends WrapperObject implements Observer {
    private Observer observer = null;

    /* access modifiers changed from: protected */
    public native void create(Observer observer2);

    /* access modifiers changed from: protected */
    public native void delete();

    public ObserverImpl(Observer observer2) {
        this.observer = observer2;
        create(this);
    }

    public void onMessage(Message message) {
        if (this.observer != null) {
            this.observer.onMessage(message);
        }
    }

    protected ObserverImpl(Scope scope) {
    }
}
