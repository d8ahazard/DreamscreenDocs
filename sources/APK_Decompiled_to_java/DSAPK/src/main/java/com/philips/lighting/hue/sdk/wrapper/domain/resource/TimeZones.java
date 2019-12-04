package com.philips.lighting.hue.sdk.wrapper.domain.resource;

import com.philips.lighting.hue.sdk.wrapper.SessionObject;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.utilities.Executor;
import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.ArrayList;
import java.util.List;

public class TimeZones extends SessionObject implements BridgeResource {
    private List<String> timezones;

    /* access modifiers changed from: private */
    public native void fetchNative(int i, BridgeResponseCallback bridgeResponseCallback);

    protected TimeZones(long sessionKey) {
        super(sessionKey);
        this.timezones = null;
        this.timezones = new ArrayList();
    }

    /* access modifiers changed from: protected */
    public void setTimeZones(byte[][] timezones2) {
        this.timezones.clear();
        for (byte[] zone : timezones2) {
            this.timezones.add(NativeTools.BytesToString(zone));
        }
    }

    public List<String> getTimeZones() {
        return this.timezones;
    }

    public String getIdentifier() {
        return new String("0");
    }

    public void setIdentifier(String identifier) {
    }

    public DomainType getType() {
        return DomainType.TIMEZONES;
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

    public String getName() {
        return null;
    }

    public void setName(String name) {
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
                    TimeZones.this.fetchNative(allowedConnectionType.getValue(), callback);
                }
            });
        }
    }

    public void syncNative() {
    }

    public int hashCode() {
        int hashCode;
        int hashCode2 = super.hashCode() * 31;
        if (this.timezones == null) {
            hashCode = 0;
        } else {
            hashCode = this.timezones.hashCode();
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
        TimeZones other = (TimeZones) obj;
        if (this.timezones == null) {
            if (other.timezones != null) {
                return false;
            }
            return true;
        } else if (!this.timezones.equals(other.timezones)) {
            return false;
        } else {
            return true;
        }
    }
}
