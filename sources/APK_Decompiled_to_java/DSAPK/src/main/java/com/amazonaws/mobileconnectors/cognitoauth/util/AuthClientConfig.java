package com.amazonaws.mobileconnectors.cognitoauth.util;

import java.security.InvalidParameterException;

public final class AuthClientConfig {
    private static long REFRESH_THRESHOLD_MAX = 1800000;
    private static long REFRESH_THRESHOLD_MIN = 0;
    private static long refreshThreshold = 300000;

    public static void setRefreshThreshold(long threshold) throws InvalidParameterException {
        if (threshold > REFRESH_THRESHOLD_MAX || threshold < REFRESH_THRESHOLD_MIN) {
            throw new InvalidParameterException(String.format("The value of refreshThreshold must between %d and %d seconds", new Object[]{Long.valueOf(REFRESH_THRESHOLD_MIN), Long.valueOf(REFRESH_THRESHOLD_MAX)}));
        } else {
            refreshThreshold = threshold;
        }
    }

    public static long getRefreshThreshold() {
        return refreshThreshold;
    }
}
