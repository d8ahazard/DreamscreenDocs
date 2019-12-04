package com.philips.lighting.hue.sdk.wrapper.knowledgebase;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeviceInfo {
    private boolean certified = false;
    private byte[] friendlyName;
    private Manufacturer manufacturer;
    private byte[] productName;
    private List<String> supportedFeatures = new ArrayList();

    protected DeviceInfo() {
    }

    public List<String> getSupportedFeatures() {
        return this.supportedFeatures;
    }

    private void setSupportedFeatures(String[] supportedFeatures2) {
        this.supportedFeatures = new ArrayList(Arrays.asList(supportedFeatures2));
    }

    public String getProductName() {
        return NativeTools.BytesToString(this.productName);
    }

    public Manufacturer getManufacturer() {
        return this.manufacturer;
    }

    public String getFriendlyName() {
        return NativeTools.BytesToString(this.friendlyName);
    }

    public boolean getCertified() {
        return this.certified;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (((((((Arrays.hashCode(this.friendlyName) + 31) * 31) + Arrays.hashCode(this.productName)) * 31) + (this.manufacturer == null ? 0 : this.manufacturer.hashCode())) * 31) + (this.supportedFeatures == null ? 0 : this.supportedFeatures.hashCode())) * 31;
        if (this.certified) {
            i = 1;
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
        DeviceInfo other = (DeviceInfo) obj;
        if (!Arrays.equals(this.friendlyName, other.friendlyName) || !Arrays.equals(this.productName, other.productName) || ((this.manufacturer != other.manufacturer && (this.manufacturer == null || !this.manufacturer.equals(other.manufacturer))) || ((this.supportedFeatures != other.supportedFeatures && (this.supportedFeatures == null || !this.supportedFeatures.equals(other.supportedFeatures))) || this.certified != other.certified))) {
            return false;
        }
        return true;
    }
}
