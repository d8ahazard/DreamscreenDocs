package com.philips.lighting.hue.sdk.wrapper.domain.device.light;

public interface LightStartUpState {
    CustomStartUpSettings getCustomSettings();

    StartUpMode getMode();

    Boolean isConfigured();

    void setCustomSettings(CustomStartUpSettings customStartUpSettings);

    void setMode(StartUpMode startUpMode);
}
