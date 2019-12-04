package com.philips.lighting.hue.sdk.wrapper.domain.resource.bridgecapabilities.resources;

import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;

public class BridgeRuleCapabilities extends BridgeResourceCapabilities {
    private Integer availableActions = null;
    private Integer availableConditions = null;

    protected BridgeRuleCapabilities(long sessionKey) {
        super(sessionKey);
    }

    /* access modifiers changed from: protected */
    public void setAvailableConditions(Integer availableConditions2) {
        this.availableConditions = availableConditions2;
    }

    /* access modifiers changed from: protected */
    public void setAvailableActions(Integer availableActions2) {
        this.availableActions = availableActions2;
    }

    public Integer getAvailableConditions() {
        return this.availableConditions;
    }

    public Integer getAvailableActions() {
        return this.availableActions;
    }

    public String getIdentifier() {
        return null;
    }

    public void setIdentifier(String identifier) {
    }

    public DomainType getType() {
        return DomainType.RULE_CAPABILITIES;
    }

    public int getDomainType() {
        return getType().getValue();
    }

    public boolean isOfType(DomainType type) {
        if (type == DomainType.RULE_CAPABILITIES) {
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
        int i = 0;
        int hashCode2 = super.hashCode() * 31;
        if (this.availableConditions == null) {
            hashCode = 0;
        } else {
            hashCode = this.availableConditions.hashCode();
        }
        int i2 = (hashCode2 + hashCode) * 31;
        if (this.availableActions != null) {
            i = this.availableActions.hashCode();
        }
        return i2 + i;
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
        BridgeRuleCapabilities other = (BridgeRuleCapabilities) obj;
        if (this.availableConditions == null) {
            if (other.availableConditions != null) {
                return false;
            }
        } else if (!this.availableConditions.equals(other.availableConditions)) {
            return false;
        }
        if (this.availableActions == null) {
            if (other.availableActions != null) {
                return false;
            }
            return true;
        } else if (!this.availableActions.equals(other.availableActions)) {
            return false;
        } else {
            return true;
        }
    }
}
