package com.philips.lighting.hue.sdk.wrapper.domain.device.light;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.Alert;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ColorMode;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.DoublePair;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.Effect;
import com.philips.lighting.hue.sdk.wrapper.utilities.Color;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class LightStateImpl extends WrapperObject implements LightState {
    @Nonnull
    public final native LightState clone();

    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void delete();

    public final native Alert getAlert();

    public final native Integer getBrightness();

    public final native Integer getBrightnessIncrement();

    @Nonnull
    public final native Color getColor();

    @Nonnull
    public final native ColorMode getColormode();

    public final native Integer getCt();

    public final native Integer getCtIncrement();

    @Nonnull
    public final native Effect getEffect();

    public final native Integer getHue();

    public final native Integer getHueIncrement();

    public final native Integer getSaturation();

    public final native Integer getSaturationIncrement();

    public final native Integer getTransitionTime();

    @Nonnull
    public final native DoublePair getXy();

    @Nonnull
    public final native DoublePair getXyIncrement();

    public final native Boolean isOn();

    public final native Boolean isReachable();

    public final native void setAlert(Alert alert);

    public final native void setBrightness(Integer num);

    public final native void setBrightnessIncrement(Integer num);

    public final native void setCt(Integer num);

    public final native void setCtIncrement(Integer num);

    public final native void setEffect(@Nonnull Effect effect);

    public final native void setHue(Integer num);

    public final native void setHueIncrement(Integer num);

    public final native void setOn(Boolean bool);

    public final native void setSaturation(Integer num);

    public final native void setSaturationIncrement(Integer num);

    public final native void setTransitionTime(Integer num);

    public final native void setXy(@Nonnull DoublePair doublePair);

    public final native void setXyIncrement(@Nonnull DoublePair doublePair);

    public final native void setXyWithColor(@Nonnull Color color);

    public final native void setXybWithColor(@Nonnull Color color);

    public native String toString();

    public LightStateImpl() {
        create();
    }

    protected LightStateImpl(Scope scope) {
    }

    public DomainType getType() {
        return DomainType.LIGHT_STATE;
    }

    @Deprecated
    public LightState setCT(Integer ct) {
        setCt(ct);
        return this;
    }

    @Deprecated
    public Integer getCT() {
        return getCt();
    }

    @Deprecated
    public LightState setXY(DoublePair xy) {
        setXy(xy);
        return this;
    }

    @Deprecated
    public DoublePair getXY() {
        return getXy();
    }

    @Deprecated
    public LightState setXY(double x, double y) {
        setXy(new DoublePair(x, y));
        return this;
    }

    @Deprecated
    public Integer getCTIncrement() {
        return getCtIncrement();
    }

    @Deprecated
    public LightState setCTIncrement(Integer ctIncrement) {
        setCtIncrement(ctIncrement);
        return this;
    }

    @Deprecated
    public DoublePair getXYIncrement() {
        return new DoublePair();
    }

    @Deprecated
    public LightState setXYIncrement(DoublePair xyIncrement) {
        setXyIncrement(xyIncrement);
        return this;
    }

    @Deprecated
    public LightState setXYIncrement(double xIncrement, double yIncrement) {
        setXYIncrement(xIncrement, yIncrement);
        return this;
    }

    @Deprecated
    public LightState setXYWithColor(Color color) {
        setXyWithColor(color);
        return this;
    }

    @Deprecated
    public LightState setXYBWithColor(Color color) {
        setXybWithColor(color);
        return this;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{getAlert(), getBrightness(), getBrightnessIncrement(), getColormode(), getCt(), getCtIncrement(), getEffect(), getHue(), getHueIncrement(), isOn(), isReachable(), getSaturation(), getSaturationIncrement(), getTransitionTime(), getXy(), getXyIncrement()});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        LightState other = (LightState) obj;
        if (!Objects.equals(getAlert(), other.getAlert()) || !Objects.equals(getBrightness(), other.getBrightness()) || !Objects.equals(getBrightnessIncrement(), other.getBrightnessIncrement()) || !Objects.equals(getColormode(), other.getColormode()) || !Objects.equals(getCt(), other.getCt()) || !Objects.equals(getCtIncrement(), other.getCtIncrement()) || !Objects.equals(getEffect(), other.getEffect()) || !Objects.equals(getHue(), other.getHue()) || !Objects.equals(getHueIncrement(), other.getHueIncrement()) || !Objects.equals(isOn(), other.isOn()) || !Objects.equals(isReachable(), other.isReachable()) || !Objects.equals(getSaturation(), other.getSaturation()) || !Objects.equals(getSaturationIncrement(), other.getSaturationIncrement()) || !Objects.equals(getTransitionTime(), other.getTransitionTime()) || !Objects.equals(getXy(), other.getXy()) || !Objects.equals(getXyIncrement(), other.getXyIncrement())) {
            return false;
        }
        return true;
    }
}
