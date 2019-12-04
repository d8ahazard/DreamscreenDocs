package com.philips.lighting.hue.sdk.wrapper.utilities;

import android.content.Context;

public final class InitSdk {
    private static Context activityContext;

    private InitSdk() {
    }

    public static void setApplicationContext(Context context) {
        activityContext = context;
    }

    public static Context getApplicationContext() throws IllegalStateException {
        if (activityContext != null) {
            return activityContext;
        }
        throw new IllegalStateException("Cannot get context, because it was not set.");
    }
}
