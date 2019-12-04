package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.environment;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorState;

public class TemperatureSensorState extends SensorState {
    private Integer temperature;

    TemperatureSensorState() {
        super(DomainType.TEMPERATURE_SENSOR);
    }

    public TemperatureSensorState(Integer temperature2) {
        this();
        this.temperature = temperature2;
    }

    public Integer getTemperature() {
        return this.temperature;
    }

    public void setTemperature(Integer temperature2) {
        this.temperature = temperature2;
    }

    public int hashCode() {
        int hashCode;
        int hashCode2 = super.hashCode() * 31;
        if (this.temperature == null) {
            hashCode = 0;
        } else {
            hashCode = this.temperature.hashCode();
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
        TemperatureSensorState other = (TemperatureSensorState) obj;
        if (this.temperature == null) {
            if (other.temperature != null) {
                return false;
            }
            return true;
        } else if (!this.temperature.equals(other.temperature)) {
            return false;
        } else {
            return true;
        }
    }
}
