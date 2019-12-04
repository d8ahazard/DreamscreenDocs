package com.philips.lighting.hue.sdk.wrapper.knowledgebase;

public enum KnowledgeBaseType {
    UNKNOWN(-1),
    LIGHTS(0),
    IMAGES(1),
    MANUFACTURERS(2);
    
    private int value;

    private KnowledgeBaseType(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static KnowledgeBaseType fromValue(int value2) {
        KnowledgeBaseType[] values;
        for (KnowledgeBaseType t : values()) {
            if (t.getValue() == value2) {
                return t;
            }
        }
        return UNKNOWN;
    }
}
