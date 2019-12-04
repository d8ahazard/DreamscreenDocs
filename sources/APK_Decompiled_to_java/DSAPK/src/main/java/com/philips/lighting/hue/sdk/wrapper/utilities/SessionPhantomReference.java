package com.philips.lighting.hue.sdk.wrapper.utilities;

import com.philips.lighting.hue.sdk.wrapper.SessionObject;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class SessionPhantomReference extends PhantomReference<SessionObject> {
    private long sessionKey;

    public SessionPhantomReference(SessionObject session, ReferenceQueue<SessionObject> refQueue) {
        super(session, refQueue);
        this.sessionKey = session.getSessionKey();
    }

    public long getSessionKey() {
        return this.sessionKey;
    }
}
