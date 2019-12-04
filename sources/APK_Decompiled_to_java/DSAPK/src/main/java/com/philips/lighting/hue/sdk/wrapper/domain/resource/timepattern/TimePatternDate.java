package com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern;

public class TimePatternDate {
    private int day;
    private int hour;
    private int minute;
    private int month;
    private int seconds;
    private int year;

    public TimePatternDate() {
    }

    public TimePatternDate(int year2, int month2, int day2, int hour2, int minute2, int seconds2) {
        this.year = year2;
        this.month = month2;
        this.day = day2;
        this.hour = hour2;
        this.minute = minute2;
        this.seconds = seconds2;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year2) {
        this.year = year2;
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
        return ((((((((((this.day + 31) * 31) + this.hour) * 31) + this.minute) * 31) + this.month) * 31) + this.seconds) * 31) + this.year;
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
        TimePatternDate other = (TimePatternDate) obj;
        if (this.day != other.day) {
            return false;
        }
        if (this.hour != other.hour) {
            return false;
        }
        if (this.minute != other.minute) {
            return false;
        }
        if (this.month != other.month) {
            return false;
        }
        if (this.seconds != other.seconds) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        return true;
    }
}
