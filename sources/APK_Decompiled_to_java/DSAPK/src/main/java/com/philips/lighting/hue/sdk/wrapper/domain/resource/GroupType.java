package com.philips.lighting.hue.sdk.wrapper.domain.resource;

public enum GroupType {
    UNKNOWN(-1),
    LIGHT_GROUP(0),
    LIGHT_SOURCE(1),
    LUMINAIRE(2),
    ROOM(3),
    ENTERTAINMENT(4);
    
    private int value;

    private GroupType(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static GroupType fromValue(int value2) {
        GroupType[] values;
        for (GroupType group : values()) {
            if (group.getValue() == value2) {
                return group;
            }
        }
        return UNKNOWN;
    }
}
