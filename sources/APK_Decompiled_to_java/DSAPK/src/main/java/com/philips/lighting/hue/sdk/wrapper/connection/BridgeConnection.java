package com.philips.lighting.hue.sdk.wrapper.connection;

import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;

public interface BridgeConnection {
    ReturnCode connect();

    void disconnect();

    HueHTTPRequest doDelete(String str, RequestCallback requestCallback);

    HueHTTPRequest doGet(String str, RequestCallback requestCallback);

    HueHTTPRequest doPost(String str, String str2, RequestCallback requestCallback);

    HueHTTPRequest doPut(String str, String str2, RequestCallback requestCallback);

    Bridge getBridge();

    BridgeConnectionOptions getConnectionOptions();

    BridgeConnectionType getConnectionType();

    HeartbeatManager getHeartbeatManager();

    ConnectionState getState();

    boolean isAuthenticated();

    boolean isConnected();

    boolean matchesConnectionType(BridgeConnectionType bridgeConnectionType);

    void setBridgeConnectionCallback(BridgeConnectionCallback bridgeConnectionCallback);

    void setConnectionOptions(BridgeConnectionOptions bridgeConnectionOptions);
}
