package com.amazonaws.auth.policy;

import java.util.Arrays;
import java.util.List;

public class Condition {
    protected String conditionKey;
    protected String type;
    protected List<String> values;

    public String getType() {
        return this.type;
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public String getConditionKey() {
        return this.conditionKey;
    }

    public void setConditionKey(String conditionKey2) {
        this.conditionKey = conditionKey2;
    }

    public List<String> getValues() {
        return this.values;
    }

    public void setValues(List<String> values2) {
        this.values = values2;
    }

    public Condition withType(String type2) {
        setType(type2);
        return this;
    }

    public Condition withConditionKey(String key) {
        setConditionKey(key);
        return this;
    }

    public Condition withValues(String... values2) {
        setValues(Arrays.asList(values2));
        return this;
    }

    public Condition withValues(List<String> values2) {
        setValues(values2);
        return this;
    }
}
