package com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern;

public class RandomizedTimerPattern extends TimerPattern {
    private TimePatternTime randomElement;

    public RandomizedTimerPattern(TimePatternTime startTime, TimePatternTime randomElement2) {
        super(startTime);
        this.randomElement = randomElement2;
        this.type = TimePatternType.RANDOMIZED_TIMER;
    }

    public TimePatternTime getRandomElement() {
        return this.randomElement;
    }

    public void setRandomElement(TimePatternTime randomElement2) {
        this.randomElement = randomElement2;
    }

    public int hashCode() {
        int hashCode;
        int hashCode2 = super.hashCode() * 31;
        if (this.randomElement == null) {
            hashCode = 0;
        } else {
            hashCode = this.randomElement.hashCode();
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
        RandomizedTimerPattern other = (RandomizedTimerPattern) obj;
        if (this.randomElement == null) {
            if (other.randomElement != null) {
                return false;
            }
            return true;
        } else if (!this.randomElement.equals(other.randomElement)) {
            return false;
        } else {
            return true;
        }
    }
}
