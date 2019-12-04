package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.environment;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorState;

public class DaylightSensorState extends SensorState {
    private Boolean daylight;

    DaylightSensorState() {
        super(DomainType.DAYLIGHT_SENSOR);
    }

    public Boolean getDaylight() {
        return this.daylight;
    }

    public void setDaylight(Boolean daylight2) {
        this.daylight = daylight2;
    }

    public int hashCode() {
        int hashCode;
        int hashCode2 = super.hashCode() * 31;
        if (this.daylight == null) {
            hashCode = 0;
        } else {
            hashCode = this.daylight.hashCode();
        }
        return hashCode2 + hashCode;
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
        DaylightSensorState other = (DaylightSensorState) obj;
        if (this.daylight == null) {
            if (other.daylight != null) {
                return false;
            }
            return true;
        } else if (!this.daylight.equals(other.daylight)) {
            return false;
        } else {
            return true;
        }
    }
}
