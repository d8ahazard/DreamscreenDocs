package com.philips.lighting.hue.sdk.wrapper.domain;

import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnection;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateUpdatedCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.FoundDevicesCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.QueueOptions;
import com.philips.lighting.hue.sdk.wrapper.connection.QueueType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.Device;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.BridgeResource;
import java.io.File;
import java.util.List;

public interface Bridge extends Device {
    ReturnCode addBridgeConnection(BridgeConnection bridgeConnection);

    @Deprecated
    void addBridgeStateUpdatedCallback(BridgeStateUpdatedCallback bridgeStateUpdatedCallback);

    void backup();

    void backup(BridgeConnectionType bridgeConnectionType);

    void backup(BridgeConnectionType bridgeConnectionType, BridgeResponseCallback bridgeResponseCallback);

    void backup(BridgeResponseCallback bridgeResponseCallback);

    ReturnCode connect();

    ReturnCode connect(BridgeConnectionType bridgeConnectionType);

    void createDevice(Device device);

    void createDevice(Device device, BridgeConnectionType bridgeConnectionType, BridgeResponseCallback bridgeResponseCallback);

    void createDevice(Device device, BridgeResponseCallback bridgeResponseCallback);

    void createResource(BridgeResource bridgeResource);

    void createResource(BridgeResource bridgeResource, BridgeConnectionType bridgeConnectionType, BridgeResponseCallback bridgeResponseCallback);

    void createResource(BridgeResource bridgeResource, BridgeResponseCallback bridgeResponseCallback);

    void deleteDevice(Device device);

    void deleteDevice(Device device, BridgeConnectionType bridgeConnectionType, BridgeResponseCallback bridgeResponseCallback);

    void deleteDevice(Device device, BridgeResponseCallback bridgeResponseCallback);

    void deleteResource(BridgeResource bridgeResource);

    void deleteResource(BridgeResource bridgeResource, BridgeConnectionType bridgeConnectionType, BridgeResponseCallback bridgeResponseCallback);

    void deleteResource(BridgeResource bridgeResource, BridgeResponseCallback bridgeResponseCallback);

    void destroy();

    void disconnect();

    void disconnect(BridgeConnectionType bridgeConnectionType);

    boolean equals(Object obj);

    ReturnCode extendFindNewDevices();

    ReturnCode extendFindNewDevices(List<String> list);

    void findNewDevices();

    void findNewDevices(BridgeConnectionType bridgeConnectionType, FoundDevicesCallback foundDevicesCallback);

    void findNewDevices(FoundDevicesCallback foundDevicesCallback);

    void findNewDevices(List<String> list);

    void findNewDevices(List<String> list, BridgeConnectionType bridgeConnectionType, FoundDevicesCallback foundDevicesCallback);

    void findNewDevices(List<String> list, FoundDevicesCallback foundDevicesCallback);

    BridgeConfiguration getBridgeConfiguration();

    BridgeConnection getBridgeConnection(BridgeConnectionType bridgeConnectionType);

    List<BridgeConnection> getBridgeConnections();

    BridgeState getBridgeState();

    String getName();

    int hashCode();

    boolean isConnected();

    void refreshUsername(BridgeResponseCallback bridgeResponseCallback);

    void removeBridgeConnection(BridgeConnection bridgeConnection);

    @Deprecated
    void removeBridgeStatedUpdatedCallback(BridgeStateUpdatedCallback bridgeStateUpdatedCallback);

    void restore(String str);

    void restore(String str, BridgeConnectionType bridgeConnectionType);

    void restore(String str, BridgeConnectionType bridgeConnectionType, BridgeResponseCallback bridgeResponseCallback);

    void restore(String str, BridgeResponseCallback bridgeResponseCallback);

    void setBridgeConnectionCallback(BridgeConnectionCallback bridgeConnectionCallback);

    void setName(String str);

    ReturnCode setQueueOptions(QueueOptions queueOptions);

    ReturnCode setQueueType(QueueType queueType);

    void startBackup();

    void startBackup(BridgeConnectionType bridgeConnectionType);

    void startBackup(BridgeConnectionType bridgeConnectionType, BridgeResponseCallback bridgeResponseCallback);

    void startBackup(BridgeResponseCallback bridgeResponseCallback);

    void syncNative();

    void updateFirmware();

    void updateFirmware(BridgeConnectionType bridgeConnectionType, BridgeResponseCallback bridgeResponseCallback);

    void updateFirmware(BridgeResponseCallback bridgeResponseCallback);

    void updateFirmware(File file);

    void updateFirmware(File file, BridgeConnectionType bridgeConnectionType, BridgeResponseCallback bridgeResponseCallback);

    void updateFirmware(File file, BridgeResponseCallback bridgeResponseCallback);

    void updateFirmware(byte[] bArr);

    void updateFirmware(byte[] bArr, BridgeConnectionType bridgeConnectionType, BridgeResponseCallback bridgeResponseCallback);

    void updateFirmware(byte[] bArr, BridgeResponseCallback bridgeResponseCallback);

    void updateResource(BridgeResource bridgeResource);

    void updateResource(BridgeResource bridgeResource, BridgeConnectionType bridgeConnectionType, BridgeResponseCallback bridgeResponseCallback);

    void updateResource(BridgeResource bridgeResource, BridgeConnectionType bridgeConnectionType, BridgeResponseCallback bridgeResponseCallback, boolean z);

    void updateResource(BridgeResource bridgeResource, BridgeResponseCallback bridgeResponseCallback);

    void updateResource(BridgeResource bridgeResource, BridgeResource bridgeResource2);

    void updateResource(BridgeResource bridgeResource, BridgeResource bridgeResource2, BridgeConnectionType bridgeConnectionType, BridgeResponseCallback bridgeResponseCallback);

    void updateResource(BridgeResource bridgeResource, BridgeResource bridgeResource2, BridgeConnectionType bridgeConnectionType, BridgeResponseCallback bridgeResponseCallback, boolean z);

    void updateResource(BridgeResource bridgeResource, BridgeResource bridgeResource2, BridgeResponseCallback bridgeResponseCallback);
}
