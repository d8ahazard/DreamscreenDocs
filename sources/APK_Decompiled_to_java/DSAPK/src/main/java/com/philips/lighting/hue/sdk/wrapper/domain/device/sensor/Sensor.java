package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor;

import com.philips.lighting.hue.sdk.wrapper.HueLog;
import com.philips.lighting.hue.sdk.wrapper.SessionObject;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.domain.device.Device;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceConfiguration;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceSoftwareUpdate;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceState;
import com.philips.lighting.hue.sdk.wrapper.knowledgebase.DeviceInfo;
import com.philips.lighting.hue.sdk.wrapper.utilities.Executor;
import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;

public class Sensor extends SessionObject implements Device {
    protected DeviceInfo deviceInfo;
    protected DeviceSoftwareUpdate deviceSoftwareUpdate;
    protected byte[] identifier;
    protected SensorConfiguration sensorConfiguration;
    protected DomainType sensorDomainType;
    protected SensorState sensorState;
    protected SensorSubType sensorSubType;
    protected byte[] sensorType;

    /* access modifiers changed from: private */
    public native void fetchNative(int i, BridgeResponseCallback bridgeResponseCallback);

    /* access modifiers changed from: private */
    public native int updateConfigurationNative(DeviceConfiguration deviceConfiguration, int i, BridgeResponseCallback bridgeResponseCallback, boolean z);

    /* access modifiers changed from: private */
    public native int updateStateNative(DeviceState deviceState, int i, BridgeResponseCallback bridgeResponseCallback, boolean z);

    protected Sensor() {
        this.identifier = null;
        this.sensorDomainType = null;
        this.sensorType = null;
        this.sensorSubType = null;
        this.sensorState = null;
        this.sensorConfiguration = null;
        this.deviceSoftwareUpdate = null;
        this.deviceInfo = null;
        this.sensorDomainType = DomainType.SENSOR;
    }

    protected Sensor(DomainType type) {
        this.identifier = null;
        this.sensorDomainType = null;
        this.sensorType = null;
        this.sensorSubType = null;
        this.sensorState = null;
        this.sensorConfiguration = null;
        this.deviceSoftwareUpdate = null;
        this.deviceInfo = null;
        this.sensorDomainType = type;
    }

    protected Sensor(DomainType type, long sessionKey) {
        super(sessionKey);
        this.identifier = null;
        this.sensorDomainType = null;
        this.sensorType = null;
        this.sensorSubType = null;
        this.sensorState = null;
        this.sensorConfiguration = null;
        this.deviceSoftwareUpdate = null;
        this.deviceInfo = null;
        this.sensorDomainType = type;
    }

    public String getIdentifier() {
        return NativeTools.BytesToString(this.identifier);
    }

    public void setIdentifier(String identifier2) {
        HueLog.w("Sensor", "Warning! Changing the identifier on a sensor can have strange effects!");
        this.identifier = NativeTools.StringToBytes(identifier2);
    }

    public String getName() {
        if (this.sensorConfiguration != null) {
            return this.sensorConfiguration.getName();
        }
        return null;
    }

    public DeviceInfo getInfo() {
        return this.deviceInfo;
    }

    public DeviceState getState() {
        return this.sensorState;
    }

    public DeviceConfiguration getConfiguration() {
        return this.sensorConfiguration;
    }

    public DomainType getType() {
        return this.sensorDomainType;
    }

    public int getDomainType() {
        return getTypeAsInt();
    }

    /* access modifiers changed from: protected */
    public int getTypeAsInt() {
        if (this.sensorDomainType != null) {
            return this.sensorDomainType.getValue();
        }
        return DomainType.UNKNOWN.getValue();
    }

    public DeviceSoftwareUpdate getDeviceSoftwareUpdate() {
        return this.deviceSoftwareUpdate;
    }

    public String getSensorType() {
        return NativeTools.BytesToString(this.sensorType);
    }

    public void setSensorType(String sensorType2) {
        this.sensorType = NativeTools.StringToBytes(sensorType2);
    }

    public SensorSubType getSensorSubType() {
        return this.sensorSubType;
    }

    public void setSensorSubType(SensorSubType sensorSubType2) {
        this.sensorSubType = sensorSubType2;
    }

    private void setSensorSubTypeFromInt(int value) {
        this.sensorSubType = SensorSubType.fromValue(value);
    }

    public SensorState getSensorState() {
        return this.sensorState;
    }

    public void setSensorState(SensorState sensorState2) {
        if (sensorState2 == null || isOfType(sensorState2.getType())) {
            this.sensorState = sensorState2;
        }
    }

    public SensorConfiguration getSensorConfiguration() {
        return this.sensorConfiguration;
    }

    public void setSensorConfiguration(SensorConfiguration sensorConfiguration2) {
        if (sensorConfiguration2 == null || isOfType(sensorConfiguration2.getType())) {
            this.sensorConfiguration = sensorConfiguration2;
        }
    }

    public boolean isOfType(DomainType type) {
        if (type == getType() || type == DomainType.SENSOR || type == DomainType.DEVICE) {
            return true;
        }
        return false;
    }

    public boolean isOfType(int typeAsInt) {
        return isOfType(DomainType.fromValue(typeAsInt));
    }

    public void updateState(DeviceState state, BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback) {
        updateState(state, allowedConnectionType, callback, true);
    }

