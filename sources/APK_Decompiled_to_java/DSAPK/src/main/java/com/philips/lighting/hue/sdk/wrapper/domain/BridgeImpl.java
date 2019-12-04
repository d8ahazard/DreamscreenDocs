package com.philips.lighting.hue.sdk.wrapper.domain;

import com.philips.lighting.hue.sdk.wrapper.HueLog;
import com.philips.lighting.hue.sdk.wrapper.SessionObject;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnection;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateUpdatedCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.FoundDevicesCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.QueueOptions;
import com.philips.lighting.hue.sdk.wrapper.connection.QueueType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.Device;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceConfiguration;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceSoftwareUpdate;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceState;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.BridgeResource;
import com.philips.lighting.hue.sdk.wrapper.knowledgebase.DeviceInfo;
import com.philips.lighting.hue.sdk.wrapper.utilities.Executor;
import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import com.philips.lighting.hue.sdk.wrapper.utilities.SessionMemoryManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BridgeImpl extends SessionObject implements Bridge, Device {
    private byte[] name;
    private BridgeState state;

    private native int addBridgeConnectionNative(BridgeConnection bridgeConnection);

    private native void backupNative(int i, BridgeResponseCallback bridgeResponseCallback);

    /* access modifiers changed from: private */
    public native void createDeviceNative(Device device, int i, BridgeResponseCallback bridgeResponseCallback);

    /* access modifiers changed from: private */
    public native int createResourceNative(BridgeResource bridgeResource, int i, BridgeResponseCallback bridgeResponseCallback);

    private native void createSession(String str, boolean z, boolean z2);

    private native void createSessionWithBridgeProxyReference(long j);

    /* access modifiers changed from: private */
    public native int deleteDeviceNative(Device device, int i, BridgeResponseCallback bridgeResponseCallback);

    /* access modifiers changed from: private */
    public native int deleteResourceNative(BridgeResource bridgeResource, int i, BridgeResponseCallback bridgeResponseCallback);

    private native void destroyNative();

    private native int extendFindNewDevicesNative(String[] strArr);

    /* access modifiers changed from: private */
    public native void findNewDevicesNative(String[] strArr, int i, FoundDevicesCallback foundDevicesCallback);

    private native BridgeConnection getBridgeConnectionNative(int i);

    private native void restoreNative(byte[] bArr, int i, BridgeResponseCallback bridgeResponseCallback);

    private native int setQueueOptionsNative(QueueOptions queueOptions);

    private native int setQueueTypeNative(int i);

    private native void startBackupNative(int i, BridgeResponseCallback bridgeResponseCallback);

    /* access modifiers changed from: private */
    public native void updateConfigurationNative(DeviceConfiguration deviceConfiguration, int i, BridgeResponseCallback bridgeResponseCallback, boolean z);

    /* access modifiers changed from: private */
    public native void updateFirmwareNative(int i, BridgeResponseCallback bridgeResponseCallback);

    private native void updateFirmwareWithFileNative(byte[] bArr, BridgeResponseCallback bridgeResponseCallback);

    /* access modifiers changed from: private */
    public native int updateResourceNative(BridgeResource bridgeResource, int i, BridgeResponseCallback bridgeResponseCallback, boolean z);

    /* access modifiers changed from: private */
    public native int updateResourceNative(BridgeResource bridgeResource, BridgeResource bridgeResource2, int i, BridgeResponseCallback bridgeResponseCallback, boolean z);

    /* access modifiers changed from: private */
    public native int updateStateNative(DeviceState deviceState, int i, BridgeResponseCallback bridgeResponseCallback, boolean z);

    /* access modifiers changed from: protected */
    public native int connectNative(int i);

    /* access modifiers changed from: protected */
    public native void disconnectNative(int i);

    public native List<BridgeConnection> getBridgeConnections();

    public native boolean isConnected();

    public native void refreshUsername(BridgeResponseCallback bridgeResponseCallback);

    public native void removeBridgeConnection(BridgeConnection bridgeConnection);

    public native void syncNative();

    BridgeImpl() {
        this((String) null);
    }

    BridgeImpl(String bridgeId) {
        this(bridgeId, true, true);
    }

    BridgeImpl(String bridgeId, boolean loadPersistedData, boolean persistenceEnabled) {
        this.name = null;
        this.state = null;
        createSession(bridgeId, loadPersistedData, persistenceEnabled);
        syncNative();
        SessionMemoryManager.addSession(this);
    }

    protected BridgeImpl(long bridgeReference) {
        this.name = null;
        this.state = null;
        createSessionWithBridgeProxyReference(bridgeReference);
        syncNative();
        SessionMemoryManager.addSession(this);
    }

    public String getName() {
        if (getBridgeConfiguration() != null) {
            return getBridgeConfiguration().getName();
        }
        return NativeTools.BytesToString(this.name);
    }

    public void setName(String name2) {
        this.name = NativeTools.StringToBytes(name2);
    }

    public String getIdentifier() {
        BridgeConfiguration config = getBridgeConfiguration();
        if (config != null) {
            return config.getUniqueIdentifier();
        }
        return null;
    }

    public ReturnCode connect() {
        return connect(BridgeConnectionType.LOCAL_REMOTE);
    }

    public ReturnCode connect(BridgeConnectionType connectionType) {
        return ReturnCode.fromValue(connectNative(connectionType.getValue()));
    }

    public void disconnect() {
        disconnect(BridgeConnectionType.LOCAL_REMOTE);
    }

    public void disconnect(BridgeConnectionType connectionType) {
        disconnectNative(connectionType.getValue());
    }

    public DeviceInfo getInfo() {
        return null;
    }

    public DeviceState getState() {
        return this.state;
    }

    public BridgeState getBridgeState() {
        return this.state;
    }

    public void createResource(final BridgeResource resource, final BridgeConnectionType allowedConnectionType, final BridgeResponseCallback callback) {
        if (allowedConnectionType == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NULL_PARAMETER);
        } else if (resource == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NULL_PARAMETER);
        } else {
            resource.setBridge(this);
            Executor.submit(new Runnable() {
                public void run() {
                    BridgeImpl.this.createResourceNative(resource, allowedConnectionType.getValue(), callback);
                }
            });
        }
    }

    public void createResource(BridgeResource resource, BridgeResponseCallback callback) {
        createResource(resource, BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void createResource(BridgeResource resource) {
        createResource(resource, BridgeConnectionType.LOCAL_REMOTE, null);
    }

    public void updateResource(BridgeResource resource, BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback) {
        updateResource(resource, allowedConnectionType, callback, true);
    }

    public void updateResource(BridgeResource resource, BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback, boolean sendDeltasOnly) {
        if (allowedConnectionType == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NULL_PARAMETER);
        } else if (resource == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NULL_PARAMETER);
        } else {
            final BridgeResource bridgeResource = resource;
            final BridgeConnectionType bridgeConnectionType = allowedConnectionType;
            final BridgeResponseCallback bridgeResponseCallback = callback;
            final boolean z = sendDeltasOnly;
            Executor.submit(new Runnable() {
                public void run() {
                    BridgeImpl.this.updateResourceNative(bridgeResource, bridgeConnectionType.getValue(), bridgeResponseCallback, z);
                }
            });
        }
    }

    public void updateResource(BridgeResource resource, BridgeResponseCallback callback) {
        updateResource(resource, BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void updateResource(BridgeResource resource) {
        updateResource(resource, BridgeConnectionType.LOCAL_REMOTE, (BridgeResponseCallback) null);
    }

    public void updateResource(BridgeResource resource, BridgeResource originalResource, BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback) {
        updateResource(resource, originalResource, allowedConnectionType, callback, true);
    }

    public void updateResource(BridgeResource resource, BridgeResource originalResource, BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback, boolean sendDeltasOnly) {
        if (allowedConnectionType == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NULL_PARAMETER);
        } else if (resource == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NULL_PARAMETER);
        } else {
            final BridgeResource bridgeResource = resource;
            final BridgeResource bridgeResource2 = originalResource;
            final BridgeConnectionType bridgeConnectionType = allowedConnectionType;
            final BridgeResponseCallback bridgeResponseCallback = callback;
            final boolean z = sendDeltasOnly;
            Executor.submit(new Runnable() {
                public void run() {
                    BridgeImpl.this.updateResourceNative(bridgeResource, bridgeResource2, bridgeConnectionType.getValue(), bridgeResponseCallback, z);
                }
            });
        }
    }

    public void updateResource(BridgeResource resource, BridgeResource originalResource, BridgeResponseCallback callback) {
        updateResource(resource, originalResource, BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void updateResource(BridgeResource resource, BridgeResource originalResource) {
        updateResource(resource, originalResource, BridgeConnectionType.LOCAL_REMOTE, (BridgeResponseCallback) null);
    }

    public void deleteResource(final BridgeResource resource, final BridgeConnectionType allowedConnectionType, final BridgeResponseCallback callback) {
        if (allowedConnectionType == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NULL_PARAMETER);
        } else if (resource == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NULL_PARAMETER);
        } else {
            Executor.submit(new Runnable() {
                public void run() {
                    BridgeImpl.this.deleteResourceNative(resource, allowedConnectionType.getValue(), callback);
                }
            });
        }
    }

    public void deleteResource(BridgeResource resource, BridgeResponseCallback callback) {
        deleteResource(resource, BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void deleteResource(BridgeResource resource) {
        deleteResource(resource, BridgeConnectionType.LOCAL_REMOTE, null);
    }

    public void updateState(DeviceState state2, BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback) {
        updateState(state2, allowedConnectionType, callback, true);
    }

    public void updateState(DeviceState state2, BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback, boolean sendDeltasOnly) {
        if (allowedConnectionType == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NULL_PARAMETER);
        } else if (state2 == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NULL_PARAMETER);
        } else if (!BridgeState.class.isAssignableFrom(state2.getClass())) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.WRONG_PARAMETER);
        } else {
            final DeviceState deviceState = state2;
            final BridgeConnectionType bridgeConnectionType = allowedConnectionType;
            final BridgeResponseCallback bridgeResponseCallback = callback;
            final boolean z = sendDeltasOnly;
            Executor.submit(new Runnable() {
                public void run() {
                    BridgeImpl.this.updateStateNative(deviceState, bridgeConnectionType.getValue(), bridgeResponseCallback, z);
                }
            });
        }
    }

    public void updateState(DeviceState state2, BridgeResponseCallback callback) {
        updateState(state2, BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void updateState(DeviceState state2, BridgeResponseCallback callback, boolean sendDeltasOnly) {
        updateState(state2, BridgeConnectionType.LOCAL_REMOTE, callback, sendDeltasOnly);
    }

    public void updateState(DeviceState state2) {
        updateState(state2, BridgeConnectionType.LOCAL_REMOTE, (BridgeResponseCallback) null);
    }

    public void updateState(DeviceState state2, boolean sendDeltasOnly) {
        updateState(state2, BridgeConnectionType.LOCAL_REMOTE, null, sendDeltasOnly);
    }

    public DeviceConfiguration getConfiguration() {
        if (this.state != null) {
            return this.state.getBridgeConfiguration();
        }
        return null;
    }

    public BridgeConfiguration getBridgeConfiguration() {
        if (this.state != null) {
            return this.state.getBridgeConfiguration();
        }
        return null;
    }

    public void updateConfiguration(DeviceConfiguration config, BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback) {
        updateConfiguration(config, allowedConnectionType, callback, true);
    }

    public void updateConfiguration(DeviceConfiguration config, BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback, boolean sendDeltasOnly) {
        if (allowedConnectionType == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NOT_ALLOWED);
        } else if (config == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NULL_PARAMETER);
        } else if (!BridgeConfiguration.class.isAssignableFrom(config.getClass())) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.WRONG_PARAMETER);
        } else {
            final DeviceConfiguration deviceConfiguration = config;
            final BridgeConnectionType bridgeConnectionType = allowedConnectionType;
            final BridgeResponseCallback bridgeResponseCallback = callback;
            final boolean z = sendDeltasOnly;
            Executor.submit(new Runnable() {
                public void run() {
                    BridgeImpl.this.updateConfigurationNative(deviceConfiguration, bridgeConnectionType.getValue(), bridgeResponseCallback, z);
                }
            });
        }
    }

    public void updateConfiguration(DeviceConfiguration config, BridgeResponseCallback callback) {
        updateConfiguration(config, BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void updateConfiguration(DeviceConfiguration config, BridgeResponseCallback callback, boolean sendDeltasOnly) {
        updateConfiguration(config, BridgeConnectionType.LOCAL_REMOTE, callback, sendDeltasOnly);
    }

    public void updateConfiguration(DeviceConfiguration config) {
        updateConfiguration(config, BridgeConnectionType.LOCAL_REMOTE, (BridgeResponseCallback) null);
    }

    public void updateConfiguration(DeviceConfiguration config, boolean sendDeltasOnly) {
        updateConfiguration(config, BridgeConnectionType.LOCAL_REMOTE, null, sendDeltasOnly);
    }

    public ReturnCode setQueueOptions(QueueOptions options) {
        return ReturnCode.fromValue(setQueueOptionsNative(options));
    }

    public ReturnCode setQueueType(QueueType type) {
        if (type == null) {
            return ReturnCode.NULL_PARAMETER;
        }
        return ReturnCode.fromValue(setQueueTypeNative(type.getValue()));
    }

    public BridgeConnection getBridgeConnection(BridgeConnectionType type) {
        if (type != null) {
            return getBridgeConnectionNative(type.getValue());
        }
        return null;
    }

    public DomainType getType() {
        return DomainType.BRIDGE;
    }

    public int getDomainType() {
        return getType().getValue();
    }

    public boolean isOfType(DomainType type) {
        return getType() == type;
    }

    public boolean isOfType(int typeAsInt) {
        return isOfType(DomainType.fromValue(typeAsInt));
    }

    public void createDevice(final Device device, final BridgeConnectionType allowedConnectionType, final BridgeResponseCallback callback) {
        if (allowedConnectionType == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NULL_PARAMETER);
        } else if (device == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NULL_PARAMETER);
        } else {
            Executor.submit(new Runnable() {
                public void run() {
                    BridgeImpl.this.createDeviceNative(device, allowedConnectionType.getValue(), callback);
                }
            });
        }
    }

    public void createDevice(Device device, BridgeResponseCallback callback) {
        createDevice(device, BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void createDevice(Device device) {
        createDevice(device, BridgeConnectionType.LOCAL_REMOTE, null);
    }

    public void deleteDevice(final Device device, final BridgeConnectionType allowedConnectionType, final BridgeResponseCallback callback) {
        if (allowedConnectionType == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NULL_PARAMETER);
        } else if (device == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NULL_PARAMETER);
        } else {
            Executor.submit(new Runnable() {
                public void run() {
                    BridgeImpl.this.deleteDeviceNative(device, allowedConnectionType.getValue(), callback);
                }
            });
        }
    }

    public void deleteDevice(Device device, BridgeResponseCallback callback) {
        deleteDevice(device, BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void deleteDevice(Device device) {
        deleteDevice(device, BridgeConnectionType.LOCAL_REMOTE, null);
    }

    public void addBridgeStateUpdatedCallback(BridgeStateUpdatedCallback callback) {
        if (this.state != null) {
            this.state.addUpdatedCallback(callback);
        } else {
            HueLog.e("Bridge", "BridgeState is null, cannot set callback!");
        }
    }

    public void removeBridgeStatedUpdatedCallback(BridgeStateUpdatedCallback callback) {
        if (this.state != null) {
            this.state.removeUpdatedCallback(callback);
        }
    }

    public void setBridgeConnectionCallback(BridgeConnectionCallback callback) {
        for (BridgeConnection connection : getBridgeConnections()) {
            connection.setBridgeConnectionCallback(callback);
        }
    }

    public ReturnCode addBridgeConnection(BridgeConnection connection) {
        return ReturnCode.fromValue(addBridgeConnectionNative(connection));
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        destroy();
        super.finalize();
    }

    public void findNewDevices(List<String> hexCodes, final BridgeConnectionType allowedConnection, final FoundDevicesCallback callback) {
        if (allowedConnection == null) {
            dispatch(callback, ReturnCode.NULL_PARAMETER, "allowed connection is null!");
        } else if (hexCodes == null || hexCodes.size() == 0) {
            findNewDevices(allowedConnection, callback);
        } else {
            final String[] hexCodesArray = new String[hexCodes.size()];
            for (int i = 0; i < hexCodes.size(); i++) {
                hexCodesArray[i] = (String) hexCodes.get(i);
            }
            Executor.submit(new Runnable() {
                public void run() {
                    BridgeImpl.this.findNewDevicesNative(hexCodesArray, allowedConnection.getValue(), callback);
                }
            });
        }
    }

    public void findNewDevices(List<String> hexCodes, FoundDevicesCallback callback) {
        findNewDevices(hexCodes, BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void findNewDevices(List<String> hexCodes) {
        findNewDevices(hexCodes, BridgeConnectionType.LOCAL_REMOTE, null);
    }

    public void findNewDevices(final BridgeConnectionType allowedConnection, final FoundDevicesCallback callback) {
        if (allowedConnection == null) {
            dispatch(callback, ReturnCode.NULL_PARAMETER, "allowed connection is null!");
        } else {
            Executor.submit(new Runnable() {
                public void run() {
                    BridgeImpl.this.findNewDevicesNative(null, allowedConnection.getValue(), callback);
                }
            });
        }
    }

    public void findNewDevices(FoundDevicesCallback callback) {
        findNewDevices(BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void findNewDevices() {
        findNewDevices(BridgeConnectionType.LOCAL_REMOTE, (FoundDevicesCallback) null);
    }

    private void dispatch(FoundDevicesCallback callback, ReturnCode code, String description) {
        List<HueError> errors = new ArrayList<>();
        errors.add(new SDKError(code.getValue(), description));
        callback.onDeviceSearchFinished(this, errors);
    }

    public ReturnCode extendFindNewDevices(List<String> hexCodes) {
        String[] hexCodesArray = null;
        if (hexCodes != null) {
            hexCodesArray = new String[hexCodes.size()];
            for (int i = 0; i < hexCodes.size(); i++) {
                hexCodesArray[i] = (String) hexCodes.get(i);
            }
        }
        return ReturnCode.fromValue(extendFindNewDevicesNative(hexCodesArray));
    }

    public ReturnCode extendFindNewDevices() {
        return extendFindNewDevices(null);
    }

    private Device getDevice(int type, String identifier) {
        if (this.state == null) {
            return null;
        }
        return this.state.getDevice(DomainType.fromValue(type), identifier);
    }

    public void updateFirmware(final BridgeConnectionType allowedConnectionType, final BridgeResponseCallback callback) {
        if (allowedConnectionType == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NULL_PARAMETER);
        } else {
            Executor.submit(new Runnable() {
                public void run() {
                    BridgeImpl.this.updateFirmwareNative(allowedConnectionType.getValue(), callback);
                }
            });
        }
    }

    public void updateFirmware(BridgeResponseCallback callback) {
        updateFirmware(BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void updateFirmware() {
        updateFirmware(BridgeConnectionType.LOCAL_REMOTE, (BridgeResponseCallback) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x003e A[SYNTHETIC, Splitter:B:20:0x003e] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0052 A[SYNTHETIC, Splitter:B:28:0x0052] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x005e A[SYNTHETIC, Splitter:B:34:0x005e] */
    /* JADX WARNING: Removed duplicated region for block: B:49:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:17:0x0034=Splitter:B:17:0x0034, B:25:0x0048=Splitter:B:25:0x0048} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateFirmware(java.io.File r7, com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType r8, com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback r9) {
        /*
            r6 = this;
            if (r7 != 0) goto L_0x0008
            com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode r4 = com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode.NULL_PARAMETER
            com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher.dispatch(r9, r6, r4)
        L_0x0007:
            return
        L_0x0008:
            if (r8 != 0) goto L_0x0010
            com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode r4 = com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode.NULL_PARAMETER
            com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher.dispatch(r9, r6, r4)
            goto L_0x0007
        L_0x0010:
            r1 = 0
            r2 = 0
            java.io.RandomAccessFile r3 = new java.io.RandomAccessFile     // Catch:{ FileNotFoundException -> 0x0033, IOException -> 0x0047 }
            java.lang.String r4 = "r"
            r3.<init>(r7, r4)     // Catch:{ FileNotFoundException -> 0x0033, IOException -> 0x0047 }
            long r4 = r3.length()     // Catch:{ FileNotFoundException -> 0x0073, IOException -> 0x0070, all -> 0x006d }
            int r4 = (int) r4     // Catch:{ FileNotFoundException -> 0x0073, IOException -> 0x0070, all -> 0x006d }
            byte[] r1 = new byte[r4]     // Catch:{ FileNotFoundException -> 0x0073, IOException -> 0x0070, all -> 0x006d }
            r3.read(r1)     // Catch:{ FileNotFoundException -> 0x0073, IOException -> 0x0070, all -> 0x006d }
            if (r3 == 0) goto L_0x0028
            r3.close()     // Catch:{ IOException -> 0x002e }
        L_0x0028:
            if (r1 == 0) goto L_0x0067
            r6.updateFirmware(r1, r8, r9)
            goto L_0x0007
        L_0x002e:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0028
        L_0x0033:
            r0 = move-exception
        L_0x0034:
            r0.printStackTrace()     // Catch:{ all -> 0x005b }
            com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode r4 = com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode.WRONG_PARAMETER     // Catch:{ all -> 0x005b }
            com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher.dispatch(r9, r6, r4)     // Catch:{ all -> 0x005b }
            if (r2 == 0) goto L_0x0007
            r2.close()     // Catch:{ IOException -> 0x0042 }
            goto L_0x0007
        L_0x0042:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0007
        L_0x0047:
            r0 = move-exception
        L_0x0048:
            r0.printStackTrace()     // Catch:{ all -> 0x005b }
            com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode r4 = com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode.WRONG_PARAMETER     // Catch:{ all -> 0x005b }
            com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher.dispatch(r9, r6, r4)     // Catch:{ all -> 0x005b }
            if (r2 == 0) goto L_0x0007
            r2.close()     // Catch:{ IOException -> 0x0056 }
            goto L_0x0007
        L_0x0056:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0007
        L_0x005b:
            r4 = move-exception
        L_0x005c:
            if (r2 == 0) goto L_0x0061
            r2.close()     // Catch:{ IOException -> 0x0062 }
        L_0x0061:
            throw r4
        L_0x0062:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0061
        L_0x0067:
            com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode r4 = com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode.WRONG_PARAMETER
            com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher.dispatch(r9, r6, r4)
            goto L_0x0007
        L_0x006d:
            r4 = move-exception
            r2 = r3
            goto L_0x005c
        L_0x0070:
            r0 = move-exception
            r2 = r3
            goto L_0x0048
        L_0x0073:
            r0 = move-exception
            r2 = r3
            goto L_0x0034
        */
        throw new UnsupportedOperationException("Method not decompiled: com.philips.lighting.hue.sdk.wrapper.domain.BridgeImpl.updateFirmware(java.io.File, com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType, com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback):void");
    }

    public void updateFirmware(File firmware, BridgeResponseCallback callback) {
        updateFirmware(firmware, BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void updateFirmware(File firmware) {
        updateFirmware(firmware, BridgeConnectionType.LOCAL_REMOTE, (BridgeResponseCallback) null);
    }

    public void updateFirmware(byte[] firmware, BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback) {
        if (firmware == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NULL_PARAMETER);
        } else if (allowedConnectionType == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NULL_PARAMETER);
        } else {
            updateFirmwareWithFileNative(firmware, callback);
        }
    }

    public void updateFirmware(byte[] firmware, BridgeResponseCallback callback) {
        updateFirmware(firmware, BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void updateFirmware(byte[] firmware) {
        updateFirmware(firmware, BridgeConnectionType.LOCAL_REMOTE, (BridgeResponseCallback) null);
    }

    public void destroy() {
        for (BridgeConnection connection : getBridgeConnections()) {
            connection.setBridgeConnectionCallback(null);
        }
        destroyNative();
    }

    public void startBackup(BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback) {
        if (allowedConnectionType == null) {
            allowedConnectionType = BridgeConnectionType.LOCAL_REMOTE;
        }
        startBackupNative(allowedConnectionType.getValue(), callback);
    }

    public void startBackup(BridgeResponseCallback callback) {
        startBackup(null, callback);
    }

    public void startBackup(BridgeConnectionType allowedConnectionType) {
        startBackup(allowedConnectionType, null);
    }

    public void startBackup() {
        startBackup(null, null);
    }

    public void backup(BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback) {
        if (allowedConnectionType == null) {
            BridgeConnectionType allowedConnectionType2 = BridgeConnectionType.LOCAL_REMOTE;
        } else {
            backupNative(allowedConnectionType.getValue(), callback);
        }
    }

    public void backup(BridgeResponseCallback callback) {
        backup(null, callback);
    }

    public void backup(BridgeConnectionType allowedConnectionType) {
        backup(allowedConnectionType, null);
    }

    public void backup() {
        backup(null, null);
    }

    public void restore(String bridgeId, BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback) {
        if (bridgeId == null) {
            BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NULL_PARAMETER);
        } else if (allowedConnectionType == null) {
            BridgeConnectionType allowedConnectionType2 = BridgeConnectionType.LOCAL_REMOTE;
        } else {
            restoreNative(NativeTools.StringToBytes(bridgeId), allowedConnectionType.getValue(), callback);
        }
    }

    public void restore(String bridgeId, BridgeResponseCallback callback) {
        restore(bridgeId, null, callback);
    }

    public void restore(String bridgeId, BridgeConnectionType allowedConnectionType) {
        restore(bridgeId, allowedConnectionType, null);
    }

    public void restore(String bridgeId) {
        restore(bridgeId, null, null);
    }

    public void setIdentifier(String identifier) {
        HueLog.e("Bridge", "Set identifier is not supported");
    }

    public int hashCode() {
        return (((super.hashCode() * 31) + Arrays.hashCode(this.name)) * 31) + (this.state == null ? 0 : this.state.hashCode());
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
        BridgeImpl other = (BridgeImpl) obj;
        if (!Arrays.equals(this.name, other.name)) {
            return false;
        }
        if (this.state == null) {
            if (other.state != null) {
                return false;
            }
            return true;
        } else if (!this.state.equals(other.state)) {
            return false;
        } else {
            return true;
        }
    }

    public void setBridge(Bridge bridge) {
    }

    public void fetch(BridgeResponseCallback callback) {
        BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NOT_IMPLEMENTED);
    }

    public void fetch(BridgeConnectionType allowedConnectionType, BridgeResponseCallback callback) {
        BridgeResponseDispatcher.dispatch(callback, this, ReturnCode.NOT_IMPLEMENTED);
    }

    public DeviceSoftwareUpdate getDeviceSoftwareUpdate() {
        if (getBridgeConfiguration() == null || getBridgeConfiguration().getSystemSoftwareUpdate() == null) {
            return null;
        }
        return getBridgeConfiguration().getSystemSoftwareUpdate().getBridgeSoftwareUpdate();
    }
}
