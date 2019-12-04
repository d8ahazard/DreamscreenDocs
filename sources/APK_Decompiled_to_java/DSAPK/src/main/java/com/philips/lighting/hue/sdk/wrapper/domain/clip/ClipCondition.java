package com.philips.lighting.hue.sdk.wrapper.domain.clip;

import com.philips.lighting.hue.sdk.wrapper.domain.device.Device;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.BridgeResource;
import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;

public class ClipCondition {
    private byte[] address = null;
    private ClipConditionAttribute attribute;
    private Device device;
    private ClipConditionOperator operator = ClipConditionOperator.NONE;
    private BridgeResource resource;
    private byte[] value = null;

    public ClipCondition() {
    }

    public ClipCondition(String address2, ClipConditionOperator operator2, String value2) {
        setAddress(address2);
        setValue(value2);
        this.operator = operator2;
    }

    protected ClipCondition(byte[] address2, ClipConditionOperator operator2, byte[] value2) {
        this.address = address2;
        this.value = value2;
        this.operator = operator2;
    }

    public String getAddress() {
        return NativeTools.BytesToString(this.address);
    }

    public void setAddress(String address2) {
        this.address = NativeTools.StringToBytes(address2);
    }

    public String getValue() {
        return NativeTools.BytesToString(this.value);
    }

    public void setValue(String value2) {
        this.value = NativeTools.StringToBytes(value2);
    }

    public ClipConditionOperator getOperator() {
        return this.operator;
    }

    public void setOperator(ClipConditionOperator operator2) {
        this.operator = operator2;
    }

    private int getOperatorAsInt() {
        if (this.operator != null) {
            return this.operator.getValue();
        }
        return ClipConditionOperator.UNKNOWN.getValue();
    }

    private void setOperatorFromInt(int value2) {
        this.operator = ClipConditionOperator.fromValue(value2);
    }

    public ClipConditionAttribute getAttribute() {
        return this.attribute;
    }

    public Device getDevice() {
        return this.device;
    }

    public BridgeResource getResource() {
        return this.resource;
    }

    public int hashCode() {
        int hashCode;
        int hashCode2;
        int i = 0;
        int hashCode3 = (Arrays.hashCode(this.address) + 31) * 31;
        if (this.attribute == null) {
            hashCode = 0;
        } else {
            hashCode = this.attribute.hashCode();
        }
        int hashCode4 = (((hashCode3 + hashCode) * 31) + (this.device == null ? 0 : this.device.hashCode())) * 31;
        if (this.operator == null) {
            hashCode2 = 0;
        } else {
            hashCode2 = this.operator.hashCode();
        }
        int i2 = (hashCode4 + hashCode2) * 31;
        if (this.resource != null) {
            i = this.resource.hashCode();
        }
        return ((i2 + i) * 31) + Arrays.hashCode(this.value);
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
        ClipCondition other = (ClipCondition) obj;
        if (!Arrays.equals(this.address, other.address)) {
            return false;
        }
        if (this.attribute == null) {
            if (other.attribute != null) {
                return false;
            }
        } else if (!this.attribute.equals(other.attribute)) {
            return false;
        }
        if (this.device == null) {
            if (other.device != null) {
                return false;
            }
        } else if (!this.device.equals(other.device)) {
            return false;
        }
        if (this.operator != other.operator) {
            return false;
        }
        if (this.resource == null) {
            if (other.resource != null) {
                return false;
            }
        } else if (!this.resource.equals(other.resource)) {
            return false;
        }
        if (!Arrays.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }
}
