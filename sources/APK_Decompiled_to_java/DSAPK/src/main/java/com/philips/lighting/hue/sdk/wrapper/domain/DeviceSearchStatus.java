package com.philips.lighting.hue.sdk.wrapper.domain;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeviceSearchStatus {
    private Boolean active;
    private List<DeviceInfo> foundDeviceInfo;
    private Date lastScan;

    public class DeviceInfo {
        private DomainType domainType = null;
        private byte[] identifier = null;
        private byte[] name = null;

        private DeviceInfo(int domainType2) {
            this.domainType = DomainType.fromValue(domainType2);
        }

        private void setDomainTypeFromInt(int type) {
            this.domainType = DomainType.fromValue(type);
        }

        public DomainType getDomainType() {
            return this.domainType;
        }

        public String getIdentifier() {
            return NativeTools.BytesToString(this.identifier);
        }

        public String getName() {
            return NativeTools.BytesToString(this.name);
        }
    }

    private DeviceSearchStatus() {
        this.active = null;
        this.lastScan = null;
        this.foundDeviceInfo = null;
        this.foundDeviceInfo = new ArrayList();
    }

    private void addFoundDeviceInfo(DeviceInfo deviceInfo) {
        if (this.foundDeviceInfo == null) {
            this.foundDeviceInfo = new ArrayList();
        }
        this.foundDeviceInfo.add(deviceInfo);
    }

    public Boolean getActive() {
        return this.active;
    }

    public Date getLastScan() {
        return this.lastScan;
    }

    public List<DeviceInfo> getFoundDeviceInfo() {
        return this.foundDeviceInfo;
    }
}
