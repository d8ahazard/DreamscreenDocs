package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.generic;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.Sensor;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorSubType;

public class GenericStatusSensor extends Sensor {
    protected GenericStatusSensor(long sessionKey) {
        super(DomainType.GENERIC_STATUS_SENSOR, sessionKey);
        this.sensorSubType = SensorSubType.CLIP;
    }

    public GenericStatusSensor() {
        super(DomainType.GENERIC_STATUS_SENSOR);
        this.sensorSubType = SensorSubType.CLIP;
        this.sensorState = new GenericStatusSensorState();
        this.sensorConfiguration = new GenericStatusSensorConfiguration();
    }

    public GenericStatusSensorState getState() {
        return (GenericStatusSensorState) this.sensorState;
    }

    public GenericStatusSensorConfiguration getConfiguration() {
        return (GenericStatusSensorConfiguration) this.sensorConfiguration;
    }
}
