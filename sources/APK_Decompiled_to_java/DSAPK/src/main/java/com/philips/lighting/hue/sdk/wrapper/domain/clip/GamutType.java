package com.philips.lighting.hue.sdk.wrapper.domain.clip;

public enum GamutType {
    A("A"),
    B("B"),
    C("C"),
    Other("other");
    
    private String description;

    private GamutType(String description2) {
        this.description = description2;
    }

    public String getDescription() {
        return this.description;
    }

    public static GamutType fromDescription(String description2) {
        GamutType[] values;
        for (GamutType i : values()) {
            if (i.getDescription() == description2) {
                return i;
            }
        }
        return Other;
    }
}
