package com.philips.lighting.hue.sdk.wrapper.domain.resource;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;

public class SceneAppData {
    private byte[] data = null;
    private Integer version = null;

    public SceneAppData(Integer version2, String data2) {
        setData(data2);
        this.version = version2;
    }

    protected SceneAppData() {
    }

    public String getData() {
        return NativeTools.BytesToString(this.data);
    }

    public void setData(String data2) {
        this.data = NativeTools.StringToBytes(data2);
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version2) {
        this.version = version2;
    }

    public void setVersion(int version2) {
        this.version = new Integer(version2);
    }

    public int hashCode() {
        return ((Arrays.hashCode(this.data) + 31) * 31) + (this.version == null ? 0 : this.version.hashCode());
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
        SceneAppData other = (SceneAppData) obj;
        if (!Arrays.equals(this.data, other.data)) {
            return false;
        }
        if (this.version == null) {
            if (other.version != null) {
                return false;
            }
            return true;
        } else if (!this.version.equals(other.version)) {
            return false;
        } else {
            return true;
        }
    }
}
