package com.philips.lighting.hue.sdk.wrapper.utilities;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;
import com.philips.lighting.hue.sdk.wrapper.utilities.Color.HSL;
import com.philips.lighting.hue.sdk.wrapper.utilities.Color.HSV;
import com.philips.lighting.hue.sdk.wrapper.utilities.Color.RGB;
import com.philips.lighting.hue.sdk.wrapper.utilities.Color.XY;
import javax.annotation.Nonnull;

public final class ColorImpl extends WrapperObject implements Color {
    public final native void configureModel(String str, String str2);

    /* access modifiers changed from: protected */
    public native void create(double d, double d2, String str, String str2);

    /* access modifiers changed from: protected */
    public native void create(int i, double d, String str, String str2);

    /* access modifiers changed from: protected */
    public native void create(@Nonnull HSL hsl, String str, String str2);

    /* access modifiers changed from: protected */
    public native void create(@Nonnull HSV hsv, String str, String str2);

    /* access modifiers changed from: protected */
    public native void create(@Nonnull RGB rgb, String str, String str2);

    /* access modifiers changed from: protected */
    public native void create(@Nonnull XY xy, double d, String str, String str2);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native double getBrightness();

    public final native double getCtKelvin();

    public final native int getCtMired();

    @Nonnull
    public final native HSL getHsl();

    @Nonnull
    public final native HSV getHsv();

    @Nonnull
    public final native RGB getRgb();

    @Nonnull
    public final native XY getXy();

    public final native void setBrightness(double d);

    public final native void setCtKelvin(double d);

    public final native void setCtMired(int i);

    public final native void setCtbKelvin(double d, double d2);

    public final native void setCtbMired(int i, double d);

    public final native void setHsl(@Nonnull HSL hsl);

    public final native void setHsv(@Nonnull HSV hsv);

    public final native void setRgb(@Nonnull RGB rgb);

    public final native void setXy(@Nonnull XY xy);

    public final native void setXyb(@Nonnull XY xy, double d);

    public ColorImpl(@Nonnull RGB color, String model, String swVersion) {
        create(color, model, swVersion);
    }

    public ColorImpl(@Nonnull HSV color, String model, String swVersion) {
        create(color, model, swVersion);
    }

    public ColorImpl(@Nonnull HSL color, String model, String swVersion) {
        create(color, model, swVersion);
    }

    public ColorImpl(@Nonnull XY color, double brightness, String model, String swVersion) {
        create(color, brightness, model, swVersion);
    }

    public ColorImpl(double ct, double brightness, String model, String swVersion) {
        create(ct, brightness, model, swVersion);
    }

    public ColorImpl(int ct, double brightness, String model, String swVersion) {
        create(ct, brightness, model, swVersion);
    }

    protected ColorImpl(Scope scope) {
    }

    @Deprecated
    public RGB getRGB() {
        return getRgb();
    }

    @Deprecated
    public HSV getHSV() {
        return getHsv();
    }

    @Deprecated
    public HSL getHSL() {
        return getHsl();
    }

    @Deprecated
    public XY getXY() {
        return getXy();
    }

    @Deprecated
    public double getCTKelvin() {
        return getCtKelvin();
    }

    @Deprecated
    public int getCTMired() {
        return getCtMired();
    }

    @Deprecated
    public void setRGB(RGB rgb) {
        if (rgb != null) {
            setRgb(rgb);
        }
    }

    @Deprecated
    public void setHSV(HSV hsv) {
        if (hsv != null) {
            setHsv(hsv);
        }
    }

    @Deprecated
    public void setHSL(HSL hsl) {
        if (hsl != null) {
            setHsl(hsl);
        }
    }

    @Deprecated
    public void setXY(XY xy) {
        if (xy != null) {
            setXy(xy);
        }
    }

    @Deprecated
    public void setCTKelvin(Double ct) {
        if (ct != null) {
            setCtKelvin(ct.doubleValue());
        }
    }

    @Deprecated
    public void setCTMired(Integer ct) {
        if (ct != null) {
            setCtMired(ct.intValue());
        }
    }

    @Deprecated
    public void setXYB(XY xy, Double brightness) {
        setXY(xy);
        setBrightness(brightness);
    }

    @Deprecated
    public void setCTBKelvin(Double ct, Double brightness) {
        setCTKelvin(ct);
        setBrightness(brightness);
    }

    @Deprecated
    public void setCTBMired(Integer ct, Double brightness) {
        setCTMired(ct);
        setBrightness(brightness);
    }

    @Deprecated
    public void setBrightness(Double brightness) {
        if (brightness != null) {
            setBrightness(brightness.doubleValue());
        }
    }

    public int hashCode() {
        XY xy = getXy();
        return ((((Double.valueOf(xy.x).hashCode() + 31) * 31) + Double.valueOf(xy.y).hashCode()) * 31) + Double.valueOf(getBrightness()).hashCode();
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
        Color other = (Color) obj;
        XY xy = getXy();
        XY otherXy = other.getXy();
        if (xy.x != otherXy.x) {
            return false;
        }
        if (xy.y != otherXy.y) {
            return false;
        }
        if (getBrightness() != other.getBrightness()) {
            return false;
        }
        return true;
    }

    @Deprecated
    public void configureModelAndSwVersion(String model, String swVersion) {
        if (model != null && swVersion != null) {
            configureModel(model, swVersion);
        }
    }
}
