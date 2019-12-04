package com.philips.lighting.hue.sdk.wrapper.domain.resource.bridgecapabilities.resources;

import com.philips.lighting.hue.sdk.wrapper.SessionObject;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.BridgeResource;
import java.util.Date;

public class BridgeResourceCapabilities extends SessionObject implements BridgeResource {
    private Integer availableResources = null;
    private Date lastUpdated = null;

    protected BridgeResourceCapabilities(long sessionKey) {
        super(sessionKey);
    }

    /* access modifiers changed from: protected */
    public void setAvailableResources(Integer resourcesAvailable) {
        this.availableResources = resourcesAvailable;
    }

    /* access modifiers changed from: protected */
    public void setLastUpdated(Date lastUpdated2) {
        this.lastUpdated = lastUpdated2;
    }

    public Integer getAvailableResources() {
        return this.availableResources;
    }

    public Date getLastUpdated() {
        return this.lastUpdated;
    }

    public String getIdentifier() {
        return null;
    }

    public void setIdentifier(String identifier) {
    }

    public DomainType getType() {
        return DomainType.RESOURCE_CAPABILITIES;
    }

    public int getDomainType() {
        return getType().getValue();
    }

    public boolean isOfType(DomainType type) {
        if (type == DomainType.RESOURCE || getType() == type) {
            return true;
        }
        return false;
    }

    public boolean isOfType(int typeAsInt) {
        return isOfType(DomainType.fromValue(typeAsInt));
    }

    public void setBridge(Bridge bridge) {
    }

    public void fetch(BridgeResponseCallback callback) {
        BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_ALLOWED);
    }

    public void fetch(BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback) {
        BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_ALLOWED);
    }

    public String getName() {
        return null;
    }

    public void setName(String name) {
    }

    public void syncNative() {
    }

    public int hashCode() {
        int hashCode;
        int hashCode2 = super.hashCode() * 31;
        if (this.availableResources == null) {
            hashCode = 0;
        } else {
            hashCode = this.availableResources.hashCode();
        }
        return hashCode2 + hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BridgeResourceCapabilities other = (BridgeResourceCapabilities) obj;
        if (this.availableResources == null) {
            if (other.availableResources != null) {
                return false;
            }
            return true;
        } else if (!this.availableResources.equals(other.availableResources)) {
            return false;
        } else {
            return true;
        }
    }
}
