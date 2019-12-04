package com.philips.lighting.hue.sdk.wrapper.domain.device;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import java.util.List;
import javax.annotation.Nonnull;

public final class CompoundDeviceImpl extends WrapperObject implements CompoundDevice {
    /* access modifiers changed from: protected */
    public native void delete();

    public final native Device getDevice(@Nonnull DomainType domainType, @Nonnull String str);

    public final native List<Device> getDevices();

    public final native List<Device> getDevices(@Nonnull DomainType domainType);

    public final native boolean isComplete();

    protected CompoundDeviceImpl(Scope scope) {
    }
}
