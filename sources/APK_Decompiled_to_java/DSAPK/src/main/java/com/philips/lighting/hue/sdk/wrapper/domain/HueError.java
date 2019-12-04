package com.philips.lighting.hue.sdk.wrapper.domain;

public abstract class HueError {
    public abstract boolean equals(Object obj);

    public abstract int hashCode();

    public abstract String toString();
}
