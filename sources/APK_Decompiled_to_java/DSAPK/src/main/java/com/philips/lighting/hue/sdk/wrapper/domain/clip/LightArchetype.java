package com.philips.lighting.hue.sdk.wrapper.domain.clip;

public enum LightArchetype {
    CLASSIC_BULB("classicbulb"),
    SULTAN_BULB("sultanbulb"),
    FLOOD_BULB("floodbulb"),
    SPOT_BULB("spotbulb"),
    CANDLE_BULB("candlebulb"),
    PLUG("plug"),
    PENDANT_ROUND("pendantround"),
    PENDANT_LONG("pendantlong"),
    CEILING_ROUND("ceilinground"),
    CEILING_SQUARE("ceilingsquare"),
    FLOOR_SHADE("floorshade"),
    FLOOR_LANTERN("floorlantern"),
    TABLE_SHADE("tableshade"),
    RECESSED_CEILING("recessedceiling"),
    RECESSED_FLOOR("recessedfloor"),
    SINGLE_SPOT("singlespot"),
    DOUBLE_SPOT("doublespot"),
    TABLE_WASH("tablewash"),
    WALL_LANTERN("walllantern"),
    WALL_SHADE("wallshade"),
    FLEXIBLE_LAMP("flexiblelamp"),
    HUE_GO("huego"),
    HUE_LIGHT_STRIP("huelightstrip"),
    GROUND_SPOT("groundspot"),
    WALL_SPOT("wallspot"),
    HUE_IRIS("hueiris"),
    HUE_BLOOM("huebloom"),
    BOLLARD("bollard"),
    HUE_PLAY("hueplay"),
    UNKNOWN("unknownarchetype");
    
    private String description;

    private LightArchetype(String description2) {
        this.description = description2;
    }

    public String getDescription() {
        return this.description;
    }
}
