package com.philips.lighting.hue.sdk.wrapper.domain.resource;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainObject;

public interface BridgeResource extends DomainObject {
    boolean equals(Object obj);

    String getName();

    int hashCode();

    void setName(String str);
}
