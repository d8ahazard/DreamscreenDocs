package com.philips.lighting.hue.sdk.wrapper.domain.resource;

import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ClipAction;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern.TimePattern;
import com.philips.lighting.hue.sdk.wrapper.utilities.Executor;
import java.util.Date;

public class Timer extends Schedule {
    private Date startTime;

    /* access modifiers changed from: private */
    public native void fetchNative(int i, BridgeResponseCallback bridgeResponseCallback);

    public Timer() {
    }

    private Timer(Schedule schedule) {
        super(schedule);
    }

    public Timer(String name, String description, TimePattern utcTime, TimePattern localTime, Boolean autoDelete, ScheduleStatus status, ClipAction action, Boolean recycle) {
        super(name, description, utcTime, localTime, autoDelete, status, action, recycle);
    }

    public int getDomainType() {
        return getType().getValue();
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime2) {
        this.startTime = startTime2;
    }

    public DomainType getType() {
        return DomainType.TIMER;
    }

    public void fetch(BridgeResponseCallback callback) {
        fetch(BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void fetch(final BridgeConnectionType allowedConnectionType, final BridgeResponseCallback callback) {
        if (allowedConnectionType == null) {
            BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_ALLOWED);
        } else {
            Executor.submit(new Runnable() {
                public void run() {
                    Timer.this.fetchNative(allowedConnectionType.getValue(), callback);
                }
            });
        }
    }

    public int hashCode() {
        int hashCode;
        int hashCode2 = super.hashCode() * 31;
        if (this.startTime == null) {
            hashCode = 0;
        } else {
            hashCode = this.startTime.hashCode();
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
        Timer other = (Timer) obj;
        if (this.startTime == null) {
            if (other.startTime != null) {
                return false;
            }
            return true;
        } else if (!this.startTime.equals(other.startTime)) {
            return false;
        } else {
            return true;
        }
    }
}
