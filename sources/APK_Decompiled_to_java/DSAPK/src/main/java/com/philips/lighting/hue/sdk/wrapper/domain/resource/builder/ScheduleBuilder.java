package com.philips.lighting.hue.sdk.wrapper.domain.resource.builder;

import com.philips.lighting.hue.sdk.wrapper.domain.clip.ClipAction;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.Schedule;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.ScheduleStatus;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern.TimePattern;

public class ScheduleBuilder {
    private ClipAction action = null;
    private Boolean autoDelete = null;
    private String description = null;
    private String identifier = null;
    private TimePattern localTime = null;
    private String name = null;
    private Boolean recycle = null;
    private ScheduleStatus status = null;
    private TimePattern utcTime = null;

    public ScheduleBuilder setName(String name2) {
        this.name = name2;
        return this;
    }

    public ScheduleBuilder setIdentifier(String identifier2) {
        if (identifier2 != null) {
            this.identifier = identifier2;
        } else {
            this.identifier = null;
        }
        return this;
    }

    public ScheduleBuilder setDescription(String description2) {
        this.description = description2;
        return this;
    }

    public ScheduleBuilder setTriggerTime(TimePattern time, boolean isUTC) {
        if (isUTC) {
            this.utcTime = time;
        } else {
            this.localTime = time;
        }
        return this;
    }

    public ScheduleBuilder setStatus(ScheduleStatus status2) {
        this.status = status2;
        return this;
    }

    public ScheduleBuilder setAutoDelete(Boolean isEnabled) {
        this.autoDelete = isEnabled;
        return this;
    }

    public ScheduleBuilder setAutoDelete(boolean isEnabled) {
        return setAutoDelete(new Boolean(isEnabled));
    }

    public ScheduleBuilder setAction(ClipAction action2) {
        this.action = action2;
        return this;
    }

    public ScheduleBuilder setRecycle(Boolean recycle2) {
        this.recycle = recycle2;
        return this;
    }

    public ScheduleBuilder setRecycle(boolean recycle2) {
        return setRecycle(new Boolean(recycle2));
    }

    public Schedule build() {
        Schedule schedule = new Schedule(this.name, this.description, this.utcTime, this.localTime, this.autoDelete, this.status, this.action, this.recycle);
        schedule.setIdentifier(this.identifier);
        return schedule;
    }
}
