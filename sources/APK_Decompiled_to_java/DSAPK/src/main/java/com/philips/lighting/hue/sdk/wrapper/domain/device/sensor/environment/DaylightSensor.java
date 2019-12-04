package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.environment;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.Sensor;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorSubType;

public class DaylightSensor extends Sensor {
    private DaylightSensor(long sessionKey) {
        super(DomainType.DAYLIGHT_SENSOR, sessionKey);
        this.sensorSubType = SensorSubType.CLIP;
    }

    public DaylightSensorState getState() {
        return (DaylightSensorState) this.sensorState;
    }

    public DaylightSensorConfiguration getConfiguration() {
        return (DaylightSensorConfiguration) this.sensorConfiguration;
    }
}
