package com.philips.lighting.hue.sdk.wrapper.domain.device.light;

import com.philips.lighting.hue.sdk.wrapper.domain.clip.Alert;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ColorMode;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.DoublePair;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.Effect;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceState;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.Action;
import com.philips.lighting.hue.sdk.wrapper.utilities.Color;
import javax.annotation.Nonnull;

public interface LightState extends DeviceState, Action {
    @Nonnull
    LightState clone();

    Alert getAlert();

    Integer getBrightness();

    Integer getBrightnessIncrement();

    @Deprecated
    Integer getCT();

    @Deprecated
    Integer getCTIncrement();

    @Nonnull
    Color getColor();

    @Nonnull
    ColorMode getColormode();

    Integer getCt();

    Integer getCtIncrement();

    @Nonnull
    Effect getEffect();

    Integer getHue();

    Integer getHueIncrement();

    Integer getSaturation();

    Integer getSaturationIncrement();

    Integer getTransitionTime();

    @Deprecated
    DoublePair getXY();

    @Deprecated
    DoublePair getXYIncrement();

    @Nonnull
    DoublePair getXy();

    @Nonnull
    DoublePair getXyIncrement();

    Boolean isOn();

    Boolean isReachable();

    void setAlert(Alert alert);

    void setBrightness(Integer num);

    void setBrightnessIncrement(Integer num);

    @Deprecated
    LightState setCT(Integer num);

    @Deprecated
    LightState setCTIncrement(Integer num);

    void setCt(Integer num);

    void setCtIncrement(Integer num);

    void setEffect(@Nonnull Effect effect);

    void setHue(Integer num);

    void setHueIncrement(Integer num);

    void setOn(Boolean bool);

    void setSaturation(Integer num);

    void setSaturationIncrement(Integer num);

    void setTransitionTime(Integer num);

    @Deprecated
    LightState setXY(double d, double d2);

    @Deprecated
    LightState setXY(DoublePair doublePair);

    @Deprecated
    LightState setXYBWithColor(Color color);

    @Deprecated
    LightState setXYIncrement(double d, double d2);

    @Deprecated
    LightState setXYIncrement(DoublePair doublePair);

    @Deprecated
    LightState setXYWithColor(Color color);

    void setXy(@Nonnull DoublePair doublePair);

    void setXyIncrement(@Nonnull DoublePair doublePair);

    void setXyWithColor(@Nonnull Color color);

    void setXybWithColor(@Nonnull Color color);
}
