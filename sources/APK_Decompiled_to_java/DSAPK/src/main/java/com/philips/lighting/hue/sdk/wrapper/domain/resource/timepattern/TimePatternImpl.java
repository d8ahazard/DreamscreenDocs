package com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern;

public abstract class TimePatternImpl implements TimePattern {
    protected TimePatternType type;

    public native String toString();

    public TimePatternImpl(TimePatternType type2) {
        this.type = type2;
    }

    public TimePatternType getType() {
        return this.type;
    }

    public int hashCode() {
        return (this.type == null ? 0 : this.type.hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (this.type != ((TimePatternImpl) obj).type) {
            return false;
        }
        return true;
    }
}
