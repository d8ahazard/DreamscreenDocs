package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.switches;

import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import androidx.core.view.PointerIconCompat;

public enum SwitchButtonEvent {
    UNKNOWN(0),
    SCENE_1(16),
    SCENE_2(17),
    SCENE_3(18),
    SCENE_4(19),
    SCENE_5(20),
    SCENE_6(21),
    SCENE_7(22),
    SCENE_8(23),
    TOGGLE(34),
    PRESS_BUTTON_1(98),
    RELEASE_BUTTON_1(99),
    PRESS_BUTTON_2(100),
    RELEASE_BUTTON_2(101),
    DIMMER_ON_INITIAL_PRESS(1000),
    DIMMER_ON_STILL_PRESSED(PointerIconCompat.TYPE_CONTEXT_MENU),
    DIMMER_ON_RELEASE(PointerIconCompat.TYPE_HAND),
    DIMMER_ON_RELEASE_LONG(PointerIconCompat.TYPE_HELP),
    DIMMER_BRIGHTNESS_UP_INITIAL_PRESS(2000),
    DIMMER_BRIGHTNESS_UP_STILL_PRESSED(2001),
    DIMMER_BRIGHTNESS_UP_RELEASE(2002),
    DIMMER_BRIGHTNESS_UP_RELEASE_LONG(2003),
    DIMMER_BRIGHTNESS_DOWN_INITIAL_PRESS(PathInterpolatorCompat.MAX_NUM_POINTS),
    DIMMER_BRIGHTNESS_DOWN_STILL_PRESSED(3001),
    DIMMER_BRIGHTNESS_DOWN_RELEASE(3002),
    DIMMER_BRIGHTNESS_DOWN_RELEASE_LONG(3003),
    DIMMER_OFF_INITIAL_PRESS(4000),
    DIMMER_OFF_STILL_PRESSED(4001),
    DIMMER_OFF_RELEASE(4002),
    DIMMER_OFF_RELEASE_LONG(4003);
    
    private int value;

    private SwitchButtonEvent(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static SwitchButtonEvent fromValue(int value2) {
        SwitchButtonEvent[] values;
        for (SwitchButtonEvent event : values()) {
            if (event.getValue() == value2) {
                return event;
            }
        }
        return UNKNOWN;
    }
}
