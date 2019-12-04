package com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern;

public class RecurringTimerPattern extends TimerPattern {
    private int recurringTimes;

    public RecurringTimerPattern(TimePatternTime startTime, int recurringTimes2) {
        super(startTime);
        this.recurringTimes = recurringTimes2;
        this.type = TimePatternType.RECURRING_TIMER;
    }

    public int getRecurringTimes() {
        return this.recurringTimes;
    }

    public void setRecurringTimes(int recurringTimes2) {
        this.recurringTimes = recurringTimes2;
    }

    public int hashCode() {
        return (super.hashCode() * 31) + this.recurringTimes;
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
        if (this.recurringTimes != ((RecurringTimerPattern) obj).recurringTimes) {
            return false;
        }
        return true;
    }
}
