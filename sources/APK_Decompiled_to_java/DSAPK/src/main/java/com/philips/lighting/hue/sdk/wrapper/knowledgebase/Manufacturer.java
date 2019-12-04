package com.philips.lighting.hue.sdk.wrapper.knowledgebase;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;

public class Manufacturer {
    private byte[] identifier;
    private byte[] manufacturer;

    private Manufacturer(byte[] identifier2, byte[] manufacturer2) {
        this.identifier = identifier2;
        this.manufacturer = manufacturer2;
    }

    public String getIdentifier() {
        return NativeTools.BytesToString(this.identifier);
    }

    public String getManufacturer() {
        return NativeTools.BytesToString(this.manufacturer);
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        if (this.identifier == null) {
            hashCode = 0;
        } else {
            hashCode = this.identifier.hashCode();
        }
        int i2 = (hashCode + 31) * 31;
        if (this.manufacturer != null) {
            i = this.manufacturer.hashCode();
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
        Manufacturer other = (Manufacturer) obj;
        if (this.identifier == null) {
            if (other.identifier != null) {
                return false;
            }
        } else if (!this.identifier.equals(other.identifier)) {
            return false;
        }
        if (this.manufacturer == null) {
            if (other.manufacturer != null) {
                return false;
            }
            return true;
        } else if (!this.manufacturer.equals(other.manufacturer)) {
            return false;
        } else {
            return true;
        }
    }
}
