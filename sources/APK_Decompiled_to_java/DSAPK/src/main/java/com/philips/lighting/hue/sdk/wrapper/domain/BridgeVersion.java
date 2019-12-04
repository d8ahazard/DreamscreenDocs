package com.philips.lighting.hue.sdk.wrapper.domain;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;

public class BridgeVersion {
    private byte[] clipVersion;
    private byte[] datastoreVersion;
    private byte[] firmwareVersion;

    public BridgeVersion(String firmwareVersion2, String clipVersion2, String datastoreVersion2) {
        setFirmwareVersion(firmwareVersion2);
        setCLIPVersion(clipVersion2);
        setDatastoreVersion(datastoreVersion2);
    }

    protected BridgeVersion(byte[] firmwareVersion2, byte[] clipVersion2, byte[] datastoreVersion2) {
        this.firmwareVersion = firmwareVersion2;
        this.clipVersion = clipVersion2;
        this.datastoreVersion = datastoreVersion2;
    }

    public String getFirmwareVersion() {
        return NativeTools.BytesToString(this.firmwareVersion);
    }

    public void setFirmwareVersion(String firmwareVersion2) {
        this.firmwareVersion = NativeTools.StringToBytes(firmwareVersion2);
    }

    public String getCLIPVersion() {
        return NativeTools.BytesToString(this.clipVersion);
    }

    public void setCLIPVersion(String clipVersion2) {
        this.clipVersion = NativeTools.StringToBytes(clipVersion2);
    }

    public String getDatastoreVersion() {
        return NativeTools.BytesToString(this.datastoreVersion);
    }

    public void setDatastoreVersion(String datastoreVersion2) {
        this.datastoreVersion = NativeTools.StringToBytes(datastoreVersion2);
    }

    public int hashCode() {
        return ((((Arrays.hashCode(this.clipVersion) + 31) * 31) + Arrays.hashCode(this.firmwareVersion)) * 31) + Arrays.hashCode(this.datastoreVersion);
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
        BridgeVersion other = (BridgeVersion) obj;
        if (!Arrays.equals(this.clipVersion, other.clipVersion)) {
            return false;
        }
        if (!Arrays.equals(this.firmwareVersion, other.firmwareVersion)) {
            return false;
        }
        if (!Arrays.equals(this.datastoreVersion, other.datastoreVersion)) {
            return false;
        }
        return true;
    }
}
