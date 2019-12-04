package com.philips.lighting.hue.sdk.wrapper.domain.resource;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;

public class GroupStream {
    Boolean active = null;
    byte[] owner = null;
    ProxyMode proxyMode = null;
    byte[] proxyNode = null;

    public GroupStream() {
    }

    public GroupStream(Boolean active2, String owner2, ProxyMode proxyMode2, String proxyNode2) {
        this.active = active2;
        setOwner(owner2);
        this.proxyMode = proxyMode2;
        setProxyNode(proxyNode2);
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active2) {
        this.active = active2;
    }

    public String getOwner() {
        return NativeTools.BytesToString(this.owner);
    }

    public void setOwner(String owner2) {
        this.owner = NativeTools.StringToBytes(owner2);
    }

    public ProxyMode getProxyMode() {
        return this.proxyMode;
    }

    public void setProxyMode(ProxyMode proxyMode2) {
        this.proxyMode = proxyMode2;
    }

    public String getProxyNode() {
        return NativeTools.BytesToString(this.proxyNode);
    }

    public void setProxyNode(String proxyNode2) {
        this.proxyNode = NativeTools.StringToBytes(proxyNode2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        GroupStream other = (GroupStream) obj;
        if (this.active == null) {
            if (other.active != null) {
                return false;
            }
        } else if (!this.active.equals(other.active)) {
            return false;
        }
        if (!Arrays.equals(this.owner, other.owner)) {
            return false;
        }
        if (this.proxyMode == null) {
            if (other.proxyMode != null) {
                return false;
            }
        } else if (!this.proxyMode.equals(other.proxyMode)) {
            return false;
        }
        if (!Arrays.equals(this.proxyNode, other.proxyNode)) {
            return false;
        }
        return true;
    }
}
