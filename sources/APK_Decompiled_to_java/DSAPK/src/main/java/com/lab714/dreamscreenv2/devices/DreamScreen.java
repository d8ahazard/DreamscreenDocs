package com.lab714.dreamscreenv2.devices;

import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class DreamScreen extends Light {
    private static final String bootloaderKey = "Ka";
    private static final byte[] requiredEspFirmwareVersion = {1, 6};
    private static final byte[] requiredPicVersionNumber = {1, 7};
    private static final String resetKey = "sA";
    private static final String tag = "DreamScreen";
    private byte[] appMusicData;
    private int bootState;
    private int cecPassthroughEnable;
    private int cecPowerEnable;
    private int cecSwitchingEnable;
    private int colorBoost;
    byte[] espFirmwareVersion;
    private byte[] flexSetup;
    byte hdmiActiveChannels;
    private int hdmiInput;
    byte[] hdmiInputName1;
    byte[] hdmiInputName2;
    byte[] hdmiInputName3;
    private int hdrToneRemapping;
    private int hpdEnable;
    private int indicatorLightAutoOff;
    boolean isDemo;
    private int letterboxingEnable;
    private byte[] minimumLuminosity;
    private byte[] musicModeColors;
    private int musicModeSource;
    private int musicModeType;
    private byte[] musicModeWeights;
    byte[] picVersionNumber;
    private int pillarboxingEnable;
    private int sectorBroadcastControl;
    private int sectorBroadcastTiming;
    private int skuSetup;
    private int usbPowerEnable;
    private int videoFrameDelay;
    private byte zones;
    private byte[] zonesBrightness;

    public DreamScreen(String ipAddress, String broadcastIpString) {
        super(ipAddress, broadcastIpString);
        this.espFirmwareVersion = new byte[]{0, 0};
        this.picVersionNumber = new byte[]{0, 0};
        this.zones = 15;
        this.zonesBrightness = new byte[]{-1, -1, -1, -1};
        this.musicModeType = 0;
        this.musicModeColors = new byte[]{2, 1, 0};
        this.musicModeWeights = new byte[]{100, 100, 100};
        this.minimumLuminosity = new byte[]{0, 0, 0};
        this.indicatorLightAutoOff = 1;
        this.usbPowerEnable = 0;
        this.sectorBroadcastControl = 0;
        this.sectorBroadcastTiming = 1;
        this.hdmiInput = 0;
        this.musicModeSource = 0;
        this.appMusicData = new byte[]{0, 0, 0};
        this.cecPassthroughEnable = 1;
        this.cecSwitchingEnable = 1;
        this.hpdEnable = 1;
        this.videoFrameDelay = 0;
        this.letterboxingEnable = 0;
        this.pillarboxingEnable = 0;
        this.hdmiActiveChannels = 0;
        this.colorBoost = 0;
        this.cecPowerEnable = 0;
        this.flexSetup = new byte[]{8, 16, 48, 0, 7, 0};
        this.skuSetup = 0;
        this.hdrToneRemapping = 0;
        this.bootState = 0;
        this.isDemo = false;
        this.productId = 1;
        this.name = "DreamScreen HD";
        try {
            this.hdmiInputName1 = "HDMI 1".getBytes("UTF-8");
            this.hdmiInputName2 = "HDMI 2".getBytes("UTF-8");
            this.hdmiInputName3 = "HDMI 3".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
    }

    public void setAsDemo() {
        this.isDemo = true;
        this.espFirmwareVersion = (byte[]) requiredEspFirmwareVersion.clone();
        this.picVersionNumber = (byte[]) requiredPicVersionNumber.clone();
        this.hdmiActiveChannels = -1;
        try {
            this.hdmiInputName1 = "DirecTV".getBytes("UTF-8");
            this.hdmiInputName2 = "Xbox One".getBytes("UTF-8");
            this.hdmiInputName3 = "Chrome cast".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
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

    public boolean picFirmwareUpdateNeeded() {
        Log.i(tag, this.picVersionNumber[0] + "." + this.picVersionNumber[1] + " compared to required, " + requiredPicVersionNumber[0] + "." + requiredPicVersionNumber[1]);
        if (this.picVersionNumber[0] == 0 && this.picVersionNumber[1] == 0) {
            return false;
        }
        if (requiredPicVersionNumber[0] > this.picVersionNumber[0]) {
            return true;
        }
        if (requiredPicVersionNumber[0] != this.picVersionNumber[0] || requiredPicVersionNumber[1] <= this.picVersionNumber[1]) {
            return false;
        }
        return true;
    }

    public boolean picFirmwareValid() {
        if (this.picVersionNumber[0] == 0 && this.picVersionNumber[1] == 0) {
            return false;
        }
        return true;
    }

    public byte[] getPicVersionNumber() {
        return this.picVersionNumber;
    }

    public void initPicVersionNumber(byte[] picVersionNumber2) {
        Log.i(tag, "initPicVersionNumber " + this.picVersionNumber[0] + "." + this.picVersionNumber[1] + " to " + picVersionNumber2[0] + "." + picVersionNumber2[1]);
        this.picVersionNumber = picVersionNumber2;
    }

    public void enterBootloaderFlags() {
        try {
            Log.i(tag, "enterBootloaderFlags");
            sendUDPWrite(4, 1, bootloaderKey.getBytes("UTF-8"), false);
        } catch (UnsupportedEncodingException e) {
        }
    }

    public void clearBootloaderFlags() {
        Log.i(tag, "clearBootloaderFlags");
        sendUDPWrite(4, 1, new byte[]{0, 0}, false);
    }

    public void readBootloaderFlags() {
        Log.i(tag, "readBootloaderFlags");
        sendUDPUnicastRead(4, 1);
    }

    public boolean areFlagsInBootloaderMode(byte[] bootloaderKey2) {
        try {
            byte[] bootloaderKeyArray = bootloaderKey.getBytes("UTF-8");
            if (bootloaderKeyArray[0] == bootloaderKey2[0] && bootloaderKeyArray[1] == bootloaderKey2[1]) {
                return true;
            }
            return false;
        } catch (UnsupportedEncodingException e) {
            Log.i(tag, "areFlagsInBootloaderMode exception, returning false");
            return false;
        }
    }

    public void resetPic() {
        try {
            Log.i(tag, "resetPic");
            sendUDPWrite(4, 2, resetKey.getBytes("UTF-8"), false);
        } catch (UnsupportedEncodingException e) {
        }
    }

    public void readBootloaderMode() {
        Log.i(tag, "readBootloaderMode");
        sendUDPUnicastRead(1, 21);
    }

    public void readPicVersionNumber() {
        Log.i(tag, "readPicVersionNumber");
        sendUDPUnicastRead(2, 2);
    }

    public byte getZones() {
        return this.zones;
    }

    public void setZones(byte zones2, boolean broadcastingToGroup) {
        this.zones = zones2;
        Log.i(tag, "setZones " + zones2);
        sendUDPWrite(3, 3, new byte[]{zones2}, broadcastingToGroup);
    }

    public void initZones(byte zones2) {
        this.zones = zones2;
    }

    public byte[] getZonesBrightness() {
        return this.zonesBrightness;
    }

    public void setZonesBrightness(byte[] zonesBrightness2, boolean broadcastingToGroup) {
        this.zonesBrightness = zonesBrightness2;
        Log.i(tag, "setZonesBrightness " + zonesBrightness2[0] + zonesBrightness2[1] + zonesBrightness2[2] + zonesBrightness2[3]);
        sendUDPWrite(3, 4, zonesBrightness2, broadcastingToGroup);
    }

    public void initZonesBrightness(byte[] zonesBrightness2) {
        this.zonesBrightness = zonesBrightness2;
    }

    public int getMusicModeType() {
        return this.musicModeType;
    }

    public void setMusicModeType(int musicModeType2, boolean broadcastingToGroup) {
        this.musicModeType = musicModeType2;
        Log.i(tag, "setMusicModeType " + musicModeType2);
        sendUDPWrite(3, 9, new byte[]{(byte) musicModeType2}, broadcastingToGroup);
    }

    public void initMusicModeType(int musicModeType2) {
        this.musicModeType = musicModeType2;
    }

    public int getMusicModeSource() {
        return this.musicModeSource;
    }

    public void setMusicModeSource(int musicModeSource2, boolean broadcastingToGroup) {
        this.musicModeSource = musicModeSource2;
        Log.i(tag, "setMusicModeSource " + musicModeSource2);
        sendUDPWrite(3, 33, new byte[]{(byte) musicModeSource2}, broadcastingToGroup);
    }

    public boolean initMusicModeSource(int musicModeSource2) {
        if (this.musicModeSource == musicModeSource2) {
            return false;
        }
        this.musicModeSource = musicModeSource2;
        return true;
    }

    public byte[] getMusicModeColors() {
        return this.musicModeColors;
    }

    public void setMusicModeColors(byte[] musicModeColors2, boolean broadcastingToGroup) {
        this.musicModeColors = musicModeColors2;
        Log.i(tag, "setMusicModeColors ");
        sendUDPWrite(3, 10, musicModeColors2, broadcastingToGroup);
    }

    public void initMusicModeColors(byte[] musicModeColors2) {
        this.musicModeColors = musicModeColors2;
    }

    public byte[] getMusicModeWeights() {
        return this.musicModeWeights;
    }

    public void setMusicModeWeights(byte[] musicModeWeights2, boolean broadcastingToGroup) {
        this.musicModeWeights = musicModeWeights2;
        Log.i(tag, "setMusicModeWeights ");
        sendUDPWrite(3, 11, musicModeWeights2, broadcastingToGroup);
    }

    public void initMusicModeWeights(byte[] musicModeWeights2) {
        this.musicModeWeights = musicModeWeights2;
    }

    public byte[] getMinimumLuminosity() {
        return this.minimumLuminosity;
    }

    public void setMinimumLuminosity(byte[] minimumLuminosity2, boolean broadcastingToGroup) {
        this.minimumLuminosity = minimumLuminosity2;
        Log.i("Light", "setMinimumLuminosity " + minimumLuminosity2[0]);
        sendUDPWrite(3, 12, minimumLuminosity2, broadcastingToGroup);
    }

    public void initMinimumLuminosity(byte[] minimumLuminosity2) {
        this.minimumLuminosity = minimumLuminosity2;
    }

    public int getIndicatorLightAutoOff() {
        return this.indicatorLightAutoOff;
    }

    public void setIndicatorLightAutoOff(int indicatorLightAutoOff2, boolean broadcastingToGroup) {
        this.indicatorLightAutoOff = indicatorLightAutoOff2;
        Log.i(tag, "setIndicatorLightAutoOff " + indicatorLightAutoOff2);
        sendUDPWrite(3, 19, new byte[]{(byte) indicatorLightAutoOff2}, broadcastingToGroup);
    }

    public void initIndicatorLightAutoOff(int indicatorLightAutoOff2) {
        this.indicatorLightAutoOff = indicatorLightAutoOff2;
    }

    public int getUsbPowerEnable() {
        return this.usbPowerEnable;
    }

    public void setUsbPowerEnable(int usbPowerEnable2, boolean broadcastingToGroup) {
        this.usbPowerEnable = usbPowerEnable2;
        Log.i(tag, "setUsbPowerEnable " + usbPowerEnable2);
        sendUDPWrite(3, 20, new byte[]{(byte) usbPowerEnable2}, broadcastingToGroup);
    }

    public void initUsbPowerEnable(int usbPowerEnable2) {
        this.usbPowerEnable = usbPowerEnable2;
    }

    public int getSectorBroadcastControl() {
        return this.sectorBroadcastControl;
    }

    public void setSectorBroadcastControl(int sectorBroadcastControl2, boolean broadcastingToGroup) {
        this.sectorBroadcastControl = sectorBroadcastControl2;
        Log.i(tag, "setSectorBroadcastControl " + sectorBroadcastControl2);
        sendUDPWrite(3, 24, new byte[]{(byte) sectorBroadcastControl2}, broadcastingToGroup);
    }

    public void initSectorBroadcastControl(int sectorBroadcastControl2) {
        this.sectorBroadcastControl = sectorBroadcastControl2;
    }

    public int getSectorBroadcastTiming() {
        return this.sectorBroadcastTiming;
    }

    public void setsectorBroadcastTiming(int sectorBroadcastTiming2, boolean broadcastingToGroup) {
        this.sectorBroadcastTiming = sectorBroadcastTiming2;
        Log.i(tag, "setSectorBroadcastControl " + sectorBroadcastTiming2);
        sendUDPWrite(3, 25, new byte[]{(byte) sectorBroadcastTiming2}, broadcastingToGroup);
    }

    public void initsectorBroadcastTiming(int sectorBroadcastTiming2) {
        this.sectorBroadcastTiming = sectorBroadcastTiming2;
    }

    public int getHdmiInput() {
        return this.hdmiInput;
    }

    public void setHdmiInput(int hdmiInput2, boolean broadcastingToGroup) {
        this.hdmiInput = hdmiInput2;
        Log.i(tag, "setHdmiInput " + hdmiInput2);
        sendUDPWrite(3, 32, new byte[]{(byte) hdmiInput2}, broadcastingToGroup);
    }

    public boolean initHdmiInput(int hdmiInput2) {
        Log.i(tag, "initHdmiInput " + hdmiInput2);
        if (this.hdmiInput == hdmiInput2) {
            return false;
        }
        this.hdmiInput = hdmiInput2;
        return true;
    }

    public void readHdmiInput() {
        Log.i(tag, "readHdmiInput");
        sendUDPUnicastRead(3, 32);
    }

    public byte[] getAppMusicData() {
        return this.appMusicData;
    }

    public void setAppMusicData(byte[] appMusicData2, boolean broadcastingToGroup) {
        this.appMusicData = appMusicData2;
        Log.i(tag, "setAppMusicData");
        sendUDPWrite(3, 33, appMusicData2, broadcastingToGroup);
    }

    public void initAppMusicData(byte[] appMusicData2) {
        this.appMusicData = appMusicData2;
    }

    public byte[] getHdmiInputName1() {
        return this.hdmiInputName1;
    }

    public void setHdmiInputName1(byte[] hdmiInputName12, boolean broadcastingToGroup) {
        this.hdmiInputName1 = hdmiInputName12;
        Log.i(tag, "setHdmiInputName1 ");
        sendUDPWrite(3, 35, hdmiInputName12, broadcastingToGroup);
    }

    public boolean initHdmiInputName1(byte[] hdmiInputName12) {
        if (this.hdmiInputName1 == hdmiInputName12) {
            return false;
        }
        this.hdmiInputName1 = hdmiInputName12;
        return true;
    }

    public byte[] getHdmiInputName2() {
        return this.hdmiInputName2;
    }

    public void setHdmiInputName2(byte[] hdmiInputName22, boolean broadcastingToGroup) {
        this.hdmiInputName2 = hdmiInputName22;
        Log.i(tag, "setHdmiInputName2 ");
        sendUDPWrite(3, 36, hdmiInputName22, broadcastingToGroup);
    }

    public boolean initHdmiInputName2(byte[] hdmiInputName22) {
        if (this.hdmiInputName2 == hdmiInputName22) {
            return false;
        }
        this.hdmiInputName2 = hdmiInputName22;
        return true;
    }

    public byte[] getHdmiInputName3() {
        return this.hdmiInputName3;
    }

    public void setHdmiInputName3(byte[] hdmiInputName32, boolean broadcastingToGroup) {
        this.hdmiInputName3 = hdmiInputName32;
        Log.i(tag, "setHdmiInputName3 ");
        sendUDPWrite(3, 37, hdmiInputName32, broadcastingToGroup);
    }

    public boolean initHdmiInputName3(byte[] hdmiInputName32) {
        if (this.hdmiInputName3 == hdmiInputName32) {
            return false;
        }
        this.hdmiInputName3 = hdmiInputName32;
        return true;
    }

    public int getCecPassthroughEnable() {
        return this.cecPassthroughEnable;
    }

    public void setCecPassthroughEnable(int cecPassthroughEnable2, boolean broadcastingToGroup) {
        this.cecPassthroughEnable = cecPassthroughEnable2;
        Log.i(tag, "setCecPassthroughEnable " + cecPassthroughEnable2);
        sendUDPWrite(3, 38, new byte[]{(byte) cecPassthroughEnable2}, broadcastingToGroup);
    }

    public void initCecPassthroughEnable(int cecPassthroughEnable2) {
        this.cecPassthroughEnable = cecPassthroughEnable2;
    }

    public int getCecSwitchingEnable() {
        return this.cecSwitchingEnable;
    }

    public void setCecSwitchingEnable(int cecSwitchingEnable2, boolean broadcastingToGroup) {
        this.cecSwitchingEnable = cecSwitchingEnable2;
        Log.i(tag, "setCecSwitchingEnable " + cecSwitchingEnable2);
        sendUDPWrite(3, 39, new byte[]{(byte) cecSwitchingEnable2}, broadcastingToGroup);
    }

    public void initCecSwitchingEnable(int cecSwitchingEnable2) {
        this.cecSwitchingEnable = cecSwitchingEnable2;
    }

    public int getHpdEnable() {
        return this.hpdEnable;
    }

    public void setHpdEnable(int hpdEnable2, boolean broadcastingToGroup) {
        this.hpdEnable = hpdEnable2;
        Log.i(tag, "setHpdEnable " + hpdEnable2);
        sendUDPWrite(3, 40, new byte[]{(byte) hpdEnable2}, broadcastingToGroup);
    }

    public void initHpdEnable(int hpdEnable2) {
        this.hpdEnable = hpdEnable2;
    }

    public int getVideoFrameDelay() {
        return this.videoFrameDelay;
    }

    public void setVideoFrameDelay(int videoFrameDelay2, boolean broadcastingToGroup) {
        this.videoFrameDelay = videoFrameDelay2;
        Log.i(tag, "setVideoFrameDelay " + videoFrameDelay2);
        sendUDPWrite(3, 42, new byte[]{(byte) videoFrameDelay2}, broadcastingToGroup);
    }

    public void initVideoFrameDelay(int videoFrameDelay2) {
        this.videoFrameDelay = videoFrameDelay2;
    }

    public int getLetterboxingEnable() {
        return this.letterboxingEnable;
    }

    public void setLetterboxingEnable(int letterboxingEnable2, boolean broadcastingToGroup) {
        this.letterboxingEnable = letterboxingEnable2;
        Log.i(tag, "setLetterboxingEnable " + letterboxingEnable2);
        sendUDPWrite(3, 43, new byte[]{(byte) letterboxingEnable2}, broadcastingToGroup);
    }

    public void initLetterboxingEnable(int letterboxingEnable2) {
        this.letterboxingEnable = letterboxingEnable2;
    }

    public byte getHdmiActiveChannels() {
        return this.hdmiActiveChannels;
    }

    public boolean initHdmiActiveChannels(byte hdmiActiveChannels2) {
        Log.i(tag, "initHdmiActiveChannels " + (hdmiActiveChannels2 & 255));
        if (this.hdmiActiveChannels == hdmiActiveChannels2) {
            return false;
        }
        this.hdmiActiveChannels = hdmiActiveChannels2;
        return true;
    }

    public void readHdmiActiveChannels() {
        Log.i(tag, "readHdmiActiveChannels");
        sendUDPUnicastRead(3, 44);
    }

    public int getColorBoost() {
        return this.colorBoost;
    }

    public void setColorBoost(int colorBoost2, boolean broadcastingToGroup) {
        this.colorBoost = colorBoost2;
        Log.i(tag, "setColorBoost " + colorBoost2);
        sendUDPWrite(3, 45, new byte[]{(byte) colorBoost2}, broadcastingToGroup);
    }

    public void initColorBoost(int colorBoost2) {
        this.colorBoost = colorBoost2;
    }

    public int getCecPowerEnable() {
        return this.cecPowerEnable;
    }

    public void setCecPowerEnable(int cecPowerEnable2, boolean broadcastingToGroup) {
        this.cecPowerEnable = cecPowerEnable2;
        Log.i(tag, "setCecPowerEnable " + cecPowerEnable2);
        sendUDPWrite(3, 46, new byte[]{(byte) cecPowerEnable2}, broadcastingToGroup);
    }

    public void initCecPowerEnable(int cecPowerEnable2) {
        this.cecPowerEnable = cecPowerEnable2;
    }

    public int getPillarboxingEnable() {
        return this.pillarboxingEnable;
    }

    public void setPillarboxingEnable(int pillarboxingEnable2, boolean broadcastingToGroup) {
        this.pillarboxingEnable = pillarboxingEnable2;
        Log.i(tag, "setPillarboxingEnable " + pillarboxingEnable2);
        sendUDPWrite(3, 47, new byte[]{(byte) pillarboxingEnable2}, broadcastingToGroup);
    }

    public void initPillarboxingEnable(int pillarboxingEnable2) {
        this.pillarboxingEnable = pillarboxingEnable2;
    }

    public int getSkuSetup() {
        return this.skuSetup;
    }

    public void setSkuSetup(int skuSetup2, boolean broadcastingToGroup) {
        this.skuSetup = skuSetup2;
        Log.i(tag, "setSkuSetup " + skuSetup2);
        sendUDPWrite(3, 64, new byte[]{(byte) skuSetup2}, broadcastingToGroup);
    }

    public void initSkuSetup(int skuSetup2) {
        this.skuSetup = skuSetup2;
    }

    public byte[] getFlexSetup() {
        return this.flexSetup;
    }

    public void setFlexSetup(byte[] flexSetup2, boolean broadcastingToGroup) {
        this.flexSetup = flexSetup2;
        Log.i(tag, "setFlexSetup ");
        sendUDPWrite(3, 65, flexSetup2, broadcastingToGroup);
    }

    public void initFlexSetup(byte[] flexSetup2) {
        this.flexSetup = flexSetup2;
    }

    public int getHdrToneRemapping() {
        return this.hdrToneRemapping;
    }

    public void setHdrToneRemapping(int hdrToneRemapping2, boolean broadcastingToGroup) {
        this.hdrToneRemapping = hdrToneRemapping2;
        Log.i(tag, "setHdrToneRemapping " + hdrToneRemapping2);
        sendUDPWrite(3, 96, new byte[]{(byte) hdrToneRemapping2}, broadcastingToGroup);
    }

    public void initHdrToneRemapping(int hdrToneRemapping2) {
        this.hdrToneRemapping = hdrToneRemapping2;
    }

    public int parsePayload(byte[] payload) {
        Log.i(tag, "parsePayload");
        try {
            String name1 = new String(Arrays.copyOfRange(payload, 0, 16), "UTF-8");
            if (name1.isEmpty()) {
                name1 = tag;
            }
            this.name = name1;
            String groupName1 = new String(Arrays.copyOfRange(payload, 16, 32), "UTF-8");
            if (groupName1.isEmpty()) {
                groupName1 = "Group";
            }
            this.groupName = groupName1;
        } catch (UnsupportedEncodingException e) {
        }
        this.groupNumber = payload[32] & 255;
        this.mode = payload[33] & 255;
        this.brightness = payload[34] & 255;
        this.zones = payload[35];
        this.zonesBrightness = Arrays.copyOfRange(payload, 36, 40);
        this.ambientColor = Arrays.copyOfRange(payload, 40, 43);
        this.saturation = Arrays.copyOfRange(payload, 43, 46);
        this.flexSetup = Arrays.copyOfRange(payload, 46, 52);
        this.musicModeType = payload[52] & 255;
        this.musicModeColors = Arrays.copyOfRange(payload, 53, 56);
        this.musicModeWeights = Arrays.copyOfRange(payload, 56, 59);
        this.minimumLuminosity = Arrays.copyOfRange(payload, 59, 62);
        this.ambientShowType = payload[62] & 255;
        this.fadeRate = payload[63] & 255;
        this.indicatorLightAutoOff = payload[69] & 255;
        this.usbPowerEnable = payload[70] & 255;
        this.sectorBroadcastControl = payload[71] & 255;
        this.sectorBroadcastTiming = payload[72] & 255;
        this.hdmiInput = payload[73] & 255;
        this.musicModeSource = payload[74] & 255;
        this.hdmiInputName1 = Arrays.copyOfRange(payload, 75, 91);
        this.hdmiInputName2 = Arrays.copyOfRange(payload, 91, 107);
        this.hdmiInputName3 = Arrays.copyOfRange(payload, 107, 123);
        this.cecPassthroughEnable = payload[123] & 255;
        this.cecSwitchingEnable = payload[124] & 255;
        this.hpdEnable = payload[125] & 255;
        this.videoFrameDelay = payload[127] & 255;
        this.letterboxingEnable = payload[128] & 255;
        this.hdmiActiveChannels = payload[129];
        this.espFirmwareVersion = Arrays.copyOfRange(payload, 130, 132);
        this.picVersionNumber = Arrays.copyOfRange(payload, 132, 134);
        this.colorBoost = payload[134] & 255;
        if (payload.length >= 137) {
            this.cecPowerEnable = payload[135] & 255;
        }
        if (payload.length >= 138) {
            this.skuSetup = payload[136] & 255;
        }
        if (payload.length >= 139) {
            this.bootState = payload[137] & 255;
        }
        if (payload.length >= 140) {
            this.pillarboxingEnable = payload[138] & 255;
        }
        if (payload.length >= 141) {
            this.hdrToneRemapping = payload[139] & 255;
        }
        return this.bootState;
    }
}
