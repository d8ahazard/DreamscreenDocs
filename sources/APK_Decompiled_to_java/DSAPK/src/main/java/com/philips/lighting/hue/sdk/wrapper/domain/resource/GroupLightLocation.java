package com.philips.lighting.hue.sdk.wrapper.domain.resource;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;

public class GroupLightLocation {
    private byte[] lightIdentifier = null;
    private Double x = null;
    private Double y = null;
    private Double z = null;

    public GroupLightLocation() {
    }

    public GroupLightLocation(String lightIdentifier2, Double x2, Double y2, Double z2) {
        setLightIdentifier(lightIdentifier2);
        this.x = x2;
        this.y = y2;
        this.z = z2;
    }

    public Double getX() {
        return this.x;
    }

    public void setX(Double x2) {
        this.x = x2;
    }

    public Double getZ() {
        return this.z;
    }

    public void setZ(Double z2) {
        this.z = z2;
    }

    public String getLightIdentifier() {
        return NativeTools.BytesToString(this.lightIdentifier);
    }

    public void setLightIdentifier(String lightIdentifier2) {
        this.lightIdentifier = NativeTools.StringToBytes(lightIdentifier2);
    }

    public Double getY() {
        return this.y;
    }

    public void setY(Double y2) {
        this.y = y2;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((this.x == null ? 0 : this.x.hashCode()) + 31) * 31) + (this.y == null ? 0 : this.y.hashCode())) * 31) + (this.z == null ? 0 : this.z.hashCode())) * 31;
        if (this.lightIdentifier != null) {
            i = Arrays.hashCode(this.lightIdentifier);
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        GroupLightLocation other = (GroupLightLocation) obj;
        if (this.x == null) {
            if (other.x != null) {
                return false;
            }
        } else if (this.x.compareTo(other.x) != 0) {
            return false;
        }
        if (this.y == null) {
            if (other.y != null) {
                return false;
            }
        } else if (this.y.compareTo(other.y) != 0) {
            return false;
        }
        if (this.z == null) {
            if (other.z != null) {
                return false;
            }
        } else if (this.z.compareTo(other.z) != 0) {
            return false;
        }
        if (!Arrays.equals(other.lightIdentifier, this.lightIdentifier)) {
            return false;
        }
        return true;
    }
}
