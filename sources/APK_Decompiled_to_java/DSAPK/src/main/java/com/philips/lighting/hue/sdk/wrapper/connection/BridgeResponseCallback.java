package com.philips.lighting.hue.sdk.wrapper.connection;

import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.HueError;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ClipResponse;
import com.philips.lighting.hue.sdk.wrapper.uicallback.CallbackWrapper;
import java.util.List;

public abstract class BridgeResponseCallback extends CallbackWrapper implements BridgeResponseInternalCallback {
    public abstract void handleCallback(Bridge bridge, ReturnCode returnCode, List<ClipResponse> list, List<HueError> list2);

    public final void handleInternalCallback(Bridge bridge, ReturnCode returnCode, List<ClipResponse> responses, List<HueError> errors) {
        final Bridge bridge2 = bridge;
        final ReturnCode returnCode2 = returnCode;
        final List<ClipResponse> list = responses;
        final List<HueError> list2 = errors;
        post(new Runnable() {
            public void run() {
                BridgeResponseCallback.this.handleCallback(bridge2, returnCode2, list, list2);
            }
        });
    }
}
