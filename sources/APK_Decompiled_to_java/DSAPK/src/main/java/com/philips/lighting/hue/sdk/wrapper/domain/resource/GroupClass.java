package com.philips.lighting.hue.sdk.wrapper.domain.resource;

public enum GroupClass {
    UNKNOWN(-1),
    NONE(0),
    LIVING_ROOM(1),
    KITCHEN(2),
    DINING(3),
    BEDROOM(4),
    KIDS_BEDROOM(5),
    BATHROOM(6),
    NURSERY(7),
    RECREATION(8),
    OFFICE(9),
    GYM(10),
    HALLWAY(11),
    TOILET(12),
    FRONT_DOOR(13),
    GARAGE(14),
    TERRACE(15),
    GARDEN(16),
    DRIVEWAY(17),
    CARPORT(18),
    TV(19),
    FREE(20),
    OTHER(21);
    
    private int value;

    private GroupClass(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static GroupClass fromValue(int value2) {
        GroupClass[] values;
        for (GroupClass type : values()) {
            if (type.getValue() == value2) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
