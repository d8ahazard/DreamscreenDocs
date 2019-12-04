package com.philips.lighting.hue.sdk.wrapper.connection;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;

public class PortalConnectionOptions {
    private byte[] accountGUID;
    private byte[] appId;
    private byte[] appName;
    private byte[] callbackUrl;
    private byte[] clientId;
    private byte[] clientSecret;
    private Integer connectTimeout = null;
    private byte[] deviceId;
    private byte[] deviceName;
    private boolean fastConnectionMode;
    private byte[] fastConnectionModeBridgeId = null;
    private Integer maxRetryCount = null;
    private Integer receiveTimeout = null;
    private Integer requestTimeout = null;

    public PortalConnectionOptions(String accountGUID2, String callbackUrl2, String appId2, String appName2, String deviceId2, String deviceName2, String clientId2, String clientSecret2) {
        setCallbackUrl(callbackUrl2);
        setAppId(appId2);
        setAppName(appName2);
        setDeviceId(deviceId2);
        setDeviceName(deviceName2);
        setClientId(clientId2);
        setClientSecret(clientSecret2);
        setAccountGUID(accountGUID2);
    }

    public String getCallbackUrl() {
        return NativeTools.BytesToString(this.callbackUrl);
    }

    public void setCallbackUrl(String callbackUrl2) {
        this.callbackUrl = NativeTools.StringToBytes(callbackUrl2);
    }

    public String getClientId() {
        return NativeTools.BytesToString(this.clientId);
    }

    public void setClientId(String clientId2) {
        this.clientId = NativeTools.StringToBytes(clientId2);
    }

    public String getClientSecret() {
        return NativeTools.BytesToString(this.clientSecret);
    }

    public void setClientSecret(String clientSecret2) {
        this.clientSecret = NativeTools.StringToBytes(clientSecret2);
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

    public String getAppId() {
        return NativeTools.BytesToString(this.appId);
    }

    public void setAppId(String appId2) {
        this.appId = NativeTools.StringToBytes(appId2);
    }

    public String getDeviceId() {
        return NativeTools.BytesToString(this.deviceId);
    }

    public void setDeviceId(String deviceId2) {
        this.deviceId = NativeTools.StringToBytes(deviceId2);
    }

    public Integer getConnectTimeout() {
        return this.connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout2) {
        this.connectTimeout = connectTimeout2;
    }

    public Integer getReceiveTimeout() {
        return this.receiveTimeout;
    }

    public void setReceiveTimeout(Integer receiveTimeout2) {
        this.receiveTimeout = receiveTimeout2;
    }

    public Integer getRequestTimeout() {
        return this.requestTimeout;
    }

    public void setRequestTimeout(Integer requestTimeout2) {
        this.requestTimeout = requestTimeout2;
    }

    public Integer getMaxRetryCount() {
        return this.maxRetryCount;
    }

    public void setMaxRetryCount(Integer maxRetryCount2) {
        this.maxRetryCount = maxRetryCount2;
    }

    public String getAccountGUID() {
        return NativeTools.BytesToString(this.accountGUID);
    }

    public void setAccountGUID(String accountGUID2) {
        this.accountGUID = NativeTools.StringToBytes(accountGUID2);
    }

    public boolean getFastConnectionMode() {
        return this.fastConnectionMode;
    }

    public String getFastConnectionUniqueId() {
        return NativeTools.BytesToString(this.fastConnectionModeBridgeId);
    }

    public void enableFastConnectionMode(String uniqueId) {
        this.fastConnectionMode = true;
        this.fastConnectionModeBridgeId = NativeTools.StringToBytes(uniqueId);
    }

    public void disableFastConnectionMode() {
        this.fastConnectionMode = false;
    }

    public int hashCode() {
        int hashCode;
        int hashCode2;
        int hashCode3;
        int i = 0;
        int hashCode4 = (((((((((((Arrays.hashCode(this.accountGUID) + 31) * 31) + Arrays.hashCode(this.appId)) * 31) + Arrays.hashCode(this.appName)) * 31) + Arrays.hashCode(this.callbackUrl)) * 31) + Arrays.hashCode(this.clientId)) * 31) + Arrays.hashCode(this.clientSecret)) * 31;
        if (this.connectTimeout == null) {
            hashCode = 0;
        } else {
            hashCode = this.connectTimeout.hashCode();
        }
        int hashCode5 = (((((((((hashCode4 + hashCode) * 31) + Arrays.hashCode(this.deviceId)) * 31) + Arrays.hashCode(this.deviceName)) * 31) + (this.fastConnectionMode ? 1231 : 1237)) * 31) + Arrays.hashCode(this.fastConnectionModeBridgeId)) * 31;
        if (this.maxRetryCount == null) {
            hashCode2 = 0;
        } else {
            hashCode2 = this.maxRetryCount.hashCode();
        }
        int i2 = (hashCode5 + hashCode2) * 31;
        if (this.receiveTimeout == null) {
            hashCode3 = 0;
        } else {
            hashCode3 = this.receiveTimeout.hashCode();
        }
        int i3 = (i2 + hashCode3) * 31;
        if (this.requestTimeout != null) {
            i = this.requestTimeout.hashCode();
        }
        return i3 + i;
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
        PortalConnectionOptions other = (PortalConnectionOptions) obj;
        if (!Arrays.equals(this.accountGUID, other.accountGUID)) {
            return false;
        }
        if (!Arrays.equals(this.appId, other.appId)) {
            return false;
        }
        if (!Arrays.equals(this.appName, other.appName)) {
            return false;
        }
        if (!Arrays.equals(this.callbackUrl, other.callbackUrl)) {
            return false;
        }
        if (!Arrays.equals(this.clientId, other.clientId)) {
            return false;
        }
        if (!Arrays.equals(this.clientSecret, other.clientSecret)) {
            return false;
        }
        if (this.connectTimeout == null) {
            if (other.connectTimeout != null) {
                return false;
            }
        } else if (!this.connectTimeout.equals(other.connectTimeout)) {
            return false;
        }
        if (!Arrays.equals(this.deviceId, other.deviceId)) {
            return false;
        }
        if (!Arrays.equals(this.deviceName, other.deviceName)) {
            return false;
        }
        if (this.fastConnectionMode != other.fastConnectionMode) {
            return false;
        }
        if (!Arrays.equals(this.fastConnectionModeBridgeId, other.fastConnectionModeBridgeId)) {
            return false;
        }
        if (this.maxRetryCount == null) {
            if (other.maxRetryCount != null) {
                return false;
            }
        } else if (!this.maxRetryCount.equals(other.maxRetryCount)) {
            return false;
        }
        if (this.receiveTimeout == null) {
            if (other.receiveTimeout != null) {
                return false;
            }
        } else if (!this.receiveTimeout.equals(other.receiveTimeout)) {
            return false;
        }
        if (this.requestTimeout == null) {
            if (other.requestTimeout != null) {
                return false;
            }
            return true;
        } else if (!this.requestTimeout.equals(other.requestTimeout)) {
            return false;
        } else {
            return true;
        }
    }
}
