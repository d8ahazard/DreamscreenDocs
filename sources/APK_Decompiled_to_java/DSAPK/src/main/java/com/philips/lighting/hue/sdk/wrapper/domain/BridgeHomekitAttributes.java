package com.philips.lighting.hue.sdk.wrapper.domain;

public class BridgeHomekitAttributes {
    private Boolean resetPairing = null;

    public void setResetPairing(Boolean resetPairing2) {
        this.resetPairing = resetPairing2;
    }

    public void setResetPairing(boolean resetPairing2) {
        this.resetPairing = new Boolean(resetPairing2);
    }

    public int hashCode() {
        int hashCode;
        if (this.resetPairing == null) {
            hashCode = 0;
        } else {
            hashCode = this.resetPairing.hashCode();
        }
        return hashCode + 31;
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
        BridgeHomekitAttributes other = (BridgeHomekitAttributes) obj;
        if (this.resetPairing == null) {
            if (other.resetPairing != null) {
                return false;
            }
            return true;
        } else if (!this.resetPairing.equals(other.resetPairing)) {
            return false;
        } else {
            return true;
        }
    }
}
