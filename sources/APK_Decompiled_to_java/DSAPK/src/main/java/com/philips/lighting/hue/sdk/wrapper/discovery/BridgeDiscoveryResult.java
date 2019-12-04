package com.philips.lighting.hue.sdk.wrapper.discovery;

import javax.annotation.Nonnull;

public interface BridgeDiscoveryResult {
    @Nonnull
    String getApiVersion();

    @Deprecated
    String getIP();

    @Nonnull
    String getIp();

    @Nonnull
    String getModelId();

    @Deprecated
    String getUniqueID();

    @Nonnull
    String getUniqueId();

    void setApiVersion(@Nonnull String str);

    @Deprecated
    void setIP(String str);

    void setIp(@Nonnull String str);

    void setModelId(@Nonnull String str);

    @Deprecated
    void setUniqueID(String str);

    void setUniqueId(@Nonnull String str);
}
