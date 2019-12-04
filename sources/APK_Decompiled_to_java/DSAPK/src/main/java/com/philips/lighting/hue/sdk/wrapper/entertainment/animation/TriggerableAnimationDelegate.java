package com.philips.lighting.hue.sdk.wrapper.entertainment.animation;

public interface TriggerableAnimationDelegate extends AnimationDelegate {
    void trigger(TriggerableAnimation triggerableAnimation, String str);

    void triggerOnLevel(TriggerableAnimation triggerableAnimation, String str);
}
