package com.philips.lighting.hue.sdk.wrapper.connection;

import com.philips.lighting.hue.sdk.wrapper.domain.HueError;
import com.philips.lighting.hue.sdk.wrapper.uicallback.CallbackWrapper;
import java.util.List;

public abstract class RequestCallback extends CallbackWrapper implements RequestInternalCallback {
    public abstract void onCallback(List<HueError> list, HueHTTPResponse hueHTTPResponse);

    public final void onInternalCallback(final List<HueError> errors, final HueHTTPResponse response) {
        post(new Runnable() {
            public void run() {
                RequestCallback.this.onCallback(errors, response);
            }
        });
    }
}
