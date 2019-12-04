package com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern;

public class RecurringTimePattern extends TruncatedTimePattern {
    private int recurringDays;

    public RecurringTimePattern(TimePatternTime startTime, int recurringDays2) {
        super(startTime);
        this.recurringDays = recurringDays2;
        this.type = TimePatternType.RECURRING_TIME;
    }

    public int getRecurringDays() {
        return this.recurringDays;
    }

    public void setRecurringDays(int recurringDays2) {
        this.recurringDays = recurringDays2;
    }

    public int hashCode() {
        return (super.hashCode() * 31) + this.recurringDays;
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
        if (this.recurringDays != ((RecurringTimePattern) obj).recurringDays) {
            return false;
        }
        return true;
    }
}
