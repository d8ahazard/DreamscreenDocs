package com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern;

public class TimerPattern extends TimePatternImpl {
    private TimePatternTime startTime;

    public TimerPattern(TimePatternTime startTime2) {
        super(TimePatternType.TIMER);
        this.startTime = startTime2;
    }

    public TimePatternTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(TimePatternTime startTime2) {
        this.startTime = startTime2;
    }
}
