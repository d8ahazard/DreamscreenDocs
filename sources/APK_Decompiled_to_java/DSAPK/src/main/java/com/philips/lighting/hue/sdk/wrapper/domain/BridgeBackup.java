package com.philips.lighting.hue.sdk.wrapper.domain;

public class BridgeBackup {
    private BridgeBackupErrorCode errorCode = null;
    private BridgeBackupStatus status = null;

    private BridgeBackup() {
    }

    public BridgeBackup(BridgeBackupStatus status2) {
        this.status = status2;
    }

    public BridgeBackupStatus getStatus() {
        return this.status;
    }

    private int getStatusValue() {
        if (this.status != null) {
            return this.status.getValue();
        }
        return 0;
    }

    public void setStatus(BridgeBackupStatus status2) {
        this.status = status2;
    }

    private void setStatusFromInt(int value) {
        this.status = BridgeBackupStatus.fromValue(value);
    }

    public BridgeBackupErrorCode getErrorCode() {
        return this.errorCode;
    }

    private void setErrorCodeFromInt(int value) {
        this.errorCode = BridgeBackupErrorCode.fromValue(value);
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        if (this.errorCode == null) {
            hashCode = 0;
        } else {
            hashCode = this.errorCode.hashCode();
        }
        int i2 = (hashCode + 31) * 31;
        if (this.status != null) {
            i = this.status.hashCode();
        }
        return i2 + i;
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
        BridgeBackup other = (BridgeBackup) obj;
        if (this.errorCode != other.errorCode) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        return true;
    }
}