    public void updateState(DeviceState state, BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback, boolean sendDeltasOnly) {
        if (allowedConnectionType == null) {
            BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_ALLOWED);
        } else if (state == null) {
            BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NULL_PARAMETER);
        } else if (!SensorState.class.isAssignableFrom(state.getClass())) {
            BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.WRONG_PARAMETER);
        } else {
            final DeviceState deviceState = state;
            final BridgeConnectionType bridgeConnectionType = allowedConnectionType;
            final BridgeResponseCallback bridgeResponseCallback = callback;
            final boolean z = sendDeltasOnly;
            Executor.submit(new Runnable() {
                public void run() {
                    ReturnCode.fromValue(Sensor.this.updateStateNative(deviceState, bridgeConnectionType.getValue(), bridgeResponseCallback, z));
                }
            });
        }
    }

    public void updateState(DeviceState state, BridgeResponseCallback callback) {
        updateState(state, BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void updateState(DeviceState state, BridgeResponseCallback callback, boolean sendDeltasOnly) {
        updateState(state, BridgeConnectionType.LOCAL_REMOTE, callback, sendDeltasOnly);
    }

    public void updateState(DeviceState state) {
        updateState(state, BridgeConnectionType.LOCAL_REMOTE, (BridgeResponseCallback) null);
    }

    public void updateState(DeviceState state, boolean sendDeltasOnly) {
        updateState(state, BridgeConnectionType.LOCAL_REMOTE, null, sendDeltasOnly);
    }

    public void updateConfiguration(DeviceConfiguration config, BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback) {
        updateConfiguration(config, allowedConnectionType, callback, true);
    }

    public void updateConfiguration(DeviceConfiguration config, BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback, boolean sendDeltasOnly) {
        if (allowedConnectionType == null) {
            BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_ALLOWED);
        } else if (config == null) {
            BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NULL_PARAMETER);
        } else if (!SensorConfiguration.class.isAssignableFrom(config.getClass())) {
            BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.WRONG_PARAMETER);
        } else {
            final DeviceConfiguration deviceConfiguration = config;
            final BridgeConnectionType bridgeConnectionType = allowedConnectionType;
            final BridgeResponseCallback bridgeResponseCallback = callback;
            final boolean z = sendDeltasOnly;
            Executor.submit(new Runnable() {
                public void run() {
                    Sensor.this.updateConfigurationNative(deviceConfiguration, bridgeConnectionType.getValue(), bridgeResponseCallback, z);
                }
            });
        }
    }

    public void updateConfiguration(DeviceConfiguration config, BridgeResponseCallback callback) {
        updateConfiguration(config, BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void updateConfiguration(DeviceConfiguration config, BridgeResponseCallback callback, boolean sendDeltasOnly) {
        updateConfiguration(config, BridgeConnectionType.LOCAL_REMOTE, callback, sendDeltasOnly);
    }

    public void updateConfiguration(DeviceConfiguration config) {
        updateConfiguration(config, BridgeConnectionType.LOCAL_REMOTE, (BridgeResponseCallback) null);
    }

    public void updateConfiguration(DeviceConfiguration config, boolean sendDeltasOnly) {
        updateConfiguration(config, BridgeConnectionType.LOCAL_REMOTE, null, sendDeltasOnly);
    }

    public void syncNative() {
    }

    public int hashCode() {
        int hashCode;
        int hashCode2;
        int hashCode3;
        int hashCode4;
        int i = 0;
        int hashCode5 = super.hashCode() * 31;
        if (this.identifier == null) {
            hashCode = 0;
        } else {
            hashCode = this.identifier.hashCode();
        }
        int i2 = (hashCode5 + hashCode) * 31;
        if (this.sensorConfiguration == null) {
            hashCode2 = 0;
        } else {
            hashCode2 = this.sensorConfiguration.hashCode();
        }
        int i3 = (i2 + hashCode2) * 31;
        if (this.sensorDomainType == null) {
            hashCode3 = 0;
        } else {
            hashCode3 = this.sensorDomainType.hashCode();
        }
        int i4 = (i3 + hashCode3) * 31;
        if (this.sensorState == null) {
            hashCode4 = 0;
        } else {
            hashCode4 = this.sensorState.hashCode();
        }
        int i5 = (i4 + hashCode4) * 31;
        if (this.sensorSubType != null) {
            i = this.sensorSubType.hashCode();
        }
        return i5 + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Sensor other = (Sensor) obj;
        if (this.identifier == null) {
            if (other.identifier != null) {
                return false;
            }
        } else if (!this.identifier.equals(other.identifier)) {
            return false;
        }
        if (this.sensorConfiguration == null) {
            if (other.sensorConfiguration != null) {
                return false;
            }
        } else if (!this.sensorConfiguration.equals(other.sensorConfiguration)) {
            return false;
        }
        if (this.sensorDomainType != other.sensorDomainType) {
            return false;
        }
        if (this.sensorState == null) {
            if (other.sensorState != null) {
                return false;
            }
        } else if (!this.sensorState.equals(other.sensorState)) {
            return false;
        }
        if (this.sensorSubType != other.sensorSubType) {
            return false;
        }
        return true;
    }

    public void setBridge(Bridge bridge) {
        setSessionKey(((SessionObject) bridge).getSessionKey());
    }

    public void fetch(BridgeResponseCallback callback) {
        fetch(BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void fetch(final BridgeConnectionType allowedConnectionType, final BridgeResponseCallback callback) {
        if (allowedConnectionType == null) {
            BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_ALLOWED);
        } else {
            Executor.submit(new Runnable() {
                public void run() {
                    Sensor.this.fetchNative(allowedConnectionType.getValue(), callback);
                }
            });
        }
    }
}
