package com.philips.lighting.hue.sdk.wrapper.domain;

import com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern.TruncatedTimePattern;

public class SystemSoftwareUpdateAutoInstall {
    private Boolean isOn;
    private Boolean isSupported;
    private TruncatedTimePattern updateTime;

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SystemSoftwareUpdateAutoInstall that = (SystemSoftwareUpdateAutoInstall) o;
        if (this.isOn != null) {
            if (!this.isOn.equals(that.isOn)) {
                return false;
            }
        } else if (that.isOn != null) {
            return false;
        }
        if (this.updateTime != null) {
            if (!this.updateTime.equals(that.updateTime)) {
                return false;
            }
        } else if (that.updateTime != null) {
            return false;
        }
        if (this.isSupported != null) {
            z = this.isSupported.equals(that.isSupported);
        } else if (that.isSupported != null) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int result;
        int i;
        int i2 = 0;
        if (this.isOn != null) {
            result = this.isOn.hashCode();
        } else {
            result = 0;
        }
        int i3 = result * 31;
        if (this.updateTime != null) {
            i = this.updateTime.hashCode();
        } else {
            i = 0;
        }
        int i4 = (i3 + i) * 31;
        if (this.isSupported != null) {
            i2 = this.isSupported.hashCode();
        }
        return i4 + i2;
    }

    public Boolean getOn() {
        return this.isOn;
    }

    public void setOn(Boolean on) {
        this.isOn = on;
    }

    public TruncatedTimePattern getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(TruncatedTimePattern updateTime2) {
        this.updateTime = updateTime2;
    }

    public Boolean getSupported() {
        return this.isSupported;
    }
}
