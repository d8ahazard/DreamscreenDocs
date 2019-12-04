package com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern;

public enum TimePatternType {
    UNKNOWN(-1),
    ABSOLUTE_TIME(0),
    TRUNCATED_TIME(1),
    RANDOMIZED_TIME(2),
    RECURRING_TIME(3),
    RECURRING_RANDOMIZED_TIME(4),
    TIME_INTERVAL(5),
    RECURRING_TIME_INTERVAL(6),
    DAY_INTERVAL(7),
    TIMER(8),
    RANDOMIZED_TIMER(9),
    RECURRING_TIMER(10),
    RECURRING_RANDOMIZED_TIMER(11);
    
    private int value;

    private TimePatternType(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static TimePatternType fromValue(int value2) {
        TimePatternType[] values;
        for (TimePatternType type : values()) {
            if (type.getValue() == value2) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
