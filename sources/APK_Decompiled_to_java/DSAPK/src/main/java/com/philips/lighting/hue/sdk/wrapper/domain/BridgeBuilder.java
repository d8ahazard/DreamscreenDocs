package com.philips.lighting.hue.sdk.wrapper.domain;

import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionProtocol;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionProvider;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateUpdatedCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.LocalBridgeConnectionOptions;
import com.philips.lighting.hue.sdk.wrapper.connection.RemoteBridgeConnectionOptions;
import java.util.ArrayList;
import java.util.List;

public class BridgeBuilder {
    private String _accountGuid;
    private String _appId;
    private String _appName;
    private BridgeConnectionCallback _bridgeConnectionCallback = null;
    private BridgeConnectionProtocol _bridgeConnectionProtocol = BridgeConnectionProtocol.PREFER_HTTPS;
    private String _bridgeId;
    private List<BridgeStateUpdatedCallback> _bridgeStateUpdatedCallbacks;
    private String _callbackUrl;
    private String _clientId;
    private String _clientSecret;
    private BridgeConnectionType _connectionType;
    private String _deviceId;
    private String _deviceName;
    private boolean _fastConnectionMode = false;
    private boolean _forbidLoadPersistence = false;
    private boolean _forbidStorePersistence = false;
    private String _ipAddress;
    private String _userName;

    public BridgeBuilder(String appName, String deviceName) {
        this._appName = appName;
        this._deviceName = deviceName;
        this._connectionType = BridgeConnectionType.NONE;
        this._bridgeStateUpdatedCallbacks = new ArrayList();
    }

    public BridgeBuilder setupRemote(String appId, String deviceId, String accountGuid, String callbackUrl, String clientId, String clientSecret) {
        this._appId = appId;
        this._deviceId = deviceId;
        this._accountGuid = accountGuid;
        this._callbackUrl = callbackUrl;
        this._clientId = clientId;
        this._clientSecret = clientSecret;
        return this;
    }

    public BridgeBuilder setIpAddress(String ip_address) {
        this._ipAddress = ip_address;
        return this;
    }

    public BridgeBuilder setForbidLoadPersistence(boolean forbidLoadPersistence) {
        this._forbidLoadPersistence = forbidLoadPersistence;
        return this;
    }

    public BridgeBuilder setForbidStorePersistence(boolean forbidStorePersistence) {
        this._forbidStorePersistence = forbidStorePersistence;
        return this;
    }

    public BridgeBuilder setFastConnectionMode(boolean fastConnectionMode) {
        this._fastConnectionMode = fastConnectionMode;
        return this;
    }

    public BridgeBuilder setUserName(String userName) {
        this._userName = userName;
        return this;
    }

    public BridgeBuilder setConnectionType(BridgeConnectionType connectionType) {
        this._connectionType = connectionType;
        return this;
    }

    public BridgeBuilder setBridgeId(String bridgeId) {
        this._bridgeId = bridgeId;
        return this;
    }

    @Deprecated
    public BridgeBuilder setBridgeConnectionCallback(BridgeConnectionCallback callback) {
        this._bridgeConnectionCallback = callback;
        return this;
    }

    @Deprecated
    public BridgeBuilder addBridgeStateUpdatedCallback(BridgeStateUpdatedCallback callback) {
        this._bridgeStateUpdatedCallbacks.add(callback);
        return this;
    }

    public BridgeBuilder setBridgeConnectionProtocol(BridgeConnectionProtocol bridgeConnectionProtocol) {
        this._bridgeConnectionProtocol = bridgeConnectionProtocol;
        return this;
    }

    public Bridge build() {
        boolean createLocal = false;
        boolean createRemote = false;
        Bridge bridge = new BridgeImpl(this._bridgeId, !this._forbidLoadPersistence, !this._forbidStorePersistence);
        switch (this._connectionType) {
            case REMOTE:
                createRemote = true;
                break;
            case LOCAL:
                createLocal = true;
                break;
            case LOCAL_REMOTE:
            case REMOTE_LOCAL:
                createLocal = true;
                createRemote = true;
                break;
        }
        if (createLocal) {
            LocalBridgeConnectionOptions options = new LocalBridgeConnectionOptions(this._ipAddress, this._appName, this._deviceName, this._userName, this._bridgeConnectionProtocol);
            if (this._fastConnectionMode) {
                options.enableFastConnectionMode(this._bridgeId);
            }
            BridgeConnectionProvider.getBridgeConnection(options, bridge);
        }
        if (createRemote) {
            if (this._clientId.isEmpty() || this._clientSecret.isEmpty()) {
                RemoteBridgeConnectionOptions options2 = new RemoteBridgeConnectionOptions(this._bridgeId, this._accountGuid, this._callbackUrl, this._userName, null, null, this._appName, this._deviceName, this._appId, this._deviceId);
                if (this._fastConnectionMode) {
                    options2.enableFastConnectionMode(this._bridgeId);
                }
                BridgeConnectionProvider.getBridgeConnection(options2, bridge);
            } else {
                RemoteBridgeConnectionOptions options3 = new RemoteBridgeConnectionOptions(this._bridgeId, this._accountGuid, this._callbackUrl, this._userName, this._clientId, this._clientSecret, this._appName, this._deviceName, this._appId, this._deviceId);
                if (this._fastConnectionMode) {
                    options3.enableFastConnectionMode(this._bridgeId);
                }
                BridgeConnectionProvider.getBridgeConnection(options3, bridge);
            }
        }
        bridge.setBridgeConnectionCallback(this._bridgeConnectionCallback);
        for (BridgeStateUpdatedCallback callback : this._bridgeStateUpdatedCallbacks) {
            bridge.addBridgeStateUpdatedCallback(callback);
        }
        return bridge;
    }
}
