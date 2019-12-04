package com.philips.lighting.hue.sdk.wrapper.connection;

import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.HueError;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.domain.device.Device;
import com.philips.lighting.hue.sdk.wrapper.uicallback.CallbackWrapper;
import java.util.List;

public abstract class FoundDevicesCallback extends CallbackWrapper implements FoundDevicesInternalCallback {
    private ReturnCode status = ReturnCode.SUCCESS;

    public abstract void onDeviceSearchFinished(Bridge bridge, List<HueError> list);

    public abstract void onDevicesFound(Bridge bridge, List<Device> list, List<HueError> list2);

    public final void onInternalDevicesFound(final Bridge bridge, final List<Device> results, final List<HueError> errors) {
        post(new Runnable() {
            public void run() {
                FoundDevicesCallback.this.onDevicesFound(bridge, results, errors);
            }
        });
    }

    public final void onInternalDeviceSearchFinished(final Bridge bridge, final List<HueError> errors) {
        if (!errors.isEmpty()) {
            this.status = ReturnCode.ERROR;
        }
        post(new Runnable() {
            public void run() {
                FoundDevicesCallback.this.onDeviceSearchFinished(bridge, errors);
            }
        });
    }

    public ReturnCode getStatus() {
        return this.status;
    }
}
