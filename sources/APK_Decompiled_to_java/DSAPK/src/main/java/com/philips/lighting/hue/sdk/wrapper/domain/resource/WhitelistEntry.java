package com.philips.lighting.hue.sdk.wrapper.domain.resource;

import com.philips.lighting.hue.sdk.wrapper.SessionObject;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;
import java.util.Date;

public class WhitelistEntry extends SessionObject implements BridgeResource {
    private byte[] appName = null;
    private Date created = null;
    private byte[] deviceName = null;
    private byte[] identifier = null;
    private Date lastUsed = null;
    private byte[] name = null;

    public native void syncNative();

    protected WhitelistEntry(long session_key) {
        super(session_key);
    }

    public WhitelistEntry() {
    }

    public String getIdentifier() {
        return NativeTools.BytesToString(this.identifier);
    }

    public void setIdentifier(String identifier2) {
        this.identifier = NativeTools.StringToBytes(identifier2);
    }

    public String getName() {
        return NativeTools.BytesToString(this.name);
    }

    public void setName(String name2) {
        this.name = NativeTools.StringToBytes(name2);
    }

    public String getUserName() {
        return NativeTools.BytesToString(this.identifier);
    }

    public String getAppName() {
        return NativeTools.BytesToString(this.appName);
    }

    public void setAppName(String appName2) {
        this.appName = NativeTools.StringToBytes(appName2);
    }

    public String getDeviceName() {
        return NativeTools.BytesToString(this.deviceName);
    }

    public void setDeviceName(String deviceName2) {
        this.deviceName = NativeTools.StringToBytes(deviceName2);
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created2) {
        this.created = created2;
    }

    public Date getLastUsed() {
        return this.lastUsed;
    }

    public void setLastUsed(Date lastUsed2) {
        this.lastUsed = lastUsed2;
    }

    public int getDomainType() {
        return getType().getValue();
    }

    public DomainType getType() {
        return DomainType.WHITELIST_ENTRY;
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
        BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_SUPPORTED);
    }

    public void fetch(BridgeConnectionType connectionType, BridgeResponseCallback callback) {
        BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_SUPPORTED);
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((super.hashCode() * 31) + Arrays.hashCode(this.appName)) * 31) + (this.created == null ? 0 : this.created.hashCode())) * 31) + Arrays.hashCode(this.deviceName)) * 31) + Arrays.hashCode(this.identifier)) * 31;
        if (this.lastUsed != null) {
            i = this.lastUsed.hashCode();
        }
        return ((hashCode + i) * 31) + Arrays.hashCode(this.name);
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
        WhitelistEntry other = (WhitelistEntry) obj;
        if (!Arrays.equals(this.appName, other.appName)) {
            return false;
        }
        if (this.created == null) {
            if (other.created != null) {
                return false;
            }
        } else if (!this.created.equals(other.created)) {
            return false;
        }
        if (!Arrays.equals(this.deviceName, other.deviceName)) {
            return false;
        }
        if (!Arrays.equals(this.identifier, other.identifier)) {
            return false;
        }
        if (this.lastUsed == null) {
            if (other.lastUsed != null) {
                return false;
            }
        } else if (!this.lastUsed.equals(other.lastUsed)) {
            return false;
        }
        if (!Arrays.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
