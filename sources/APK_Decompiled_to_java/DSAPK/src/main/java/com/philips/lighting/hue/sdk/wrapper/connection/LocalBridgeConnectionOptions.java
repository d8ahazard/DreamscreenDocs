package com.philips.lighting.hue.sdk.wrapper.connection;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;

public class LocalBridgeConnectionOptions implements BridgeConnectionOptions {
    private byte[] appName;
    private BridgeConnectionProtocol bridgeConnectionProtocol;
    private Integer connectTimeout;
    private byte[] connectionIp;
    private byte[] deviceName;
    private boolean fastConnectionMode;
    private byte[] fastConnectionModeBridgeId;
    private Integer maxRetryCount;
    private boolean queueEnabled;
    private Integer receiveTimeout;
    private Integer requestTimeout;
    private byte[] userName;

    private LocalBridgeConnectionOptions() {
        this.connectTimeout = null;
        this.receiveTimeout = null;
        this.requestTimeout = null;
        this.maxRetryCount = null;
        this.fastConnectionMode = false;
        this.fastConnectionModeBridgeId = null;
        this.bridgeConnectionProtocol = BridgeConnectionProtocol.PREFER_HTTPS;
    }

    public LocalBridgeConnectionOptions(String connectionIp2, String appName2, String deviceName2) {
        this(connectionIp2, appName2, deviceName2, BridgeConnectionProtocol.PREFER_HTTPS);
    }

    public LocalBridgeConnectionOptions(String connectionIp2, String appName2, String deviceName2, BridgeConnectionProtocol bridgeConnectionProtocol2) {
        this.connectTimeout = null;
        this.receiveTimeout = null;
        this.requestTimeout = null;
        this.maxRetryCount = null;
        this.fastConnectionMode = false;
        this.fastConnectionModeBridgeId = null;
        this.bridgeConnectionProtocol = BridgeConnectionProtocol.PREFER_HTTPS;
        setConnectionIp(connectionIp2);
        setUserName(null);
        setAppName(appName2);
        setDeviceName(deviceName2);
        this.bridgeConnectionProtocol = bridgeConnectionProtocol2;
    }

    public LocalBridgeConnectionOptions(String connectionIp2, String appName2, String deviceName2, String userName2) {
        this(connectionIp2, appName2, deviceName2, userName2, BridgeConnectionProtocol.PREFER_HTTPS);
    }

    public LocalBridgeConnectionOptions(String connectionIp2, String appName2, String deviceName2, String userName2, BridgeConnectionProtocol bridgeConnectionProtocol2) {
        this.connectTimeout = null;
        this.receiveTimeout = null;
        this.requestTimeout = null;
        this.maxRetryCount = null;
        this.fastConnectionMode = false;
        this.fastConnectionModeBridgeId = null;
        this.bridgeConnectionProtocol = BridgeConnectionProtocol.PREFER_HTTPS;
        setConnectionIp(connectionIp2);
        setUserName(userName2);
        setAppName(appName2);
        setDeviceName(deviceName2);
        this.bridgeConnectionProtocol = bridgeConnectionProtocol2;
    }

    public BridgeConnectionType getConnectionType() {
        return BridgeConnectionType.LOCAL;
    }

    public int getConnectionTypeAsInt() {
        return getConnectionType().getValue();
    }

    public String getPushlinkConnectionString() {
        return buildConnectionString(this.bridgeConnectionProtocol.toString(), getConnectionIp(), null, null, new String[0]);
    }

    public String getConnectionIp() {
        return NativeTools.BytesToString(this.connectionIp);
    }

    public void setConnectionIp(String connectionIp2) {
        this.connectionIp = NativeTools.StringToBytes(connectionIp2);
    }

    public String getUserName() {
        return NativeTools.BytesToString(this.userName);
    }

