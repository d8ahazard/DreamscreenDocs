package com.philips.lighting.hue.sdk.wrapper.knowledgebase;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;

public class GamutColor {
    @Deprecated
    private byte[] identifier;
    private double x;
    private double y;

    @Deprecated
    private GamutColor(byte[] identifier2, double x2, double y2) {
        this.identifier = identifier2;
        this.x = x2;
        this.y = y2;
    }

    private GamutColor(double x2, double y2) {
        this.identifier = NativeTools.StringToBytes("");
        this.x = x2;
        this.y = y2;
    }

    @Deprecated
    public String getIdentifier() {
        return NativeTools.BytesToString(this.identifier);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int hashCode() {
        int result = Arrays.hashCode(this.identifier) + 31;
        long temp = Double.doubleToLongBits(this.x);
        int result2 = (result * 31) + ((int) ((temp >>> 32) ^ temp));
        long temp2 = Double.doubleToLongBits(this.y);
        return (result2 * 31) + ((int) ((temp2 >>> 32) ^ temp2));
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
        GamutColor other = (GamutColor) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        return true;
    }
}
