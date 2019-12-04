package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.switches;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorConfiguration;

public class OpenCloseSensorConfiguration extends SensorConfiguration {
    public OpenCloseSensorConfiguration() {
        super(DomainType.OPEN_CLOSE_SENSOR);
    }

    public OpenCloseSensorConfiguration(SensorConfiguration config) {
        super(DomainType.OPEN_CLOSE_SENSOR, config);
    }
}
