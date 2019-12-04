package com.amazonaws;

import com.amazonaws.mobileconnectors.cognitoauth.util.ClientConstants;

public enum Protocol {
    HTTP("http"),
    HTTPS(ClientConstants.DOMAIN_SCHEME);
    
    private final String protocol;

    private Protocol(String protocol2) {
        this.protocol = protocol2;
    }

    public String toString() {
        return this.protocol;
    }
}
