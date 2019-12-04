package com.lab714.dreamscreenv2.devices;

import android.util.Log;
import java.io.UnsupportedEncodingException;

public class DreamScreen4K extends DreamScreen {
    private static final byte[] required4KEspFirmwareVersion = {1, 6};
    private static final byte[] required4KPicVersionNumber = {5, 6};
    private static final String tag = "DreamScreen4K";

    public DreamScreen4K(String ipAddress, String broadcastIpString) {
        super(ipAddress, broadcastIpString);
        this.productId = 2;
        this.name = "DreamScreen 4K";
    }

    public void setAsDemo() {
        this.isDemo = true;
        this.espFirmwareVersion = (byte[]) required4KEspFirmwareVersion.clone();
        this.picVersionNumber = (byte[]) required4KPicVersionNumber.clone();
        this.hdmiActiveChannels = -1;
        try {
            this.hdmiInputName1 = "DirecTV".getBytes("UTF-8");
            this.hdmiInputName2 = "Xbox One".getBytes("UTF-8");
            this.hdmiInputName3 = "Chrome cast".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
    }

    public boolean espFirmwareUpdateNeeded() {
        if (this.espFirmwareVersion[0] == 0 && this.espFirmwareVersion[1] == 0) {
            return false;
        }
        if (required4KEspFirmwareVersion[0] > this.espFirmwareVersion[0]) {
            return true;
        }
        if (required4KEspFirmwareVersion[0] != this.espFirmwareVersion[0] || required4KEspFirmwareVersion[1] <= this.espFirmwareVersion[1]) {
            return false;
        }
        return true;
    }

    public boolean picFirmwareUpdateNeeded() {
        Log.i(tag, this.picVersionNumber[0] + "." + this.picVersionNumber[1] + " compared to required, " + required4KPicVersionNumber[0] + "." + required4KPicVersionNumber[1]);
        if (this.picVersionNumber[0] == 0 && this.picVersionNumber[1] == 0) {
            return false;
        }
        if (required4KPicVersionNumber[0] > this.picVersionNumber[0]) {
            return true;
        }
        if (required4KPicVersionNumber[0] != this.picVersionNumber[0] || required4KPicVersionNumber[1] <= this.picVersionNumber[1]) {
            return false;
        }
        return true;
    }

    public void readDiagnostics() {
        Log.i(tag, "readDiagnostics");
        sendUDPUnicastRead(2, 3);
    }
}
