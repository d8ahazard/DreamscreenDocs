package com.philips.lighting.hue.sdk.wrapper.connection;

public interface BridgeConnectionOptions {
    void disableFastConnectionMode();

    void enableFastConnectionMode(String str);

    void enableQueue(boolean z);

    boolean equals(Object obj);

    String getAppName();

    Integer getConnectTimeout();

    BridgeConnectionType getConnectionType();

    int getConnectionTypeAsInt();

    String getDeviceName();

    boolean getFastConnectionMode();

    String getFastConnectionUniqueId();

    Integer getMaxRetryCount();

    Integer getReceiveTimeout();

    Integer getRequestTimeout();

    String getUserName();

    int hashCode();

    boolean isQueueEnabled();

    void setAppName(String str);

    void setConnectTimeout(Integer num);

    void setDeviceName(String str);

    void setMaxRetryCount(Integer num);

    void setReceiveTimeout(Integer num);

    void setRequestTimeout(Integer num);

    void setUserName(String str);
}
