package com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern;

public class AbsoluteTimePattern extends TimePatternImpl {
    private TimePatternDate startDate;

    public AbsoluteTimePattern(TimePatternDate startDate2) {
        super(TimePatternType.ABSOLUTE_TIME);
        this.startDate = startDate2;
    }

    public void setStartDate(TimePatternDate startDate2) {
        this.startDate = startDate2;
    }

    public TimePatternDate getStartDate() {
        return this.startDate;
    }

    public int hashCode() {
        int hashCode;
        int hashCode2 = super.hashCode() * 31;
        if (this.startDate == null) {
            hashCode = 0;
        } else {
            hashCode = this.startDate.hashCode();
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
        AbsoluteTimePattern other = (AbsoluteTimePattern) obj;
        if (this.startDate == null) {
            if (other.startDate != null) {
                return false;
            }
            return true;
        } else if (!this.startDate.equals(other.startDate)) {
            return false;
        } else {
            return true;
        }
    }
}
