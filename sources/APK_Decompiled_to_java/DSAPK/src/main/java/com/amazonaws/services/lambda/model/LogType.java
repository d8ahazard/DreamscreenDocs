package com.amazonaws.services.lambda.model;

import java.util.HashMap;
import java.util.Map;

public enum LogType {
    None("None"),
    Tail("Tail");
    
    private static final Map<String, LogType> enumMap = null;
    private String value;

    static {
        enumMap = new HashMap();
        enumMap.put("None", None);
        enumMap.put("Tail", Tail);
    }

    private LogType(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static LogType fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        } else if (enumMap.containsKey(value2)) {
            return (LogType) enumMap.get(value2);
        } else {
            throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
        }
    }
}
