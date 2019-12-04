package com.philips.lighting.hue.sdk.wrapper.domain.resource.bridgecapabilities.resources;

import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;

public class BridgeSceneCapabilities extends BridgeResourceCapabilities {
    private Integer availableLightStates = null;

    protected BridgeSceneCapabilities(long sessionKey) {
        super(sessionKey);
    }

    /* access modifiers changed from: protected */
    public void setAvailableLightStates(Integer availableLightStates2) {
        this.availableLightStates = availableLightStates2;
    }

    public Integer getAvailableLightStates() {
        return this.availableLightStates;
    }

    public String getIdentifier() {
        return null;
    }

    public void setIdentifier(String identifier) {
    }

    public DomainType getType() {
        return DomainType.SCENE_CAPABILITIES;
    }

    public int getDomainType() {
        return getType().getValue();
    }

    public boolean isOfType(DomainType type) {
        if (type == DomainType.SCENE_CAPABILITIES) {
            return true;
        }
        return super.isOfType(type);
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
        if (this.availableLightStates == null) {
            hashCode = 0;
        } else {
            hashCode = this.availableLightStates.hashCode();
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
        BridgeSceneCapabilities other = (BridgeSceneCapabilities) obj;
        if (this.availableLightStates == null) {
            if (other.availableLightStates != null) {
                return false;
            }
            return true;
        } else if (!this.availableLightStates.equals(other.availableLightStates)) {
            return false;
        } else {
            return true;
        }
    }
}
