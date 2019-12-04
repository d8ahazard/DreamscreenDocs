package com.philips.lighting.hue.sdk.wrapper.domain.device.light;

import com.philips.lighting.hue.sdk.wrapper.domain.clip.LightArchetype;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.LightDirection;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.LightFunction;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceConfiguration;

public interface LightConfiguration extends DeviceConfiguration {
    LightArchetype getArchetype();

    LightDirection getDirection();

    LightFunction getFunction();

    LightStartUpState getLightStartUpState();

    String getLuminaireUniqueId();

    String getManufacturerName();

    String getModelId();

    String getName();

    String getSwVersion();

    String getUniqueId();

    void setArchetype(LightArchetype lightArchetype);

    void setDirection(LightDirection lightDirection);

    void setFunction(LightFunction lightFunction);

    void setLightStartUpState(LightStartUpState lightStartUpState);

    void setLuminaireUniqueId(String str);

    void setName(String str);
}
