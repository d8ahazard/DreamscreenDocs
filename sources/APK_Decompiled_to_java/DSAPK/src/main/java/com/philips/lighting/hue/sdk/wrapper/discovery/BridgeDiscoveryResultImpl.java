package com.philips.lighting.hue.sdk.wrapper.discovery;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;
import javax.annotation.Nonnull;

public final class BridgeDiscoveryResultImpl extends WrapperObject implements BridgeDiscoveryResult {
    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void create(@Nonnull String str, @Nonnull String str2, @Nonnull String str3, @Nonnull String str4);

    /* access modifiers changed from: protected */
    public native void delete();

    @Nonnull
    public final native String getApiVersion();

    @Nonnull
    public final native String getIp();

    @Nonnull
    public final native String getModelId();

    @Nonnull
    public final native String getUniqueId();

    public final native void setApiVersion(@Nonnull String str);

    public final native void setIp(@Nonnull String str);

    public final native void setModelId(@Nonnull String str);

    public final native void setUniqueId(@Nonnull String str);

    public BridgeDiscoveryResultImpl() {
        create();
    }

    public BridgeDiscoveryResultImpl(@Nonnull String uniqueId, @Nonnull String ip, @Nonnull String apiVersion, @Nonnull String modelId) {
        create(uniqueId, ip, apiVersion, modelId);
    }

    protected BridgeDiscoveryResultImpl(Scope scope) {
    }

    @Deprecated
    public String getUniqueID() {
        return getUniqueId();
    }

    @Deprecated
    public void setUniqueID(String uniqueId) {
        setUniqueId(uniqueId);
    }

    @Deprecated
    public String getIP() {
        return getIp();
    }

    @Deprecated
    public void setIP(String ip) {
        setIp(ip);
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BridgeDiscoveryResult that = (BridgeDiscoveryResult) o;
        if (getIp() != null) {
            if (!getIp().equals(that.getIp())) {
                return false;
            }
        } else if (that.getIp() != null) {
            return false;
        }
        if (getUniqueId() != null) {
            if (!getUniqueId().equals(that.getUniqueId())) {
                return false;
            }
        } else if (that.getUniqueId() != null) {
            return false;
        }
        if (getModelId() != null) {
            if (!getModelId().equals(that.getModelId())) {
                return false;
            }
        } else if (that.getModelId() != null) {
            return false;
        }
        if (getApiVersion() != null) {
            z = getApiVersion().equals(that.getApiVersion());
        } else if (that.getApiVersion() != null) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int result;
        int i;
        int i2;
        int i3 = 0;
        if (getIp() != null) {
            result = getIp().hashCode();
        } else {
            result = 0;
        }
        int i4 = result * 31;
        if (getUniqueId() != null) {
            i = getUniqueId().hashCode();
        } else {
            i = 0;
        }
        int i5 = (i4 + i) * 31;
        if (getModelId() != null) {
            i2 = getModelId().hashCode();
        } else {
            i2 = 0;
        }
        int i6 = (i5 + i2) * 31;
        if (getApiVersion() != null) {
            i3 = getApiVersion().hashCode();
        }
        return i6 + i3;
    }
}
