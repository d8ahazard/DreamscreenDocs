package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.switches;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorState;

public class OpenCloseSensorState extends SensorState {
    private Boolean open;

    OpenCloseSensorState() {
        super(DomainType.OPEN_CLOSE_SENSOR);
    }

    public OpenCloseSensorState(Boolean open2) {
        this();
        this.open = open2;
    }

    public Boolean getOpen() {
        return this.open;
    }

    public void setOpen(Boolean open2) {
        this.open = open2;
    }

    public int hashCode() {
        return (super.hashCode() * 31) + (this.open == null ? 0 : this.open.hashCode());
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
        OpenCloseSensorState other = (OpenCloseSensorState) obj;
        if (this.open == null) {
            if (other.open != null) {
                return false;
            }
            return true;
        } else if (!this.open.equals(other.open)) {
            return false;
        } else {
            return true;
        }
    }
}
