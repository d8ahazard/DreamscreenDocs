package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.environment;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorState;

public class LightLevelSensorState extends SensorState {
    private Boolean dark;
    private Boolean daylight;
    private Integer lightLevel;

    LightLevelSensorState() {
        super(DomainType.LIGHT_LEVEL_SENSOR);
        this.daylight = null;
        this.dark = null;
        this.lightLevel = null;
    }

    public LightLevelSensorState(Integer lightLevel2) {
        this();
        this.lightLevel = lightLevel2;
    }

    public Integer getLightLevel() {
        return this.lightLevel;
    }

    public void setLightLevel(Integer lightLevel2) {
        this.lightLevel = lightLevel2;
    }

    public void setLightLevel(int lightLevel2) {
        this.lightLevel = new Integer(lightLevel2);
    }

    public Boolean getDaylight() {
        return this.daylight;
    }

    public Boolean getDark() {
        return this.dark;
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        int hashCode2 = ((super.hashCode() * 31) + (this.dark == null ? 0 : this.dark.hashCode())) * 31;
        if (this.daylight == null) {
            hashCode = 0;
        } else {
            hashCode = this.daylight.hashCode();
        }
        int i2 = (hashCode2 + hashCode) * 31;
        if (this.lightLevel != null) {
            i = this.lightLevel.hashCode();
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
        LightLevelSensorState other = (LightLevelSensorState) obj;
        if (this.dark == null) {
            if (other.dark != null) {
                return false;
            }
        } else if (!this.dark.equals(other.dark)) {
            return false;
        }
        if (this.daylight == null) {
            if (other.daylight != null) {
                return false;
            }
        } else if (!this.daylight.equals(other.daylight)) {
            return false;
        }
        if (this.lightLevel == null) {
            if (other.lightLevel != null) {
                return false;
            }
            return true;
        } else if (!this.lightLevel.equals(other.lightLevel)) {
            return false;
        } else {
            return true;
        }
    }
}
