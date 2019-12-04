package com.amazonaws.services.lambda.model;

import java.util.HashMap;
import java.util.Map;

public enum InvocationType {
    Event("Event"),
    RequestResponse("RequestResponse"),
    DryRun("DryRun");
    
    private static final Map<String, InvocationType> enumMap = null;
    private String value;

    static {
        enumMap = new HashMap();
        enumMap.put("Event", Event);
        enumMap.put("RequestResponse", RequestResponse);
        enumMap.put("DryRun", DryRun);
    }

    private InvocationType(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static InvocationType fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        } else if (enumMap.containsKey(value2)) {
            return (InvocationType) enumMap.get(value2);
        } else {
            throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
        }
    }
}
