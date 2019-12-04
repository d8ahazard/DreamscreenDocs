package com.philips.lighting.hue.sdk.wrapper.domain.clip;

public enum LightDirection {
    OMNIDIRECTIONAL("omnidirectional"),
    UPWARDS("upwards"),
    DOWNWARDS("downwards"),
    HORIZONTAL("horizontal"),
    VERTICAL("vertical"),
    UNKNOWN("unknowndirection");
    
    private String description;

    private LightDirection(String description2) {
        this.description = description2;
    }

    public String getDescription() {
        return this.description;
    }
}
