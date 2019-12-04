package com.philips.lighting.hue.sdk.wrapper.domain.clip;

public enum LightFunction {
    FUNCTIONAL("functional"),
    DECORATIVE("decorative"),
    MIXED("mixed"),
    UNKNOWN("unknownfunction");
    
    private String description;

    private LightFunction(String description2) {
        this.description = description2;
    }

    public String getDescription() {
        return this.description;
    }
}
