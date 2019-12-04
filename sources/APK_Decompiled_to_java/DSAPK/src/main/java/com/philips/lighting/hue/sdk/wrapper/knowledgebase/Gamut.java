package com.philips.lighting.hue.sdk.wrapper.knowledgebase;

import com.philips.lighting.hue.sdk.wrapper.domain.clip.GamutType;
import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Gamut {
    private List<GamutColor> colorPoints;
    private GamutType gamutType = GamutType.Other;
    @Deprecated
    private byte[] identifier = null;

    @Deprecated
    private Gamut(byte[] identifier2, GamutColor[] colorPoints2) {
        this.identifier = identifier2;
        this.colorPoints = new ArrayList(Arrays.asList(colorPoints2));
        this.gamutType = GamutType.fromDescription(NativeTools.BytesToString(identifier2));
    }

    private Gamut(GamutType gamutType2, GamutColor[] colorPoints2) {
        this.identifier = NativeTools.StringToBytes(gamutType2.getDescription());
        this.colorPoints = new ArrayList(Arrays.asList(colorPoints2));
        this.gamutType = gamutType2;
    }

    @Deprecated
    public String getIdentifier() {
        return this.gamutType.getDescription();
    }

    public List<GamutColor> getColorPoints() {
        return this.colorPoints;
    }

    public GamutType getType() {
        return this.gamutType;
    }

    public int hashCode() {
        int hashCode;
        if (this.colorPoints == null) {
            hashCode = 0;
        } else {
            hashCode = this.colorPoints.hashCode();
        }
        return ((hashCode + 31) * 31) + this.gamutType.getDescription().hashCode();
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
        Gamut other = (Gamut) obj;
        if (this.colorPoints == null) {
            if (other.colorPoints != null) {
                return false;
            }
        } else if (!this.colorPoints.equals(other.colorPoints)) {
            return false;
        }
        if (this.gamutType != other.gamutType) {
            return false;
        }
        return true;
    }
}
