package com.philips.lighting.hue.sdk.wrapper.domain.resource.builder;

import com.philips.lighting.hue.sdk.wrapper.HueLog;
import com.philips.lighting.hue.sdk.wrapper.SessionObject;
import com.philips.lighting.hue.sdk.wrapper.domain.BridgeVersion;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ClipAction;
import com.philips.lighting.hue.sdk.wrapper.domain.device.Device;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceConfiguration;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceState;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.BridgeResource;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.Group;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.Scene;
import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.List;

public class ClipActionBuilder extends SessionObject {
    private static final String TAG = ClipActionBuilder.class.getSimpleName();

    private native void constructNative();

    private native void destroyNative();

    public native List<ClipAction> build(BridgeVersion bridgeVersion);

    public native ClipActionBuilder createResource(BridgeResource bridgeResource);

    public native ClipActionBuilder deleteDevice(Device device);

    public native ClipActionBuilder deleteResource(BridgeResource bridgeResource);

    public native ClipActionBuilder recallSceneNative(byte[] bArr, byte[] bArr2);

    public native ClipActionBuilder setGroupLightStateNative(byte[] bArr, LightState lightState);

    public native ClipActionBuilder setUsernameNative(byte[] bArr);

    public native ClipActionBuilder updateDeviceConfiguration(Device device, DeviceConfiguration deviceConfiguration);

    public native ClipActionBuilder updateDeviceState(Device device, DeviceState deviceState);

    public native ClipActionBuilder updateResource(BridgeResource bridgeResource);

    public ClipActionBuilder() {
        constructNative();
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        destroyNative();
        super.finalize();
    }

    public void syncNative() {
    }

    public ClipActionBuilder setUsername(String username) {
        return setUsernameNative(NativeTools.StringToBytes(username));
    }

    /* Debug info: failed to restart local var, previous not found, register: 2 */
    public ClipActionBuilder recallScene(Scene scene) {
        if (scene != null) {
            return recallScene(scene.getIdentifier());
        }
        HueLog.e(TAG, "Scene is null, cannot build action");
        return this;
    }

    /* Debug info: failed to restart local var, previous not found, register: 3 */
    public ClipActionBuilder recallScene(Scene scene, Group group) {
        if (scene != null) {
            String groupId = null;
            if (group != null) {
                groupId = group.getIdentifier();
            } else {
                HueLog.w(TAG, "Group was null. Will use group zero to build action");
            }
            return recallScene(scene.getIdentifier(), groupId);
        }
        HueLog.e(TAG, "Scene is null, cannot build action");
        return this;
    }

    public ClipActionBuilder recallScene(Scene scene, String groupId) {
        return recallScene(scene.getIdentifier(), groupId);
    }

    public ClipActionBuilder recallScene(String sceneId, Group group) {
        return recallScene(sceneId, group.getIdentifier());
    }

    public ClipActionBuilder recallScene(String sceneId) {
        return recallScene(sceneId, (String) null);
    }

    public ClipActionBuilder recallScene(String sceneId, String groupId) {
        return recallSceneNative(NativeTools.StringToBytes(sceneId), NativeTools.StringToBytes(groupId));
    }

    public ClipActionBuilder setGroupLightState(String groupId, LightState lightState) {
        return setGroupLightStateNative(NativeTools.StringToBytes(groupId), lightState);
    }

    public ClipActionBuilder setGroupLightState(Group group, LightState lightState) {
        if (group != null) {
            return setGroupLightState(group.getIdentifier(), lightState);
        }
        return this;
    }

    public ClipAction buildSingle(BridgeVersion bridgeVersion) {
        List<ClipAction> actions = build(bridgeVersion);
        if (actions == null || actions.size() <= 0) {
            return null;
        }
        return (ClipAction) actions.get(0);
    }
}
