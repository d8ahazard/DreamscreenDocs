package com.philips.lighting.hue.sdk.wrapper.entertainment;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.entertainment.Message.Type;
import com.philips.lighting.hue.sdk.wrapper.entertainment.effect.Effect;
import com.philips.lighting.hue.sdk.wrapper.entertainment.lightscript.LightScript;
import java.util.HashMap;
import java.util.Map;

public final class Entertainment extends WrapperObject {
    private Map _observers = new HashMap();

    public final native void addEffect(Effect effect);

    public final native void addLightScript(LightScript lightScript);

    /* access modifiers changed from: protected */
    public native void create(Bridge bridge, int i, String str);

    /* access modifiers changed from: protected */
    public native void create(Bridge bridge, String str);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native Effect getEffectByName(String str);

    public final native void lockMixer();

    /* access modifiers changed from: protected */
    public final native void registerObserver(ObserverImpl observerImpl, int i);

    public final native void selectGroup(String str);

    public final native void shutDown(Callback callback);

    public final native void start(StartCallback startCallback);

    public final native void stop(Callback callback);

    public final native void unlockMixer();

    /* access modifiers changed from: protected */
    public final native void unregisterObserver(ObserverImpl observerImpl);

    /* access modifiers changed from: protected */
    public final native void unregisterObserver(ObserverImpl observerImpl, int i);

    public Entertainment(Bridge bridge, String groupId) {
        create(bridge, groupId);
    }

    public Entertainment(Bridge bridge, int streamingPort, String groupId) {
        create(bridge, streamingPort, groupId);
    }

    public void registerObserver(Observer observer, Type type) {
        registerObserver(observer, type.flag);
    }

    public void unregisterObserver(Observer observer, Type type) {
        unregisterObserver(observer, type.flag);
    }

    public synchronized void registerObserver(Observer observer, int messageMask) {
        registerObserver(getObserverImpl(observer), messageMask);
    }

    public synchronized void unregisterObserver(Observer observer, int messageMask) {
        unregisterObserver(getObserverImpl(observer), messageMask);
    }

    public synchronized void unregisterObserver(Observer observer) {
        unregisterObserver(getObserverImpl(observer));
        this._observers.remove(observer);
    }

    private ObserverImpl getObserverImpl(Observer observer) {
        if (this._observers.containsKey(observer)) {
            return (ObserverImpl) this._observers.get(observer);
        }
        ObserverImpl observerWrapper = new ObserverImpl(observer);
        this._observers.put(observer, observerWrapper);
        return observerWrapper;
    }

    protected Entertainment(Scope scope) {
    }
}
