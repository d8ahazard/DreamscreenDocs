package com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern;

import com.philips.lighting.hue.sdk.wrapper.SessionObject;

public class TimePatternBuilder extends SessionObject {
    private native void destroyNative();

    public native TimePattern build();

    public native TimePatternBuilder endAt(int i, int i2, int i3);

    public native TimePatternBuilder repeatsOnAllDays();

    public native TimePatternBuilder repeatsOnFriday();

    public native TimePatternBuilder repeatsOnMonday();

    public native TimePatternBuilder repeatsOnSaturday();

    public native TimePatternBuilder repeatsOnSunday();

    public native TimePatternBuilder repeatsOnThursday();

    public native TimePatternBuilder repeatsOnTuesday();

    public native TimePatternBuilder repeatsOnWednesday();

    public native TimePatternBuilder repeatsOnWeekdays();

    public native TimePatternBuilder repeatsOnWeekend();

    public native TimePatternBuilder repeatsTimes(int i);

    public native TimePatternBuilder setDayInterval(int i, int i2, int i3, int i4);

    public native TimePatternBuilder startAfter(int i, int i2, int i3);

    public native TimePatternBuilder startAt(int i, int i2, int i3);

    public native TimePatternBuilder startAtDate(int i, int i2, int i3, int i4, int i5, int i6);

    public native TimePatternBuilder withRandomOffset(int i, int i2, int i3);

    public void syncNative() {
    }

    public void setSessionKey(long sessionKey) {
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        destroyNative();
        super.finalize();
    }
}
