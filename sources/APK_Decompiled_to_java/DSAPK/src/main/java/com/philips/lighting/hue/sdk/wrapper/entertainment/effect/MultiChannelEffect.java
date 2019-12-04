package com.philips.lighting.hue.sdk.wrapper.entertainment.effect;

import com.philips.lighting.hue.sdk.wrapper.entertainment.Channel;

public class MultiChannelEffect extends AnimationEffect {
    public final native void addChannel(Channel channel);

    public final native void clear();

    /* access modifiers changed from: protected */
    public native void create(String str, int i);

    /* access modifiers changed from: protected */
    public native void delete();

    public MultiChannelEffect() {
        super(Scope.Internal);
        create("", 0);
    }

    public MultiChannelEffect(String name, int priority) {
        super(Scope.Internal);
        create(name, priority);
    }

    public EffectDelegate getDelegate() {
        return null;
    }

    protected MultiChannelEffect(Scope scope) {
        super(scope);
    }
}
