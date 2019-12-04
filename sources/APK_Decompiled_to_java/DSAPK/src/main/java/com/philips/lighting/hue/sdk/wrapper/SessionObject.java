package com.philips.lighting.hue.sdk.wrapper;

import java.util.UUID;

public abstract class SessionObject {
    private long sessionKey;

    public abstract void syncNative();

    protected SessionObject() {
        this.sessionKey = UUID.randomUUID().getMostSignificantBits();
    }

    protected SessionObject(long key) {
        this.sessionKey = key;
    }

    public long getSessionKey() {
        return this.sessionKey;
    }

    public void setSessionKey(long sessionKey2) {
        this.sessionKey = sessionKey2;
    }

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }
}
