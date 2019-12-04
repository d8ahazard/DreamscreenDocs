package com.philips.lighting.hue.sdk.wrapper.domain;

import com.philips.lighting.hue.sdk.wrapper.domain.clip.ConnectionState;

public class BridgeInternetServicesConfiguration {
    private ConnectionState internet = ConnectionState.NO_VALUE;
    private ConnectionState remoteAccess = ConnectionState.NO_VALUE;
    private ConnectionState swUpdate = ConnectionState.NO_VALUE;
    private ConnectionState time = ConnectionState.NO_VALUE;

    public ConnectionState getInternet() {
        return this.internet;
    }

    public ConnectionState getRemoteAccess() {
        return this.remoteAccess;
    }

    public ConnectionState getTime() {
        return this.time;
    }

    public ConnectionState getSwUpdate() {
        return this.swUpdate;
    }

    public int hashCode() {
        return ((((((this.internet.getValue() + 31) * 31) + this.remoteAccess.getValue()) * 31) + this.time.getValue()) * 31) + this.swUpdate.getValue();
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
        BridgeInternetServicesConfiguration other = (BridgeInternetServicesConfiguration) obj;
        if (this.internet != other.internet) {
            return false;
        }
        if (this.internet != other.remoteAccess) {
            return false;
        }
        if (this.internet != other.time) {
            return false;
        }
        if (this.internet != other.swUpdate) {
            return false;
        }
        return true;
    }
}
