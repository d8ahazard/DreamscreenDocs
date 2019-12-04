package com.philips.lighting.hue.sdk.wrapper.domain;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;

public class ClipError extends HueError {
    private byte[] address;
    private byte[] description;
    private ErrorType errorType;
    private byte[] identifier;

    public ClipError(String identifier2, ErrorType errorType2, String address2, String description2) {
        this.identifier = NativeTools.StringToBytes(identifier2);
        this.errorType = errorType2;
        this.address = NativeTools.StringToBytes(address2);
        this.description = NativeTools.StringToBytes(description2);
    }

    protected ClipError(byte[] identifier2, int errorType2, byte[] address2, byte[] description2) {
        this.identifier = identifier2;
        this.errorType = ErrorType.fromValue(errorType2);
        this.address = address2;
        this.description = description2;
    }

    public String getIdentifier() {
        return NativeTools.BytesToString(this.identifier);
    }

    public ErrorType getErrorType() {
        return this.errorType;
    }

    public String getAddress() {
        return NativeTools.BytesToString(this.address);
    }

    public String getDescription() {
        return NativeTools.BytesToString(this.description);
    }

    public String toString() {
        String id = "<unknown>";
        ErrorType type = ErrorType.UNKNOWN;
        String addr = "<unknown>";
        String desc = "<unknown>";
        if (getIdentifier() != null) {
            id = getIdentifier();
        }
        if (getErrorType() != null) {
            type = getErrorType();
        }
        if (getAddress() != null) {
            addr = getAddress();
        }
        if (getDescription() != null) {
            desc = getDescription();
        }
        return "Clip Error: " + addr + " - ID " + id + " - type " + type + ", " + desc;
    }

    public int hashCode() {
        int hashCode;
        int hashCode2 = (((Arrays.hashCode(this.address) + 31) * 31) + Arrays.hashCode(this.description)) * 31;
        if (this.errorType == null) {
            hashCode = 0;
        } else {
            hashCode = this.errorType.hashCode();
        }
        return ((hashCode2 + hashCode) * 31) + Arrays.hashCode(this.identifier);
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
        ClipError other = (ClipError) obj;
        if (!Arrays.equals(this.address, other.address)) {
            return false;
        }
        if (!Arrays.equals(this.description, other.description)) {
            return false;
        }
        if (this.errorType != other.errorType) {
            return false;
        }
        if (!Arrays.equals(this.identifier, other.identifier)) {
            return false;
        }
        return true;
    }
}
