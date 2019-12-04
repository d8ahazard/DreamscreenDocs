package com.philips.lighting.hue.sdk.wrapper.utilities;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface Color {
    public static final double MAX_BRIGHTNESS = 255.0d;
    public static final double MAX_COLOR_TEMPERATURE = 500.0d;
    public static final double MAX_HUE = 65535.0d;
    public static final double MAX_RGB = 255.0d;
    public static final double MAX_SATURATION = 255.0d;

    public static final class HSL {
        public double h;
        public double l;
        public double s;

        public HSL() {
        }

        public HSL(double h2, double s2, double l2) {
            this.h = h2;
            this.s = s2;
            this.l = l2;
        }

        public boolean equals(@Nullable Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            HSL other = (HSL) o;
            if (!Objects.equals(Double.valueOf(this.h), Double.valueOf(other.h)) || !Objects.equals(Double.valueOf(this.s), Double.valueOf(other.s)) || !Objects.equals(Double.valueOf(this.l), Double.valueOf(other.l))) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{Double.valueOf(this.h), Double.valueOf(this.s), Double.valueOf(this.l)});
        }
    }

    public static final class HSV {
        public double h;
        public double s;
        public double v;

        public HSV() {
        }

        public HSV(double h2, double s2, double v2) {
            this.h = h2;
            this.s = s2;
            this.v = v2;
        }

        public boolean equals(@Nullable Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            HSV other = (HSV) o;
            if (!Objects.equals(Double.valueOf(this.h), Double.valueOf(other.h)) || !Objects.equals(Double.valueOf(this.s), Double.valueOf(other.s)) || !Objects.equals(Double.valueOf(this.v), Double.valueOf(other.v))) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{Double.valueOf(this.h), Double.valueOf(this.s), Double.valueOf(this.v)});
        }
    }

    public static final class RGB {
        public int b;
        public int g;
        public int r;

        public RGB() {
        }

        public RGB(int r2, int g2, int b2) {
            this.r = r2;
            this.g = g2;
            this.b = b2;
        }

        public boolean equals(@Nullable Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            RGB other = (RGB) o;
            if (!Objects.equals(Integer.valueOf(this.r), Integer.valueOf(other.r)) || !Objects.equals(Integer.valueOf(this.g), Integer.valueOf(other.g)) || !Objects.equals(Integer.valueOf(this.b), Integer.valueOf(other.b))) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{Integer.valueOf(this.r), Integer.valueOf(this.g), Integer.valueOf(this.b)});
        }
    }

    public static final class XY {
        public double x;
        public double y;

        public XY() {
        }

        public XY(double x2, double y2) {
            this.x = x2;
            this.y = y2;
        }

        public boolean equals(@Nullable Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            XY other = (XY) o;
            if (!Objects.equals(Double.valueOf(this.x), Double.valueOf(other.x)) || !Objects.equals(Double.valueOf(this.y), Double.valueOf(other.y))) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{Double.valueOf(this.x), Double.valueOf(this.y)});
        }
    }

    void configureModel(String str, String str2);

    @Deprecated
    void configureModelAndSwVersion(String str, String str2);

    double getBrightness();

    @Deprecated
    double getCTKelvin();

    @Deprecated
    int getCTMired();

    double getCtKelvin();

    int getCtMired();

    @Deprecated
    HSL getHSL();

    @Deprecated
    HSV getHSV();

    @Nonnull
    HSL getHsl();

    @Nonnull
    HSV getHsv();

    @Deprecated
    RGB getRGB();

    @Nonnull
    RGB getRgb();

    @Deprecated
    XY getXY();

    @Nonnull
    XY getXy();

    void setBrightness(double d);

    @Deprecated
    void setBrightness(Double d);

    @Deprecated
    void setCTBKelvin(Double d, Double d2);

    @Deprecated
    void setCTBMired(Integer num, Double d);

    @Deprecated
    void setCTKelvin(Double d);

    @Deprecated
    void setCTMired(Integer num);

    void setCtKelvin(double d);

    void setCtMired(int i);

    void setCtbKelvin(double d, double d2);

    void setCtbMired(int i, double d);

    @Deprecated
    void setHSL(HSL hsl);

    @Deprecated
    void setHSV(HSV hsv);

    void setHsl(@Nonnull HSL hsl);

    void setHsv(@Nonnull HSV hsv);

    @Deprecated
    void setRGB(RGB rgb);

    void setRgb(@Nonnull RGB rgb);

    @Deprecated
    void setXY(XY xy);

    @Deprecated
    void setXYB(XY xy, Double d);

    void setXy(@Nonnull XY xy);

    void setXyb(@Nonnull XY xy, double d);
}
