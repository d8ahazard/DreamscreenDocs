package com.philips.lighting.hue.sdk.wrapper.domain.resource.bridgecapabilities;

import com.philips.lighting.hue.sdk.wrapper.SessionObject;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.BridgeResource;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.bridgecapabilities.resources.BridgeResourceCapabilities;
import com.philips.lighting.hue.sdk.wrapper.utilities.Executor;
import java.util.HashMap;
import java.util.Map;

public class BridgeCapabilities extends SessionObject implements BridgeResource {
    private Map<DomainType, BridgeResourceCapabilities> resourceCapabilities;

    /* access modifiers changed from: private */
    public native void fetchNative(int i, BridgeResponseCallback bridgeResponseCallback);

    protected BridgeCapabilities(long sessionKey) {
        super(sessionKey);
        this.resourceCapabilities = null;
        this.resourceCapabilities = new HashMap();
    }

    /* access modifiers changed from: protected */
    public void clearResourceCapabilities() {
        this.resourceCapabilities.clear();
    }

    /* access modifiers changed from: protected */
    public void addResourceCapabilities(int domainType, BridgeResourceCapabilities resourceCapabilities2) {
        this.resourceCapabilities.put(DomainType.fromValue(domainType), resourceCapabilities2);
    }

    public Map<DomainType, BridgeResourceCapabilities> getResourceCapabilities() {
        return this.resourceCapabilities;
    }

    public BridgeResourceCapabilities getResourceCapabilitiesForDomainType(DomainType domainType) {
        return (BridgeResourceCapabilities) this.resourceCapabilities.get(domainType);
    }

    public String getIdentifier() {
        return new String("0");
    }

    public void setIdentifier(String identifier) {
    }

    public DomainType getType() {
        return DomainType.CAPABILITIES;
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
        fetch(BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void fetch(final BridgeConnectionType allowedConnectionType, final BridgeResponseCallback callback) {
        if (allowedConnectionType == null) {
            BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_ALLOWED);
        } else {
            Executor.submit(new Runnable() {
                public void run() {
                    BridgeCapabilities.this.fetchNative(allowedConnectionType.getValue(), callback);
                }
            });
        }
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
        if (this.resourceCapabilities == null) {
            hashCode = 0;
        } else {
            hashCode = this.resourceCapabilities.hashCode();
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
        BridgeCapabilities other = (BridgeCapabilities) obj;
        if (this.resourceCapabilities == null) {
            if (other.resourceCapabilities != null) {
                return false;
            }
            return true;
        } else if (!this.resourceCapabilities.equals(other.resourceCapabilities)) {
            return false;
        } else {
            return true;
        }
    }
}
