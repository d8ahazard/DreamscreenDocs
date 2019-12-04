package com.philips.lighting.hue.sdk.wrapper.knowledgebase;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class ParameterRange {
    private byte[] parameterName;
    private List<String> values = new ArrayList();

    private ParameterRange(byte[] parameterName2, byte[][] values2) {
        this.parameterName = parameterName2;
        for (byte[] value : values2) {
            this.values.add(NativeTools.BytesToString(value));
        }
    }

    public String getParameterName() {
        return NativeTools.BytesToString(this.parameterName);
    }

    public List<String> getValues() {
        return this.values;
    }

    public List<Integer> getValuesAsInt() {
        List<Integer> ints = new ArrayList<>();
        for (String s : this.values) {
            ints.add(Integer.valueOf(Integer.parseInt(s)));
        }
        return ints;
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        if (this.parameterName == null) {
            hashCode = 0;
        } else {
            hashCode = this.parameterName.hashCode();
        }
        int i2 = (hashCode + 31) * 31;
        if (this.values != null) {
            i = this.values.hashCode();
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
        ParameterRange other = (ParameterRange) obj;
        if (this.parameterName == null) {
            if (other.parameterName != null) {
                return false;
            }
        } else if (!this.parameterName.equals(other.parameterName)) {
            return false;
        }
        if (this.values == null) {
            if (other.values != null) {
                return false;
            }
            return true;
        } else if (!this.values.equals(other.values)) {
            return false;
        } else {
            return true;
        }
    }
}
