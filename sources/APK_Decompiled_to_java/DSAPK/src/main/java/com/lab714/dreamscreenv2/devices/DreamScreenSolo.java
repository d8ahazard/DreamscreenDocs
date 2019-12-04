package com.lab714.dreamscreenv2.devices;

import android.util.Log;
import java.io.UnsupportedEncodingException;

public class DreamScreenSolo extends DreamScreen {
    private static final byte[] requiredSoloEspFirmwareVersion = {1, 6};
    private static final byte[] requiredSoloPicVersionNumber = {6, 2};
    private static final String tag = "DreamScreenSolo";

    public DreamScreenSolo(String ipAddress, String broadcastIpString) {
        super(ipAddress, broadcastIpString);
        this.productId = 7;
        this.name = "DreamScreen Solo";
    }

    public void setAsDemo() {
        this.isDemo = true;
        this.espFirmwareVersion = (byte[]) requiredSoloEspFirmwareVersion.clone();
        this.picVersionNumber = (byte[]) requiredSoloPicVersionNumber.clone();
        this.hdmiActiveChannels = 0;
        try {
            this.hdmiInputName1 = "".getBytes("UTF-8");
            this.hdmiInputName2 = "".getBytes("UTF-8");
            this.hdmiInputName3 = "".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
    }

    public boolean espFirmwareUpdateNeeded() {
        if (this.espFirmwareVersion[0] == 0 && this.espFirmwareVersion[1] == 0) {
            return false;
        }
        if (requiredSoloEspFirmwareVersion[0] > this.espFirmwareVersion[0]) {
            return true;
        }
        if (requiredSoloEspFirmwareVersion[0] != this.espFirmwareVersion[0] || requiredSoloEspFirmwareVersion[1] <= this.espFirmwareVersion[1]) {
            return false;
        }
        return true;
    }

    public boolean picFirmwareUpdateNeeded() {
        Log.i(tag, this.picVersionNumber[0] + "." + this.picVersionNumber[1] + " compared to required, " + requiredSoloPicVersionNumber[0] + "." + requiredSoloPicVersionNumber[1]);
        if (this.picVersionNumber[0] == 0 && this.picVersionNumber[1] == 0) {
            return false;
        }
        if (requiredSoloPicVersionNumber[0] > this.picVersionNumber[0]) {
            return true;
        }
        if (requiredSoloPicVersionNumber[0] != this.picVersionNumber[0] || requiredSoloPicVersionNumber[1] <= this.picVersionNumber[1]) {
            return false;
        }
        return true;
    }

    public void readDiagnostics() {
        Log.i(tag, "readDiagnostics");
        sendUDPUnicastRead(2, 3);
    }
}
