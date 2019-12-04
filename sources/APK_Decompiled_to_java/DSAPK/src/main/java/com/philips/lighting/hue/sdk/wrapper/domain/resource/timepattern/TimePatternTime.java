package com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern;

public class TimePatternTime {
    private int hour;
    private int minute;
    private int seconds;

    public TimePatternTime() {
    }

    public TimePatternTime(int hour2, int minute2, int seconds2) {
        this.hour = hour2;
        this.minute = minute2;
        this.seconds = seconds2;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour2) {
        this.hour = hour2;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int minute2) {
        this.minute = minute2;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public void setSeconds(int seconds2) {
        this.seconds = seconds2;
    }

    public int hashCode() {
        return ((((this.hour + 31) * 31) + this.minute) * 31) + this.seconds;
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
        TimePatternTime other = (TimePatternTime) obj;
        if (this.hour != other.hour) {
            return false;
        }
        if (this.minute != other.minute) {
            return false;
        }
        if (this.seconds != other.seconds) {
            return false;
        }
        return true;
    }
}
