package com.philips.lighting.hue.sdk.wrapper.domain;

import com.philips.lighting.hue.sdk.wrapper.domain.clip.PortalConnectionState;

public class BridgePortalConfiguration {
    private BridgePortalState bridgePortalState = null;
    private PortalConnectionState portalConnection = null;
    private Boolean portalServices = null;

    public Boolean getPortalServices() {
        return this.portalServices;
    }

    public void setPortalServices(Boolean portalServices2) {
        this.portalServices = portalServices2;
    }

    public void setPortalServices(boolean portalServices2) {
        this.portalServices = new Boolean(portalServices2);
    }

    public PortalConnectionState getPortalConnection() {
        return this.portalConnection;
    }

    public BridgePortalState getBridgePortalState() {
        return this.bridgePortalState;
    }

    public int hashCode() {
        int hashCode;
        int hashCode2;
        int i = 0;
        if (this.bridgePortalState == null) {
            hashCode = 0;
        } else {
            hashCode = this.bridgePortalState.hashCode();
        }
        int i2 = (hashCode + 31) * 31;
        if (this.portalConnection == null) {
            hashCode2 = 0;
        } else {
            hashCode2 = this.portalConnection.hashCode();
        }
        int i3 = (i2 + hashCode2) * 31;
        if (this.portalServices != null) {
            i = this.portalServices.hashCode();
        }
        return i3 + i;
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
        BridgePortalConfiguration other = (BridgePortalConfiguration) obj;
        if (this.bridgePortalState == null) {
            if (other.bridgePortalState != null) {
                return false;
            }
        } else if (!this.bridgePortalState.equals(other.bridgePortalState)) {
            return false;
        }
        if (this.portalConnection != other.portalConnection) {
            return false;
        }
        if (this.portalServices == null) {
            if (other.portalServices != null) {
                return false;
            }
            return true;
        } else if (!this.portalServices.equals(other.portalServices)) {
            return false;
        } else {
            return true;
        }
    }
}
