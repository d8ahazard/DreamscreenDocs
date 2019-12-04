package com.philips.lighting.hue.sdk.wrapper.connection;

import com.philips.lighting.hue.sdk.wrapper.SessionObject;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;

public class RemoteBridgeConnection extends SessionObject implements BridgeConnection {
    private BridgeConnectionOptions bridgeConnectionOptions;
    private HeartbeatManager heartbeatManager;
    private OAuthTokenPair tokenPair;

    private native int connectNative();

    private native int getStateNative();

    private native int invalidateAuthorizationNative();

    private native void setAccessTokensNative(String str, String str2);

    private native int setAuthorizationUrlNative(String str);

    public native void disconnect();

    public native HueHTTPRequest doDelete(String str, RequestCallback requestCallback);

    public native HueHTTPRequest doGet(String str, RequestCallback requestCallback);

    public native HueHTTPRequest doPost(String str, String str2, RequestCallback requestCallback);

    public native HueHTTPRequest doPut(String str, String str2, RequestCallback requestCallback);

    public native Bridge getBridge();

    /* access modifiers changed from: protected */
    public native void getConnectionOptionsNative();

    public native String getLoginURL();

    public native boolean isAuthenticated();

    public native boolean isConnected();

    public native void setBridgeConnectionCallback(BridgeConnectionCallback bridgeConnectionCallback);

    /* access modifiers changed from: protected */
    public native void setConnectionOptionsNative();

    public native void syncNative();

    private RemoteBridgeConnection(long sessionKey) {
        super(sessionKey);
    }

    RemoteBridgeConnection(RemoteBridgeConnectionOptions connectionOptions) {
        this.bridgeConnectionOptions = connectionOptions;
    }

    public ConnectionState getState() {
        return ConnectionState.fromValue(getStateNative());
    }

    public void setConnectionOptions(BridgeConnectionOptions connectionOptions) {
        this.bridgeConnectionOptions = (RemoteBridgeConnectionOptions) connectionOptions;
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

    public ReturnCode setAuthorizationUrl(String authorizationUrl) {
        return ReturnCode.fromValue(setAuthorizationUrlNative(authorizationUrl));
    }

    @Deprecated
    public void setAccessTokens(OAuthTokenPair tokenPair2) {
        this.tokenPair = tokenPair2;
        setAccessTokensNative(this.tokenPair.getAccessToken(), this.tokenPair.getRefreshToken());
    }

    public ReturnCode invalidateAuthorization() {
        return ReturnCode.fromValue(invalidateAuthorizationNative());
    }

    public BridgeConnectionType getConnectionType() {
        return BridgeConnectionType.REMOTE;
    }

    public boolean matchesConnectionType(BridgeConnectionType connectionType) {
        switch (connectionType) {
            case LOCAL:
                return false;
            default:
                return true;
        }
    }
}
