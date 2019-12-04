package com.philips.lighting.hue.sdk.wrapper.entertainment;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;

public final class Area extends WrapperObject {

    public static class Predefine {
        public static Area All = Area.Create("All");
        public static Area Back = Area.Create("Back");
        public static Area BackCenter = Area.Create("BackCenter");
        public static Area BackHalf = Area.Create("BackHalf");
        public static Area BackHalfCenter = Area.Create("BackHalfCenter");
        public static Area BackHalfLeft = Area.Create("BackHalfLeft");
        public static Area BackHalfRight = Area.Create("BackHalfRight");
        public static Area BackLeft = Area.Create("BackLeft");
        public static Area BackLeftQuarter = Area.Create("BackLeftQuarter");
        public static Area BackRight = Area.Create("BackRight");
        public static Area BackRightQuarter = Area.Create("BackRightQuarter");
        public static Area Center = Area.Create("Center");
        public static Area CenterFB = Area.Create("CenterFB");
        public static Area CenterLR = Area.Create("CenterLR");
        public static Area CenterLeft = Area.Create("CenterLeft");
        public static Area CenterRight = Area.Create("CenterRight");
        public static Area Front = Area.Create("Front");
        public static Area FrontCenter = Area.Create("FrontCenter");
        public static Area FrontHalf = Area.Create("FrontHalf");
        public static Area FrontHalfCenter = Area.Create("FrontHalfCenter");
        public static Area FrontHalfLeft = Area.Create("FrontHalfLeft");
        public static Area FrontHalfRight = Area.Create("FrontHalfRight");
        public static Area FrontLeft = Area.Create("FrontLeft");
        public static Area FrontLeftQuarter = Area.Create("FrontLeftQuarter");
        public static Area FrontRight = Area.Create("FrontRight");
        public static Area FrontRightQuarter = Area.Create("FrontRightQuarter");
        public static Area Left = Area.Create("Left");
        public static Area LeftHalf = Area.Create("LeftHalf");
        public static Area LeftHalfBack = Area.Create("LeftHalfBack");
        public static Area LeftHalfCenter = Area.Create("LeftHalfCenter");
        public static Area LeftHalfFront = Area.Create("LeftHalfFront");
        public static Area Right = Area.Create("Right");
        public static Area RightHalf = Area.Create("RightHalf");
        public static Area RightHalfBack = Area.Create("RightHalfBack");
        public static Area RightHalfCenter = Area.Create("RightHalfCenter");
        public static Area RightHalfFront = Area.Create("RightHalfFront");
    }

    protected static native Area Create(String str);

    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void create(double d, double d2, double d3, double d4, String str, boolean z);

    /* access modifiers changed from: protected */
    public native void create(Location location, Location location2, String str, boolean z);

    /* access modifiers changed from: protected */
    public native void delete();

    public native boolean equals(Object obj);

    public native Location getBottomRight();

    public native boolean getInverted();

    public native String getName();

    public native Location getTopLeft();

    public native boolean isInArea(Location location);

    public native void setBottomRight(Location location);

    public native void setInverted(boolean z);

    public native void setName(String str);

    public native void setTopLeft(Location location);

    public Area() {
        create();
    }

    protected Area(Scope scope) {
    }

    public Area(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY, String name, boolean inverted) {
        create(topLeftX, topLeftY, bottomRightX, bottomRightY, name, inverted);
    }

    public Area(Location topLeft, Location bottomRight, String name, boolean inverted) {
        create(topLeft, bottomRight, name, inverted);
    }

    public static Area GetKnownArea(String name) {
        return Create(name);
    }
}
