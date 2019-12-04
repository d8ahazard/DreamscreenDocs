package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.environment;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorState;

public class HumiditySensorState extends SensorState {
    private Integer humidity;

    HumiditySensorState() {
        super(DomainType.HUMIDITY_SENSOR);
    }

    public HumiditySensorState(Integer humidity2) {
        this();
        this.humidity = humidity2;
    }

    public Integer getHumidity() {
        return this.humidity;
    }

    public void setHumidity(Integer humidity2) {
        this.humidity = humidity2;
    }

    public int hashCode() {
        int hashCode;
        int hashCode2 = super.hashCode() * 31;
        if (this.humidity == null) {
            hashCode = 0;
        } else {
            hashCode = this.humidity.hashCode();
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
        HumiditySensorState other = (HumiditySensorState) obj;
        if (this.humidity == null) {
            if (other.humidity != null) {
                return false;
            }
            return true;
        } else if (!this.humidity.equals(other.humidity)) {
            return false;
        } else {
            return true;
        }
    }
}
