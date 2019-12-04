package com.philips.lighting.hue.sdk.wrapper.entertainment.effect;

import com.philips.lighting.hue.sdk.wrapper.entertainment.Color;
import com.philips.lighting.hue.sdk.wrapper.entertainment.Light;

public interface EffectDelegate {
    Color getColor(Effect effect, Light light);

    String getTypeName();

    void render(Effect effect);
}
