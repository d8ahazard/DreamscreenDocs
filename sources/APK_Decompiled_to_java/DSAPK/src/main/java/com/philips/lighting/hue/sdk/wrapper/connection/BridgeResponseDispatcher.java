package com.philips.lighting.hue.sdk.wrapper.connection;

import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.HueError;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.domain.SDKError;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ClipResponse;
import java.util.ArrayList;
import java.util.List;

public class BridgeResponseDispatcher {
    private static final String MSG_INVALID_METHOD_CALL = "Invalid method call!";

    public static void dispatch(BridgeResponseCallback callback, Bridge bridge, ReturnCode returnCode) {
        if (callback != null) {
            List<ClipResponse> responses = new ArrayList<>();
            List<HueError> errors = new ArrayList<>();
            errors.add(new SDKError(returnCode.getValue(), MSG_INVALID_METHOD_CALL));
            callback.handleCallback(bridge, returnCode, responses, errors);
        }
    }
}
