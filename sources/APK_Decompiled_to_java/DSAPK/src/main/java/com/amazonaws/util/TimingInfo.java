package com.amazonaws.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TimingInfo {
    private static final double TIME_CONVERSION = 1000.0d;
    static final int UNKNOWN = -1;
    private Long endTimeNano;
    private final Long startEpochTimeMilli;
    private final long startTimeNano;

    public static TimingInfo startTiming() {
        return new TimingInfo(Long.valueOf(System.currentTimeMillis()), System.nanoTime(), null);
    }

    public static TimingInfo startTimingFullSupport() {
        return new TimingInfoFullSupport(Long.valueOf(System.currentTimeMillis()), System.nanoTime(), null);
    }

    public static TimingInfo startTimingFullSupport(long startTimeNano2) {
        return new TimingInfoFullSupport(null, startTimeNano2, null);
    }

    public static TimingInfo newTimingInfoFullSupport(long startTimeNano2, long endTimeNano2) {
        return new TimingInfoFullSupport(null, startTimeNano2, Long.valueOf(endTimeNano2));
    }

    public static TimingInfo newTimingInfoFullSupport(long startEpochTimeMilli2, long startTimeNano2, long endTimeNano2) {
        return new TimingInfoFullSupport(Long.valueOf(startEpochTimeMilli2), startTimeNano2, Long.valueOf(endTimeNano2));
    }

    public static TimingInfo unmodifiableTimingInfo(long startTimeNano2, Long endTimeNano2) {
        return new TimingInfoUnmodifiable(null, startTimeNano2, endTimeNano2);
    }

    public static TimingInfo unmodifiableTimingInfo(long startEpochTimeMilli2, long startTimeNano2, Long endTimeNano2) {
        return new TimingInfoUnmodifiable(Long.valueOf(startEpochTimeMilli2), startTimeNano2, endTimeNano2);
    }

    protected TimingInfo(Long startEpochTimeMilli2, long startTimeNano2, Long endTimeNano2) {
        this.startEpochTimeMilli = startEpochTimeMilli2;
        this.startTimeNano = startTimeNano2;
        this.endTimeNano = endTimeNano2;
    }

    @Deprecated
    public final long getStartTime() {
        if (isStartEpochTimeMilliKnown()) {
            return this.startEpochTimeMilli.longValue();
        }
        return TimeUnit.NANOSECONDS.toMillis(this.startTimeNano);
    }

    @Deprecated
    public final long getStartEpochTimeMilli() {
        Long v = getStartEpochTimeMilliIfKnown();
        if (v == null) {
            return -1;
        }
        return v.longValue();
    }

    public final Long getStartEpochTimeMilliIfKnown() {
        return this.startEpochTimeMilli;
    }

    public final long getStartTimeNano() {
        return this.startTimeNano;
    }

    @Deprecated
    public final long getEndTime() {
        return getEndEpochTimeMilli();
    }

    @Deprecated
    public final long getEndEpochTimeMilli() {
        Long v = getEndEpochTimeMilliIfKnown();
        if (v == null) {
            return -1;
        }
        return v.longValue();
    }

    public final Long getEndEpochTimeMilliIfKnown() {
        if (!isStartEpochTimeMilliKnown() || !isEndTimeKnown()) {
            return null;
        }
        return Long.valueOf(this.startEpochTimeMilli.longValue() + TimeUnit.NANOSECONDS.toMillis(this.endTimeNano.longValue() - this.startTimeNano));
    }

    public final long getEndTimeNano() {
        if (this.endTimeNano == null) {
            return -1;
        }
        return this.endTimeNano.longValue();
    }

    public final Long getEndTimeNanoIfKnown() {
        return this.endTimeNano;
    }

    @Deprecated
    public final double getTimeTakenMillis() {
        Double v = getTimeTakenMillisIfKnown();
        if (v == null) {
            return -1.0d;
        }
        return v.doubleValue();
    }

    public final Double getTimeTakenMillisIfKnown() {
        if (isEndTimeKnown()) {
            return Double.valueOf(durationMilliOf(this.startTimeNano, this.endTimeNano.longValue()));
        }
        return null;
    }

    public static double durationMilliOf(long startTimeNano2, long endTimeNano2) {
        return ((double) TimeUnit.NANOSECONDS.toMicros(endTimeNano2 - startTimeNano2)) / TIME_CONVERSION;
    }

    @Deprecated
    public final long getElapsedTimeMillis() {
        Double v = getTimeTakenMillisIfKnown();
        if (v == null) {
            return -1;
        }
        return v.longValue();
    }

    public final boolean isEndTimeKnown() {
        return this.endTimeNano != null;
    }

    public final boolean isStartEpochTimeMilliKnown() {
        return this.startEpochTimeMilli != null;
    }

    public final String toString() {
        return String.valueOf(getTimeTakenMillis());
    }

    @Deprecated
    public void setEndTime(long endTimeMilli) {
        this.endTimeNano = Long.valueOf(TimeUnit.MILLISECONDS.toNanos(endTimeMilli));
    }

    public void setEndTimeNano(long endTimeNano2) {
        this.endTimeNano = Long.valueOf(endTimeNano2);
    }

    public TimingInfo endTiming() {
        this.endTimeNano = Long.valueOf(System.nanoTime());
        return this;
    }

    public void addSubMeasurement(String subMeasurementName, TimingInfo timingInfo) {
    }

    public TimingInfo getSubMeasurement(String subMeasurementName) {
        return null;
    }

    public TimingInfo getSubMeasurement(String subMesurementName, int index) {
        return null;
    }

    public TimingInfo getLastSubMeasurement(String subMeasurementName) {
        return null;
    }

    public List<TimingInfo> getAllSubMeasurements(String subMeasurementName) {
        return null;
    }

    public Map<String, List<TimingInfo>> getSubMeasurementsByName() {
        return Collections.emptyMap();
    }

    public Number getCounter(String key) {
        return null;
    }

    public Map<String, Number> getAllCounters() {
        return Collections.emptyMap();
    }

    public void setCounter(String key, long count) {
    }

    public void incrementCounter(String key) {
    }
}