    public void setUserName(String userName2) {
        this.userName = NativeTools.StringToBytes(userName2);
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

    public BridgeConnectionProtocol getProtocol() {
        return this.bridgeConnectionProtocol;
    }

    public void setProtocol(BridgeConnectionProtocol protocol) {
        this.bridgeConnectionProtocol = protocol;
    }

    private static String buildConnectionString(String protocol, String host, Integer port, String username, String... resourcepaths) {
        String result;
        if (port == null) {
            result = String.format("%s://%s/api", new Object[]{protocol, host});
        } else {
            result = String.format("%s://%s:%i/api", new Object[]{protocol, host, port});
        }
        if (username != null) {
            result = result + "/" + username;
        }
        if (!(resourcepaths == null || resourcepaths.length == 0)) {
            for (String resourcepath : resourcepaths) {
                if (resourcepath != null) {
                    result = result + "/" + resourcepath;
                }
            }
        }
        return result;
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

    public void enableQueue(boolean enable) {
        this.queueEnabled = enable;
    }

    public boolean isQueueEnabled() {
        return this.queueEnabled;
    }

    public Integer getMaxRetryCount() {
        return this.maxRetryCount;
    }

    public void setMaxRetryCount(Integer count) {
        this.maxRetryCount = count;
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
        int i;
        int hashCode2;
        int i2;
        int hashCode3;
        int i3 = 1231;
        int i4 = 0;
        int hashCode4 = (Arrays.hashCode(this.appName) + 31) * 31;
        if (this.connectTimeout == null) {
            hashCode = 0;
        } else {
            hashCode = this.connectTimeout.hashCode();
        }
        int hashCode5 = (((((hashCode4 + hashCode) * 31) + Arrays.hashCode(this.connectionIp)) * 31) + Arrays.hashCode(this.deviceName)) * 31;
        if (this.fastConnectionMode) {
            i = 1231;
        } else {
            i = 1237;
        }
        int hashCode6 = (((hashCode5 + i) * 31) + Arrays.hashCode(this.fastConnectionModeBridgeId)) * 31;
        if (this.maxRetryCount == null) {
            hashCode2 = 0;
        } else {
            hashCode2 = this.maxRetryCount.hashCode();
        }
        int i5 = (hashCode6 + hashCode2) * 31;
        if (this.queueEnabled) {
            i2 = 1231;
        } else {
            i2 = 1237;
        }
        int i6 = (i5 + i2) * 31;
        if (this.receiveTimeout == null) {
            hashCode3 = 0;
        } else {
            hashCode3 = this.receiveTimeout.hashCode();
        }
        int i7 = (i6 + hashCode3) * 31;
        if (this.requestTimeout != null) {
            i4 = this.requestTimeout.hashCode();
        }
        int hashCode7 = (((i7 + i4) * 31) + Arrays.hashCode(this.userName)) * 31;
        if (this.bridgeConnectionProtocol != BridgeConnectionProtocol.HTTPS) {
            i3 = this.bridgeConnectionProtocol == BridgeConnectionProtocol.PREFER_HTTPS ? 1237 : 1249;
        }
        return hashCode7 + i3;
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
        LocalBridgeConnectionOptions other = (LocalBridgeConnectionOptions) obj;
        if (!Arrays.equals(this.appName, other.appName)) {
            return false;
        }
        if (this.connectTimeout == null) {
            if (other.connectTimeout != null) {
                return false;
            }
        } else if (!this.connectTimeout.equals(other.connectTimeout)) {
            return false;
        }
        if (!Arrays.equals(this.connectionIp, other.connectionIp)) {
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
        if (this.queueEnabled != other.queueEnabled) {
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
        } else if (!this.requestTimeout.equals(other.requestTimeout)) {
            return false;
        }
        if (!Arrays.equals(this.userName, other.userName)) {
            return false;
        }
        if (this.bridgeConnectionProtocol != other.bridgeConnectionProtocol) {
            return false;
        }
        return true;
    }
}
