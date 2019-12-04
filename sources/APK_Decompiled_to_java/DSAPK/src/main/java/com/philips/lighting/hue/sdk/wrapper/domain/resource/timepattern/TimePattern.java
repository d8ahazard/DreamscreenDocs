package com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern;

public interface TimePattern {
    boolean equals(Object obj);

    TimePatternType getType();

    int hashCode();

    String toString();
}
