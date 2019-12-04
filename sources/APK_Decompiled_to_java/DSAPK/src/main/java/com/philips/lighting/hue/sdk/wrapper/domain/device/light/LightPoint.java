package com.philips.lighting.hue.sdk.wrapper.domain.device.light;

import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.Device;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceConfiguration;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceSoftwareUpdate;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceState;
import com.philips.lighting.hue.sdk.wrapper.knowledgebase.DeviceInfo;
import com.philips.lighting.hue.sdk.wrapper.knowledgebase.LightInfo;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface LightPoint extends Device {
    @Nonnull
    LightPoint clone();

    void fetch(@Nonnull BridgeConnectionType bridgeConnectionType, @Nonnull BridgeResponseCallback bridgeResponseCallback);

    void fetch(@Nonnull BridgeResponseCallback bridgeResponseCallback);

    Bridge getBridge();

    DeviceConfiguration getConfiguration();

    DeviceSoftwareUpdate getDeviceSoftwareUpdate();

    @Deprecated
    int getDomainType();

    String getIdentifier();

    DeviceInfo getInfo();

    LightConfiguration getLightConfiguration();

    @Nullable
    LightInfo getLightInfo();

    LightState getLightState();

    LightType getLightType();

    String getName();

    DeviceState getState();

    DomainType getType();

    @Deprecated
    boolean isOfType(int i);

    boolean isOfType(@Nonnull DomainType domainType);

    void setBridge(Bridge bridge);

    void setIdentifier(String str);

    void setLightConfiguration(LightConfiguration lightConfiguration);

    void setLightState(LightState lightState);

    void updateConfiguration(DeviceConfiguration deviceConfiguration);

    void updateConfiguration(DeviceConfiguration deviceConfiguration, @Nonnull BridgeConnectionType bridgeConnectionType, @Nonnull BridgeResponseCallback bridgeResponseCallback);

    void updateConfiguration(DeviceConfiguration deviceConfiguration, @Nonnull BridgeConnectionType bridgeConnectionType, @Nonnull BridgeResponseCallback bridgeResponseCallback, boolean z);

    void updateConfiguration(DeviceConfiguration deviceConfiguration, @Nonnull BridgeResponseCallback bridgeResponseCallback);

    void updateConfiguration(DeviceConfiguration deviceConfiguration, @Nonnull BridgeResponseCallback bridgeResponseCallback, boolean z);

    void updateConfiguration(DeviceConfiguration deviceConfiguration, boolean z);

    void updateState(DeviceState deviceState);

    void updateState(DeviceState deviceState, @Nonnull BridgeConnectionType bridgeConnectionType, @Nonnull BridgeResponseCallback bridgeResponseCallback);

    void updateState(DeviceState deviceState, @Nonnull BridgeConnectionType bridgeConnectionType, @Nonnull BridgeResponseCallback bridgeResponseCallback, boolean z);

    void updateState(DeviceState deviceState, @Nonnull BridgeResponseCallback bridgeResponseCallback);

    void updateState(DeviceState deviceState, @Nonnull BridgeResponseCallback bridgeResponseCallback, boolean z);

    void updateState(DeviceState deviceState, boolean z);
}
