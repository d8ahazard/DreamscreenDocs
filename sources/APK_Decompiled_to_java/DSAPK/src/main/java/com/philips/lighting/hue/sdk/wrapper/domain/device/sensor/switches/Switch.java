package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.switches;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.Sensor;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorSubType;

public class Switch extends Sensor {
    protected Switch(long sessionKey) {
        super(DomainType.SWITCH, sessionKey);
        this.sensorSubType = SensorSubType.CLIP;
    }

    public Switch() {
        super(DomainType.SWITCH);
        this.sensorSubType = SensorSubType.CLIP;
        this.sensorState = new SwitchState();
        this.sensorConfiguration = new SwitchConfiguration();
    }

    public SwitchState getState() {
        return (SwitchState) this.sensorState;
    }

    public SwitchConfiguration getConfiguration() {
        return (SwitchConfiguration) this.sensorConfiguration;
    }
}
