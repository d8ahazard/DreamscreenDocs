package com.lab714.dreamscreenv2.devices;

import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class SideKick extends Light {
    private static final byte[] requiredEspFirmwareVersion = {3, 1};
    private static final String tag = "SideKick";
    private byte[] espFirmwareVersion;
    private boolean isDemo;
    private byte[] sectorAssignment;
    private byte[] sectorData;

    public SideKick(String ipAddress, String broadcastIpString) {
        super(ipAddress, broadcastIpString);
        this.espFirmwareVersion = new byte[]{0, 0};
        this.sectorData = new byte[]{0};
        this.sectorAssignment = new byte[0];
        this.isDemo = false;
        this.productId = 3;
        this.name = tag;
    }

    public void setAsDemo() {
        this.isDemo = true;
        this.espFirmwareVersion = (byte[]) requiredEspFirmwareVersion.clone();
    }

    public boolean isDemo() {
        return this.isDemo;
    }

    public boolean espFirmwareUpdateNeeded() {
        if (this.espFirmwareVersion[0] == 0 && this.espFirmwareVersion[1] == 0) {
            return false;
        }
        if (requiredEspFirmwareVersion[0] > this.espFirmwareVersion[0]) {
            return true;
        }
        if (requiredEspFirmwareVersion[0] != this.espFirmwareVersion[0] || requiredEspFirmwareVersion[1] <= this.espFirmwareVersion[1]) {
            return false;
        }
        return true;
    }

    public byte[] getEspFirmwareVersion() {
        return this.espFirmwareVersion;
    }

    public void initEspFirmwareVersion(byte[] espFirmwareVersion2) {
        this.espFirmwareVersion = espFirmwareVersion2;
    }

    public byte[] getSectorData() {
        return this.sectorData;
    }

    public void setSectorData(byte[] sectorData2, boolean broadcastingToGroup) {
        this.sectorData = sectorData2;
        Log.i(tag, "setSectorData ");
        sendUDPWrite(3, 22, sectorData2, broadcastingToGroup);
    }

    public void initSectorData(byte[] sectorData2) {
        this.sectorData = sectorData2;
    }

    public byte[] getSectorAssignment() {
        return this.sectorAssignment;
    }

    public void setSectorAssignment(byte[] sectorAssignment2, boolean broadcastingToGroup) {
        this.sectorAssignment = sectorAssignment2;
        Log.i(tag, "setSectorAssignment ");
        sendUDPWrite(3, 23, sectorAssignment2, broadcastingToGroup);
    }

    public void initSectorAssignment(byte[] sectorAssignment2) {
        this.sectorAssignment = sectorAssignment2;
    }

    public void parsePayload(byte[] payload) {
        Log.i(tag, "parsePayload");
        try {
            String name = new String(Arrays.copyOfRange(payload, 0, 16), "UTF-8");
            if (name.isEmpty()) {
                name = tag;
            }
            this.name = name;
            String groupName = new String(Arrays.copyOfRange(payload, 16, 32), "UTF-8");
            if (groupName.isEmpty()) {
                groupName = "unassigned";
            }
            this.groupName = groupName;
        } catch (UnsupportedEncodingException e) {
        }
        this.groupNumber = payload[32] & 255;
        this.mode = payload[33] & 255;
        this.brightness = payload[34] & 255;
        this.ambientColor = Arrays.copyOfRange(payload, 35, 38);
        this.saturation = Arrays.copyOfRange(payload, 38, 41);
        this.fadeRate = payload[41] & 255;
        this.sectorAssignment = Arrays.copyOfRange(payload, 42, 57);
        this.espFirmwareVersion = Arrays.copyOfRange(payload, 57, 59);
        if (payload.length == 62) {
            this.ambientModeType = payload[59] & 255;
            this.ambientShowType = payload[60] & 255;
        }
    }
}
