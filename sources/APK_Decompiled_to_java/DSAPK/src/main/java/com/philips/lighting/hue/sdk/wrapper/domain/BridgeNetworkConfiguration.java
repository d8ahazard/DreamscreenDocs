package com.philips.lighting.hue.sdk.wrapper.domain;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;

public class BridgeNetworkConfiguration {
    private Boolean dhcp = null;
    private byte[] gateway = null;
    private byte[] ipAddress = null;
    private byte[] mac = null;
    private byte[] netmask = null;
    private Integer zigbeeChannel = null;

    public Integer getZigbeeChannel() {
        return this.zigbeeChannel;
    }

    public void setZigbeeChannel(Integer zigbeeChannel2) {
        this.zigbeeChannel = zigbeeChannel2;
    }

    public void setZigbeeChannel(int zigbeeChannel2) {
        this.zigbeeChannel = new Integer(zigbeeChannel2);
    }

    public String getMac() {
        return NativeTools.BytesToString(this.mac);
    }

    public Boolean getDhcp() {
        return this.dhcp;
    }

    public void setDhcp(Boolean dhcp2) {
        this.dhcp = dhcp2;
    }

    public void setDhcp(boolean dhcp2) {
        this.dhcp = new Boolean(dhcp2);
    }

    public String getIpAddress() {
        return NativeTools.BytesToString(this.ipAddress);
    }

    public void setIpAddress(String ipAddress2) {
        this.ipAddress = NativeTools.StringToBytes(ipAddress2);
    }

    public String getNetmask() {
        return NativeTools.BytesToString(this.netmask);
    }

    public void setNetmask(String netmask2) {
        this.netmask = NativeTools.StringToBytes(netmask2);
    }

    public String getGateway() {
        return NativeTools.BytesToString(this.gateway);
    }

    public void setGateway(String gateway2) {
        this.gateway = NativeTools.StringToBytes(gateway2);
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((((this.dhcp == null ? 0 : this.dhcp.hashCode()) + 31) * 31) + Arrays.hashCode(this.gateway)) * 31) + Arrays.hashCode(this.ipAddress)) * 31) + Arrays.hashCode(this.mac)) * 31) + Arrays.hashCode(this.netmask)) * 31;
        if (this.zigbeeChannel != null) {
            i = this.zigbeeChannel.hashCode();
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
        BridgeNetworkConfiguration other = (BridgeNetworkConfiguration) obj;
        if (this.dhcp == null) {
            if (other.dhcp != null) {
                return false;
            }
        } else if (!this.dhcp.equals(other.dhcp)) {
            return false;
        }
        if (!Arrays.equals(this.gateway, other.gateway)) {
            return false;
        }
        if (!Arrays.equals(this.ipAddress, other.ipAddress)) {
            return false;
        }
        if (!Arrays.equals(this.mac, other.mac)) {
            return false;
        }
        if (!Arrays.equals(this.netmask, other.netmask)) {
            return false;
        }
        if (this.zigbeeChannel == null) {
            if (other.zigbeeChannel != null) {
                return false;
            }
            return true;
        } else if (!this.zigbeeChannel.equals(other.zigbeeChannel)) {
            return false;
        } else {
            return true;
        }
    }
}
