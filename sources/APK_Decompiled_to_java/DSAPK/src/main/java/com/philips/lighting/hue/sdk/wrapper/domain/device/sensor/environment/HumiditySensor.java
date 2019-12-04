package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.environment;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.Sensor;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorSubType;

public class HumiditySensor extends Sensor {
    protected HumiditySensor(long sessionKey) {
        super(DomainType.HUMIDITY_SENSOR, sessionKey);
        this.sensorSubType = SensorSubType.CLIP;
    }

    public HumiditySensor() {
        super(DomainType.HUMIDITY_SENSOR);
        this.sensorSubType = SensorSubType.CLIP;
        this.sensorState = new HumiditySensorState();
        this.sensorConfiguration = new HumiditySensorConfiguration();
    }

    public HumiditySensorState getState() {
        return (HumiditySensorState) this.sensorState;
    }

    public HumiditySensorConfiguration getConfiguration() {
        return (HumiditySensorConfiguration) this.sensorConfiguration;
    }
}
