package com.philips.lighting.hue.sdk.wrapper.domain;

import com.philips.lighting.hue.sdk.wrapper.domain.clip.PortalConnectionState;

public class BridgePortalState {
    private PortalConnectionState portalCommunication = null;
    private Boolean portalIncoming = null;
    private Boolean portalOutgoing = null;
    private Boolean portalSignedOn = null;

    public Boolean getPortalSignedOn() {
        return this.portalSignedOn;
    }

    public Boolean getPortalIncoming() {
        return this.portalIncoming;
    }

    public Boolean getPortalOutgoing() {
        return this.portalOutgoing;
    }

    public PortalConnectionState getPortalCommunication() {
        return this.portalCommunication;
    }

    public int hashCode() {
        int hashCode;
        int hashCode2;
        int hashCode3;
        int i = 0;
        if (this.portalCommunication == null) {
            hashCode = 0;
        } else {
            hashCode = this.portalCommunication.hashCode();
        }
        int i2 = (hashCode + 31) * 31;
        if (this.portalIncoming == null) {
            hashCode2 = 0;
        } else {
            hashCode2 = this.portalIncoming.hashCode();
        }
        int i3 = (i2 + hashCode2) * 31;
        if (this.portalOutgoing == null) {
            hashCode3 = 0;
        } else {
            hashCode3 = this.portalOutgoing.hashCode();
        }
        int i4 = (i3 + hashCode3) * 31;
        if (this.portalSignedOn != null) {
            i = this.portalSignedOn.hashCode();
        }
        return i4 + i;
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
        BridgePortalState other = (BridgePortalState) obj;
        if (this.portalCommunication != other.portalCommunication) {
            return false;
        }
        if (this.portalIncoming == null) {
            if (other.portalIncoming != null) {
                return false;
            }
        } else if (!this.portalIncoming.equals(other.portalIncoming)) {
            return false;
        }
        if (this.portalOutgoing == null) {
            if (other.portalOutgoing != null) {
                return false;
            }
        } else if (!this.portalOutgoing.equals(other.portalOutgoing)) {
            return false;
        }
        if (this.portalSignedOn == null) {
            if (other.portalSignedOn != null) {
                return false;
            }
            return true;
        } else if (!this.portalSignedOn.equals(other.portalSignedOn)) {
            return false;
        } else {
            return true;
        }
    }
}
