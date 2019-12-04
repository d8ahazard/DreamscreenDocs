package com.philips.lighting.hue.sdk.wrapper.domain.resource;

import com.philips.lighting.hue.sdk.wrapper.SessionObject;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainObject;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.utilities.Executor;
import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResourceLink extends SessionObject implements BridgeResource {
    private Integer classId = null;
    private byte[] description = null;
    private byte[] identifier = null;
    private List<DomainObject> links = null;
    private byte[] name = null;
    private byte[] owner = null;
    private Boolean recycle = null;
    private ResourceLinkType resourceLinkType = null;

    /* access modifiers changed from: private */
    public native void fetchNative(int i, BridgeResponseCallback bridgeResponseCallback);

    public ResourceLink() {
    }

    protected ResourceLink(long sessionKey) {
        super(sessionKey);
    }

    public String getIdentifier() {
        return NativeTools.BytesToString(this.identifier);
    }

    public void setIdentifier(String identifier2) {
        this.identifier = NativeTools.StringToBytes(identifier2);
    }

    public String getName() {
        return NativeTools.BytesToString(this.name);
    }

    public void setName(String name2) {
        this.name = NativeTools.StringToBytes(name2);
    }

    public int getDomainType() {
        return getType().getValue();
    }

    public DomainType getType() {
        return DomainType.RESOURCE_LINK;
    }

    public boolean isOfType(DomainType type) {
        if (type == DomainType.RESOURCE || getType() == type) {
            return true;
        }
        return false;
    }

    public boolean isOfType(int typeAsInt) {
        return isOfType(DomainType.fromValue(typeAsInt));
    }

    public void setBridge(Bridge bridge) {
        setSessionKey(((SessionObject) bridge).getSessionKey());
    }

    public void fetch(BridgeResponseCallback callback) {
        fetch(BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void fetch(final BridgeConnectionType allowedConnectionType, final BridgeResponseCallback callback) {
        if (allowedConnectionType == null) {
            BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_ALLOWED);
        } else {
            Executor.submit(new Runnable() {
                public void run() {
                    ResourceLink.this.fetchNative(allowedConnectionType.getValue(), callback);
                }
            });
        }
    }

    public String getDescription() {
        return NativeTools.BytesToString(this.description);
    }

    public void setDescription(String description2) {
        this.description = NativeTools.StringToBytes(description2);
    }

    public ResourceLinkType getResourceLinkType() {
        return this.resourceLinkType;
    }

    public void setResourceLinkType(ResourceLinkType resourceLinkType2) {
        this.resourceLinkType = resourceLinkType2;
    }

    private int getResourceLinkTypeAsInt() {
        if (this.resourceLinkType != null) {
            return this.resourceLinkType.getValue();
        }
        return ResourceLinkType.UNKNOWN.getValue();
    }

    private void setResourceLinkTypeFromInt(int value) {
        this.resourceLinkType = ResourceLinkType.fromValue(value);
    }

    public Integer getClassId() {
        return this.classId;
    }

    public void setClassId(Integer classId2) {
        this.classId = classId2;
    }

    public void setClassId(int classId2) {
        this.classId = new Integer(classId2);
    }

    public String getOwner() {
        return NativeTools.BytesToString(this.owner);
    }

    public Boolean getRecycle() {
        return this.recycle;
    }

    public void setRecycle(Boolean recycle2) {
        this.recycle = recycle2;
    }

    public void setRecycle(boolean recycle2) {
        this.recycle = new Boolean(recycle2);
    }

    public List<DomainObject> getLinks() {
        return this.links;
    }

    public void setLinks(List<DomainObject> links2) {
        this.links = links2;
    }

    public void addLink(DomainObject link) {
        if (this.links == null) {
            this.links = new ArrayList();
        }
        this.links.add(link);
    }

    private DomainObject[] getLinksAsArray() {
        if (this.links == null) {
            return null;
        }
        DomainObject[] array = new DomainObject[this.links.size()];
        for (int i = 0; i < this.links.size(); i++) {
            array[i] = (DomainObject) this.links.get(i);
        }
        return array;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((((((((this.classId == null ? 0 : this.classId.hashCode()) + 31) * 31) + Arrays.hashCode(this.description)) * 31) + Arrays.hashCode(this.identifier)) * 31) + (this.links == null ? 0 : this.links.hashCode())) * 31) + Arrays.hashCode(this.name)) * 31) + Arrays.hashCode(this.owner)) * 31) + (this.recycle == null ? 0 : this.recycle.hashCode())) * 31;
        if (this.resourceLinkType != null) {
            i = this.resourceLinkType.hashCode();
        }
        return hashCode + i;
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
        ResourceLink other = (ResourceLink) obj;
        if (this.classId == null) {
            if (other.classId != null) {
                return false;
            }
        } else if (!this.classId.equals(other.classId)) {
            return false;
        }
        if (!Arrays.equals(this.description, other.description)) {
            return false;
        }
        if (!Arrays.equals(this.identifier, other.identifier)) {
            return false;
        }
        if (this.links == null) {
            if (other.links != null) {
                return false;
            }
        } else if (!this.links.equals(other.links)) {
            return false;
        }
        if (!Arrays.equals(this.name, other.name)) {
            return false;
        }
        if (!Arrays.equals(this.owner, other.owner)) {
            return false;
        }
        if (this.recycle == null) {
            if (other.recycle != null) {
                return false;
            }
        } else if (!this.recycle.equals(other.recycle)) {
            return false;
        }
        if (this.resourceLinkType != other.resourceLinkType) {
            return false;
        }
        return true;
    }

    public void syncNative() {
    }
}
