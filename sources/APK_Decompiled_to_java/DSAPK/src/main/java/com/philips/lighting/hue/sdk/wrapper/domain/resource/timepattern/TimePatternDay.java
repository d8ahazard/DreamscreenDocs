package com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern;

public class TimePatternDay {
    private int day;
    private int month;

    public TimePatternDay() {
    }

    public TimePatternDay(int month2, int day2) {
        this.month = month2;
        this.day = day2;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month2) {
        this.month = month2;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day2) {
        this.day = day2;
    }

    public int hashCode() {
        return ((this.day + 31) * 31) + this.month;
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
        TimePatternDay other = (TimePatternDay) obj;
        if (this.day != other.day) {
            return false;
        }
        if (this.month != other.month) {
            return false;
        }
        return true;
    }
}
