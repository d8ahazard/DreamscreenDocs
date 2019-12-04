package com.philips.lighting.hue.sdk.wrapper.knowledgebase;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LightInfo extends DeviceInfo {
    private Gamut gamut;
    private List<Image> images;
    private byte[] lightType;
    private Integer maxCTRange;
    private Integer maxLumen;
    private Integer minCTRange;
    private Integer minDimLevel;
    private byte[] modelIdentifier;
    @Deprecated
    private List<ParameterRange> parameterRanges;
    private byte[] swVersion;

    private LightInfo() {
        this.gamut = null;
        this.modelIdentifier = null;
        this.swVersion = null;
        this.lightType = null;
        this.parameterRanges = null;
        this.images = null;
        this.maxLumen = null;
        this.minDimLevel = null;
        this.minCTRange = null;
        this.maxCTRange = null;
        this.parameterRanges = new ArrayList();
        this.images = new ArrayList();
    }

    public List<ParameterRange> getParameterRanges() {
        return this.parameterRanges;
    }

    @Deprecated
    public ParameterRange getParameterRange(String parameterName) {
        for (ParameterRange r : this.parameterRanges) {
            if (r.getParameterName().equals(parameterName)) {
                return r;
            }
        }
        return null;
    }

    @Deprecated
    private void setParameterRanges(ParameterRange[] parameterRanges2) {
        this.parameterRanges = new ArrayList(Arrays.asList(parameterRanges2));
    }

    public List<Image> getImages() {
        return this.images;
    }

    private void setImages(Image[] images2) {
        this.images = new ArrayList(Arrays.asList(images2));
    }

    private void setMaxLumen(int maxLumen2) {
        if (maxLumen2 >= 0) {
            this.maxLumen = new Integer(maxLumen2);
        } else {
            this.maxLumen = null;
        }
    }

    private void setMinDimLevel(int minDimLevel2) {
        if (minDimLevel2 >= 0) {
            this.minDimLevel = new Integer(minDimLevel2);
        } else {
            this.minDimLevel = null;
        }
    }

    private void setMinCTRange(int minCTRange2) {
        if (minCTRange2 >= 0) {
            this.minCTRange = new Integer(minCTRange2);
        } else {
            this.minCTRange = null;
        }
    }

    private void setMaxCTRange(int maxCTRange2) {
        if (maxCTRange2 >= 0) {
            this.maxCTRange = new Integer(maxCTRange2);
        } else {
            this.maxCTRange = null;
        }
    }

    public Gamut getGamut() {
        return this.gamut;
    }

    public String getModelIdentifier() {
        return NativeTools.BytesToString(this.modelIdentifier);
    }

    public String getSwVersion() {
        return NativeTools.BytesToString(this.swVersion);
    }

    public String getLightType() {
        return NativeTools.BytesToString(this.lightType);
    }

    public Integer getMaxLumen() {
        return this.maxLumen;
    }

    public Integer getMinDimLevel() {
        return this.minDimLevel;
    }

    public Integer getMinCTRange() {
        return this.minCTRange;
    }

    public Integer getMaxCTRange() {
        return this.maxCTRange;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((((((((((((this.gamut == null ? 0 : this.gamut.hashCode()) + 31) * 31) + (this.images == null ? 0 : this.images.hashCode())) * 31) + Arrays.hashCode(this.lightType)) * 31) + Arrays.hashCode(this.modelIdentifier)) * 31) + (this.parameterRanges == null ? 0 : this.parameterRanges.hashCode())) * 31) + Arrays.hashCode(this.swVersion)) * 31) + (this.maxLumen == null ? 0 : this.maxLumen.intValue())) * 31) + (this.minDimLevel == null ? 0 : this.minDimLevel.intValue())) * 31) + (this.minCTRange == null ? 0 : this.minCTRange.intValue())) * 31;
        if (this.maxCTRange != null) {
            i = this.maxCTRange.intValue();
        }
        return hashCode + i;
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
        LightInfo other = (LightInfo) obj;
        if (this.gamut == null) {
            if (other.gamut != null) {
                return false;
            }
        } else if (!this.gamut.equals(other.gamut)) {
            return false;
        }
        if (this.images == null) {
            if (other.images != null) {
                return false;
            }
        } else if (!this.images.equals(other.images)) {
            return false;
        }
        if (!Arrays.equals(this.lightType, other.lightType)) {
            return false;
        }
        if (!Arrays.equals(this.modelIdentifier, other.modelIdentifier)) {
            return false;
        }
        if (this.parameterRanges == null) {
            if (other.parameterRanges != null) {
                return false;
            }
        } else if (!this.parameterRanges.equals(other.parameterRanges)) {
            return false;
        }
        if (!Arrays.equals(this.swVersion, other.swVersion)) {
            return false;
        }
        if ((this.maxLumen == null && other.maxLumen != null) || (this.maxLumen != null && other.maxLumen == null)) {
            return false;
        }
        if (this.maxLumen != null && other.maxLumen != null && !this.maxLumen.equals(other.maxLumen)) {
            return false;
        }
        if ((this.minDimLevel == null && other.minDimLevel != null) || (this.minDimLevel != null && other.minDimLevel == null)) {
            return false;
        }
        if (this.minDimLevel != null && other.minDimLevel != null && !this.minDimLevel.equals(other.minDimLevel)) {
            return false;
        }
        if ((this.minCTRange == null && other.minCTRange != null) || (this.minCTRange != null && other.minCTRange == null)) {
            return false;
        }
        if (this.minCTRange != null && other.minCTRange != null && !this.minCTRange.equals(other.minCTRange)) {
            return false;
        }
        if ((this.maxCTRange == null && other.maxCTRange != null) || (this.maxCTRange != null && other.maxCTRange == null)) {
            return false;
        }
        if (this.maxCTRange == null || other.maxCTRange == null || this.maxCTRange.equals(other.maxCTRange)) {
            return true;
        }
        return false;
    }
}
