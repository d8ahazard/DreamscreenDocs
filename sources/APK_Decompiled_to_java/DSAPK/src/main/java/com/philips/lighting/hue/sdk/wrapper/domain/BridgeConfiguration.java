package com.philips.lighting.hue.sdk.wrapper.domain;

import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceConfiguration;
import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;

public class BridgeConfiguration implements DeviceConfiguration {
    private BridgeBackup backup = null;
    private Boolean factoryNew = null;
    private BridgeHomekitAttributes homekitAttributes = null;
    private BridgeInternetServicesConfiguration internetServicesConfiguration = null;
    private Boolean linkButton = null;
    private byte[] manufacturerName = null;
    private byte[] modelId = null;
    private byte[] name = null;
    private BridgeNetworkConfiguration networkConfiguration = null;
    private BridgePortalConfiguration portalConfiguration = null;
    private Boolean reboot = null;
    private byte[] replacesBridgeId = null;
    private byte[] starterKitId = null;
    private SystemSoftwareUpdate systemSoftwareUpdate = null;
    private BridgeTimeConfiguration timeConfiguration = null;
    private Boolean touchlink = null;
    private byte[] uniqueId = null;
    private BridgeVersion version = null;

    public String getName() {
        return NativeTools.BytesToString(this.name);
    }

    public void setName(String name2) {
        this.name = NativeTools.StringToBytes(name2);
    }

    public String getModelIdentifier() {
        return NativeTools.BytesToString(this.modelId);
    }

    public String getSwVersion() {
        if (this.version != null) {
            return this.version.getFirmwareVersion();
        }
        return null;
    }

    public Boolean getLinkButton() {
        return this.linkButton;
    }

    public void setLinkButton(Boolean linkButton2) {
        this.linkButton = linkButton2;
    }

    public void setLinkButton(boolean linkButton2) {
        this.linkButton = new Boolean(linkButton2);
    }

    public BridgeVersion getVersion() {
        return this.version;
    }

    public void setReboot(Boolean reboot2) {
        this.reboot = reboot2;
    }

    public void setReboot(boolean reboot2) {
        this.reboot = new Boolean(reboot2);
    }

    public void setTouchlink(Boolean touchLink) {
        this.touchlink = this.touchlink;
    }

    public void setTouchlink(boolean touchlink2) {
        this.touchlink = new Boolean(touchlink2);
    }

    public SystemSoftwareUpdate getSystemSoftwareUpdate() {
        return this.systemSoftwareUpdate;
    }

    public void setSystemSoftwareUpdate(SystemSoftwareUpdate systemSoftwareUpdate2) {
        this.systemSoftwareUpdate = systemSoftwareUpdate2;
    }

    public DomainType getType() {
        return DomainType.BRIDGE_CONFIGURATION;
    }

    public String getUniqueIdentifier() {
        return NativeTools.BytesToString(this.uniqueId);
    }

    public String getManufacturerName() {
        return NativeTools.BytesToString(this.manufacturerName);
    }

    public BridgeBackup getBackup() {
        return this.backup;
    }

    public void setBackup(BridgeBackup backup2) {
        this.backup = backup2;
    }

    public String getReplacesBridgeId() {
        return NativeTools.BytesToString(this.replacesBridgeId);
    }

    public void setReplacesBridgeId(String replacesBridgeId2) {
        this.replacesBridgeId = NativeTools.StringToBytes(replacesBridgeId2);
    }

    public String getStarterKitId() {
        return NativeTools.BytesToString(this.starterKitId);
    }

    public void setStarterKitId(String starterKitId2) {
        this.starterKitId = NativeTools.StringToBytes(starterKitId2);
    }

    public Boolean getFactoryNew() {
        return this.factoryNew;
    }

    public BridgeNetworkConfiguration getNetworkConfiguration() {
        return this.networkConfiguration;
    }

    public void setNetworkConfiguration(BridgeNetworkConfiguration networkConfiguration2) {
        this.networkConfiguration = networkConfiguration2;
    }

    public BridgeTimeConfiguration getTimeConfiguration() {
        return this.timeConfiguration;
    }

    public void setTimeConfiguration(BridgeTimeConfiguration timeConfiguration2) {
        this.timeConfiguration = timeConfiguration2;
    }

    public BridgePortalConfiguration getPortalConfiguration() {
        return this.portalConfiguration;
    }

    public void setPortalConfiguration(BridgePortalConfiguration portalConfiguration2) {
        this.portalConfiguration = portalConfiguration2;
    }

    public void setHomekitAttributes(BridgeHomekitAttributes homekitAttributes2) {
        this.homekitAttributes = homekitAttributes2;
    }

    public BridgeInternetServicesConfiguration getInternetServicesConfiguration() {
        return this.internetServicesConfiguration;
    }

    public BridgeHomekitAttributes getHomekitAttributes() {
        return this.homekitAttributes;
    }

