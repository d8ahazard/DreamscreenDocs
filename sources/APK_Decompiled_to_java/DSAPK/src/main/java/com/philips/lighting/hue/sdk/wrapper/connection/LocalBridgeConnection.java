package com.philips.lighting.hue.sdk.wrapper.connection;

import com.philips.lighting.hue.sdk.wrapper.SessionObject;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;

public class LocalBridgeConnection extends SessionObject implements BridgeConnection {
    private BridgeConnectionOptions bridgeConnectionOptions;
    private HeartbeatManager heartbeatManager;

    private native int connectNative();

    private native int getStateNative();

    public native void disconnect();

    public native HueHTTPRequest doDelete(String str, RequestCallback requestCallback);

    public native HueHTTPRequest doGet(String str, RequestCallback requestCallback);

    public native HueHTTPRequest doPost(String str, String str2, RequestCallback requestCallback);

    public native HueHTTPRequest doPut(String str, String str2, RequestCallback requestCallback);

    public native Bridge getBridge();

    /* access modifiers changed from: protected */
    public native void getConnectionOptionsNative();

    public native boolean isAuthenticated();

    public native boolean isConnected();

    public native void setBridgeConnectionCallback(BridgeConnectionCallback bridgeConnectionCallback);

    /* access modifiers changed from: protected */
    public native void setConnectionOptionsNative();

    public native void syncNative();

    private LocalBridgeConnection(long sessionKey) {
        super(sessionKey);
    }

    LocalBridgeConnection(LocalBridgeConnectionOptions connectionOptions) {
        this.bridgeConnectionOptions = connectionOptions;
    }

    public ConnectionState getState() {
        return ConnectionState.fromValue(getStateNative());
    }

    public void setConnectionOptions(BridgeConnectionOptions connectionOptions) {
        this.bridgeConnectionOptions = (LocalBridgeConnectionOptions) connectionOptions;
        setConnectionOptionsNative();
    }

    public BridgeConnectionOptions getConnectionOptions() {
        getConnectionOptionsNative();
        return this.bridgeConnectionOptions;
    }

    public ReturnCode connect() {
        return ReturnCode.fromValue(connectNative());
    }

    public HeartbeatManager getHeartbeatManager() {
        return this.heartbeatManager;
    }

    private void setHeartbeatManager(HeartbeatManager heartbeatManager2) {
        this.heartbeatManager = heartbeatManager2;
    }

    public BridgeConnectionType getConnectionType() {
        return BridgeConnectionType.LOCAL;
    }

    public boolean matchesConnectionType(BridgeConnectionType connectionType) {
        switch (connectionType) {
            case REMOTE:
                return false;
            default:
                return true;
        }
    }
}
