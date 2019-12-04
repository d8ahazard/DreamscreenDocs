package com.philips.lighting.hue.sdk.wrapper.domain.clip;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.Device;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.SensorState;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.BridgeResource;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.Scene;
import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;

public class ClipAction {
    private ClipActionType actionType = ClipActionType.UNKNOWN;
    private byte[] address;
    private byte[] body;
    private Object bodyObject = null;
    private DomainType bodyObjectType = DomainType.UNKNOWN;
    private Device device = null;
    private byte[] method;
    private BridgeResource resource = null;
    private byte[] username = null;

    public ClipAction() {
    }

    public ClipAction(String address2, String method2, String body2) {
        setAddress(address2);
        setMethod(method2);
        setBody(body2);
    }

    public String getAddress() {
        return NativeTools.BytesToString(this.address);
    }

    public void setAddress(String address2) {
        this.address = NativeTools.StringToBytes(address2);
    }

    public String getMethod() {
        return NativeTools.BytesToString(this.method);
    }

    public void setMethod(String method2) {
        this.method = NativeTools.StringToBytes(method2);
    }

    public String getBody() {
        return NativeTools.BytesToString(this.body);
    }

    public void setBody(String body2) {
        this.body = NativeTools.StringToBytes(body2);
    }

    public String getUsername() {
        return NativeTools.BytesToString(this.username);
    }

    public ClipActionType getActionType() {
        return this.actionType;
    }

    public Device getDevice() {
        return this.device;
    }

    public BridgeResource getResource() {
        return this.resource;
    }

    public LightState getBodyObjectAsLightState() {
        if (this.bodyObjectType == DomainType.LIGHT_STATE) {
            return (LightState) this.bodyObject;
        }
        return null;
    }

    public SensorState getBodyObjectAsSensorState() {
        if (this.bodyObjectType == DomainType.SENSOR_STATE) {
            return (SensorState) this.bodyObject;
        }
        return null;
    }

    public Scene getBodyObjectAsScene() {
        if (this.bodyObjectType == DomainType.SCENE) {
            return (Scene) this.bodyObject;
        }
        return null;
    }

    private void setActionTypeFromInt(int value) {
        this.actionType = ClipActionType.fromValue(value);
    }

    private void setBodyObjectTypeFromInt(int value) {
        this.bodyObjectType = DomainType.fromValue(value);
    }

    public int hashCode() {
        int hashCode;
        int hashCode2;
        int hashCode3;
        int i = 0;
        if (this.actionType == null) {
            hashCode = 0;
        } else {
            hashCode = this.actionType.hashCode();
        }
        int hashCode4 = (((((hashCode + 31) * 31) + Arrays.hashCode(this.address)) * 31) + Arrays.hashCode(this.body)) * 31;
        if (this.bodyObject == null) {
            hashCode2 = 0;
        } else {
            hashCode2 = this.bodyObject.hashCode();
        }
        int i2 = (hashCode4 + hashCode2) * 31;
        if (this.bodyObjectType == null) {
            hashCode3 = 0;
        } else {
            hashCode3 = this.bodyObjectType.hashCode();
        }
        int hashCode5 = (((((i2 + hashCode3) * 31) + (this.device == null ? 0 : this.device.hashCode())) * 31) + Arrays.hashCode(this.method)) * 31;
        if (this.resource != null) {
            i = this.resource.hashCode();
        }
        return ((hashCode5 + i) * 31) + Arrays.hashCode(this.username);
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
        ClipAction other = (ClipAction) obj;
        if (this.actionType != other.actionType) {
            return false;
        }
        if (!Arrays.equals(this.address, other.address)) {
            return false;
        }
        if (!Arrays.equals(this.body, other.body)) {
            return false;
        }
        if (this.bodyObject == null) {
            if (other.bodyObject != null) {
                return false;
            }
        } else if (!this.bodyObject.equals(other.bodyObject)) {
            return false;
        }
        if (this.bodyObjectType != other.bodyObjectType) {
            return false;
        }
        if (this.device == null) {
            if (other.device != null) {
                return false;
            }
        } else if (!this.device.equals(other.device)) {
            return false;
        }
        if (!Arrays.equals(this.method, other.method)) {
            return false;
        }
        if (this.resource == null) {
            if (other.resource != null) {
                return false;
            }
        } else if (!this.resource.equals(other.resource)) {
            return false;
        }
        if (!Arrays.equals(this.username, other.username)) {
            return false;
        }
        return true;
    }
}
