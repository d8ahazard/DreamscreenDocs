package com.philips.lighting.hue.sdk.wrapper.domain.resource.builder;

import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainObject;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.ResourceLink;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.ResourceLinkType;
import java.util.List;

public class ResourceLinkBuilder {
    private ResourceLink _resourceLink = new ResourceLink();

    public ResourceLink build() {
        return this._resourceLink;
    }

    public ResourceLinkBuilder reset() {
        this._resourceLink = new ResourceLink();
        return this;
    }

    public ResourceLinkBuilder setIdentifier(String identifier) {
        this._resourceLink.setIdentifier(identifier);
        return this;
    }

    public ResourceLinkBuilder setName(String name) {
        this._resourceLink.setName(name);
        return this;
    }

    public ResourceLinkBuilder setBridge(Bridge bridge) {
        this._resourceLink.setBridge(bridge);
        return this;
    }

    public ResourceLinkBuilder setDescription(String description) {
        this._resourceLink.setDescription(description);
        return this;
    }

    public ResourceLinkBuilder setResourceLinkType(ResourceLinkType resourceLinkType) {
        this._resourceLink.setResourceLinkType(resourceLinkType);
        return this;
    }

    public ResourceLinkBuilder setClassId(Integer classId) {
        this._resourceLink.setClassId(classId);
        return this;
    }

    public ResourceLinkBuilder setClassId(int classId) {
        this._resourceLink.setClassId(classId);
        return this;
    }

    public ResourceLinkBuilder setRecycle(Boolean recycle) {
        this._resourceLink.setRecycle(recycle);
        return this;
    }

    public ResourceLinkBuilder setRecycle(boolean recycle) {
        this._resourceLink.setRecycle(recycle);
        return this;
    }

    public ResourceLinkBuilder setLinks(List<DomainObject> links) {
        this._resourceLink.setLinks(links);
        return this;
    }

    public ResourceLinkBuilder addLink(DomainObject link) {
        this._resourceLink.addLink(link);
        return this;
    }
}
