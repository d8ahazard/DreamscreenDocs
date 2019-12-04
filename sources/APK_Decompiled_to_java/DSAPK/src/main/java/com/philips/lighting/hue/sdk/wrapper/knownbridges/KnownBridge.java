package com.philips.lighting.hue.sdk.wrapper.knownbridges;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Date;

public class KnownBridge {
    private Date firstConnected;
    private String ipAddress;
    private Date lastConnected;
    private String name;
    private String uniqueId;

    public KnownBridge(byte[] uniqueId2, byte[] ipAddress2, byte[] name2, long firstConnected2, long lastConnected2) {
        this.uniqueId = NativeTools.BytesToString(uniqueId2);
        this.ipAddress = NativeTools.BytesToString(ipAddress2);
        this.name = NativeTools.BytesToString(name2);
        this.firstConnected = new Date(firstConnected2);
        this.lastConnected = new Date(lastConnected2);
    }

    public String getUniqueId() {
        return this.uniqueId;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public String getName() {
        return this.name;
    }

    public Date getFirstConnected() {
        return this.firstConnected;
    }

    public Date getLastConnected() {
        return this.lastConnected;
    }
}
