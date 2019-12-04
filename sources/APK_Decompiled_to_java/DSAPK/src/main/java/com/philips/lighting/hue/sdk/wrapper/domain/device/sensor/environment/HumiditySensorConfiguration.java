package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.environment;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorConfiguration;

public class HumiditySensorConfiguration extends SensorConfiguration {
    public HumiditySensorConfiguration() {
        super(DomainType.HUMIDITY_SENSOR);
    }

    public HumiditySensorConfiguration(SensorConfiguration config) {
        super(DomainType.HUMIDITY_SENSOR, config);
    }
}
