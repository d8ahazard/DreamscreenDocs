package com.philips.lighting.hue.sdk.wrapper.utilities;

import com.philips.lighting.hue.sdk.wrapper.SessionObject;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightConfiguration;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import java.lang.reflect.Array;

public class HueColor extends SessionObject {
    public static final double MAX_BRIGHTNESS = 255.0d;
    public static final double MAX_COLOR_TEMPERATURE = 500.0d;
    public static final double MAX_HUE = 65535.0d;
    public static final double MAX_RGB = 255.0d;
    public static final double MAX_SATURATION = 255.0d;

    public static class HSL {
        public double h = 0.0d;
        public double l = 0.0d;
        public double s = 0.0d;

        public HSL(double h2, double s2, double l2) {
            this.h = h2;
            this.s = s2;
            this.l = l2;
        }

        public HSL(Double h2, Double s2, Double l2) {
            this.h = h2.doubleValue();
            this.s = s2.doubleValue();
            this.l = l2.doubleValue();
        }

        public HSL() {
        }
    }

    public static class HSV {
        public double h = 0.0d;
        public double s = 0.0d;
        public double v = 0.0d;

        public HSV(double h2, double s2, double v2) {
            this.h = h2;
            this.s = s2;
            this.v = v2;
        }

        public HSV(Double h2, Double s2, Double v2) {
            this.h = h2.doubleValue();
            this.s = s2.doubleValue();
            this.v = v2.doubleValue();
        }

        public HSV() {
        }
    }

    public static class RGB {
        public int b = 0;
        public int g = 0;
        public int r = 0;

        public RGB(int r2, int g2, int b2) {
            this.r = r2;
            this.g = g2;
            this.b = b2;
        }

        public RGB(Integer r2, Integer g2, Integer b2) {
            this.r = r2.intValue();
            this.g = g2.intValue();
            this.b = b2.intValue();
        }

        public RGB() {
        }
    }

    public static class XY {
        public double x = 0.0d;
        public double y = 0.0d;

        public XY(double x2, double y2) {
            this.x = x2;
            this.y = y2;
        }

        public XY(Double x2, Double y2) {
            this.x = x2.doubleValue();
            this.y = y2.doubleValue();
        }

        public XY() {
        }
    }

    private native void configureModelAndVersionNative(byte[] bArr, byte[] bArr2);

    private static native int[] convertToRGBColorsNative(double[] dArr, String str, String str2);

    private static native double[] convertToXYNative(int[] iArr, String str, String str2);

    private native void createCTKelvinColorNative(double d, double d2, byte[] bArr, byte[] bArr2);

    private native void createCTMiredColorNative(int i, double d, byte[] bArr, byte[] bArr2);

    private native void createHSLColorNative(double d, double d2, double d3, byte[] bArr, byte[] bArr2);

    private native void createHSVColorNative(double d, double d2, double d3, byte[] bArr, byte[] bArr2);

    private native void createRGBColorNative(int i, int i2, int i3, byte[] bArr, byte[] bArr2);

    private native void createXYColorNative(double d, double d2, double d3, byte[] bArr, byte[] bArr2);

    private native void destroyNative();

    private native void setBrightnessNative(double d);

    private native void setCTKelvinNative(double d);

    private native void setCTMiredNative(int i);

    private native void setHSLNative(double d, double d2, double d3);

    private native void setHSVNative(double d, double d2, double d3);

    private native void setRGBNative(int i, int i2, int i3);

    private native void setXYNative(double d, double d2);

    public native double getBrightness();

    public native double getCTKelvin();

    public native int getCTMired();

    public native HSL getHSL();

    public native HSV getHSV();

    public native RGB getRGB();

    public native XY getXY();

    public HueColor(RGB color, String model, String swVersion) {
        createRGBColorNative(color.r, color.g, color.b, NativeTools.StringToBytes(model), NativeTools.StringToBytes(swVersion));
    }

    public HueColor(HSV color, String model, String swVersion) {
        createHSVColorNative(color.h, color.s, color.v, NativeTools.StringToBytes(model), NativeTools.StringToBytes(swVersion));
    }

    public HueColor(HSL color, String model, String swVersion) {
        createHSLColorNative(color.h, color.s, color.l, NativeTools.StringToBytes(model), NativeTools.StringToBytes(swVersion));
    }

    public HueColor(XY color, double brightness, String model, String swVersion) {
        createXYColorNative(color.x, color.y, brightness, NativeTools.StringToBytes(model), NativeTools.StringToBytes(swVersion));
    }

    public HueColor(double ct, double brightness, String model, String swVersion) {
        createCTKelvinColorNative(ct, brightness, NativeTools.StringToBytes(model), NativeTools.StringToBytes(swVersion));
    }

