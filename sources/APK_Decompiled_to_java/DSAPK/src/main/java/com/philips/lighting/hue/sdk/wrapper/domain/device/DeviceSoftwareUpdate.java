package com.philips.lighting.hue.sdk.wrapper.domain.device;

import java.util.Date;

public class DeviceSoftwareUpdate {
    protected Date lastInstall = null;
    protected DeviceSoftwareUpdateState updateState = null;

    protected DeviceSoftwareUpdate() {
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeviceSoftwareUpdate that = (DeviceSoftwareUpdate) o;
        if (this.updateState != that.updateState) {
            return false;
        }
        if (this.lastInstall != null) {
            return this.lastInstall.equals(that.lastInstall);
        }
        if (that.lastInstall != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result;
        int i = 0;
        if (this.updateState != null) {
            result = this.updateState.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.lastInstall != null) {
            i = this.lastInstall.hashCode();
        }
        return i2 + i;
    }

    public DeviceSoftwareUpdateState getUpdateState() {
        return this.updateState;
    }

    public Date getLastInstall() {
        return this.lastInstall;
    }
}
