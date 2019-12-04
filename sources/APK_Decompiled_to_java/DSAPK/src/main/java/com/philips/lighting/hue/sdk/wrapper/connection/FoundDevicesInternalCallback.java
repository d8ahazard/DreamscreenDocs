package com.philips.lighting.hue.sdk.wrapper.connection;

import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.HueError;
import com.philips.lighting.hue.sdk.wrapper.domain.device.Device;
import java.util.List;

interface FoundDevicesInternalCallback {
    void onInternalDeviceSearchFinished(Bridge bridge, List<HueError> list);

    void onInternalDevicesFound(Bridge bridge, List<Device> list, List<HueError> list2);
}
