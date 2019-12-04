package com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern;

public class TruncatedTimePattern extends TimePatternImpl {
    private TimePatternTime startTime;

    public TruncatedTimePattern(TimePatternTime startTime2) {
        super(TimePatternType.TRUNCATED_TIME);
        this.startTime = startTime2;
    }

    public TimePatternTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(TimePatternTime startTime2) {
        this.startTime = startTime2;
    }

    public int hashCode() {
        int hashCode;
        int hashCode2 = super.hashCode() * 31;
        if (this.startTime == null) {
            hashCode = 0;
        } else {
            hashCode = this.startTime.hashCode();
        }
        return hashCode2 + hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TruncatedTimePattern other = (TruncatedTimePattern) obj;
        if (this.startTime == null) {
            if (other.startTime != null) {
                return false;
            }
            return true;
        } else if (!this.startTime.equals(other.startTime)) {
            return false;
        } else {
            return true;
        }
    }
}
