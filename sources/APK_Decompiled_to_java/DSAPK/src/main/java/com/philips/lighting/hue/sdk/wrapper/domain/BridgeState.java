package com.philips.lighting.hue.sdk.wrapper.domain;

import com.philips.lighting.hue.sdk.wrapper.HueLog;
import com.philips.lighting.hue.sdk.wrapper.SessionObject;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateCacheType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateUpdatedCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateUpdatedEvent;
import com.philips.lighting.hue.sdk.wrapper.domain.device.Device;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceState;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightSource;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.MultiSourceLuminaire;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.CompoundSensor;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.Sensor;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.BridgeResource;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.Group;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.ResourceLink;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.Scene;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.Schedule;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.TimeZones;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.Timer;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.WhitelistEntry;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.bridgecapabilities.BridgeCapabilities;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.rule.Rule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class BridgeState extends SessionObject implements DeviceState {
    private static final String TAG = BridgeState.class.getSimpleName();
    private BridgeStateUpdatedCallback bridgeStateUpdatedCallback;
    private BridgeConfiguration config;
    private Map<DomainType, Map<DomainType, Map<String, Device>>> deviceMap;
    private Object deviceMapLock;
    private Map<DomainType, Map<String, BridgeResource>> resourceMap;
    private Object resourceMapLock;
    /* access modifiers changed from: private */
    public List<BridgeStateUpdatedCallback> updatedCallbacks;
    /* access modifiers changed from: private */
    public Object updatedCallbacksLock;

    private native int refreshNative(int i, int i2);

    private native void setUpdatedCallback(BridgeStateUpdatedCallback bridgeStateUpdatedCallback2);

    public native DeviceSearchStatus getDeviceSearchStatus();

    public native void syncNative();

    public native String toJSONString();

    private BridgeState(long session_key) {
        super(session_key);
        this.bridgeStateUpdatedCallback = new BridgeStateUpdatedCallback() {
            public void onBridgeStateUpdated(Bridge bridge, BridgeStateUpdatedEvent event) {
                synchronized (BridgeState.this.updatedCallbacksLock) {
                    if (BridgeState.this.updatedCallbacks != null) {
                        for (BridgeStateUpdatedCallback callback : BridgeState.this.updatedCallbacks) {
                            callback.onBridgeStateUpdated(bridge, event);
                        }
                    }
                }
            }
        };
        this.config = null;
        this.deviceMap = null;
        this.deviceMapLock = null;
        this.resourceMap = null;
        this.resourceMapLock = null;
        this.updatedCallbacks = null;
        this.updatedCallbacksLock = null;
        this.deviceMapLock = new Object();
        this.resourceMapLock = new Object();
        this.updatedCallbacksLock = new Object();
        this.deviceMap = new HashMap();
        this.resourceMap = new HashMap();
    }

    public void addUpdatedCallback(BridgeStateUpdatedCallback callback) {
        if (callback != null) {
            synchronized (this.updatedCallbacksLock) {
                if (this.updatedCallbacks == null) {
                    this.updatedCallbacks = new CopyOnWriteArrayList();
                    setUpdatedCallback(this.bridgeStateUpdatedCallback);
                }
                this.updatedCallbacks.add(callback);
            }
        }
    }

    public void removeUpdatedCallback(BridgeStateUpdatedCallback callback) {
        if (callback != null) {
            synchronized (this.updatedCallbacksLock) {
                if (this.updatedCallbacks != null) {
                    this.updatedCallbacks.remove(callback);
                    if (this.updatedCallbacks.size() == 0) {
                        setUpdatedCallback(null);
                        this.updatedCallbacks = null;
                    }
                }
            }
        }
    }

    public List<LightPoint> getLightPoints() {
        ArrayList<LightPoint> lights = new ArrayList<>();
        synchronized (this.deviceMapLock) {
            Map<DomainType, Map<String, Device>> map = (Map) this.deviceMap.get(DomainType.LIGHT_POINT);
            if (map != null) {
                Map<String, Device> map2 = (Map) map.get(DomainType.LIGHT_POINT);
                if (map2 != null) {
                    for (Device dev : map2.values()) {
                        lights.add((LightPoint) dev);
                    }
                }
            }
        }
        return lights;
    }

    public List<LightPoint> getLightPoints(int maxNrOfLights, LightType lightType) {
        ArrayList<LightPoint> lights = new ArrayList<>();
        synchronized (this.deviceMapLock) {
            Map<DomainType, Map<String, Device>> map = (Map) this.deviceMap.get(DomainType.LIGHT_POINT);
            if (map != null) {
                Map<String, Device> map2 = (Map) map.get(DomainType.LIGHT_POINT);
                if (map2 != null) {
                    for (Device dev : map2.values()) {
                        if (((LightPoint) dev).getLightType() == lightType) {
                            lights.add((LightPoint) dev);
                            if (lights.size() == maxNrOfLights) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return lights;
    }

    public List<MultiSourceLuminaire> getMultiSourceLuminaires() {
        ArrayList<MultiSourceLuminaire> lights = new ArrayList<>();
        synchronized (this.deviceMapLock) {
            Map<DomainType, Map<String, Device>> map = (Map) this.deviceMap.get(DomainType.LIGHT_POINT);
            if (map != null) {
                Map<String, Device> map2 = (Map) map.get(DomainType.MULTI_SOURCE_LUMINAIRE);
                if (map2 != null) {
                    for (Device dev : map2.values()) {
                        if (dev.getType() == DomainType.MULTI_SOURCE_LUMINAIRE) {
                            lights.add((MultiSourceLuminaire) dev);
                        }
                    }
                }
            }
        }
        return lights;
    }

    public List<LightPoint> getLights() {
        ArrayList<LightPoint> lights = new ArrayList<>();
        synchronized (this.deviceMapLock) {
            Map<DomainType, Map<String, Device>> lightTypeMap = (Map) this.deviceMap.get(DomainType.LIGHT_POINT);
            if (lightTypeMap != null) {
                for (Map<String, Device> lightMap : lightTypeMap.values()) {
                    for (Device dev : lightMap.values()) {
                        lights.add((LightPoint) dev);
                    }
                }
            }
        }
        return lights;
    }

    public LightPoint getLightPoint(String identifier) {
        return (LightPoint) getDevice(DomainType.LIGHT_POINT, identifier);
    }

    public MultiSourceLuminaire getMultiSourceLuminaire(String identifier) {
        if (identifier != null) {
            return (MultiSourceLuminaire) getDevice(DomainType.MULTI_SOURCE_LUMINAIRE, identifier);
        }
        return null;
    }

    public LightSource getLightSource(String identifier) {
        if (identifier != null) {
            return (LightSource) getDevice(DomainType.LIGHT_SOURCE, identifier);
        }
        return null;
    }

    public LightPoint getLight(String identifier) {
        LightPoint light = getMultiSourceLuminaire(identifier);
        if (light == null) {
            light = getLightSource(identifier);
        }
        if (light == null) {
            return getLightPoint(identifier);
        }
        return light;
    }

    public List<Sensor> getSensors() {
        ArrayList<Sensor> sensors = new ArrayList<>();
        synchronized (this.deviceMapLock) {
            Map<DomainType, Map<String, Device>> sensorTypeMap = (Map) this.deviceMap.get(DomainType.SENSOR);
            if (sensorTypeMap != null) {
                for (Map<String, Device> sensorMap : sensorTypeMap.values()) {
                    for (Device dev : sensorMap.values()) {
                        sensors.add((Sensor) dev);
                    }
                }
            }
        }
        return sensors;
    }

    public Sensor getSensor(String identifier) {
        return (Sensor) getDevice(DomainType.SENSOR, identifier);
    }

    public List<Device> getDevices(DomainType type) {
        List<Device> devices = new ArrayList<>();
        if (type == DomainType.LIGHT_POINT) {
            for (Device device : getLights()) {
                devices.add(device);
            }
        } else if (type == DomainType.SENSOR) {
            for (Device device2 : getSensors()) {
                devices.add(device2);
            }
        } else if (type == DomainType.COMPOUND_SENSOR) {
            for (Device device3 : getSensors()) {
                if (device3.isOfType(type)) {
                    devices.add(device3);
                }
            }
        } else {
            DomainType superType = type.getSuperType();
            if (superType != DomainType.UNKNOWN) {
                synchronized (this.deviceMapLock) {
                    Map<DomainType, Map<String, Device>> map = (Map) this.deviceMap.get(superType);
                    if (map != null) {
                        Map<String, Device> map2 = (Map) map.get(type);
                        if (map2 != null) {
                            for (Device device4 : map2.values()) {
                                devices.add(device4);
                            }
                        }
                    }
                }
            } else {
                HueLog.e(TAG, "Cannot resolve superType for domainType " + type);
            }
        }
        return devices;
    }

    public List<Device> getDevices() {
        List<Device> devices = new ArrayList<>();
        synchronized (this.deviceMapLock) {
            for (Map<DomainType, Map<String, Device>> deviceTypeMap : this.deviceMap.values()) {
                for (Map<String, Device> deviceInstanceMap : deviceTypeMap.values()) {
                    for (Device device : deviceInstanceMap.values()) {
                        devices.add(device);
                    }
                }
            }
        }
        return devices;
    }

    public List<Device> getDevices(String feature) {
        List<Device> devices = new ArrayList<>();
        for (Device device : getDevices()) {
            if (device.getInfo() != null && device.getInfo().getSupportedFeatures().contains(feature)) {
                devices.add(device);
            }
        }
        return devices;
    }

    public List<Device> getDevices(List<String> features) {
        List<Device> devices = new ArrayList<>();
        for (Device device : getDevices()) {
            boolean areAllFeaturesSupported = true;
            for (String feature : features) {
                areAllFeaturesSupported &= device.getInfo() != null && device.getInfo().getSupportedFeatures().contains(feature);
            }
            if (areAllFeaturesSupported) {
                devices.add(device);
            }
        }
        return devices;
    }

    public Device getDevice(DomainType type, String identifier) {
        if (identifier == null) {
            return null;
        }
        if (type == DomainType.LIGHT_SOURCE) {
            for (MultiSourceLuminaire msl : getMultiSourceLuminaires()) {
                Device ls = msl.getDevice(type, identifier);
                if (ls != null) {
                    return ls;
                }
            }
        } else if (type == DomainType.LIGHT_POINT) {
            Device light = getDeviceWithSuperType(DomainType.LIGHT_POINT, DomainType.LIGHT_POINT, identifier);
            if (light != null) {
                return light;
            }
            for (MultiSourceLuminaire msl2 : getMultiSourceLuminaires()) {
                if (msl2 != null && identifier.equals(msl2.getIdentifier())) {
                    return msl2;
                }
                Iterator it = msl2.getDevices().iterator();
                while (true) {
                    if (it.hasNext()) {
                        LightSource ls2 = (LightSource) ((Device) it.next());
                        if (ls2 != null && identifier.equals(ls2.getIdentifier())) {
                            return ls2;
                        }
                        Device lp = ls2.getDevice(type, identifier);
                        if (lp != null) {
                            return lp;
                        }
                    }
                }
            }
        } else if (type == DomainType.SENSOR) {
            for (Sensor sensor : getSensors()) {
                if (sensor.getIdentifier().equals(identifier)) {
                    return sensor;
                }
            }
            for (Device compoundDevice : getDevices(DomainType.COMPOUND_SENSOR)) {
                CompoundSensor compoundSensor = (CompoundSensor) compoundDevice;
                if (compoundSensor != null) {
                    Device sensor2 = compoundSensor.getDevice(type, identifier);
                    if (sensor2 != null) {
                        return sensor2;
                    }
                }
            }
        } else {
            if (type.getSuperType() == DomainType.SENSOR) {
                Device device = getDeviceWithSuperType(type.getSuperType(), type, identifier);
                if (device != null) {
                    return device;
                }
                for (Device compoundDevice2 : getDevices(DomainType.COMPOUND_SENSOR)) {
                    CompoundSensor compoundSensor2 = (CompoundSensor) compoundDevice2;
                    if (compoundSensor2 != null) {
                        Device sensor3 = compoundSensor2.getDevice(type, identifier);
                        if (sensor3 != null) {
                            return sensor3;
                        }
                    }
                }
            } else {
                DomainType superType = type.getSuperType();
                if (superType != DomainType.UNKNOWN) {
                    Device device2 = getDeviceWithSuperType(superType, type, identifier);
                    if (device2 != null) {
                        return device2;
                    }
                } else {
                    HueLog.e(TAG, "Cannot resolve superType for domain type " + type);
                }
            }
        }
        return getDeviceByUniqueIdentifier(type, identifier);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.philips.lighting.hue.sdk.wrapper.domain.device.Device getDeviceWithSuperType(com.philips.lighting.hue.sdk.wrapper.domain.DomainType r5, com.philips.lighting.hue.sdk.wrapper.domain.DomainType r6, java.lang.String r7) {
        /*
            r4 = this;
            java.lang.Object r3 = r4.deviceMapLock
            monitor-enter(r3)
            java.util.Map<com.philips.lighting.hue.sdk.wrapper.domain.DomainType, java.util.Map<com.philips.lighting.hue.sdk.wrapper.domain.DomainType, java.util.Map<java.lang.String, com.philips.lighting.hue.sdk.wrapper.domain.device.Device>>> r2 = r4.deviceMap     // Catch:{ all -> 0x0020 }
            java.lang.Object r0 = r2.get(r5)     // Catch:{ all -> 0x0020 }
            java.util.Map r0 = (java.util.Map) r0     // Catch:{ all -> 0x0020 }
            if (r0 == 0) goto L_0x001d
            java.lang.Object r1 = r0.get(r6)     // Catch:{ all -> 0x0020 }
            java.util.Map r1 = (java.util.Map) r1     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x001d
            java.lang.Object r2 = r1.get(r7)     // Catch:{ all -> 0x0020 }
            com.philips.lighting.hue.sdk.wrapper.domain.device.Device r2 = (com.philips.lighting.hue.sdk.wrapper.domain.device.Device) r2     // Catch:{ all -> 0x0020 }
            monitor-exit(r3)     // Catch:{ all -> 0x0020 }
        L_0x001c:
            return r2
        L_0x001d:
            monitor-exit(r3)     // Catch:{ all -> 0x0020 }
            r2 = 0
            goto L_0x001c
        L_0x0020:
            r2 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0020 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.philips.lighting.hue.sdk.wrapper.domain.BridgeState.getDeviceWithSuperType(com.philips.lighting.hue.sdk.wrapper.domain.DomainType, com.philips.lighting.hue.sdk.wrapper.domain.DomainType, java.lang.String):com.philips.lighting.hue.sdk.wrapper.domain.device.Device");
    }

    /* access modifiers changed from: protected */
    public BridgeResource getResourceWithType(DomainType type, String identifier) {
        synchronized (this.resourceMapLock) {
            Map<String, BridgeResource> map = (Map) this.resourceMap.get(type);
            if (map == null) {
                return null;
            }
            BridgeResource bridgeResource = (BridgeResource) map.get(identifier);
            return bridgeResource;
        }
    }

    public Device getDeviceByUniqueIdentifier(DomainType type, String identifier) {
        if (type == DomainType.LIGHT_POINT) {
            for (MultiSourceLuminaire msl : getMultiSourceLuminaires()) {
                if (msl != null) {
                    if (msl.getConfiguration() != null && identifier.equals(msl.getConfiguration().getUniqueIdentifier())) {
                        return msl;
                    }
                    for (Device device : msl.getDevices(DomainType.LIGHT_SOURCE)) {
                        if (device != null) {
                            if (device.getConfiguration() != null && identifier.equals(device.getConfiguration().getUniqueIdentifier())) {
                                return device;
                            }
                            for (Device lp : ((LightSource) device).getDevices()) {
                                if (lp != null && lp.getConfiguration() != null && identifier.equals(lp.getConfiguration().getUniqueIdentifier())) {
                                    return lp;
                                }
                            }
                            continue;
                        }
                    }
                    continue;
                }
            }
            for (LightPoint lp2 : getLightPoints()) {
                if (lp2 != null && lp2.getConfiguration() != null && identifier.equals(lp2.getConfiguration().getUniqueIdentifier())) {
                    return lp2;
                }
            }
        } else if (type == DomainType.LIGHT_SOURCE) {
            for (MultiSourceLuminaire msl2 : getMultiSourceLuminaires()) {
                if (msl2 != null) {
                    if (msl2.getConfiguration() != null && identifier.equals(msl2.getConfiguration().getUniqueIdentifier())) {
                        return msl2;
                    }
                    for (Device device2 : msl2.getDevices(DomainType.LIGHT_SOURCE)) {
                        if (device2 != null && device2.getConfiguration() != null && identifier.equals(device2.getConfiguration().getUniqueIdentifier())) {
                            return device2;
                        }
                    }
                    continue;
                }
            }
        } else {
            for (Device device3 : getDevices(type)) {
                if (device3 != null && device3.getConfiguration() != null && identifier.equals(device3.getConfiguration().getUniqueIdentifier())) {
                    return device3;
                }
            }
        }
        return null;
    }

    public List<BridgeResource> getResources(DomainType type) {
        List<BridgeResource> resources = new ArrayList<>();
        synchronized (this.resourceMapLock) {
            Map<String, BridgeResource> resourceTypeMap = (Map) this.resourceMap.get(type);
            if (resourceTypeMap != null) {
                for (BridgeResource resource : resourceTypeMap.values()) {
                    resources.add(resource);
                }
            }
        }
        return resources;
    }

    public List<BridgeResource> getResources() {
        List<BridgeResource> resources = new ArrayList<>();
        synchronized (this.resourceMapLock) {
            for (Map<String, BridgeResource> resourceTypeMap : this.resourceMap.values()) {
                for (BridgeResource resource : resourceTypeMap.values()) {
                    resources.add(resource);
                }
            }
        }
        return resources;
    }

    public BridgeResource getResource(DomainType type, String identifier) {
        return getResourceWithType(type, identifier);
    }

    public List<Scene> getScenes() {
        List<Scene> scenes = new ArrayList<>();
        for (BridgeResource resource : getResources(DomainType.SCENE)) {
            scenes.add((Scene) resource);
        }
        return scenes;
    }

    public Scene getScene(String identifier) {
        return (Scene) getResource(DomainType.SCENE, identifier);
    }

    public List<Group> getGroups() {
        List<Group> groups = new ArrayList<>();
        for (BridgeResource resource : getResources(DomainType.GROUP)) {
            groups.add((Group) resource);
        }
        return groups;
    }

    public Group getGroup(String identifier) {
        return (Group) getResource(DomainType.GROUP, identifier);
    }

    public List<Schedule> getSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        for (BridgeResource resource : getResources(DomainType.SCHEDULE)) {
            schedules.add((Schedule) resource);
        }
        for (BridgeResource resource2 : getResources(DomainType.TIMER)) {
            schedules.add((Schedule) resource2);
        }
        return schedules;
    }

    public Schedule getSchedule(String identifier) {
        Schedule s = (Schedule) getResource(DomainType.SCHEDULE, identifier);
        return s != null ? s : (Schedule) getResource(DomainType.TIMER, identifier);
    }

    public List<Timer> getTimers() {
        List<Timer> timers = new ArrayList<>();
        for (BridgeResource resource : getResources(DomainType.TIMER)) {
            timers.add((Timer) resource);
        }
        return timers;
    }

    public Timer getTimer(String identifier) {
        return (Timer) getResource(DomainType.TIMER, identifier);
    }

    public List<WhitelistEntry> getWhitelist() {
        List<WhitelistEntry> whitelist = new ArrayList<>();
        for (BridgeResource resource : getResources(DomainType.WHITELIST_ENTRY)) {
            whitelist.add((WhitelistEntry) resource);
        }
        return whitelist;
    }

    public WhitelistEntry getWhitelistEntry(String identifier) {
        return (WhitelistEntry) getResource(DomainType.WHITELIST_ENTRY, identifier);
    }

    public List<Rule> getRules() {
        List<Rule> rules = new ArrayList<>();
        for (BridgeResource resource : getResources(DomainType.RULE)) {
            rules.add((Rule) resource);
        }
        return rules;
    }

    public Rule getRule(String identifier) {
        return (Rule) getResource(DomainType.RULE, identifier);
    }

    public List<ResourceLink> getResourceLinks() {
        List<ResourceLink> links = new ArrayList<>();
        for (BridgeResource resource : getResources(DomainType.RESOURCE_LINK)) {
            links.add((ResourceLink) resource);
        }
        return links;
    }

    public ResourceLink getResourceLink(String identifier) {
        return (ResourceLink) getResource(DomainType.RESOURCE_LINK, identifier);
    }

    public TimeZones getTimeZones() {
        return (TimeZones) getResource(DomainType.TIMEZONES, new String("0"));
    }

    public BridgeCapabilities getCapabilities() {
        return (BridgeCapabilities) getResource(DomainType.CAPABILITIES, new String("0"));
    }

    /* access modifiers changed from: protected */
    public void addBridgeResources(BridgeResource[] bridgeResources, int domainTypeInt) {
        DomainType domainType = DomainType.fromValue(domainTypeInt);
        Map<String, BridgeResource> resourceMapShadow = new HashMap<>();
        for (BridgeResource resource : bridgeResources) {
            if (resource != null) {
                DomainType type = resource.getType();
                if (type != domainType) {
                    HueLog.e("HueSDK", "addBridgeResources: Domain object " + resource.getIdentifier() + " of type " + type + " does not match expected type " + domainType);
                } else {
                    resourceMapShadow.put(resource.getIdentifier(), resource);
                }
            }
        }
        synchronized (this.resourceMapLock) {
            this.resourceMap.put(domainType, resourceMapShadow);
        }
    }

    /* access modifiers changed from: protected */
    public void addBridgeDevices(Device[] bridgeDevices, int domainTypeInt) {
        DomainType superType;
        DomainType domainType = DomainType.fromValue(domainTypeInt);
        Map<DomainType, Map<String, Device>> hashMap = new HashMap<>();
        for (Device device : bridgeDevices) {
            if (device != null) {
                DomainType type = device.getType();
                if (type == DomainType.LIGHT_POINT) {
                    superType = DomainType.LIGHT_POINT;
                } else {
                    superType = type.getSuperType();
                }
                if (superType != domainType) {
                    HueLog.e("HueSDK", "addBridgeDevices: Domain object " + device.getIdentifier() + " of type " + superType + " does not match expected type " + domainType);
                } else {
                    Map<DomainType, Map<String, Device>> map = hashMap;
                    if (!map.containsKey(type)) {
                        map.put(type, new HashMap());
                    }
                    ((Map) map.get(type)).put(device.getIdentifier(), device);
                }
            }
        }
        synchronized (this.deviceMapLock) {
            this.deviceMap.put(domainType, hashMap);
        }
    }

    public ReturnCode refresh(BridgeStateCacheType cacheType, BridgeConnectionType connectionType) {
        if (cacheType == null) {
            return ReturnCode.NOT_ALLOWED;
        }
        if (connectionType == null) {
            return ReturnCode.NOT_ALLOWED;
        }
        return ReturnCode.fromValue(refreshNative(cacheType.getValue(), connectionType.getValue()));
    }

    public DomainType getType() {
        return DomainType.BRIDGE_STATE;
    }

    public BridgeConfiguration getBridgeConfiguration() {
        return this.config;
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        int hashCode2 = ((super.hashCode() * 31) + (this.config == null ? 0 : this.config.hashCode())) * 31;
        if (this.deviceMap == null) {
            hashCode = 0;
        } else {
            hashCode = this.deviceMap.hashCode();
        }
        int i2 = (hashCode2 + hashCode) * 31;
        if (this.resourceMap != null) {
            i = this.resourceMap.hashCode();
        }
        return i2 + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BridgeState other = (BridgeState) obj;
        if (this.config == null) {
            if (other.config != null) {
                return false;
            }
        } else if (!this.config.equals(other.config)) {
            return false;
        }
        if (this.deviceMap == null) {
            if (other.deviceMap != null) {
                return false;
            }
        } else if (!this.deviceMap.equals(other.deviceMap)) {
            return false;
        }
        if (this.resourceMap == null) {
            if (other.resourceMap != null) {
                return false;
            }
            return true;
        } else if (!this.resourceMap.equals(other.resourceMap)) {
            return false;
        } else {
            return true;
        }
    }
}
