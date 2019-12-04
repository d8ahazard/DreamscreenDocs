package com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern;

public class DayIntervalPattern extends TimePatternImpl {
    private TimePatternDay endDay;
    private int recurringDays;
    private TimePatternDay startDay;

    public DayIntervalPattern(TimePatternDay startDay2, TimePatternDay endDay2, int recurringDays2) {
        super(TimePatternType.DAY_INTERVAL);
        this.startDay = startDay2;
        this.endDay = endDay2;
        this.recurringDays = recurringDays2;
    }

    public int getRecurringDays() {
        return this.recurringDays;
    }

    public void setRecurringDays(int recurringDays2) {
        this.recurringDays = recurringDays2;
    }

    public TimePatternDay getStartDay() {
        return this.startDay;
    }

    public void setStartDay(TimePatternDay startDay2) {
        this.startDay = startDay2;
    }

    public TimePatternDay getEndDay() {
        return this.endDay;
    }

    public void setEndDay(TimePatternDay endDay2) {
        this.endDay = endDay2;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((super.hashCode() * 31) + (this.endDay == null ? 0 : this.endDay.hashCode())) * 31) + this.recurringDays) * 31;
        if (this.startDay != null) {
            i = this.startDay.hashCode();
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
        DayIntervalPattern other = (DayIntervalPattern) obj;
        if (this.endDay == null) {
            if (other.endDay != null) {
                return false;
            }
        } else if (!this.endDay.equals(other.endDay)) {
            return false;
        }
        if (this.recurringDays != other.recurringDays) {
            return false;
        }
        if (this.startDay == null) {
            if (other.startDay != null) {
                return false;
            }
            return true;
        } else if (!this.startDay.equals(other.startDay)) {
            return false;
        } else {
            return true;
        }
    }
}
