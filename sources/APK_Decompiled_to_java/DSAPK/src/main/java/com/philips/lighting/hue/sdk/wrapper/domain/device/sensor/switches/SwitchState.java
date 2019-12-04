package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.switches;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorState;

public class SwitchState extends SensorState {
    private SwitchButtonEvent switchButtonEvent;

    SwitchState() {
        super(DomainType.SWITCH);
    }

    public SwitchState(SwitchButtonEvent event) {
        this();
        this.switchButtonEvent = event;
    }

    public SwitchButtonEvent getSwitchButtonEvent() {
        return this.switchButtonEvent;
    }

    private int getSwitchButtonEventAsInt() {
        if (this.switchButtonEvent != null) {
            return this.switchButtonEvent.getValue();
        }
        return SwitchButtonEvent.UNKNOWN.getValue();
    }

    public void setSwitchButtonEvent(SwitchButtonEvent switchButtonEvent2) {
        this.switchButtonEvent = switchButtonEvent2;
    }

    private void setSwitchButtonEventFromInt(int value) {
        this.switchButtonEvent = SwitchButtonEvent.fromValue(value);
    }

    public int hashCode() {
        int hashCode;
        int hashCode2 = super.hashCode() * 31;
        if (this.switchButtonEvent == null) {
            hashCode = 0;
        } else {
            hashCode = this.switchButtonEvent.hashCode();
        }
        return hashCode2 + hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (this.switchButtonEvent != ((SwitchState) obj).switchButtonEvent) {
            return false;
        }
        return true;
    }
}
