package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.generic;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorConfiguration;

public class GenericStatusSensorConfiguration extends SensorConfiguration {
    public GenericStatusSensorConfiguration() {
        super(DomainType.GENERIC_STATUS_SENSOR);
    }

    public GenericStatusSensorConfiguration(SensorConfiguration config) {
        super(DomainType.GENERIC_STATUS_SENSOR, config);
    }
}
