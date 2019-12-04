package com.philips.lighting.hue.sdk.wrapper.domain;

import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;

public interface DomainObject {
    void fetch(BridgeConnectionType bridgeConnectionType, BridgeResponseCallback bridgeResponseCallback);

    void fetch(BridgeResponseCallback bridgeResponseCallback);

    int getDomainType();

    String getIdentifier();

    DomainType getType();

    boolean isOfType(int i);

    boolean isOfType(DomainType domainType);

    void setBridge(Bridge bridge);

    void setIdentifier(String str);
}
