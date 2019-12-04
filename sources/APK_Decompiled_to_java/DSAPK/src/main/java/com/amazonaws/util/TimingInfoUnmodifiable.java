package com.amazonaws.util;

final class TimingInfoUnmodifiable extends TimingInfo {
    TimingInfoUnmodifiable(Long startEpochTimeMilli, long startTimeNano, Long endTimeNano) {
        super(startEpochTimeMilli, startTimeNano, endTimeNano);
    }

    public void setEndTime(long _) {
        throw new UnsupportedOperationException();
    }

    public void setEndTimeNano(long _) {
        throw new UnsupportedOperationException();
    }

    public TimingInfo endTiming() {
        throw new UnsupportedOperationException();
    }
}
