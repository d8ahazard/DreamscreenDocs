package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor;

import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.domain.device.CompoundDevice;
import com.philips.lighting.hue.sdk.wrapper.domain.device.Device;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceConfiguration;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceState;
import java.util.ArrayList;
import java.util.List;

public class CompoundSensor extends Sensor implements CompoundDevice {
    private boolean complete = true;
    private List<Sensor> sensors = null;

    protected CompoundSensor(int domainType, long sessionKey) {
        super(DomainType.fromValue(domainType), sessionKey);
    }

    public List<Device> getDevices() {
        List<Device> devices = new ArrayList<>();
        devices.addAll(this.sensors);
        return devices;
    }

    public List<Device> getDevices(DomainType type) {
        List<Device> devices = new ArrayList<>();
        for (Sensor sensor : this.sensors) {
            if (sensor.isOfType(type)) {
                devices.add(sensor);
            }
        }
        return devices;
    }

    public Device getDevice(DomainType type, String identifier) {
        for (Sensor sensor : this.sensors) {
            if (sensor.isOfType(type) && sensor.getIdentifier() != null && sensor.getIdentifier().equals(identifier)) {
                return sensor;
            }
        }
        return null;
    }

    public boolean isComplete() {
        return this.complete;
    }

    public DeviceState getState() {
        return null;
    }

    public SensorState getSensorState() {
        return null;
    }

    public boolean isOfType(DomainType type) {
        if (super.isOfType(type) || type == DomainType.COMPOUND_DEVICE || type == DomainType.COMPOUND_SENSOR) {
            return true;
        }
        return false;
    }

    public boolean isOfType(int typeAsInt) {
        return isOfType(DomainType.fromValue(typeAsInt));
    }

    public void updateState(DeviceState state, BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback) {
        BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_IMPLEMENTED);
    }

    public void updateState(DeviceState state, BridgeResponseCallback callback) {
        BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_IMPLEMENTED);
    }

    public void updateState(DeviceState state) {
    }

    public void updateConfiguration(DeviceConfiguration config, BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback) {
        BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_IMPLEMENTED);
    }

    public void updateConfiguration(DeviceConfiguration config, BridgeResponseCallback callback) {
        BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_IMPLEMENTED);
    }

    public void updateConfiguration(DeviceConfiguration config) {
    }

    public void syncNative() {
    }

    /* access modifiers changed from: protected */
    public void addSensor(Sensor sensor) {
        initSensors();
        this.sensors.add(sensor);
    }

    /* access modifiers changed from: protected */
    public void initSensors() {
        if (this.sensors == null) {
            this.sensors = new ArrayList();
        }
    }

    /* access modifiers changed from: protected */
    public void clearSensors() {
        if (this.sensors != null) {
            this.sensors.clear();
        }
    }

    /* access modifiers changed from: protected */
    public Sensor[] getSensorsArray() {
        Sensor[] sensorArray = new Sensor[this.sensors.size()];
        for (int i = 0; i < this.sensors.size(); i++) {
            sensorArray[i] = (Sensor) this.sensors.get(i);
        }
        return sensorArray;
    }

    public void fetch(BridgeResponseCallback callback) {
        BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_SUPPORTED);
    }

    public void fetch(BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback) {
        BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_SUPPORTED);
    }

    public int hashCode() {
        return (((super.hashCode() * 31) + (this.complete ? 1231 : 1237)) * 31) + (this.sensors == null ? 0 : this.sensors.hashCode());
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
        CompoundSensor other = (CompoundSensor) obj;
        if (this.complete != other.complete) {
            return false;
        }
        if (this.sensors == null) {
            if (other.sensors != null) {
                return false;
            }
            return true;
        } else if (!this.sensors.equals(other.sensors)) {
            return false;
        } else {
            return true;
        }
    }
}
