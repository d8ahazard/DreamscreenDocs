package com.lab714.dreamscreenv2.devices;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public abstract class Light {
    private static final int dreamScreenPort = 8888;
    private static final String resetEspKey = "er";
    private static final String tag = "Light";
    private static final byte[] uartComm_crc8_table = {0, 7, 14, 9, 28, 27, 18, 21, 56, 63, 54, 49, 36, 35, 42, 45, 112, 119, 126, 121, 108, 107, 98, 101, 72, 79, 70, 65, 84, 83, 90, 93, -32, -25, -18, -23, -4, -5, -14, -11, -40, -33, -42, -47, -60, -61, -54, -51, -112, -105, -98, -103, -116, -117, -126, -123, -88, -81, -90, -95, -76, -77, -70, -67, -57, -64, -55, -50, -37, -36, -43, -46, -1, -8, -15, -10, -29, -28, -19, -22, -73, -80, -71, -66, -85, -84, -91, -94, -113, -120, -127, -122, -109, -108, -99, -102, 39, 32, 41, 46, 59, 60, 53, 50, 31, 24, 17, 22, 3, 4, 13, 10, 87, 80, 89, 94, 75, 76, 69, 66, 111, 104, 97, 102, 115, 116, 125, 122, -119, -114, -121, Byte.MIN_VALUE, -107, -110, -101, -100, -79, -74, -65, -72, -83, -86, -93, -92, -7, -2, -9, -16, -27, -30, -21, -20, -63, -58, -49, -56, -35, -38, -45, -44, 105, 110, 103, 96, 117, 114, 123, 124, 81, 86, 95, 88, 77, 74, 67, 68, 25, 30, 23, 16, 5, 2, 11, 12, 33, 38, 47, 40, 61, 58, 51, 52, 78, 73, 64, 71, 82, 85, 92, 91, 118, 113, 120, Byte.MAX_VALUE, 106, 109, 100, 99, 62, 57, 48, 55, 34, 37, 44, 43, 6, 1, 8, 15, 26, 29, 20, 19, -82, -87, -96, -89, -78, -75, -68, -69, -106, -111, -104, -97, -118, -115, -124, -125, -34, -39, -48, -41, -62, -59, -52, -53, -26, -31, -24, -17, -6, -3, -12, -13};
    byte[] ambientColor = {0, 0, 0};
    int ambientModeType = 0;
    int ambientShowType = 0;
    int brightness = 0;
    /* access modifiers changed from: private */
    public InetAddress broadcastIP;
    private byte[] espSerialNumber = {0, 0};
    int fadeRate = 4;
    String groupName = "unassigned";
    int groupNumber = 0;
    /* access modifiers changed from: private */
    public InetAddress lightsUnicastIP;
    int mode = 0;
    String name = tag;
    int productId;
    byte[] saturation = {-1, -1, -1};

    private class UDPBroadcast extends AsyncTask<byte[], Void, Void> {
        private UDPBroadcast() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(byte[]... bytes) {
            try {
                DatagramSocket s = new DatagramSocket();
                s.setBroadcast(true);
                byte[] command = bytes[0];
                s.send(new DatagramPacket(command, command.length, Light.this.broadcastIP, Light.dreamScreenPort));
                s.close();
            } catch (SocketException socketException) {
                Log.i(Light.tag, "sending broadcast socketException-" + socketException.toString());
            } catch (Exception e) {
                Log.i(Light.tag, "sending broadcast error-" + e.toString());
            }
            return null;
        }
    }

    private class UDPUnicast extends AsyncTask<byte[], Void, Void> {
        private UDPUnicast() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(byte[]... bytes) {
            try {
                DatagramSocket s = new DatagramSocket();
                byte[] command = bytes[0];
                s.send(new DatagramPacket(command, command.length, Light.this.lightsUnicastIP, Light.dreamScreenPort));
                s.close();
            } catch (SocketException socketException) {
                Log.i(Light.tag, "sending unicast socketException-" + socketException.toString());
            } catch (Exception e) {
                Log.i(Light.tag, "sending unicast error-" + e.toString());
            }
            return null;
        }
    }

    Light(String ipAddress, String broadcastIpString) {
        try {
            this.lightsUnicastIP = InetAddress.getByName(ipAddress);
            this.broadcastIP = InetAddress.getByName(broadcastIpString);
        } catch (UnknownHostException e) {
            Log.i(tag, "UnknownHostException " + e.toString());
        }
    }

    public void resetEsp(boolean broadcastingToGroup) {
        try {
            Log.i(tag, "resetEsp");
            sendUDPWrite(1, 5, resetEspKey.getBytes("UTF-8"), broadcastingToGroup);
        } catch (UnsupportedEncodingException e) {
        }
    }

    public byte[] getEspSerialNumber() {
        return this.espSerialNumber;
    }

    public void initEspSerialNumber(byte[] espSerialNumber2) {
        this.espSerialNumber = espSerialNumber2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        try {
            this.name = name2;
            Log.i(tag, "setName " + name2);
            sendUDPWrite(1, 7, name2.getBytes("UTF-8"), false);
        } catch (UnsupportedEncodingException e) {
        }
    }

    public boolean initName(String name2) {
        if (this.name.equals(name2)) {
            return false;
        }
        this.name = name2;
        return true;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName2, boolean broadcastingToGroup) {
        try {
            this.groupName = groupName2;
            Log.i(tag, "setGroupName " + groupName2);
            sendUDPWrite(1, 8, groupName2.getBytes("UTF-8"), broadcastingToGroup);
        } catch (UnsupportedEncodingException e) {
        }
    }

    public boolean initGroupName(String groupName2) {
        if (this.groupName.equals(groupName2)) {
            return false;
        }
        this.groupName = groupName2;
        return true;
    }

    public int getGroupNumber() {
        return this.groupNumber;
    }

    public void setGroupNumber(int groupNumber2, boolean broadcastingToGroup) {
        Log.i(tag, "setGroupNumber " + groupNumber2);
        sendUDPWrite(1, 9, new byte[]{(byte) groupNumber2}, broadcastingToGroup);
        this.groupNumber = groupNumber2;
    }

    public boolean initGroupNumber(int groupNumber2) {
        if (this.groupNumber == groupNumber2) {
            return false;
        }
        this.groupNumber = groupNumber2;
        return true;
    }

    public int getProductId() {
        return this.productId;
    }

    public int getBrightness() {
        return this.brightness;
    }

    public void setBrightness(int brightness2, boolean broadcastingToGroup) {
        this.brightness = brightness2;
        Log.i(tag, "setBrightness " + brightness2);
        sendUDPWrite(3, 2, new byte[]{(byte) brightness2}, broadcastingToGroup);
    }

    public void setBrightnessConstantUnicast(int brightness2) {
        this.brightness = brightness2;
        Log.i(tag, "setBrightness constant " + brightness2);
        constantUDPUnicastWrite(3, 2, new byte[]{(byte) brightness2});
    }

    public boolean initBrightness(int brightness2) {
        if (this.brightness == brightness2) {
            return false;
        }
        this.brightness = brightness2;
        return true;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode2, boolean broadcastingToGroup) {
        this.mode = mode2;
        Log.i(tag, "setMode " + mode2);
        sendUDPWrite(3, 1, new byte[]{(byte) mode2}, broadcastingToGroup);
    }

    public boolean initMode(int mode2) {
        Log.i(tag, "Set mode " + this.mode + " to " + mode2);
        if (this.mode == mode2) {
            return false;
        }
        this.mode = mode2;
        return true;
    }

    public byte[] getAmbientColor() {
        return this.ambientColor;
    }

    public void setAmbientColor(byte[] ambientColor2, boolean broadcastingToGroup) {
        this.ambientColor = ambientColor2;
        Log.i(tag, "setColor " + ambientColor2[0] + " " + ambientColor2[1] + " " + ambientColor2[2]);
        sendUDPWrite(3, 5, ambientColor2, broadcastingToGroup);
    }

    public void setAmbientColorConstantUnicast(byte[] ambientColor2) {
        this.ambientColor = ambientColor2;
        Log.i(tag, "setColor constant " + ambientColor2[0] + " " + ambientColor2[1] + " " + ambientColor2[2]);
        constantUDPUnicastWrite(3, 5, ambientColor2);
    }

    public boolean initAmbientColor(byte[] ambientColor2) {
        if (this.ambientColor == ambientColor2) {
            return false;
        }
        this.ambientColor = ambientColor2;
        return true;
    }

    public int getAmbientModeType() {
        return this.ambientModeType;
    }

    public void setAmbientModeType(int ambientModeType2, boolean broadcastingToGroup) {
        this.ambientModeType = ambientModeType2;
        Log.i(tag, "setAmbientModeType " + ambientModeType2);
        sendUDPWrite(3, 8, new byte[]{(byte) ambientModeType2}, broadcastingToGroup);
    }

    public boolean initAmbientModeType(int ambientModeType2) {
        if (this.ambientModeType == ambientModeType2) {
            return false;
        }
        this.ambientModeType = ambientModeType2;
        return true;
    }

    public int getAmbientShowType() {
        return this.ambientShowType;
    }

    public void setAmbientShowType(int ambientShowType2, boolean broadcastingToGroup) {
        this.ambientShowType = ambientShowType2;
        Log.i(tag, "setAmbientShowType " + ambientShowType2);
        sendUDPWrite(3, 13, new byte[]{(byte) ambientShowType2}, broadcastingToGroup);
    }

    public boolean initAmbientShowType(int ambientShowType2) {
        if (this.ambientShowType == ambientShowType2) {
            return false;
        }
        this.ambientShowType = ambientShowType2;
        return true;
    }

    public int getFadeRate() {
        return this.fadeRate;
    }

    public void setFadeRate(int fadeRate2, boolean broadcastingToGroup) {
        this.fadeRate = fadeRate2;
        Log.i(tag, "setFadeRate " + fadeRate2);
        sendUDPWrite(3, 14, new byte[]{(byte) fadeRate2}, broadcastingToGroup);
    }

    public void initFadeRate(int fadeRate2) {
        this.fadeRate = fadeRate2;
    }

    public byte[] getSaturation() {
        return this.saturation;
    }

    public void setSaturation(byte[] saturation2, boolean broadcastingToGroup) {
        this.saturation = saturation2;
        Log.i(tag, "setSaturation" + saturation2);
        sendUDPWrite(3, 6, saturation2, broadcastingToGroup);
    }

    public void initSaturation(byte[] saturation2) {
        this.saturation = saturation2;
    }

    public String getIp() {
        return this.lightsUnicastIP.getHostAddress();
    }

    public InetAddress getLightsUnicastIP() {
        return this.lightsUnicastIP;
    }

    public InetAddress getBroadcastIP() {
        return this.broadcastIP;
    }

    public void doFactoryReset(final boolean broadcastingToGroup) {
        Log.i(tag, "doFactoryReset");
        try {
            byte[] resetPicPayload = "Bg".getBytes("UTF-8");
            final byte[] resetEspPayload = "aH".getBytes("UTF-8");
            if (this.productId == 1 || this.productId == 2 || this.productId == 7) {
                sendUDPWrite(4, 3, resetPicPayload, broadcastingToGroup);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Light.this.sendUDPWrite(1, 6, resetEspPayload, broadcastingToGroup);
                    }
                }, 1500);
            } else if (this.productId == 3) {
                sendUDPWrite(1, 6, resetEspPayload, broadcastingToGroup);
            } else if (this.productId == 4) {
                sendUDPWrite(1, 6, resetEspPayload, broadcastingToGroup);
            } else {
                Log.i(tag, "invalid productId");
            }
        } catch (UnsupportedEncodingException e) {
            Log.i(tag, "doFactoryReset error " + e.toString());
        }
    }

    public void sendPing() {
        sendUDPUnicastRead(1, 11);
    }

    /* access modifiers changed from: protected */
    public void sendUDPWrite(byte command1, byte command2, byte[] payload, boolean broadcastingToGroup) {
        ByteArrayOutputStream response = new ByteArrayOutputStream();
        response.write(252);
        response.write((byte) (payload.length + 5));
        response.write((byte) this.groupNumber);
        if (broadcastingToGroup) {
            response.write(33);
        } else {
            response.write(17);
        }
        response.write(command1);
        response.write(command2);
        for (byte b : payload) {
            response.write(b);
        }
        response.write(uartComm_calculate_crc8(response.toByteArray()));
        if (broadcastingToGroup) {
            sendUDPBroadcast(response.toByteArray());
        } else {
            sendUDPUnicast(response.toByteArray());
        }
    }

    private void constantUDPUnicastWrite(byte command1, byte command2, byte[] payload) {
        ByteArrayOutputStream response = new ByteArrayOutputStream();
        response.write(252);
        response.write((byte) (payload.length + 5));
        response.write((byte) this.groupNumber);
        response.write(1);
        response.write(command1);
        response.write(command2);
        for (byte b : payload) {
            response.write(b);
        }
        response.write(uartComm_calculate_crc8(response.toByteArray()));
        sendUDPUnicast(response.toByteArray());
    }

    /* access modifiers changed from: protected */
    public void sendUDPUnicastRead(byte command1, byte command2) {
        ByteArrayOutputStream response = new ByteArrayOutputStream();
        response.write(252);
        response.write(5);
        response.write((byte) this.groupNumber);
        response.write(16);
        response.write(command1);
        response.write(command2);
        response.write(uartComm_calculate_crc8(response.toByteArray()));
        sendUDPUnicast(response.toByteArray());
    }

    private void sendUDPBroadcast(byte[] commandBytes) {
        new UDPBroadcast().execute(new byte[][]{commandBytes});
    }

    private void sendUDPUnicast(byte[] commandBytes) {
        new UDPUnicast().execute(new byte[][]{commandBytes});
    }

    public static byte uartComm_calculate_crc8(byte[] data) {
        int size = (data[1] & 255) + 1;
        byte crc = 0;
        for (int cntr = 0; cntr < size; cntr++) {
            crc = uartComm_crc8_table[((byte) (data[cntr] ^ crc)) & 255];
        }
        return crc;
    }
}
