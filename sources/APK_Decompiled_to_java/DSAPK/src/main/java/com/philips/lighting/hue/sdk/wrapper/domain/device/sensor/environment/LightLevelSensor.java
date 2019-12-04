package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.environment;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.Sensor;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorSubType;

public class LightLevelSensor extends Sensor {
    protected LightLevelSensor(long sessionKey) {
        super(DomainType.LIGHT_LEVEL_SENSOR, sessionKey);
        this.sensorSubType = SensorSubType.CLIP;
    }

    public LightLevelSensor() {
        super(DomainType.LIGHT_LEVEL_SENSOR);
        this.sensorSubType = SensorSubType.CLIP;
        this.sensorState = new LightLevelSensorState();
        this.sensorConfiguration = new LightLevelSensorConfiguration();
    }

    public LightLevelSensorState getState() {
        return (LightLevelSensorState) this.sensorState;
    }

    public LightLevelSensorConfiguration getConfiguration() {
        return (LightLevelSensorConfiguration) this.sensorConfiguration;
    }
}
