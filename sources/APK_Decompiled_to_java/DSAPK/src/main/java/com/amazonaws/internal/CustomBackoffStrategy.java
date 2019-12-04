package com.amazonaws.internal;

public abstract class CustomBackoffStrategy {
    public abstract int getBackoffPeriod(int i);
}
