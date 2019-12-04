package com.philips.lighting.hue.sdk.wrapper.domain.device.light;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.Device;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceConfiguration;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceSoftwareUpdate;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceState;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.Group;
import com.philips.lighting.hue.sdk.wrapper.knowledgebase.DeviceInfo;
import com.philips.lighting.hue.sdk.wrapper.knowledgebase.LightInfo;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class MultiSourceLuminaireImpl extends WrapperObject implements MultiSourceLuminaire {
    @Nonnull
    public final native LightPoint clone();

    /* access modifiers changed from: protected */
    public native void delete();

    public final native void fetch(@Nonnull BridgeConnectionType bridgeConnectionType, @Nonnull BridgeResponseCallback bridgeResponseCallback);

    public final native void fetch(@Nonnull BridgeResponseCallback bridgeResponseCallback);

    public final native Bridge getBridge();

    public final native DeviceConfiguration getConfiguration();

    public final native Device getDevice(@Nonnull DomainType domainType, @Nonnull String str);

    public final native DeviceSoftwareUpdate getDeviceSoftwareUpdate();

    public final native List<Device> getDevices();

    public final native List<Device> getDevices(@Nonnull DomainType domainType);

    @Nonnull
    public final native Group getGroup();

    public final native String getIdentifier();

    public final native DeviceInfo getInfo();

    public final native LightConfiguration getLightConfiguration();

    public final native LightState getLightState();

    public final native LightType getLightType();

    public final native String getName();

    public final native DeviceState getState();

    @Nonnull
    public final native CompoundLightUpdateMode getUpdateMode();

    public final native boolean isComplete();

    public final native boolean isOfType(@Nonnull DomainType domainType);

    public final native void setBridge(Bridge bridge);

    public final native void setIdentifier(String str);

    public final native void setLightConfiguration(LightConfiguration lightConfiguration);

    public final native void setLightState(LightState lightState);

    public final native void setUpdateMode(@Nonnull CompoundLightUpdateMode compoundLightUpdateMode);

    public final native void updateConfiguration(DeviceConfiguration deviceConfiguration);

    public final native void updateConfiguration(DeviceConfiguration deviceConfiguration, @Nonnull BridgeConnectionType bridgeConnectionType, @Nonnull BridgeResponseCallback bridgeResponseCallback);

    public final native void updateConfiguration(DeviceConfiguration deviceConfiguration, @Nonnull BridgeConnectionType bridgeConnectionType, @Nonnull BridgeResponseCallback bridgeResponseCallback, boolean z);

    public final native void updateConfiguration(DeviceConfiguration deviceConfiguration, @Nonnull BridgeResponseCallback bridgeResponseCallback);

    public final native void updateConfiguration(DeviceConfiguration deviceConfiguration, @Nonnull BridgeResponseCallback bridgeResponseCallback, boolean z);

    public final native void updateConfiguration(DeviceConfiguration deviceConfiguration, boolean z);

    public final native void updateState(DeviceState deviceState);

    public final native void updateState(DeviceState deviceState, @Nonnull BridgeConnectionType bridgeConnectionType, @Nonnull BridgeResponseCallback bridgeResponseCallback);

    public final native void updateState(DeviceState deviceState, @Nonnull BridgeConnectionType bridgeConnectionType, @Nonnull BridgeResponseCallback bridgeResponseCallback, boolean z);

    public final native void updateState(DeviceState deviceState, @Nonnull BridgeResponseCallback bridgeResponseCallback);

    public final native void updateState(DeviceState deviceState, @Nonnull BridgeResponseCallback bridgeResponseCallback, boolean z);

    public final native void updateState(DeviceState deviceState, boolean z);

    protected MultiSourceLuminaireImpl(Scope scope) {
    }

    @Nonnull
    public DomainType getType() {
        return DomainType.MULTI_SOURCE_LUMINAIRE;
    }

    @Deprecated
    public boolean isOfType(int typeAsInt) {
        return isOfType(DomainType.fromValue(typeAsInt));
    }

    @Deprecated
    public int getDomainType() {
        return getType().getValue();
    }

    @Nullable
    public LightInfo getLightInfo() {
        return (LightInfo) getInfo();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{getIdentifier(), getLightType(), getState(), getConfiguration(), getDevices(), getGroup()});
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
        MultiSourceLuminaire other = (MultiSourceLuminaire) obj;
        if (!Objects.equals(getIdentifier(), other.getIdentifier()) || !Objects.equals(getLightType(), other.getLightType()) || !Objects.equals(getState(), other.getState()) || !Objects.equals(getConfiguration(), other.getConfiguration()) || !Objects.equals(getDevices(), other.getDevices()) || !Objects.equals(getGroup(), other.getGroup())) {
            return false;
        }
        return true;
    }
}
