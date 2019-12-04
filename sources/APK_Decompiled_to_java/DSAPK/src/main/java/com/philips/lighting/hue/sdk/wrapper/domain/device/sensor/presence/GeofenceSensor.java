package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.presence;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.Sensor;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorSubType;

public class GeofenceSensor extends Sensor {
    protected GeofenceSensor(long sessionKey) {
        super(DomainType.GEOFENCE_SENSOR, sessionKey);
        this.sensorSubType = SensorSubType.CLIP;
    }

    public GeofenceSensor() {
        super(DomainType.GEOFENCE_SENSOR);
        this.sensorSubType = SensorSubType.CLIP;
        this.sensorState = new GeofenceSensorState();
        this.sensorConfiguration = new GeofenceSensorConfiguration();
    }

    public GeofenceSensorState getState() {
        return (GeofenceSensorState) this.sensorState;
    }

    public GeofenceSensorConfiguration getConfiguration() {
        return (GeofenceSensorConfiguration) this.sensorConfiguration;
    }
}
