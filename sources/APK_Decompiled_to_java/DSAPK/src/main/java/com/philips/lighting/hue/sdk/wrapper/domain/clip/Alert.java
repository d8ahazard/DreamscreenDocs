package com.philips.lighting.hue.sdk.wrapper.domain.clip;

public enum Alert {
    NONE("none"),
    SELECT("select"),
    LSELECT("lselect");
    
    private String description;

    private Alert(String val) {
        this.description = val;
    }

    public String getDescription() {
        return this.description;
    }
}
