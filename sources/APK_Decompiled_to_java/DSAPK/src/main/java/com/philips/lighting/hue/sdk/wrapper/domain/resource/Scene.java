package com.philips.lighting.hue.sdk.wrapper.domain.resource;

import com.philips.lighting.hue.sdk.wrapper.SessionObject;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.SceneType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;
import com.philips.lighting.hue.sdk.wrapper.utilities.Executor;
import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Scene extends SessionObject implements BridgeResource, Action {
    private SceneAppData appData;
    private byte[] groupId;
    private byte[] id;
    private Boolean isActive;
    private Date lastUpdated;
    private List<LightPoint> lights;
    private Boolean locked;
    private byte[] name;
    private byte[] owner;
    private byte[] picture;
    private Boolean recycle;
    private SceneType sceneType;
    private Boolean storeLightState;
    private Integer transitionTime;
    private Integer version;

    private native LightPoint createLightPoint(long j, @Nonnull String str, @Nullable LightState lightState);

    /* access modifiers changed from: private */
    public native void fetchNative(int i, BridgeResponseCallback bridgeResponseCallback);

    /* access modifiers changed from: private */
    public native void recallNative(int i, BridgeResponseCallback bridgeResponseCallback);

    private native void setSessionKeyOnLightPoint(LightPoint lightPoint, long j);

    private native void syncLightPointsNative(String str);

    private native void updateLightPointsFromCacheNative(LightPoint[] lightPointArr, String[] strArr);

    public native void syncNative();

    public Scene() {
        this.id = null;
        this.name = null;
        this.sceneType = null;
        this.lights = null;
        this.groupId = null;
        this.isActive = null;
        this.transitionTime = null;
        this.owner = null;
        this.recycle = null;
        this.locked = null;
        this.lastUpdated = null;
        this.picture = null;
        this.appData = null;
        this.storeLightState = null;
        this.version = null;
        this.sceneType = SceneType.LIGHT;
    }

    public Scene(String id2) {
        this.id = null;
        this.name = null;
        this.sceneType = null;
        this.lights = null;
        this.groupId = null;
        this.isActive = null;
        this.transitionTime = null;
        this.owner = null;
        this.recycle = null;
        this.locked = null;
        this.lastUpdated = null;
        this.picture = null;
        this.appData = null;
        this.storeLightState = null;
        this.version = null;
        this.sceneType = SceneType.LIGHT;
        setIdentifier(id2);
    }

    private Scene(long sessionKey) {
        super(sessionKey);
        this.id = null;
        this.name = null;
        this.sceneType = null;
        this.lights = null;
        this.groupId = null;
        this.isActive = null;
        this.transitionTime = null;
        this.owner = null;
        this.recycle = null;
        this.locked = null;
        this.lastUpdated = null;
        this.picture = null;
        this.appData = null;
        this.storeLightState = null;
        this.version = null;
    }

    public Scene(String identifier, String name2, List<String> lightIdentifiers, Boolean isActive2, Integer transitionTime2, Bridge bridge) {
        this.id = null;
        this.name = null;
        this.sceneType = null;
        this.lights = null;
        this.groupId = null;
        this.isActive = null;
        this.transitionTime = null;
        this.owner = null;
        this.recycle = null;
        this.locked = null;
        this.lastUpdated = null;
        this.picture = null;
        this.appData = null;
        this.storeLightState = null;
        this.version = null;
        this.sceneType = SceneType.LIGHT;
        setIdentifier(identifier);
        setName(name2);
        setBridge(bridge);
        this.isActive = isActive2;
        this.transitionTime = transitionTime2;
        setLightIds(lightIdentifiers);
    }

    public Scene(String identifier, String name2, List<LightPoint> lights2, Boolean isActive2, Integer transitionTime2, String owner2, Boolean recycle2, Boolean locked2, Date lastUpdated2, SceneAppData appData2, String picture2, Boolean storeLightState2) {
        this.id = null;
        this.name = null;
        this.sceneType = null;
        this.lights = null;
        this.groupId = null;
        this.isActive = null;
        this.transitionTime = null;
        this.owner = null;
        this.recycle = null;
        this.locked = null;
        this.lastUpdated = null;
        this.picture = null;
        this.appData = null;
        this.storeLightState = null;
        this.version = null;
        this.sceneType = SceneType.LIGHT;
        setIdentifier(identifier);
        setName(name2);
        this.isActive = isActive2;
        this.transitionTime = transitionTime2;
        setOwner(owner2);
        this.recycle = recycle2;
        this.lastUpdated = lastUpdated2;
        this.appData = appData2;
        setPicture(picture2);
        this.storeLightState = storeLightState2;
        List<String> lightIds = new ArrayList<>();
        if (lights2 != null) {
            for (LightPoint lp : lights2) {
                lightIds.add(lp.getIdentifier());
            }
        }
        setLightIds(lightIds);
    }

    public Scene(String identifier, String name2, String groupIdentifier, Integer transitionTime2, String owner2, Boolean recycle2, Boolean locked2, Date lastUpdated2, SceneAppData appData2, String picture2, Boolean storeLightState2) {
        this.id = null;
        this.name = null;
        this.sceneType = null;
        this.lights = null;
        this.groupId = null;
        this.isActive = null;
        this.transitionTime = null;
        this.owner = null;
        this.recycle = null;
        this.locked = null;
        this.lastUpdated = null;
        this.picture = null;
        this.appData = null;
        this.storeLightState = null;
        this.version = null;
        this.sceneType = SceneType.GROUP;
        setIdentifier(identifier);
        setName(name2);
        setGroupIdentifier(groupIdentifier);
        this.transitionTime = transitionTime2;
        setOwner(owner2);
        this.recycle = recycle2;
        this.lastUpdated = lastUpdated2;
        this.appData = appData2;
        setPicture(picture2);
        this.storeLightState = storeLightState2;
    }

    public String getIdentifier() {
        return NativeTools.BytesToString(this.id);
    }

    public void setIdentifier(String id2) {
        this.id = NativeTools.StringToBytes(id2);
    }

    public String getName() {
        return NativeTools.BytesToString(this.name);
    }

    public void setName(String name2) {
        this.name = NativeTools.StringToBytes(name2);
    }

    public SceneType getSceneType() {
        return this.sceneType;
    }

    public String getGroupIdentifier() {
        return NativeTools.BytesToString(this.groupId);
    }

    public void setGroupIdentifier(String identifier) {
        this.sceneType = SceneType.GROUP;
        this.groupId = NativeTools.StringToBytes(identifier);
    }

    public synchronized List<String> getLightIds() {
        List<String> ids;
        ids = new ArrayList<>();
        if (this.lights != null) {
            for (LightPoint light : this.lights) {
                ids.add(light.getIdentifier());
            }
        }
        return ids;
    }

    public synchronized void setLightIds(List<String> lightIds) {
        if (lightIds != null) {
            initLights();
            this.lights.clear();
            for (String lightId : lightIds) {
                removeLight(lightId);
                this.lights.add(createLightPoint(getSessionKey(), lightId, null));
            }
            syncLightPointsNative(null);
        }
    }

    public synchronized void setLightIdsAsStrings(List<String> lightIds) {
        if (lightIds != null) {
            initLights();
            this.lights.clear();
            for (String lightId : lightIds) {
                removeLight(new String(lightId));
                this.lights.add(createLightPoint(getSessionKey(), new String(lightId), null));
            }
            syncLightPointsNative(null);
        }
    }

    public synchronized void setLights(List<LightPoint> lights2) {
        this.lights = lights2;
        syncLightPointsNative(null);
    }

    public synchronized void addLightId(String lightId) {
        if (lightId != null) {
            initLights();
            removeLight(lightId);
            this.lights.add(createLightPoint(getSessionKey(), lightId, null));
            if (isMSL(lightId)) {
                syncLightPointsNative(null);
            }
        }
    }

    public synchronized void addLightId(String lightId, LightState state) {
        if (lightId != null) {
            initLights();
            removeLight(lightId);
            this.lights.add(createLightPoint(getSessionKey(), lightId, state));
            if (isMSL(lightId)) {
                syncLightPointsNative(null);
            }
        }
    }

    public synchronized void addLight(LightPoint light) {
        if (light != null) {
            initLights();
            removeLight(light.getIdentifier());
            this.lights.add(light);
            if (isMSL(light.getIdentifier())) {
                syncLightPointsNative(null);
            }
        }
    }

    public synchronized List<LightPoint> getLights() {
        List<LightPoint> asList;
        if (this.lights == null) {
            asList = Arrays.asList(new LightPoint[0]);
        } else {
            LightPoint[] lightPoints = new LightPoint[this.lights.size()];
            String[] identifiers = new String[this.lights.size()];
            for (int i = 0; i < this.lights.size(); i++) {
                identifiers[i] = ((LightPoint) this.lights.get(i)).getIdentifier();
            }
            updateLightPointsFromCacheNative(lightPoints, identifiers);
            for (int i2 = 0; i2 < this.lights.size(); i2++) {
                if (lightPoints[i2] == null) {
                    lightPoints[i2] = (LightPoint) this.lights.get(i2);
                } else {
                    lightPoints[i2].setLightState(((LightPoint) this.lights.get(i2)).getLightState());
                }
            }
            asList = Arrays.asList(lightPoints);
        }
        return asList;
    }

    @Deprecated
    public Boolean isActive() {
        return this.isActive;
    }

    @Deprecated
    public void setIsActive(Boolean isActive2) {
        this.isActive = isActive2;
    }

    public Integer getTransitionTime() {
        return this.transitionTime;
    }

    public void setTransitionTime(Integer transitionTime2) {
        this.transitionTime = transitionTime2;
    }

    private synchronized void removeLightInternal(String lightId) {
        int lightIndex = -1;
        for (int i = 0; i < this.lights.size(); i++) {
            if (((LightPoint) this.lights.get(i)).getIdentifier().equals(lightId)) {
                lightIndex = i;
            }
        }
        if (lightIndex != -1) {
            this.lights.remove(lightIndex);
        }
    }

    public synchronized void setLightStateForLight(String lightId, LightState state) {
        if (!(lightId == null || state == null)) {
            initLights();
            removeLightInternal(lightId);
            this.lights.add(createLightPoint(getSessionKey(), lightId, state));
            if (isMSL(lightId)) {
                syncLightPointsNative(null);
            }
        }
    }

    public synchronized void removeLight(String lightId) {
        initLights();
        removeLightInternal(lightId);
        if (isMSL(lightId)) {
            syncLightPointsNative(lightId);
        }
    }

    public synchronized void removeLight(LightPoint light) {
        if (light != null) {
            removeLight(light.getIdentifier());
        }
    }

    public synchronized void recall() {
        recall(BridgeConnectionType.LOCAL_REMOTE, null);
    }

    public synchronized void recall(BridgeResponseCallback callback) {
        recall(BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public synchronized void recall(final BridgeConnectionType allowedConnectionType, final BridgeResponseCallback callback) {
        if (allowedConnectionType == null) {
            BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_ALLOWED);
        } else {
            Executor.submit(new Runnable() {
                public void run() {
                    Scene.this.recallNative(allowedConnectionType.getValue(), callback);
                }
            });
        }
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
                    Scene.this.fetchNative(allowedConnectionType.getValue(), callback);
                }
            });
        }
    }

    public int getDomainType() {
        return getType().getValue();
    }

    public DomainType getType() {
        return DomainType.SCENE;
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

    public synchronized void setBridge(Bridge bridge) {
        long key = ((SessionObject) bridge).getSessionKey();
        setSessionKey(key);
        if (this.lights != null) {
            for (LightPoint light : this.lights) {
                setSessionKeyOnLightPoint(light, key);
            }
        }
    }

    public String getOwner() {
        return NativeTools.BytesToString(this.owner);
    }

    public void setOwner(String owner2) {
        this.owner = NativeTools.StringToBytes(owner2);
    }

    public Boolean getRecycle() {
        return this.recycle;
    }

    public void setRecycle(Boolean recycle2) {
        this.recycle = recycle2;
    }

    public Boolean getLocked() {
        return this.locked;
    }

    public Date getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated2) {
        this.lastUpdated = lastUpdated2;
    }

    public String getPicture() {
        return NativeTools.BytesToString(this.picture);
    }

    public void setPicture(String picture2) {
        this.picture = NativeTools.StringToBytes(picture2);
    }

    public SceneAppData getAppData() {
        return this.appData;
    }

    public void setAppData(SceneAppData appData2) {
        this.appData = appData2;
    }

    public Boolean getStoreLightState() {
        return this.storeLightState;
    }

    public void setStoreLightState(Boolean storeLightState2) {
        this.storeLightState = storeLightState2;
    }

    public Integer getVersion() {
        return this.version;
    }

    /* access modifiers changed from: protected */
    public synchronized void initLights() {
        if (this.lights == null) {
            this.lights = new ArrayList();
        }
    }

    /* access modifiers changed from: protected */
    public synchronized LightPoint[] getLightsArray() {
        LightPoint[] lightsArray;
        if (this.lights == null) {
            lightsArray = null;
        } else {
            lightsArray = new LightPoint[this.lights.size()];
            int i = 0;
            for (LightPoint light : this.lights) {
                lightsArray[i] = light;
                i++;
            }
        }
        return lightsArray;
    }

    /* access modifiers changed from: protected */
    public synchronized void setLightsArray(LightPoint[] lightsArray) {
        setLightsArrayInternal(lightsArray);
        syncLightPointsNative(null);
    }

    private synchronized void setLightsArrayInternal(LightPoint[] lightsArray) {
        initLights();
        this.lights.clear();
        for (LightPoint light : lightsArray) {
            this.lights.add(light);
        }
    }

    public synchronized int hashCode() {
        int hashCode;
        int hashCode2;
        int hashCode3;
        int hashCode4;
        int result;
        int i = 0;
        synchronized (this) {
            int hashCode5 = ((((super.hashCode() * 31) + (this.appData == null ? 0 : this.appData.hashCode())) * 31) + Arrays.hashCode(this.id)) * 31;
            if (this.isActive == null) {
                hashCode = 0;
            } else {
                hashCode = this.isActive.hashCode();
            }
            int i2 = (hashCode5 + hashCode) * 31;
            if (this.lastUpdated == null) {
                hashCode2 = 0;
            } else {
                hashCode2 = this.lastUpdated.hashCode();
            }
            int hashCode6 = (((((((((((((i2 + hashCode2) * 31) + (this.lights == null ? 0 : this.lights.hashCode())) * 31) + (this.locked == null ? 0 : this.locked.hashCode())) * 31) + Arrays.hashCode(this.name)) * 31) + Arrays.hashCode(this.owner)) * 31) + Arrays.hashCode(this.picture)) * 31) + (this.recycle == null ? 0 : this.recycle.hashCode())) * 31;
            if (this.storeLightState == null) {
                hashCode3 = 0;
            } else {
                hashCode3 = this.storeLightState.hashCode();
            }
            int i3 = (hashCode6 + hashCode3) * 31;
            if (this.transitionTime == null) {
                hashCode4 = 0;
            } else {
                hashCode4 = this.transitionTime.hashCode();
            }
            int i4 = (i3 + hashCode4) * 31;
            if (this.version != null) {
                i = this.version.hashCode();
            }
            result = i4 + i;
        }
        return result;
    }

    public synchronized boolean equals(Object obj) {
        boolean z = true;
        synchronized (this) {
            if (this != obj) {
                if (!super.equals(obj)) {
                    z = false;
                } else if (getClass() != obj.getClass()) {
                    z = false;
                } else {
                    Scene other = (Scene) obj;
                    if (this.appData == null) {
                        if (other.appData != null) {
                            z = false;
                        }
                    } else if (!this.appData.equals(other.appData)) {
                        z = false;
                    }
                    if (!Arrays.equals(this.id, other.id)) {
                        z = false;
                    } else {
                        if (this.isActive == null) {
                            if (other.isActive != null) {
                                z = false;
                            }
                        } else if (!this.isActive.equals(other.isActive)) {
                            z = false;
                        }
                        if (this.lastUpdated == null) {
                            if (other.lastUpdated != null) {
                                z = false;
                            }
                        } else if (!this.lastUpdated.equals(other.lastUpdated)) {
                            z = false;
                        }
                        if (this.lights == null) {
                            if (other.lights != null) {
                                z = false;
                            }
                        } else if (!this.lights.equals(other.lights)) {
                            z = false;
                        }
                        if (this.locked == null) {
                            if (other.locked != null) {
                                z = false;
                            }
                        } else if (!this.locked.equals(other.locked)) {
                            z = false;
                        }
                        if (!Arrays.equals(this.name, other.name)) {
                            z = false;
                        } else if (!Arrays.equals(this.owner, other.owner)) {
                            z = false;
                        } else if (!Arrays.equals(this.picture, other.picture)) {
                            z = false;
                        } else {
                            if (this.recycle == null) {
                                if (other.recycle != null) {
                                    z = false;
                                }
                            } else if (!this.recycle.equals(other.recycle)) {
                                z = false;
                            }
                            if (this.storeLightState == null) {
                                if (other.storeLightState != null) {
                                    z = false;
                                }
                            } else if (!this.storeLightState.equals(other.storeLightState)) {
                                z = false;
                            }
                            if (this.transitionTime == null) {
                                if (other.transitionTime != null) {
                                    z = false;
                                }
                            } else if (!this.transitionTime.equals(other.transitionTime)) {
                                z = false;
                            }
                            if (this.version == null) {
                                if (other.version != null) {
                                    z = false;
                                }
                            } else if (!this.version.equals(other.version)) {
                                z = false;
                            }
                        }
                    }
                }
            }
        }
        return z;
    }

    private static boolean isMSL(String lightId) {
        return !lightId.matches("\\d+");
    }
}
