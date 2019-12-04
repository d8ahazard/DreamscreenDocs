package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.generic;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.Sensor;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorSubType;

public class GenericFlagSensor extends Sensor {
    protected GenericFlagSensor(long sessionKey) {
        super(DomainType.GENERIC_FLAG_SENSOR, sessionKey);
        this.sensorSubType = SensorSubType.CLIP;
    }

    public GenericFlagSensor() {
        super(DomainType.GENERIC_FLAG_SENSOR);
        this.sensorSubType = SensorSubType.CLIP;
        this.sensorState = new GenericFlagSensorState();
        this.sensorConfiguration = new GenericFlagSensorConfiguration();
    }

    public GenericFlagSensorState getState() {
        return (GenericFlagSensorState) this.sensorState;
    }

    public GenericFlagSensorConfiguration getConfiguration() {
        return (GenericFlagSensorConfiguration) this.sensorConfiguration;
    }
}
