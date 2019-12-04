package com.philips.lighting.hue.sdk.wrapper.connection;

import com.philips.lighting.hue.sdk.wrapper.domain.HueError;
import java.util.List;

interface RequestInternalCallback {
    void onInternalCallback(List<HueError> list, HueHTTPResponse hueHTTPResponse);
}
