package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.environment;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorConfiguration;

public class LightLevelSensorConfiguration extends SensorConfiguration {
    private Integer thresholdDark;
    private Integer thresholdOffset;

    LightLevelSensorConfiguration() {
        super(DomainType.LIGHT_LEVEL_SENSOR);
        this.thresholdDark = null;
        this.thresholdOffset = null;
    }

    public LightLevelSensorConfiguration(SensorConfiguration config) {
        super(DomainType.LIGHT_LEVEL_SENSOR, config);
        this.thresholdDark = null;
        this.thresholdOffset = null;
    }

    public LightLevelSensorConfiguration(Integer thresholdDark2, Integer thresholdOffset2) {
        this();
        this.thresholdDark = thresholdDark2;
        this.thresholdOffset = thresholdOffset2;
    }

    public Integer getThresholdDark() {
        return this.thresholdDark;
    }

    public void setThresholdDark(Integer thresholdDark2) {
        this.thresholdDark = thresholdDark2;
    }

    public Integer getThresholdOffset() {
        return this.thresholdOffset;
    }

    public void setThresholdOffset(Integer thresholdOffset2) {
        this.thresholdOffset = thresholdOffset2;
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        int hashCode2 = super.hashCode() * 31;
        if (this.thresholdDark == null) {
            hashCode = 0;
        } else {
            hashCode = this.thresholdDark.hashCode();
        }
        int i2 = (hashCode2 + hashCode) * 31;
        if (this.thresholdOffset != null) {
            i = this.thresholdOffset.hashCode();
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
        LightLevelSensorConfiguration other = (LightLevelSensorConfiguration) obj;
        if (this.thresholdDark == null) {
            if (other.thresholdDark != null) {
                return false;
            }
        } else if (!this.thresholdDark.equals(other.thresholdDark)) {
            return false;
        }
        if (this.thresholdOffset == null) {
            if (other.thresholdOffset != null) {
                return false;
            }
            return true;
        } else if (!this.thresholdOffset.equals(other.thresholdOffset)) {
            return false;
        } else {
            return true;
        }
    }
}
