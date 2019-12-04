package com.philips.lighting.hue.sdk.wrapper.domain.device;

import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainObject;
import com.philips.lighting.hue.sdk.wrapper.knowledgebase.DeviceInfo;

public interface Device extends DomainObject {
    boolean equals(Object obj);

    DeviceConfiguration getConfiguration();

    DeviceSoftwareUpdate getDeviceSoftwareUpdate();

    DeviceInfo getInfo();

    String getName();

    DeviceState getState();

    int hashCode();

    void updateConfiguration(DeviceConfiguration deviceConfiguration);

    void updateConfiguration(DeviceConfiguration deviceConfiguration, BridgeConnectionType bridgeConnectionType, BridgeResponseCallback bridgeResponseCallback);

    void updateConfiguration(DeviceConfiguration deviceConfiguration, BridgeConnectionType bridgeConnectionType, BridgeResponseCallback bridgeResponseCallback, boolean z);

    void updateConfiguration(DeviceConfiguration deviceConfiguration, BridgeResponseCallback bridgeResponseCallback);

    void updateConfiguration(DeviceConfiguration deviceConfiguration, BridgeResponseCallback bridgeResponseCallback, boolean z);

    void updateConfiguration(DeviceConfiguration deviceConfiguration, boolean z);

    void updateState(DeviceState deviceState);

    void updateState(DeviceState deviceState, BridgeConnectionType bridgeConnectionType, BridgeResponseCallback bridgeResponseCallback);

    void updateState(DeviceState deviceState, BridgeConnectionType bridgeConnectionType, BridgeResponseCallback bridgeResponseCallback, boolean z);

    void updateState(DeviceState deviceState, BridgeResponseCallback bridgeResponseCallback);

    void updateState(DeviceState deviceState, BridgeResponseCallback bridgeResponseCallback, boolean z);

    void updateState(DeviceState deviceState, boolean z);
}
