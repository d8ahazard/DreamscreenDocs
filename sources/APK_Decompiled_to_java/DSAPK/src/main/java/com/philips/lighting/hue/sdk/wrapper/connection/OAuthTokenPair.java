package com.philips.lighting.hue.sdk.wrapper.connection;

import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;

public class OAuthTokenPair {
    private byte[] accessToken = null;
    private byte[] refreshToken = null;

    public OAuthTokenPair() {
    }

    public OAuthTokenPair(String accessToken2, String refreshToken2) {
        setAccessToken(accessToken2);
        setRefreshToken(refreshToken2);
    }

    public String getAccessToken() {
        return NativeTools.BytesToString(this.accessToken);
    }

    public void setAccessToken(String accessToken2) {
        this.accessToken = NativeTools.StringToBytes(accessToken2);
    }

    public String getRefreshToken() {
        return NativeTools.BytesToString(this.refreshToken);
    }

    public void setRefreshToken(String refreshToken2) {
        this.refreshToken = NativeTools.StringToBytes(refreshToken2);
    }

    public boolean hasAccessToken() {
        return this.accessToken != null;
    }

    public boolean hasRefreshToken() {
        return this.refreshToken != null;
    }

    public int hashCode() {
        return ((Arrays.hashCode(this.accessToken) + 31) * 31) + Arrays.hashCode(this.refreshToken);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OAuthTokenPair other = (OAuthTokenPair) obj;
        if (!Arrays.equals(this.accessToken, other.accessToken)) {
            return false;
        }
        if (!Arrays.equals(this.refreshToken, other.refreshToken)) {
            return false;
        }
        return true;
    }
}
