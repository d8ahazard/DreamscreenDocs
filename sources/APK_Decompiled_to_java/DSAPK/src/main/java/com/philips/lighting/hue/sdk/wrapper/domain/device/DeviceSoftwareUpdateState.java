package com.philips.lighting.hue.sdk.wrapper.domain.device;

public enum DeviceSoftwareUpdateState {
    UNKNOWN,
    NOT_UPDATABLE,
    NO_UPDATES,
    TRANSFERRING,
    READY_TO_INSTALL,
    INSTALLING,
    BATTERY_LOW,
    IMAGE_REJECTED,
    ERROR
}
