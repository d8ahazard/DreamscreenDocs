package com.philips.lighting.hue.sdk.wrapper.domain;

import com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern.AbsoluteTimePattern;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern.TimePattern;
import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;

public class BridgeTimeConfiguration {
    private TimePattern localTime = null;
    private byte[] timeZone = null;
    private TimePattern utc = null;

    public AbsoluteTimePattern getUtc() {
        return (AbsoluteTimePattern) this.utc;
    }

    public void setUtc(AbsoluteTimePattern utc2) {
        this.utc = utc2;
    }

    public AbsoluteTimePattern getLocalTime() {
        return (AbsoluteTimePattern) this.localTime;
    }

    public void setLocalTime(AbsoluteTimePattern localTime2) {
        this.localTime = localTime2;
    }

    public String getTimeZone() {
        return NativeTools.BytesToString(this.timeZone);
    }

    public void setTimeZone(String timeZone2) {
        this.timeZone = NativeTools.StringToBytes(timeZone2);
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        if (this.localTime == null) {
            hashCode = 0;
        } else {
            hashCode = this.localTime.hashCode();
        }
        int hashCode2 = (((hashCode + 31) * 31) + Arrays.hashCode(this.timeZone)) * 31;
        if (this.utc != null) {
            i = this.utc.hashCode();
        }
        return hashCode2 + i;
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
        BridgeTimeConfiguration other = (BridgeTimeConfiguration) obj;
        if (this.localTime == null) {
            if (other.localTime != null) {
                return false;
            }
        } else if (!this.localTime.equals(other.localTime)) {
            return false;
        }
        if (!Arrays.equals(this.timeZone, other.timeZone)) {
            return false;
        }
        if (this.utc == null) {
            if (other.utc != null) {
                return false;
            }
            return true;
        } else if (!this.utc.equals(other.utc)) {
            return false;
        } else {
            return true;
        }
    }
}