    public HueColor(int ct, double brightness, String model, String swVersion) {
        createCTMiredColorNative(ct, brightness, NativeTools.StringToBytes(model), NativeTools.StringToBytes(swVersion));
    }

    public void setRGB(RGB rgb) {
        if (rgb != null) {
            setRGBNative(rgb.r, rgb.g, rgb.b);
        }
    }

    public void setHSV(HSV hsv) {
        if (hsv != null) {
            setHSVNative(hsv.h, hsv.s, hsv.v);
        }
    }

    public void setHSL(HSL hsl) {
        if (hsl != null) {
            setHSLNative(hsl.h, hsl.s, hsl.l);
        }
    }

    public void setXY(XY xy) {
        if (xy != null) {
            setXYNative(xy.x, xy.y);
        }
    }

    public void setCTKelvin(Double ct) {
        if (ct != null) {
            setCTKelvinNative(ct.doubleValue());
        }
    }

    public void setCTMired(Integer ct) {
        if (ct != null) {
            setCTMiredNative(ct.intValue());
        }
    }

    public void setBrightness(Double brightness) {
        if (brightness != null) {
            setBrightnessNative(brightness.doubleValue());
        }
    }

    public void setXYB(XY xy, Double brightness) {
        setXY(xy);
        setBrightness(brightness);
    }

    public void setCTBKelvin(Double ct, Double brightness) {
        setCTKelvin(ct);
        setBrightness(brightness);
    }

    public void setCTBMired(Integer ct, Double brightness) {
        setCTMired(ct);
        setBrightness(brightness);
    }

    public void configureModelAndSwVersion(String model, String swVersion) {
        if (model != null && swVersion != null) {
            configureModelAndVersionNative(NativeTools.StringToBytes(model), NativeTools.StringToBytes(swVersion));
        }
    }

    public void syncNative() {
    }

    public void setSessionKey(long sessionKey) {
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        destroyNative();
        super.finalize();
    }

    public static double[][] bulkConvertToXY(int[] rgbColors, LightPoint lightPoint) {
        String model = null;
        String version = null;
        if (lightPoint != null) {
            LightConfiguration lightConfiguration = lightPoint.getLightConfiguration();
            if (lightConfiguration != null) {
                String modelIdentifier = lightConfiguration.getModelIdentifier();
                if (modelIdentifier != null) {
                    model = modelIdentifier;
                }
                String swVersion = lightConfiguration.getSwVersion();
                if (swVersion != null) {
                    version = swVersion;
                }
            }
        }
        return bulkConvertToXY(rgbColors, model, version);
    }

    public static double[][] bulkConvertToXY(int[] rgbColors, String model, String swVersion) {
        double[] converted = convertToXYNative(rgbColors, model, swVersion);
        double[][] result = (double[][]) Array.newInstance(Double.TYPE, new int[]{converted.length / 2, 2});
        int index = 0;
        for (int i = 0; i < converted.length; i += 2) {
            result[index][0] = converted[i];
            index++;
        }
        int index2 = 0;
        for (int i2 = 1; i2 < converted.length; i2 += 2) {
            result[index2][1] = converted[i2];
            index2++;
        }
        return result;
    }

    public static int[] bulkConvertToRGBColors(double[][] xyPoints, LightPoint lightPoint) {
        String model = null;
        String version = null;
        if (lightPoint != null) {
            LightConfiguration lightConfiguration = lightPoint.getLightConfiguration();
            if (lightConfiguration != null) {
                String modelIdentifier = lightConfiguration.getModelIdentifier();
                if (modelIdentifier != null) {
                    model = modelIdentifier;
                }
                String swVersion = lightConfiguration.getSwVersion();
                if (swVersion != null) {
                    version = swVersion;
                }
            }
        }
        return bulkConvertToRGBColors(xyPoints, model, version);
    }

    public static int[] bulkConvertToRGBColors(double[][] xyPoints, String model, String swVersion) {
        double[] colorpoints = new double[(xyPoints.length * 2)];
        int j = 0;
        for (int i = 0; i < xyPoints.length; i++) {
            colorpoints[j] = xyPoints[i][0];
            int j2 = j + 1;
            colorpoints[j2] = xyPoints[i][1];
            j = j2 + 1;
        }
        return convertToRGBColorsNative(colorpoints, model, swVersion);
    }

    public int hashCode() {
        XY xy = getXY();
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
        HueColor other = (HueColor) obj;
        XY xy = getXY();
        XY otherXY = other.getXY();
        if (xy.x != otherXY.x) {
            return false;
        }
        if (xy.y != otherXY.y) {
            return false;
        }
        if (getBrightness() != other.getBrightness()) {
            return false;
        }
        return true;
    }
}
