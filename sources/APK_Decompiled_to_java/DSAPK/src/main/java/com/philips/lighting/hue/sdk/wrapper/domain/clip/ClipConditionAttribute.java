package com.philips.lighting.hue.sdk.wrapper.domain.clip;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;

public class ClipConditionAttribute {
    private byte[] clipAttribute;
    private ClipDataType dataType;

    private ClipConditionAttribute(byte[] attribute, int dataType2) {
        this.clipAttribute = attribute;
        this.dataType = ClipDataType.fromValue(dataType2);
    }

    private void setDataTypeFromInt(int value) {
        this.dataType = ClipDataType.fromValue(value);
    }

    public String getClipAttribute() {
        return NativeTools.BytesToString(this.clipAttribute);
    }

    public ClipDataType getDataType() {
        return this.dataType;
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        if (this.clipAttribute == null) {
            hashCode = 0;
        } else {
            hashCode = this.clipAttribute.hashCode();
        }
        int i2 = (hashCode + 31) * 31;
        if (this.dataType != null) {
            i = this.dataType.hashCode();
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
        ClipConditionAttribute other = (ClipConditionAttribute) obj;
        if (this.clipAttribute == null) {
            if (other.clipAttribute != null) {
                return false;
            }
        } else if (!this.clipAttribute.equals(other.clipAttribute)) {
            return false;
        }
        if (this.dataType != other.dataType) {
            return false;
        }
        return true;
    }
}
