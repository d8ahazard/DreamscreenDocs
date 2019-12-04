package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.presence;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.Sensor;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorSubType;

public class PresenceSensor extends Sensor {
    protected PresenceSensor(long sessionKey) {
        super(DomainType.PRESENCE_SENSOR, sessionKey);
        this.sensorSubType = SensorSubType.CLIP;
    }

    public PresenceSensor() {
        super(DomainType.PRESENCE_SENSOR);
        this.sensorSubType = SensorSubType.CLIP;
        this.sensorState = new PresenceSensorState();
        this.sensorConfiguration = new PresenceSensorConfiguration();
    }

    public PresenceSensorState getState() {
        return (PresenceSensorState) this.sensorState;
    }

    public PresenceSensorConfiguration getConfiguration() {
        return (PresenceSensorConfiguration) this.sensorConfiguration;
    }
}
