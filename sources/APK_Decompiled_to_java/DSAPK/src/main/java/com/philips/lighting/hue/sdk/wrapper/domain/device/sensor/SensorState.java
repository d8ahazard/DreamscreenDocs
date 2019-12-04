package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceState;
import java.util.Date;

public abstract class SensorState implements DeviceState {
    protected Date lastUpdated;
    protected DomainType sensorType;

    protected SensorState(DomainType type) {
        this.sensorType = type;
    }

    public DomainType getType() {
        return this.sensorType;
    }

    /* access modifiers changed from: protected */
    public int getTypeAsInt() {
        if (this.sensorType != null) {
            return this.sensorType.getValue();
        }
        return DomainType.UNKNOWN.getValue();
    }

    public Date getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated2) {
        this.lastUpdated = lastUpdated2;
    }

    public boolean isOfType(DomainType type) {
        if (type == getType() || type == DomainType.SENSOR_STATE) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        if (this.lastUpdated == null) {
            hashCode = 0;
        } else {
            hashCode = this.lastUpdated.hashCode();
        }
        int i2 = (hashCode + 31) * 31;
        if (this.sensorType != null) {
            i = this.sensorType.hashCode();
        }
        return i2 + i;
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
        SensorState other = (SensorState) obj;
        if (this.lastUpdated == null) {
            if (other.lastUpdated != null) {
                return false;
            }
        } else if (!this.lastUpdated.equals(other.lastUpdated)) {
            return false;
        }
        if (this.sensorType != other.sensorType) {
            return false;
        }
        return true;
    }
}
