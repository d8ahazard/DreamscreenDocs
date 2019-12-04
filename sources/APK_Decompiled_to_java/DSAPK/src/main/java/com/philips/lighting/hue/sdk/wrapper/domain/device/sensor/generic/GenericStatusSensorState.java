package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.generic;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorState;

public class GenericStatusSensorState extends SensorState {
    private Integer status;

    GenericStatusSensorState() {
        super(DomainType.GENERIC_STATUS_SENSOR);
    }

    public GenericStatusSensorState(Integer status2) {
        this();
        this.status = status2;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status2) {
        this.status = status2;
    }

    public int hashCode() {
        return (super.hashCode() * 31) + (this.status == null ? 0 : this.status.hashCode());
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
        GenericStatusSensorState other = (GenericStatusSensorState) obj;
        if (this.status == null) {
            if (other.status != null) {
                return false;
            }
            return true;
        } else if (!this.status.equals(other.status)) {
            return false;
        } else {
            return true;
        }
    }
}
