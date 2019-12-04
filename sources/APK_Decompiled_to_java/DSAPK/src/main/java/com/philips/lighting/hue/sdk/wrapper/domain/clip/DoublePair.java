package com.philips.lighting.hue.sdk.wrapper.domain.clip;

import java.util.Arrays;

public class DoublePair {
    private double[] value;

    private DoublePair(double[] value2, boolean isNone) {
        this.value = value2;
    }

    public DoublePair(double value1, double value2) {
        this(new double[]{value1, value2}, false);
    }

    public DoublePair() {
        this(new double[2], true);
    }

    public double[] getValue() {
        return this.value;
    }

    public void setValue(double value1, double value2) {
        this.value = new double[]{value1, value2};
    }

    public void setValues(double[] values) {
        if (values.length == 2) {
            this.value = values;
        }
    }

    public double getValue1() {
        return this.value[0];
    }

    public double getValue2() {
        return this.value[1];
    }

    public int hashCode() {
        return (super.hashCode() * 31) + Arrays.hashCode(this.value);
    }

    public boolean equals(Object obj) {
        if (this == obj || super.equals(obj)) {
            return true;
        }
        if (getClass() == obj.getClass()) {
            DoublePair other = (DoublePair) obj;
            if (this.value.length != other.value.length) {
                return false;
            }
            if (this.value[0] == other.value[0] && this.value[1] == other.value[1]) {
                return true;
            }
        } else if (obj.getClass() == Double[].class) {
            Double[] other2 = (Double[]) obj;
            if (other2.length != 2) {
                return false;
            }
            if (other2[0].doubleValue() == this.value[0] && other2[1].doubleValue() == this.value[1]) {
                return true;
            }
        } else if (obj.getClass() == double[].class) {
            double[] other3 = (double[]) obj;
            if (other3.length != 2) {
                return false;
            }
            if (other3[0] == this.value[0] && other3[1] == this.value[1]) {
                return true;
            }
        }
        return false;
    }
}
