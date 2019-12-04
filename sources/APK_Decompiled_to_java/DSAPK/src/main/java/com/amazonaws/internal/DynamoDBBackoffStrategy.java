package com.amazonaws.internal;

public class DynamoDBBackoffStrategy extends CustomBackoffStrategy {
    public static final CustomBackoffStrategy DEFAULT = new DynamoDBBackoffStrategy();

    public int getBackoffPeriod(int retries) {
        if (retries <= 0) {
            return 0;
        }
        int delay = ((int) Math.pow(2.0d, (double) (retries - 1))) * 50;
        if (delay < 0) {
            return ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        }
        return delay;
    }
}