    public int hashCode() {
        int hashCode;
        int hashCode2;
        int hashCode3;
        int hashCode4;
        int hashCode5;
        int hashCode6;
        int hashCode7;
        int hashCode8;
        int i = 0;
        int hashCode9 = ((this.backup == null ? 0 : this.backup.hashCode()) + 31) * 31;
        if (this.factoryNew == null) {
            hashCode = 0;
        } else {
            hashCode = this.factoryNew.hashCode();
        }
        int i2 = (hashCode9 + hashCode) * 31;
        if (this.homekitAttributes == null) {
            hashCode2 = 0;
        } else {
            hashCode2 = this.homekitAttributes.hashCode();
        }
        int i3 = (i2 + hashCode2) * 31;
        if (this.linkButton == null) {
            hashCode3 = 0;
        } else {
            hashCode3 = this.linkButton.hashCode();
        }
        int hashCode10 = (((((((i3 + hashCode3) * 31) + Arrays.hashCode(this.manufacturerName)) * 31) + Arrays.hashCode(this.modelId)) * 31) + Arrays.hashCode(this.name)) * 31;
        if (this.networkConfiguration == null) {
            hashCode4 = 0;
        } else {
            hashCode4 = this.networkConfiguration.hashCode();
        }
        int i4 = (hashCode10 + hashCode4) * 31;
        if (this.portalConfiguration == null) {
            hashCode5 = 0;
        } else {
            hashCode5 = this.portalConfiguration.hashCode();
        }
        int hashCode11 = (((((((i4 + hashCode5) * 31) + (this.reboot == null ? 0 : this.reboot.hashCode())) * 31) + Arrays.hashCode(this.replacesBridgeId)) * 31) + Arrays.hashCode(this.starterKitId)) * 31;
        if (this.systemSoftwareUpdate == null) {
            hashCode6 = 0;
        } else {
            hashCode6 = this.systemSoftwareUpdate.hashCode();
        }
        int i5 = (hashCode11 + hashCode6) * 31;
        if (this.timeConfiguration == null) {
            hashCode7 = 0;
        } else {
            hashCode7 = this.timeConfiguration.hashCode();
        }
        int i6 = (i5 + hashCode7) * 31;
        if (this.touchlink == null) {
            hashCode8 = 0;
        } else {
            hashCode8 = this.touchlink.hashCode();
        }
        int hashCode12 = (((((i6 + hashCode8) * 31) + Arrays.hashCode(this.uniqueId)) * 31) + (this.version == null ? 0 : this.version.hashCode())) * 31;
        if (this.internetServicesConfiguration != null) {
            i = this.internetServicesConfiguration.hashCode();
        }
        return hashCode12 + i;
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
        BridgeConfiguration other = (BridgeConfiguration) obj;
        if (this.backup == null) {
            if (other.backup != null) {
                return false;
            }
        } else if (!this.backup.equals(other.backup)) {
            return false;
        }
        if (this.factoryNew == null) {
            if (other.factoryNew != null) {
                return false;
            }
        } else if (!this.factoryNew.equals(other.factoryNew)) {
            return false;
        }
        if (this.homekitAttributes == null) {
            if (other.homekitAttributes != null) {
                return false;
            }
        } else if (!this.homekitAttributes.equals(other.homekitAttributes)) {
            return false;
        }
        if (this.linkButton == null) {
            if (other.linkButton != null) {
                return false;
            }
        } else if (!this.linkButton.equals(other.linkButton)) {
            return false;
        }
        if (!Arrays.equals(this.manufacturerName, other.manufacturerName)) {
            return false;
        }
        if (!Arrays.equals(this.modelId, other.modelId)) {
            return false;
        }
        if (!Arrays.equals(this.name, other.name)) {
            return false;
        }
        if (this.networkConfiguration == null) {
            if (other.networkConfiguration != null) {
                return false;
            }
        } else if (!this.networkConfiguration.equals(other.networkConfiguration)) {
            return false;
        }
        if (this.portalConfiguration == null) {
            if (other.portalConfiguration != null) {
                return false;
            }
        } else if (!this.portalConfiguration.equals(other.portalConfiguration)) {
            return false;
        }
        if (this.reboot == null) {
            if (other.reboot != null) {
                return false;
            }
        } else if (!this.reboot.equals(other.reboot)) {
            return false;
        }
        if (!Arrays.equals(this.replacesBridgeId, other.replacesBridgeId)) {
            return false;
        }
        if (!Arrays.equals(this.starterKitId, other.starterKitId)) {
            return false;
        }
        if (this.systemSoftwareUpdate == null) {
            if (other.systemSoftwareUpdate != null) {
                return false;
            }
        } else if (!this.systemSoftwareUpdate.equals(other.systemSoftwareUpdate)) {
            return false;
        }
        if (this.timeConfiguration == null) {
            if (other.timeConfiguration != null) {
                return false;
            }
        } else if (!this.timeConfiguration.equals(other.timeConfiguration)) {
            return false;
        }
        if (this.touchlink == null) {
            if (other.touchlink != null) {
                return false;
            }
        } else if (!this.touchlink.equals(other.touchlink)) {
            return false;
        }
        if (!Arrays.equals(this.uniqueId, other.uniqueId)) {
            return false;
        }
        if (this.version == null) {
            if (other.version != null) {
                return false;
            }
        } else if (!this.version.equals(other.version)) {
            return false;
        }
        if (this.internetServicesConfiguration != null) {
            return true;
        }
        if (other.internetServicesConfiguration != null) {
            return false;
        }
        if (!this.internetServicesConfiguration.equals(other.internetServicesConfiguration)) {
            return false;
        }
        return true;
    }
}
