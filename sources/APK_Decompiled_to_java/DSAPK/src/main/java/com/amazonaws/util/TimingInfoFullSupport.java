package com.amazonaws.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.LogFactory;

class TimingInfoFullSupport extends TimingInfo {
    private final Map<String, Number> countersByName = new HashMap();
    private final Map<String, List<TimingInfo>> subMeasurementsByName = new HashMap();

    TimingInfoFullSupport(Long startEpochTimeMilli, long startTimeNano, Long endTimeNano) {
        super(startEpochTimeMilli, startTimeNano, endTimeNano);
    }

    public void addSubMeasurement(String subMeasurementName, TimingInfo ti) {
        List<TimingInfo> timings = (List) this.subMeasurementsByName.get(subMeasurementName);
        if (timings == null) {
            timings = new ArrayList<>();
            this.subMeasurementsByName.put(subMeasurementName, timings);
        }
        if (ti.isEndTimeKnown()) {
            timings.add(ti);
        } else {
            LogFactory.getLog(getClass()).debug("Skip submeasurement timing info with no end time for " + subMeasurementName);
        }
    }

    public TimingInfo getSubMeasurement(String subMeasurementName) {
        return getSubMeasurement(subMeasurementName, 0);
    }

    public TimingInfo getSubMeasurement(String subMesurementName, int index) {
        List<TimingInfo> timings = (List) this.subMeasurementsByName.get(subMesurementName);
        if (index < 0 || timings == null || timings.size() == 0 || index >= timings.size()) {
            return null;
        }
        return (TimingInfo) timings.get(index);
    }

    public TimingInfo getLastSubMeasurement(String subMeasurementName) {
        if (this.subMeasurementsByName == null || this.subMeasurementsByName.size() == 0) {
            return null;
        }
        List<TimingInfo> timings = (List) this.subMeasurementsByName.get(subMeasurementName);
        if (timings == null || timings.size() == 0) {
            return null;
        }
        return (TimingInfo) timings.get(timings.size() - 1);
    }

    public List<TimingInfo> getAllSubMeasurements(String subMeasurementName) {
        return (List) this.subMeasurementsByName.get(subMeasurementName);
    }

    public Map<String, List<TimingInfo>> getSubMeasurementsByName() {
        return this.subMeasurementsByName;
    }

    public Number getCounter(String key) {
        return (Number) this.countersByName.get(key);
    }

    public Map<String, Number> getAllCounters() {
        return this.countersByName;
    }

    public void setCounter(String key, long count) {
        this.countersByName.put(key, Long.valueOf(count));
    }

    public void incrementCounter(String key) {
        int count = 0;
        Number counter = getCounter(key);
        if (counter != null) {
            count = counter.intValue();
        }
        setCounter(key, (long) (count + 1));
    }
}
