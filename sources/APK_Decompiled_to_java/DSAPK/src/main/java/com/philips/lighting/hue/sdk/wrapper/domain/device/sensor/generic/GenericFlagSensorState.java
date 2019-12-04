package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.generic;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorState;

public class GenericFlagSensorState extends SensorState {
    private Boolean flag;

    GenericFlagSensorState() {
        super(DomainType.GENERIC_FLAG_SENSOR);
    }

    public GenericFlagSensorState(Boolean flag2) {
        this();
        this.flag = flag2;
    }

    public Boolean getFlag() {
        return this.flag;
    }

    public void setFlag(Boolean flag2) {
        this.flag = flag2;
    }

    public int hashCode() {
        return (super.hashCode() * 31) + (this.flag == null ? 0 : this.flag.hashCode());
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
        GenericFlagSensorState other = (GenericFlagSensorState) obj;
        if (this.flag == null) {
            if (other.flag != null) {
                return false;
            }
            return true;
        } else if (!this.flag.equals(other.flag)) {
            return false;
        } else {
            return true;
        }
    }
}
