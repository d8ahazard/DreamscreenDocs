package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.presence;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorConfiguration;
import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;

public class GeofenceSensorConfiguration extends SensorConfiguration {
    private byte[] device;
    private Integer radius;

    GeofenceSensorConfiguration() {
        super(DomainType.GEOFENCE_SENSOR);
    }

    public GeofenceSensorConfiguration(SensorConfiguration config) {
        super(DomainType.GEOFENCE_SENSOR, config);
    }

    public GeofenceSensorConfiguration(String device2, Integer radius2) {
        this();
        setDevice(device2);
        this.radius = radius2;
    }

    public String getDevice() {
        return NativeTools.BytesToString(this.device);
    }

    public void setDevice(String device2) {
        this.device = NativeTools.StringToBytes(device2);
    }

    public Integer getRadius() {
        return this.radius;
    }

    public void setRadius(Integer radius2) {
        this.radius = radius2;
    }

    public int hashCode() {
        return (((super.hashCode() * 31) + Arrays.hashCode(this.device)) * 31) + (this.radius == null ? 0 : this.radius.hashCode());
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
        GeofenceSensorConfiguration other = (GeofenceSensorConfiguration) obj;
        if (!Arrays.equals(this.device, other.device)) {
            return false;
        }
        if (this.radius == null) {
            if (other.radius != null) {
                return false;
            }
            return true;
        } else if (!this.radius.equals(other.radius)) {
            return false;
        } else {
            return true;
        }
    }
}
