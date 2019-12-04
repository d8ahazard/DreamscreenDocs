package com.philips.lighting.hue.sdk.wrapper.domain.resource;

import com.philips.lighting.hue.sdk.wrapper.SessionObject;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.Sensor;
import com.philips.lighting.hue.sdk.wrapper.utilities.Executor;
import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Group extends SessionObject implements BridgeResource {
    private AggregatedPresenceSensorState aggregatedPresenceSensorState;
    private GroupClass groupClass;
    private GroupState groupState;
    private byte[] identifier;
    private byte[][] lightIds;
    private GroupLightLocation[] lightLocations;
    private byte[] modelId;
    private byte[] name;
    private Boolean recycle;
    private List<Sensor> sensors;
    private GroupStream stream;
    private GroupType type;

    /* access modifiers changed from: private */
    public native void applyLightStateNative(Action action, int i, BridgeResponseCallback bridgeResponseCallback);

    /* access modifiers changed from: private */
    public native void applySceneNative(Action action, int i, BridgeResponseCallback bridgeResponseCallback);

    /* access modifiers changed from: private */
    public native void fetchNative(int i, BridgeResponseCallback bridgeResponseCallback);

    private native int removeLightLocationNative(String str);

    private native void syncLightIdsNative(String str);

    private native void syncLightLocationsNative();

    public native void addSensor(Sensor sensor);

    public native boolean hasSensor(Sensor sensor);

    public native void removeSensor(Sensor sensor);

    public native void syncNative();

    public native void syncSensorsNative();

    public Group() {
        this.name = null;
        this.identifier = null;
        this.sensors = null;
        this.modelId = null;
        this.recycle = null;
        this.type = GroupType.UNKNOWN;
        this.groupClass = null;
        this.groupState = null;
        this.stream = null;
        this.aggregatedPresenceSensorState = null;
    }

    protected Group(long session_key) {
        super(session_key);
        this.name = null;
        this.identifier = null;
        this.sensors = null;
        this.modelId = null;
        this.recycle = null;
        this.type = GroupType.UNKNOWN;
        this.groupClass = null;
        this.groupState = null;
        this.stream = null;
        this.aggregatedPresenceSensorState = null;
    }

    public Group(String identifier2) {
        this.name = null;
        this.identifier = null;
        this.sensors = null;
        this.modelId = null;
        this.recycle = null;
        this.type = GroupType.UNKNOWN;
        this.groupClass = null;
        this.groupState = null;
        this.stream = null;
        this.aggregatedPresenceSensorState = null;
        setIdentifier(identifier2);
    }

    public Group(String name2, List<String> lightIds2) {
        this.name = null;
        this.identifier = null;
        this.sensors = null;
        this.modelId = null;
        this.recycle = null;
        this.type = GroupType.UNKNOWN;
        this.groupClass = null;
        this.groupState = null;
        this.stream = null;
        this.aggregatedPresenceSensorState = null;
        setName(name2);
        setLightIds(lightIds2);
    }

    public Group(String name2, List<String> lightIds2, List<Sensor> sensors2) {
        this(name2, lightIds2);
        setSensors(sensors2);
    }

    public Group(String name2, List<String> lightIds2, Bridge bridge) {
        this(name2, lightIds2);
        setBridge(bridge);
    }

    public Group(String name2, List<String> lightIds2, List<Sensor> sensors2, Bridge bridge) {
        this(name2, lightIds2, sensors2);
        setBridge(bridge);
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

    public Boolean getRecycle() {
        return this.recycle;
    }

    public void setRecycle(Boolean recycle2) {
        this.recycle = recycle2;
    }

    public void setRecycle(boolean recycle2) {
        this.recycle = new Boolean(recycle2);
    }

    public void apply(Action action) {
        apply(action, BridgeConnectionType.LOCAL_REMOTE, null);
    }

    public void apply(Action action, BridgeResponseCallback callback) {
        apply(action, BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void apply(final Action action, final BridgeConnectionType connectionType, final BridgeResponseCallback callback) {
        if (action == null) {
            BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_ALLOWED);
        } else if (connectionType == null) {
            BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_ALLOWED);
        } else if (Scene.class.isInstance(action)) {
            Executor.submit(new Runnable() {
                public void run() {
                    Group.this.applySceneNative(action, connectionType.getValue(), callback);
                }
            });
        } else if (LightState.class.isInstance(action)) {
            Executor.submit(new Runnable() {
                public void run() {
                    Group.this.applyLightStateNative(action, connectionType.getValue(), callback);
                }
            });
        } else {
            BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.UNSUPPORTED_PARAMETER_TYPE);
        }
    }

    public GroupType getGroupType() {
        return this.type;
    }

    public void setGroupType(GroupType type2) {
        this.type = type2;
    }

    private void setGroupType(int type2) {
        this.type = GroupType.fromValue(type2);
    }

    /* access modifiers changed from: protected */
    public int getGroupTypeAsInt() {
        return this.type.getValue();
    }

    public GroupClass getGroupClass() {
        return this.groupClass;
    }

    public void setGroupClass(GroupClass groupClass2) {
        this.groupClass = groupClass2;
    }

    private void setGroupClassFromInt(int groupClass2) {
        this.groupClass = GroupClass.fromValue(groupClass2);
    }

    /* access modifiers changed from: protected */
    public int getGroupClassAsInt() {
        return this.groupClass.getValue();
    }

    public String getModelId() {
        return NativeTools.BytesToString(this.modelId);
    }

    public List<String> getLightIds() {
        if (this.lightIds == null) {
            return new ArrayList();
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (byte[] lightid : this.lightIds) {
            arrayList.add(NativeTools.BytesToString(lightid));
        }
        return arrayList;
    }

    public void setLightIds(List<String> lightIds2) {
        if (lightIds2 != null) {
            Set<String> ids_removed = new HashSet<>();
            if (this.lightIds != null) {
                for (byte[] BytesToString : this.lightIds) {
                    ids_removed.add(NativeTools.BytesToString(BytesToString));
                }
            }
            this.lightIds = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{lightIds2.size(), 0});
            for (int i = 0; i < lightIds2.size(); i++) {
                this.lightIds[i] = NativeTools.StringToBytes((String) lightIds2.get(i));
                ids_removed.remove(lightIds2.get(i));
            }
            if (ids_removed.size() > 0) {
                for (String id_removed : ids_removed) {
                    removeLightLocationInternal(id_removed);
                }
                syncLightLocationsNative();
            }
        }
        syncLightIdsNative(null);
    }

    public void addLightId(String lightId) {
        if (lightId != null) {
            if (this.lightIds != null) {
                boolean doAdd = true;
                int i = 0;
                while (true) {
                    if (i >= this.lightIds.length) {
                        break;
                    } else if (NativeTools.BytesToString(this.lightIds[i]).equals(lightId)) {
                        doAdd = false;
                        break;
                    } else {
                        i++;
                    }
                }
                if (doAdd) {
                    byte[][] temp = this.lightIds;
                    this.lightIds = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{temp.length + 1, 0});
                    for (int i2 = 0; i2 < temp.length; i2++) {
                        this.lightIds[i2] = temp[i2];
                    }
                    this.lightIds[temp.length] = NativeTools.StringToBytes(lightId);
                }
            } else {
                this.lightIds = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{1, 0});
                this.lightIds[0] = NativeTools.StringToBytes(lightId);
            }
            syncLightIdsNative(null);
        }
    }

    public void addLight(LightPoint lightPoint) {
        addLightId(lightPoint.getIdentifier());
    }

    public void removeLightId(String lightId) {
        if (lightId != null) {
            syncLightIdsNative(lightId);
            if (this.lightIds != null) {
                byte[][] temp = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{this.lightIds.length, 0});
                int new_length = 0;
                for (int i = 0; i < temp.length; i++) {
                    if (!lightId.equals(NativeTools.BytesToString(this.lightIds[i]))) {
                        temp[new_length] = this.lightIds[i];
                        new_length++;
                    }
                }
                if (new_length < this.lightIds.length) {
                    if (new_length > 0) {
                        this.lightIds = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{new_length, 0});
                        for (int i2 = 0; i2 < new_length; i2++) {
                            this.lightIds[i2] = temp[i2];
                            temp[i2] = null;
                        }
                    } else {
                        this.lightIds = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{0, 0});
                    }
                }
            }
            removeLightLocation(lightId);
        }
    }

    public void removeLight(LightPoint lightPoint) {
        removeLightId(lightPoint.getIdentifier());
    }

    @Nonnull
    public List<Sensor> getSensors() {
        if (this.sensors != null) {
            return this.sensors;
        }
        return new ArrayList();
    }

    public void setSensors(List<Sensor> sensors2) {
        this.sensors = sensors2;
        syncSensorsNative();
    }

    public GroupState getGroupState() {
        return this.groupState;
    }

    public int getDomainType() {
        return getType().getValue();
    }

    public DomainType getType() {
        return DomainType.GROUP;
    }

    public boolean isOfType(DomainType type2) {
        if (type2 == DomainType.RESOURCE || getType() == type2) {
            return true;
        }
        return false;
    }

    public boolean isOfType(int typeAsInt) {
        return isOfType(DomainType.fromValue(typeAsInt));
    }

    public void setBridge(Bridge bridge) {
        setSessionKey(((SessionObject) bridge).getSessionKey());
        syncLightIdsNative(null);
        syncLightLocationsNative();
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
                    Group.this.fetchNative(allowedConnectionType.getValue(), callback);
                }
            });
        }
    }

    public List<GroupLightLocation> getLightLocations() {
        return Arrays.asList(this.lightLocations);
    }

    public void setLightLocations(List<GroupLightLocation> newLightLocations) {
        if (newLightLocations != null) {
            this.lightLocations = new GroupLightLocation[newLightLocations.size()];
            this.lightLocations = (GroupLightLocation[]) newLightLocations.toArray(this.lightLocations);
            syncLightLocationsNative();
        }
    }

    public void addLightLocation(GroupLightLocation newLightLocation) {
        if (newLightLocation != null) {
            if (this.lightLocations != null) {
                boolean doAdd = true;
                int i = 0;
                while (true) {
                    if (i >= this.lightLocations.length) {
                        break;
                    } else if (newLightLocation.getLightIdentifier().equals(this.lightLocations[i].getLightIdentifier())) {
                        this.lightLocations[i] = newLightLocation;
                        doAdd = false;
                        break;
                    } else {
                        i++;
                    }
                }
                if (doAdd) {
                    GroupLightLocation[] backup = this.lightLocations;
                    this.lightLocations = new GroupLightLocation[(backup.length + 1)];
                    for (int i2 = 0; i2 < backup.length; i2++) {
                        this.lightLocations[i2] = backup[i2];
                    }
                    this.lightLocations[backup.length] = newLightLocation;
                }
            } else {
                this.lightLocations = new GroupLightLocation[1];
                this.lightLocations[0] = newLightLocation;
            }
            syncLightLocationsNative();
        }
    }

    public ReturnCode removeLightLocation(String lightId) {
        ReturnCode ret = ReturnCode.fromValue(removeLightLocationNative(lightId));
        if (ret == ReturnCode.SUCCESS) {
            return ret;
        }
        ReturnCode ret2 = removeLightLocationInternal(lightId);
        syncLightLocationsNative();
        return ret2;
    }

    private ReturnCode removeLightLocationInternal(String lightId) {
        if (lightId == null) {
            return ReturnCode.NULL_PARAMETER;
        }
        if (this.lightLocations != null) {
            GroupLightLocation[] temp = new GroupLightLocation[this.lightLocations.length];
            int new_length = 0;
            for (int i = 0; i < temp.length; i++) {
                if (!lightId.equals(this.lightLocations[i].getLightIdentifier())) {
                    temp[new_length] = this.lightLocations[i];
                    new_length++;
                }
            }
            if (new_length < this.lightLocations.length) {
                if (new_length > 0) {
                    this.lightLocations = new GroupLightLocation[new_length];
                    for (int i2 = 0; i2 < new_length; i2++) {
                        this.lightLocations[i2] = temp[i2];
                        temp[i2] = null;
                    }
                } else {
                    this.lightLocations = null;
                }
                return ReturnCode.SUCCESS;
            }
        }
        return ReturnCode.DOES_NOT_EXIST;
    }

    public GroupStream getStream() {
        return this.stream;
    }

    public void setStream(GroupStream new_stream) {
        this.stream = new_stream;
    }

    @Nullable
    public AggregatedPresenceSensorState getAggregatedPresenceSensorState() {
        return this.aggregatedPresenceSensorState;
    }

    private void setAggregatedPresenceSensorState(@Nullable AggregatedPresenceSensorState aggregatedPresenceSensorState2) {
        this.aggregatedPresenceSensorState = aggregatedPresenceSensorState2;
    }

    public int hashCode() {
        int hashCode;
        int hashCode2;
        int i = 0;
        int hashCode3 = super.hashCode() * 31;
        if (this.groupState == null) {
            hashCode = 0;
        } else {
            hashCode = this.groupState.hashCode();
        }
        int hashCode4 = (((((((((((hashCode3 + hashCode) * 31) + Arrays.hashCode(this.identifier)) * 31) + Arrays.hashCode(this.lightIds)) * 31) + Arrays.hashCode(this.modelId)) * 31) + Arrays.hashCode(this.name)) * 31) + (this.recycle == null ? 0 : this.recycle.hashCode())) * 31;
        if (this.groupClass == null) {
            hashCode2 = 0;
        } else {
            hashCode2 = this.groupClass.hashCode();
        }
        int hashCode5 = (((hashCode4 + hashCode2) * 31) + (this.type == null ? 0 : this.type.hashCode())) * 31;
        if (this.aggregatedPresenceSensorState != null) {
            i = this.aggregatedPresenceSensorState.hashCode();
        }
        return hashCode5 + i;
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
        Group other = (Group) obj;
        if (this.groupState == null) {
            if (other.groupState != null) {
                return false;
            }
        } else if (!this.groupState.equals(other.groupState)) {
            return false;
        }
        if (!Arrays.equals(this.identifier, other.identifier)) {
            return false;
        }
        if (!Arrays.deepEquals(this.lightIds, other.lightIds)) {
            return false;
        }
        if (!Arrays.deepEquals(this.lightLocations, other.lightLocations)) {
            return false;
        }
        if (!Arrays.equals(this.modelId, other.modelId)) {
            return false;
        }
        if (!Arrays.equals(this.name, other.name)) {
            return false;
        }
        if (this.stream == null) {
            if (other.stream != null) {
                return false;
            }
        } else if (!this.stream.equals(other.stream)) {
            return false;
        }
        if (this.recycle == null) {
            if (other.recycle != null) {
                return false;
            }
        } else if (!this.recycle.equals(other.recycle)) {
            return false;
        }
        if (this.groupClass != other.groupClass) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.aggregatedPresenceSensorState, other.aggregatedPresenceSensorState)) {
            return false;
        }
        if ((this.sensors == null && other.sensors != null) || (this.sensors != null && other.sensors == null)) {
            return false;
        }
        if (this.sensors != null) {
            if (this.sensors.size() != other.sensors.size()) {
                return false;
            }
            for (int i = 0; i < this.sensors.size(); i++) {
                if (!((Sensor) this.sensors.get(i)).getIdentifier().equals(((Sensor) other.sensors.get(i)).getIdentifier())) {
                    return false;
                }
            }
        }
        return true;
    }
}
