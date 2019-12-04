package com.philips.lighting.hue.sdk.wrapper.domain.resource.builder;

import com.philips.lighting.hue.sdk.wrapper.HueLog;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ClipCondition;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ClipConditionOperator;
import com.philips.lighting.hue.sdk.wrapper.domain.device.Device;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.switches.SwitchButtonEvent;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.BridgeResource;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern.TimePattern;
import java.util.ArrayList;
import java.util.List;

public class ClipConditionBuilder {
    private String address;
    private List<ClipCondition> conditions;

    public ClipConditionBuilder() {
        this.conditions = null;
        this.address = null;
        this.conditions = new ArrayList();
    }

    public ClipConditionBuilder forDevice(Device device) {
        if (!(device == null || device.getIdentifier() == null)) {
            if (device.isOfType(DomainType.LIGHT_POINT)) {
                this.address = "/lights/" + device.getIdentifier() + "/";
            } else if (device.isOfType(DomainType.SENSOR)) {
                this.address = "/sensors/" + device.getIdentifier() + "/";
            } else if (device.isOfType(DomainType.BRIDGE)) {
                this.address = "/";
            }
        }
        return this;
    }

    public ClipConditionBuilder forResource(BridgeResource resource) {
        if (!(resource == null || resource.getIdentifier() == null)) {
            if (resource.isOfType(DomainType.RULE)) {
                this.address = "/rules/" + resource.getIdentifier() + "/";
            } else if (resource.isOfType(DomainType.GROUP)) {
                this.address = "/groups/" + resource.getIdentifier() + "/";
            }
        }
        return this;
    }

    public ClipConditionBuilder equalTo(String attribute, Integer value) {
        if (attribute == null || value == null) {
            return this;
        }
        return equalTo(attribute, value.intValue());
    }

    public ClipConditionBuilder equalTo(String attribute, SwitchButtonEvent value) {
        if (attribute == null || value == null) {
            return this;
        }
        return equalTo(attribute, value.getValue());
    }

    public ClipConditionBuilder equalTo(String attribute, int value) {
        if (attribute != null) {
            addClipCondition(attribute, ClipConditionOperator.EQ, "" + value);
        }
        return this;
    }

    public ClipConditionBuilder equalTo(String attribute, Boolean value) {
        if (attribute == null || value == null) {
            return this;
        }
        return equalTo(attribute, value.booleanValue());
    }

    public ClipConditionBuilder equalTo(String attribute, boolean value) {
        if (attribute != null) {
            addClipCondition(attribute, ClipConditionOperator.EQ, "" + value);
        }
        return this;
    }

    public ClipConditionBuilder greaterThan(String attribute, int value) {
        if (attribute != null) {
            addClipCondition(attribute, ClipConditionOperator.GT, "" + value);
        }
        return this;
    }

    public ClipConditionBuilder greaterThan(String attribute, Integer value) {
        if (attribute == null || value == null) {
            return this;
        }
        return greaterThan(attribute, value.intValue());
    }

    public ClipConditionBuilder greaterThan(String attribute, SwitchButtonEvent value) {
        if (attribute == null || value == null) {
            return this;
        }
        return greaterThan(attribute, value.getValue());
    }

    public ClipConditionBuilder lessThan(String attribute, int value) {
        if (attribute != null) {
            addClipCondition(attribute, ClipConditionOperator.LT, "" + value);
        }
        return this;
    }

    public ClipConditionBuilder lessThan(String attribute, Integer value) {
        if (attribute == null || value == null) {
            return this;
        }
        return lessThan(attribute, value.intValue());
    }

    public ClipConditionBuilder lessThan(String attribute, SwitchButtonEvent value) {
        if (attribute == null || value == null) {
            return this;
        }
        return lessThan(attribute, value.getValue());
    }

    public ClipConditionBuilder hasChanged(String attribute) {
        if (attribute != null) {
            addClipCondition(attribute, ClipConditionOperator.DX, null);
        }
        return this;
    }

    public ClipConditionBuilder hasChangedLongerThan(String attribute, TimePattern timerPattern) {
        if (!(attribute == null || timerPattern == null)) {
            addClipCondition(attribute, ClipConditionOperator.DDX, timerPattern.toString());
        }
        return this;
    }

    public ClipConditionBuilder hasNotChangedFor(String attribute, TimePattern timerPattern) {
        if (!(attribute == null || timerPattern == null)) {
            addClipCondition(attribute, ClipConditionOperator.STABLE, timerPattern.toString());
        }
        return this;
    }

    public ClipConditionBuilder hasChangedAtLeastOnceDuring(String attribute, TimePattern timerPattern) {
        if (!(attribute == null || timerPattern == null)) {
            addClipCondition(attribute, ClipConditionOperator.NOT_STABLE, timerPattern.toString());
        }
        return this;
    }

    public ClipConditionBuilder isIn(String attribute, TimePattern recurringTimeInterval) {
        if (!(attribute == null || recurringTimeInterval == null)) {
            addClipCondition(attribute, ClipConditionOperator.IN, recurringTimeInterval.toString());
        }
        return this;
    }

    public ClipConditionBuilder isNotIn(String attribute, TimePattern recurringTimeInterval) {
        if (!(attribute == null || recurringTimeInterval == null)) {
            addClipCondition(attribute, ClipConditionOperator.NOT_IN, recurringTimeInterval.toString());
        }
        return this;
    }

    public List<ClipCondition> build() {
        return this.conditions;
    }

    public ClipCondition buildSingle() {
        if (this.conditions.size() > 0) {
            return (ClipCondition) this.conditions.get(0);
        }
        return null;
    }

    private void addClipCondition(String address2, ClipConditionOperator operator, String value) {
        if (this.address != null) {
            this.conditions.add(new ClipCondition(this.address + address2, operator, value));
        } else {
            HueLog.e("ClipConditionBuilder", "Address is not set! Did you forget to use forDevice() or forResource()?");
        }
    }
}
