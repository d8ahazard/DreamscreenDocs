package com.philips.lighting.hue.sdk.wrapper.entertainment.animation;

import java.util.List;

public class SequenceAnimation extends TriggerableAnimation {
    public static final double DEFAULT_REPEAT_TIMES = 0.0d;

    public final native void append(Animation animation, String str);

    /* access modifiers changed from: protected */
    public native void create(double d);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native int getAnimationCount();

    public final native List<Animation> getAnimations();

    public final native void setAnimations(List<Animation> list);

    public SequenceAnimation() {
        super(Scope.Internal);
        create(0.0d);
    }

    public SequenceAnimation(double repeatTimes) {
        super(Scope.Internal);
        create(repeatTimes);
    }

    public AnimationDelegate getDelegate() {
        return null;
    }

    protected SequenceAnimation(Scope scope) {
        super(scope);
    }
}
