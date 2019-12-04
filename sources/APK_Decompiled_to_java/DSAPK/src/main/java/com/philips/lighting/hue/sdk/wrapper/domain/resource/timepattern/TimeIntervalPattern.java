package com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern;

public class TimeIntervalPattern extends TimePatternImpl {
    private TimePatternTime endTime;
    private TimePatternTime startTime;

    public TimeIntervalPattern(TimePatternTime startTime2, TimePatternTime endTime2) {
        super(TimePatternType.TIME_INTERVAL);
        this.startTime = startTime2;
        this.endTime = endTime2;
    }

    public TimePatternTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(TimePatternTime startTime2) {
        this.startTime = startTime2;
    }

    public TimePatternTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(TimePatternTime endTime2) {
        this.endTime = endTime2;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((super.hashCode() * 31) + (this.endTime == null ? 0 : this.endTime.hashCode())) * 31;
        if (this.startTime != null) {
            i = this.startTime.hashCode();
        }
        return hashCode + i;
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
        TimeIntervalPattern other = (TimeIntervalPattern) obj;
        if (this.endTime == null) {
            if (other.endTime != null) {
                return false;
            }
        } else if (!this.endTime.equals(other.endTime)) {
            return false;
        }
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
