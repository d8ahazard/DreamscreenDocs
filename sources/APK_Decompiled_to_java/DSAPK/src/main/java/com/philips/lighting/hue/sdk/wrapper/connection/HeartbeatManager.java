package com.philips.lighting.hue.sdk.wrapper.connection;

import com.philips.lighting.hue.sdk.wrapper.SessionObject;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;

public class HeartbeatManager extends SessionObject {
    private BridgeConnection connection;

    private native int performOneHeartbeatNative(int i);

    private native int startHeartbeatNative(int i, int i2);

    private native int stopAllHeartbeatsNative();

    private native int stopHeartbeatNative(int i);

    public native void setShouldUpdateBridgeState(boolean z);

    private HeartbeatManager(long session_key, BridgeConnection connection2) {
        super(session_key);
        this.connection = connection2;
    }

    public void syncNative() {
    }

    public ReturnCode startHeartbeat(BridgeStateCacheType cacheType, int interval) {
        return ReturnCode.fromValue(startHeartbeatNative(cacheType.getValue(), interval));
    }

    public ReturnCode stopHeartbeat(BridgeStateCacheType cacheType) {
        if (cacheType.equals(BridgeStateCacheType.UNKNOWN)) {
            return ReturnCode.WRONG_PARAMETER;
        }
        return ReturnCode.fromValue(stopHeartbeatNative(cacheType.getValue()));
    }

    public ReturnCode stopAllHeartbeats() {
        return ReturnCode.fromValue(stopAllHeartbeatsNative());
    }

    public ReturnCode performOneHeartbeat(BridgeStateCacheType cacheType) {
        return ReturnCode.fromValue(performOneHeartbeatNative(cacheType.getValue()));
    }
}
