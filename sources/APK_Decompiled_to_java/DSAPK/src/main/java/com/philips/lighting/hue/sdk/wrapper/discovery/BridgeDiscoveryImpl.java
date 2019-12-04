package com.philips.lighting.hue.sdk.wrapper.discovery;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscovery.Callback;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscovery.Option;
import java.util.EnumSet;
import javax.annotation.Nonnull;

public final class BridgeDiscoveryImpl extends WrapperObject implements BridgeDiscovery {
    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void delete();

    public final native boolean isSearching();

    public final native void search(Callback callback);

    public final native void search(@Nonnull EnumSet<Option> enumSet, Callback callback);

    public final native void stop();

    public BridgeDiscoveryImpl() {
        create();
    }

    protected BridgeDiscoveryImpl(Scope scope) {
    }

    @Deprecated
    public void search(Option option, Callback cb) {
        search(EnumSet.of(option), cb);
    }

    @Deprecated
    public void search(int options, Callback cb) {
        search(Option.enumSetFromValue((long) options), cb);
    }
}
