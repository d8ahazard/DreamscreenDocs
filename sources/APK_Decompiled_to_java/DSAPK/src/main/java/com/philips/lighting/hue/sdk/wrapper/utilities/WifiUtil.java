package com.philips.lighting.hue.sdk.wrapper.utilities;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import javax.annotation.Nullable;

public class WifiUtil {
    WifiManager wifiManager;

    public WifiUtil(Context context) {
        this.wifiManager = (WifiManager) context.getSystemService("wifi");
    }

    @Nullable
    public Boolean isEnabled() {
        if (this.wifiManager != null) {
            return Boolean.valueOf(this.wifiManager.isWifiEnabled());
        }
        return null;
    }

    @Nullable
    private Boolean isWifiOnAndConnected() {
        if (this.wifiManager == null) {
            return null;
        }
        if (!this.wifiManager.isWifiEnabled()) {
            return Boolean.valueOf(false);
        }
        if (this.wifiManager.getConnectionInfo().getNetworkId() == -1) {
            return Boolean.valueOf(false);
        }
        return Boolean.valueOf(true);
    }

    private String getName() {
        return "";
    }

    @Nullable
    private String getSSID() {
        if (this.wifiManager == null) {
            return null;
        }
        WifiInfo wifiInfo = this.wifiManager.getConnectionInfo();
        if (wifiInfo != null) {
            return wifiInfo.getSSID();
        }
        return null;
    }

    @Nullable
    private String getIpV4Address() {
        if (this.wifiManager == null) {
            return null;
        }
        int ipAddress = this.wifiManager.getConnectionInfo().getIpAddress();
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }
        try {
            return InetAddress.getByAddress(BigInteger.valueOf((long) ipAddress).toByteArray()).getHostAddress();
        } catch (UnknownHostException e) {
            Log.e("WIFIIP", "Unable to get host address.");
            return null;
        }
    }
}
