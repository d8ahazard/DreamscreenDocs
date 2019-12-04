package com.philips.lighting.hue.sdk.wrapper.knowledgebase;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;

public class Image {
    private byte[] color;
    private byte[] identifier;
    private byte[] type;
    private byte[] uri;
    private byte[] version;

    private Image(byte[] identifier2, byte[] type2, byte[] color2, byte[] uri2, byte[] version2) {
        this.identifier = identifier2;
        this.type = type2;
        this.color = color2;
        this.uri = uri2;
        this.version = version2;
    }

    public String getIdentifier() {
        return NativeTools.BytesToString(this.identifier);
    }

    public String getType() {
        return NativeTools.BytesToString(this.type);
    }

    public String getColor() {
        return NativeTools.BytesToString(this.color);
    }

    public String getUri() {
        return NativeTools.BytesToString(this.uri);
    }

    public String getVersion() {
        return NativeTools.BytesToString(this.version);
    }

    public int hashCode() {
        return ((((((((Arrays.hashCode(this.color) + 31) * 31) + Arrays.hashCode(this.identifier)) * 31) + Arrays.hashCode(this.type)) * 31) + Arrays.hashCode(this.uri)) * 31) + Arrays.hashCode(this.version);
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
        Image other = (Image) obj;
        if (!Arrays.equals(this.color, other.color)) {
            return false;
        }
        if (!Arrays.equals(this.identifier, other.identifier)) {
            return false;
        }
        if (!Arrays.equals(this.type, other.type)) {
            return false;
        }
        if (!Arrays.equals(this.uri, other.uri)) {
            return false;
        }
        if (!Arrays.equals(this.version, other.version)) {
            return false;
        }
        return true;
    }
}
