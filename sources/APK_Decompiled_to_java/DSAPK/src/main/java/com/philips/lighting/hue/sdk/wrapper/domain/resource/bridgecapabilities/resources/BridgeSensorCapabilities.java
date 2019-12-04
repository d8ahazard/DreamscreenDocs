package com.philips.lighting.hue.sdk.wrapper.domain.resource.bridgecapabilities.resources;

import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorSubType;
import java.util.Arrays;

public class BridgeSensorCapabilities extends BridgeResourceCapabilities {
    private Integer[] availableSensorsPerSubType = new Integer[SensorSubType.values().length];

    protected BridgeSensorCapabilities(long sessionKey) {
        super(sessionKey);
    }

    /* access modifiers changed from: protected */
    public void setAvailableSensorsPerSubType(Integer[] availableSensorsPerSubType2) {
        this.availableSensorsPerSubType = availableSensorsPerSubType2;
    }

    public Integer getAvailableSensorsForSubType(SensorSubType subType) {
        if (subType.getValue() <= this.availableSensorsPerSubType.length) {
            return this.availableSensorsPerSubType[subType.getValue()];
        }
        return null;
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
        int hashCode2 = super.hashCode() * 31;
        if (this.availableSensorsPerSubType == null) {
            hashCode = 0;
        } else {
            hashCode = Arrays.hashCode(this.availableSensorsPerSubType);
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
        BridgeSensorCapabilities other = (BridgeSensorCapabilities) obj;
        if (this.availableSensorsPerSubType == null) {
            if (other.availableSensorsPerSubType != null) {
                return false;
            }
            return true;
        } else if (!Arrays.equals(this.availableSensorsPerSubType, other.availableSensorsPerSubType)) {
            return false;
        } else {
            return true;
        }
    }
}
