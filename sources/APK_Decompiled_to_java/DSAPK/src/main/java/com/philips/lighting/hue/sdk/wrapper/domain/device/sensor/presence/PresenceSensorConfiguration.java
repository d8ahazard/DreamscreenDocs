package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.presence;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorConfiguration;

public class PresenceSensorConfiguration extends SensorConfiguration {
    private Integer maximumSensitivity;
    private Integer sensitivity;

    public PresenceSensorConfiguration() {
        super(DomainType.PRESENCE_SENSOR);
        this.sensitivity = null;
        this.maximumSensitivity = null;
    }

    public PresenceSensorConfiguration(SensorConfiguration config) {
        super(DomainType.PRESENCE_SENSOR, config);
        this.sensitivity = null;
        this.maximumSensitivity = null;
    }

    public PresenceSensorConfiguration(Integer sensitivity2) {
        this();
        this.sensitivity = sensitivity2;
    }

    public PresenceSensorConfiguration(int sensitivity2) {
        this();
        this.sensitivity = new Integer(sensitivity2);
    }

    public Integer getSensitivity() {
        return this.sensitivity;
    }

    public void setSensitivity(Integer sensitivity2) {
        this.sensitivity = sensitivity2;
    }

    public void setSensitivity(int sensitivity2) {
        this.sensitivity = new Integer(sensitivity2);
    }

    public Integer getMaximumSensitivity() {
        return this.maximumSensitivity;
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        int hashCode2 = super.hashCode() * 31;
        if (this.maximumSensitivity == null) {
            hashCode = 0;
        } else {
            hashCode = this.maximumSensitivity.hashCode();
        }
        int i2 = (hashCode2 + hashCode) * 31;
        if (this.sensitivity != null) {
            i = this.sensitivity.hashCode();
        }
        return i2 + i;
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
        PresenceSensorConfiguration other = (PresenceSensorConfiguration) obj;
        if (this.maximumSensitivity == null) {
            if (other.maximumSensitivity != null) {
                return false;
            }
        } else if (!this.maximumSensitivity.equals(other.maximumSensitivity)) {
            return false;
        }
        if (this.sensitivity == null) {
            if (other.sensitivity != null) {
                return false;
            }
            return true;
        } else if (!this.sensitivity.equals(other.sensitivity)) {
            return false;
        } else {
            return true;
        }
    }
}
