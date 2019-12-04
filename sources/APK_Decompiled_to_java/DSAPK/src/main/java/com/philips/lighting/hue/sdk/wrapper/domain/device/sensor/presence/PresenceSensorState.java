package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.presence;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorState;

public class PresenceSensorState extends SensorState {
    private Boolean presence;

    PresenceSensorState() {
        super(DomainType.PRESENCE_SENSOR);
    }

    public PresenceSensorState(Boolean presence2) {
        this();
        this.presence = presence2;
    }

    public Boolean getPresence() {
        return this.presence;
    }

    public void setPresence(Boolean presence2) {
        this.presence = presence2;
    }

    public int hashCode() {
        int hashCode;
        int hashCode2 = super.hashCode() * 31;
        if (this.presence == null) {
            hashCode = 0;
        } else {
            hashCode = this.presence.hashCode();
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
        PresenceSensorState other = (PresenceSensorState) obj;
        if (this.presence == null) {
            if (other.presence != null) {
                return false;
            }
            return true;
        } else if (!this.presence.equals(other.presence)) {
            return false;
        } else {
            return true;
        }
    }
}
