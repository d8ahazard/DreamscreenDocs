package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.switches;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.Sensor;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorSubType;

public class OpenCloseSensor extends Sensor {
    protected OpenCloseSensor(long sessionKey) {
        super(DomainType.OPEN_CLOSE_SENSOR, sessionKey);
        this.sensorSubType = SensorSubType.CLIP;
    }

    public OpenCloseSensor() {
        super(DomainType.OPEN_CLOSE_SENSOR);
        this.sensorSubType = SensorSubType.CLIP;
        this.sensorState = new OpenCloseSensorState();
        this.sensorConfiguration = new OpenCloseSensorConfiguration();
    }

    public OpenCloseSensorState getState() {
        return (OpenCloseSensorState) this.sensorState;
    }

    public OpenCloseSensorConfiguration getConfiguration() {
        return (OpenCloseSensorConfiguration) this.sensorConfiguration;
    }
}
