package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.environment;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.Sensor;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorSubType;

public class TemperatureSensor extends Sensor {
    protected TemperatureSensor(long sessionKey) {
        super(DomainType.TEMPERATURE_SENSOR, sessionKey);
        this.sensorSubType = SensorSubType.CLIP;
    }

    public TemperatureSensor() {
        super(DomainType.TEMPERATURE_SENSOR);
        this.sensorSubType = SensorSubType.CLIP;
        this.sensorState = new TemperatureSensorState();
        this.sensorConfiguration = new TemperatureSensorConfiguration();
    }

    public TemperatureSensorState getState() {
        return (TemperatureSensorState) this.sensorState;
    }

    public TemperatureSensorConfiguration getConfiguration() {
        return (TemperatureSensorConfiguration) this.sensorConfiguration;
    }
}
