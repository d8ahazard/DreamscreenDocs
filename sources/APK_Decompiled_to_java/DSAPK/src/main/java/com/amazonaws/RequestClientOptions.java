package com.amazonaws;

import java.util.EnumMap;
import java.util.Map;

public final class RequestClientOptions {
    public static final int DEFAULT_STREAM_BUFFER_SIZE = 131073;
    private static final int STREAM_BUFFER_SHIFT_VAL = 17;
    private final Map<Marker, String> markers = new EnumMap(Marker.class);

    public enum Marker {
        USER_AGENT
    }

    @Deprecated
    public String getClientMarker() {
        return getClientMarker(Marker.USER_AGENT);
    }

    public String getClientMarker(Marker marker) {
        return (String) this.markers.get(marker);
    }

    public void putClientMarker(Marker marker, String value) {
        this.markers.put(marker, value);
    }

    @Deprecated
    public void addClientMarker(String clientMarker) {
        appendUserAgent(clientMarker);
    }

    public void appendUserAgent(String userAgent) {
        String marker = (String) this.markers.get(Marker.USER_AGENT);
        if (marker == null) {
            marker = "";
        }
        putClientMarker(Marker.USER_AGENT, createUserAgentMarkerString(marker, userAgent));
    }

    private String createUserAgentMarkerString(String marker, String userAgent) {
        return marker.contains(userAgent) ? marker : marker + " " + userAgent;
    }
}
