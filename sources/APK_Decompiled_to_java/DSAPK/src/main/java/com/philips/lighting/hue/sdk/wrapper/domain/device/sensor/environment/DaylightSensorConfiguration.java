package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.environment;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorConfiguration;
import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;

public class DaylightSensorConfiguration extends SensorConfiguration {
    private Boolean configured;
    private byte[] latitude;
    private byte[] longitude;
    private Integer sunriseOffset;
    private Integer sunsetOffset;

    DaylightSensorConfiguration() {
        super(DomainType.DAYLIGHT_SENSOR);
    }

    public DaylightSensorConfiguration(SensorConfiguration config) {
        super(DomainType.DAYLIGHT_SENSOR, config);
    }

    public DaylightSensorConfiguration(String latitude2, String longitude2, Integer sunriseOffset2, Integer sunsetOffset2) {
        this();
        setLatitude(latitude2);
        setLongitude(longitude2);
        this.sunriseOffset = sunriseOffset2;
        this.sunsetOffset = sunsetOffset2;
    }

    public void setLongitude(String longitude2) {
        this.longitude = NativeTools.StringToBytes(longitude2);
    }

    public void setLatitude(String latitude2) {
        this.latitude = NativeTools.StringToBytes(latitude2);
    }

    public Integer getSunriseOffset() {
        return this.sunriseOffset;
    }

    public void setSunriseOffset(Integer sunriseOffset2) {
        this.sunriseOffset = sunriseOffset2;
    }

    public Integer getSunsetOffset() {
        return this.sunsetOffset;
    }

    public void setSunsetOffset(Integer sunsetOffset2) {
        this.sunsetOffset = sunsetOffset2;
    }

    public Boolean isConfigured() {
        return this.configured;
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        int hashCode2 = ((((super.hashCode() * 31) + Arrays.hashCode(this.latitude)) * 31) + Arrays.hashCode(this.longitude)) * 31;
        if (this.sunriseOffset == null) {
            hashCode = 0;
        } else {
            hashCode = this.sunriseOffset.hashCode();
        }
        int i2 = (hashCode2 + hashCode) * 31;
        if (this.sunsetOffset != null) {
            i = this.sunsetOffset.hashCode();
        }
        return i2 + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || getClass() != obj.getClass()) {
            return false;
        }
        DaylightSensorConfiguration other = (DaylightSensorConfiguration) obj;
        if (!Arrays.equals(this.latitude, other.latitude) || !Arrays.equals(this.longitude, other.longitude)) {
            return false;
        }
        if (this.sunriseOffset == null) {
            if (other.sunriseOffset != null) {
                return false;
            }
        } else if (!this.sunriseOffset.equals(other.sunriseOffset)) {
            return false;
        }
        if (this.sunsetOffset == null) {
            if (other.sunsetOffset != null) {
                return false;
            }
        } else if (!this.sunsetOffset.equals(other.sunsetOffset)) {
            return false;
        }
        if (this.configured == null) {
            if (other.configured != null) {
                return false;
            }
            return true;
        } else if (!this.configured.equals(other.configured)) {
            return false;
        } else {
            return true;
        }
    }
}
