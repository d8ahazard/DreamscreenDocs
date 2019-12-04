package com.philips.lighting.hue.sdk.wrapper.domain;

import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceSoftwareUpdate;
import java.util.Date;

public class SystemSoftwareUpdate {
    private SystemSoftwareUpdateAutoInstall autoInstall;
    private DeviceSoftwareUpdate bridgeSoftwareUpdate;
    private Boolean checkForUpdate;
    private Boolean install;
    private Date lastChange;
    private SystemSoftwareUpdateState updateState;
    private SystemSoftwareUpdateVersion version;

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SystemSoftwareUpdate that = (SystemSoftwareUpdate) o;
        if (this.bridgeSoftwareUpdate != null) {
            if (!this.bridgeSoftwareUpdate.equals(that.bridgeSoftwareUpdate)) {
                return false;
            }
        } else if (that.bridgeSoftwareUpdate != null) {
            return false;
        }
        if (this.updateState != that.updateState) {
            return false;
        }
        if (this.install != null) {
            if (!this.install.equals(that.install)) {
                return false;
            }
        } else if (that.install != null) {
            return false;
        }
        if (this.checkForUpdate != null) {
            if (!this.checkForUpdate.equals(that.checkForUpdate)) {
                return false;
            }
        } else if (that.checkForUpdate != null) {
            return false;
        }
        if (this.lastChange != null) {
            if (!this.lastChange.equals(that.lastChange)) {
                return false;
            }
        } else if (that.lastChange != null) {
            return false;
        }
        if (this.version != that.version) {
            return false;
        }
        if (this.autoInstall != null) {
            z = this.autoInstall.equals(that.autoInstall);
        } else if (that.autoInstall != null) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int result;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6 = 0;
        if (this.bridgeSoftwareUpdate != null) {
            result = this.bridgeSoftwareUpdate.hashCode();
        } else {
            result = 0;
        }
        int i7 = result * 31;
        if (this.updateState != null) {
            i = this.updateState.hashCode();
        } else {
            i = 0;
        }
        int i8 = (i7 + i) * 31;
        if (this.install != null) {
            i2 = this.install.hashCode();
        } else {
            i2 = 0;
        }
        int i9 = (i8 + i2) * 31;
        if (this.checkForUpdate != null) {
            i3 = this.checkForUpdate.hashCode();
        } else {
            i3 = 0;
        }
        int i10 = (i9 + i3) * 31;
        if (this.lastChange != null) {
            i4 = this.lastChange.hashCode();
        } else {
            i4 = 0;
        }
        int i11 = (i10 + i4) * 31;
        if (this.version != null) {
            i5 = this.version.hashCode();
        } else {
            i5 = 0;
        }
        int i12 = (i11 + i5) * 31;
        if (this.autoInstall != null) {
            i6 = this.autoInstall.hashCode();
        }
        return i12 + i6;
    }

    public DeviceSoftwareUpdate getBridgeSoftwareUpdate() {
        return this.bridgeSoftwareUpdate;
    }

    public SystemSoftwareUpdateState getUpdateState() {
        return this.updateState;
    }

    public void setInstall(Boolean install2) {
        this.install = install2;
    }

    public Boolean getCheckForUpdate() {
        return this.checkForUpdate;
    }

    public void setCheckForUpdate(Boolean checkForUpdate2) {
        this.checkForUpdate = checkForUpdate2;
    }

    public Date getLastChange() {
        return this.lastChange;
    }

    public SystemSoftwareUpdateVersion getVersion() {
        return this.version;
    }

    public void setAutoInstall(SystemSoftwareUpdateAutoInstall autoInstall2) {
        this.autoInstall = autoInstall2;
    }

    public SystemSoftwareUpdateAutoInstall getAutoInstall() {
        return this.autoInstall;
    }
}
