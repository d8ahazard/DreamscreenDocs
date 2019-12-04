package com.lab714.dreamscreenv2.devices;

import androidx.annotation.Nullable;
import android.util.Log;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

public class Connect extends Light {
    public static final String DEFAULT_NAME = "Connect";
    public static final byte[] DEFAULT_SECTOR_ASSIGNMENT = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 0, 0};
    public static final ArrayList<Integer> DS_REMOTE_COMMAND_VALUES = new ArrayList<>(Arrays.asList(new Integer[]{Integer.valueOf(1836103725), Integer.valueOf(1342658845), Integer.valueOf(1339163517), Integer.valueOf(459778273), Integer.valueOf(720925977), Integer.valueOf(1114347969), Integer.valueOf(30463369), Integer.valueOf(-1573044211)}));
    public static final ArrayList<String> IR_COMMANDS = new ArrayList<>(Arrays.asList(new String[]{"Undefined", "Mode Toggle", "Mode Sleep", "Mode Video", "Mode Audio", "Mode Ambient", "Brightness Up 10%", "Brightness Down 10%", "HDMI Toggle", "HDMI 1", "HDMI 2", "HDMI 3", "Ambient Scene Toggle"}));
    private static final String JSON_HUE_BRIDGE_CLIENT_KEY = "hueBridgeClientKey";
    private static final String JSON_HUE_BRIDGE_GROUP_NAME = "hueBridgeGroupName";
    private static final String JSON_HUE_BRIDGE_GROUP_NUMBER = "hueBridgeGroupNumber";
    private static final String JSON_HUE_BRIDGE_USERNAME = "hueBridgeUsername";
    private static final String JSON_HUE_BULB_IDS = "hueBulbIds";
    private static final String JSON_LIGHT_IPADDRESSES = "lightIpAddresses";
    private static final String JSON_LIGHT_NAMES = "lightNames";
    private static final String JSON_LIGHT_SECTOR_ASSIGNMENTS = "lightSectorAssignments";
    private static final String JSON_LIGHT_TYPE = "lightType";
    public static final int LIGHT_COUNT = 10;
    public static final ArrayList<Integer> VALID_LIFX_PRODUCTIDS = new ArrayList<>(Arrays.asList(new Integer[]{Integer.valueOf(1), Integer.valueOf(3), Integer.valueOf(20), Integer.valueOf(22), Integer.valueOf(27), Integer.valueOf(28), Integer.valueOf(29), Integer.valueOf(30), Integer.valueOf(31), Integer.valueOf(32), Integer.valueOf(36), Integer.valueOf(37), Integer.valueOf(43), Integer.valueOf(44), Integer.valueOf(45), Integer.valueOf(46), Integer.valueOf(49), Integer.valueOf(52)}));
    private static final byte[] requiredEspFirmwareVersion = {0, 4};
    private static final String tag = "Connect";
    private int ambientLightAutoAdjustEnabled;
    private int displayAnimationEnabled;
    private String emailAddress;
    private boolean emailReceived;
    private byte[] espFirmwareVersion;
    private int hdmiInput;
    private byte[] hueBridgeClientKey;
    private String hueBridgeGroupName;
    private int hueBridgeGroupNumber;
    private String hueBridgeUsername;
    private byte[] hueBulbIds;
    /* access modifiers changed from: private */
    public boolean hueLifxSettingsReceived;
    private int irEnabled;
    private int irLearningMode;
    private byte[] irManifest;
    private boolean isDemo;
    private final InetAddress[] lightIpAddresses;
    private String[] lightNames;
    private byte[] lightSectorAssignments;
    private int lightType;
    private int microphoneAudioBroadcastEnabled;
    private byte[] sectorData;
    private String thingName;

    public Connect(String ipAddress, String broadcastIpString) {
        super(ipAddress, broadcastIpString);
        this.espFirmwareVersion = new byte[]{0, 0};
        this.hdmiInput = 0;
        this.displayAnimationEnabled = 0;
        this.ambientLightAutoAdjustEnabled = 0;
        this.microphoneAudioBroadcastEnabled = 0;
        this.irEnabled = 1;
        this.irLearningMode = 0;
        this.irManifest = new byte[40];
        this.emailAddress = "";
        this.thingName = "";
        this.lightType = 0;
        this.lightIpAddresses = new InetAddress[10];
        this.lightSectorAssignments = new byte[150];
        this.hueBridgeUsername = "";
        this.hueBulbIds = new byte[10];
        this.hueBridgeClientKey = new byte[16];
        this.hueBridgeGroupNumber = 0;
        this.hueBridgeGroupName = "";
        this.lightNames = new String[10];
        this.sectorData = new byte[]{0};
        this.isDemo = false;
        this.hueLifxSettingsReceived = false;
        this.emailReceived = false;
        this.productId = 4;
        this.name = "Connect";
        byte b = 0;
        while (b < 10) {
            try {
                this.lightIpAddresses[b] = InetAddress.getByAddress(new byte[]{0, 0, 0, 0});
                this.hueBulbIds[b] = 0;
                this.lightNames[b] = "";
                b = (byte) (b + 1);
            } catch (UnknownHostException e) {
            }
        }
        for (byte b2 = 0; b2 < 16; b2 = (byte) (b2 + 1)) {
            this.hueBridgeClientKey[b2] = 0;
        }
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
        Log.i("Connect", "setSectorData ");
        send    UDPWrite(3, 22, sectorData2, broadcastingToGroup);
    }

    public void initSectorData(byte[] sectorData2) {
        this.sectorData = sectorData2;
    }

    public int getHdmiInput() {
        return this.hdmiInput;
    }

    public void setHdmiInput(int hdmiInput2, boolean broadcastingToGroup) {
        this.hdmiInput = hdmiInput2;
        Log.i("Connect", "setHdmiInput " + hdmiInput2);
        sendUDPWrite(3, 32, new byte[]{(byte) hdmiInput2}, broadcastingToGroup);
    }

    public boolean initHdmiInput(int hdmiInput2) {
        Log.i("Connect", "initHdmiInput " + hdmiInput2);
        if (this.hdmiInput == hdmiInput2) {
            return false;
        }
        this.hdmiInput = hdmiInput2;
        return true;
    }

    public int getDisplayAnimationEnabled() {
        return this.displayAnimationEnabled;
    }

    public void setDisplayAnimationEnabled(int displayAnimationEnabled2, boolean broadcastingToGroup) {
        this.displayAnimationEnabled = displayAnimationEnabled2;
        Log.i("Connect", "setDisplayAnimationEnabled " + displayAnimationEnabled2);
        sendUDPWrite(5, 1, new byte[]{(byte) displayAnimationEnabled2}, broadcastingToGroup);
    }

    public void initDisplayAnimationEnabled(int displayAnimationEnabled2) {
        this.displayAnimationEnabled = displayAnimationEnabled2;
    }

    public int getAmbientLightAutoAdjustEnabled() {
        return this.ambientLightAutoAdjustEnabled;
    }

    public void setAmbientLightAutoAdjustEnabled(int ambientLightAutoAdjustEnabled2, boolean broadcastingToGroup) {
        this.ambientLightAutoAdjustEnabled = ambientLightAutoAdjustEnabled2;
        Log.i("Connect", "setAmbientLightAutoAdjustEnabled " + ambientLightAutoAdjustEnabled2);
        sendUDPWrite(5, 2, new byte[]{(byte) ambientLightAutoAdjustEnabled2}, broadcastingToGroup);
    }

    public void initAmbientLightAutoAdjustEnabled(int ambientLightAutoAdjustEnabled2) {
        this.ambientLightAutoAdjustEnabled = ambientLightAutoAdjustEnabled2;
    }

    public int getMicrophoneAudioBroadcastEnabled() {
        return this.microphoneAudioBroadcastEnabled;
    }

    public void setMicrophoneAudioBroadcastEnabled(int microphoneAudioBroadcastEnabled2, boolean broadcastingToGroup) {
        this.microphoneAudioBroadcastEnabled = microphoneAudioBroadcastEnabled2;
        Log.i("Connect", "setMicrophoneAudioBroadcastEnabled " + microphoneAudioBroadcastEnabled2);
        sendUDPWrite(5, 3, new byte[]{(byte) microphoneAudioBroadcastEnabled2}, broadcastingToGroup);
    }

    public void initMicrophoneAudioBroadcastEnabled(int microphoneAudioBroadcastEnabled2) {
        this.microphoneAudioBroadcastEnabled = microphoneAudioBroadcastEnabled2;
    }

    public int getIrEnabled() {
        return this.irEnabled;
    }

    public void setIrEnabled(int irEnabled2, boolean broadcastingToGroup) {
        this.irEnabled = irEnabled2;
        Log.i("Connect", "setIrEnabled " + irEnabled2);
        sendUDPWrite(5, 16, new byte[]{(byte) irEnabled2}, broadcastingToGroup);
    }

    public void initIrEnabled(int irEnabled2) {
        this.irEnabled = irEnabled2;
    }

    public int getIrLearningMode() {
        return this.irLearningMode;
    }

    public void setIrLearningMode(int irLearningMode2, boolean broadcastingToGroup) {
        this.irLearningMode = irLearningMode2;
        Log.i("Connect", "setIrLearningMode " + irLearningMode2);
        sendUDPWrite(5, 17, new byte[]{(byte) irLearningMode2}, broadcastingToGroup);
    }

    public void initIrLearningMode(int irLearningMode2) {
        this.irLearningMode = irLearningMode2;
    }

    public byte[] getIrManifest() {
        return this.irManifest;
    }

    public void initIrManifest(byte[] irManifest2) {
        this.irManifest = irManifest2;
    }

    public void setIrManifestEntry(byte[] irManifestEntry, boolean broadcastingToGroup) {
        Log.i("Connect", "setIrManifestEntry " + irManifestEntry);
        sendUDPWrite(5, 19, irManifestEntry, broadcastingToGroup);
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress2, boolean broadcastingToGroup) {
        try {
            this.emailAddress = emailAddress2;
            byte[] payload = emailAddress2.getBytes("UTF-8");
            if (payload.length > 249) {
                Log.i("Connect", "setEmailAddress but too many bytes. canceling");
            } else {
                sendUDPWrite(5, 32, payload, broadcastingToGroup);
            }
        } catch (UnsupportedEncodingException e) {
        }
    }

    public void initEmailAddress(String emailAddress2) {
        if (emailAddress2.length() > 249) {
            Log.i("Connect", "initEmail address error, length " + emailAddress2.length());
            return;
        }
        this.emailReceived = true;
        Log.i("Connect", emailAddress2.length() + " initEmailAddress " + emailAddress2);
        this.emailAddress = emailAddress2;
    }

    public void readEmailAddress() {
        Log.i("Connect", "readEmailAddress");
        sendUDPUnicastRead(5, 32);
    }

    public String getThingName() {
        return this.thingName;
    }

    public void setThingName(String thingName2, boolean broadcastingToGroup) {
        try {
            this.thingName = thingName2;
            Log.i("Connect", "setThingName " + thingName2);
            sendUDPWrite(5, 33, thingName2.getBytes("UTF-8"), broadcastingToGroup);
        } catch (UnsupportedEncodingException e) {
        }
    }

    public void initThingName(String thingName2) {
        Log.i("Connect", thingName2.length() + " initThingName " + thingName2);
        this.thingName = thingName2;
    }

    public void sendCertificates(final String certificate, final String privateKey) {
        new Thread(new Runnable() {
            /* JADX WARNING: Removed duplicated region for block: B:11:0x00a6 A[DONT_GENERATE] */
            /* JADX WARNING: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r10 = this;
                    r2 = 0
                    java.net.URL r6 = new java.net.URL     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    r7.<init>()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    java.lang.String r8 = "http://"
                    java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    com.lab714.dreamscreenv2.devices.Connect r8 = com.lab714.dreamscreenv2.devices.Connect.this     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    java.lang.String r8 = r8.getIp()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    java.lang.String r8 = "/api/connect/state"
                    java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    r6.<init>(r7)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    java.net.URLConnection r7 = r6.openConnection()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    r0 = r7
                    java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    r2 = r0
                    r7 = 1
                    r2.setDoOutput(r7)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    java.lang.String r7 = "PUT"
                    r2.setRequestMethod(r7)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    java.lang.String r7 = "Content-Type"
                    java.lang.String r8 = "application/json"
                    r2.setRequestProperty(r7, r8)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    r3.<init>()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    java.lang.String r7 = "certificate"
                    java.lang.String r8 = r3     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    r3.put(r7, r8)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    java.lang.String r7 = "private_key"
                    java.lang.String r8 = r4     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    r3.put(r7, r8)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    java.io.OutputStream r4 = r2.getOutputStream()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    java.lang.String r7 = r3.toString()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    java.lang.String r8 = "UTF-8"
                    byte[] r7 = r7.getBytes(r8)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    r4.write(r7)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    r4.close()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    int r5 = r2.getResponseCode()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    java.lang.String r7 = "Connect"
                    java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    r8.<init>()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    java.lang.String r9 = "responseCode "
                    java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    java.lang.StringBuilder r8 = r8.append(r5)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    android.util.Log.i(r7, r8)     // Catch:{ JSONException -> 0x0086, IOException -> 0x00b1 }
                    if (r2 == 0) goto L_0x0085
                    r2.disconnect()
                L_0x0085:
                    return
                L_0x0086:
                    r7 = move-exception
                    r1 = r7
                L_0x0088:
                    java.lang.String r7 = "Connect"
                    java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x00aa }
                    r8.<init>()     // Catch:{ all -> 0x00aa }
                    java.lang.String r9 = "sendCertificate error: "
                    java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x00aa }
                    java.lang.String r9 = r1.toString()     // Catch:{ all -> 0x00aa }
                    java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x00aa }
                    java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x00aa }
                    android.util.Log.i(r7, r8)     // Catch:{ all -> 0x00aa }
                    if (r2 == 0) goto L_0x0085
                    r2.disconnect()
                    goto L_0x0085
                L_0x00aa:
                    r7 = move-exception
                    if (r2 == 0) goto L_0x00b0
                    r2.disconnect()
                L_0x00b0:
                    throw r7
                L_0x00b1:
                    r7 = move-exception
                    r1 = r7
                    goto L_0x0088
                */
                throw new UnsupportedOperationException("Method not decompiled: com.lab714.dreamscreenv2.devices.Connect.AnonymousClass1.run():void");
            }
        }).start();
    }

    public int getLightType() {
        return this.lightType;
    }

    public boolean initLightType(int lightType2) {
        Log.i("Connect", "initLightType " + lightType2);
        if (lightType2 < 0 || lightType2 == this.lightType) {
            return false;
        }
        this.lightType = lightType2;
        return true;
    }

    public InetAddress[] getLightIpAddresses() {
        return this.lightIpAddresses;
    }

    public void initLightIpAddresses(InetAddress[] lightIpAddresses2) {
        for (byte b = 0; b < 10; b = (byte) (b + 1)) {
            this.lightIpAddresses[b] = lightIpAddresses2[b];
        }
    }

    public byte[] getLightSectorAssignments() {
        return this.lightSectorAssignments;
    }

    public void initLightSectorAssignments(byte[] lightSectorAssignments2) {
        this.lightSectorAssignments = lightSectorAssignments2;
    }

    public String getHueBridgeUsername() {
        return this.hueBridgeUsername;
    }

    public void initHueBridgeUsername(String hueBridgeUsername2) {
        this.hueBridgeUsername = hueBridgeUsername2;
    }

    public byte[] getHueBulbIds() {
        return this.hueBulbIds;
    }

    public void initHueBulbIds(byte[] hueBulbIds2) {
        this.hueBulbIds = hueBulbIds2;
    }

    public byte[] getHueBridgeClientKey() {
        return this.hueBridgeClientKey;
    }

    public void initHueBridgeClientKey(byte[] hueBridgeClientKey2) {
        this.hueBridgeClientKey = hueBridgeClientKey2;
    }

    public int getHueBridgeGroupNumber() {
        return this.hueBridgeGroupNumber;
    }

    public void initHueBridgeGroupNumber(int hueBridgeGroupNumber2) {
        this.hueBridgeGroupNumber = hueBridgeGroupNumber2;
    }

    public String getHueBridgeGroupName() {
        return this.hueBridgeGroupName;
    }

    public void initHueBridgeGroupName(String hueBridgeGroupName2) {
        this.hueBridgeGroupName = hueBridgeGroupName2;
    }

    public String[] getLightNames() {
        return this.lightNames;
    }

    public void initLightNames(String[] lightNames2) {
        Log.i("Connect", "initLightNames " + lightNames2[0] + " - " + lightNames2[1] + " - " + lightNames2[2] + " - " + lightNames2[3]);
        this.lightNames = lightNames2;
    }

    public void setHueLifxSettings(int lightType2, @Nullable InetAddress[] lightIpAddresses2, @Nullable byte[] lightSectorAssignments2, @Nullable String hueBridgeUsername2, @Nullable byte[] hueBulbIds2, @Nullable byte[] hueBridgeClientKey2, int hueBridgeGroupNumber2, @Nullable String[] lightNames2, @Nullable String hueBridgeGroupName2) {
        Log.i("Connect", "setHueLifxSettings");
        final JSONObject json = new JSONObject();
        if (lightType2 >= 0 && lightType2 <= 2) {
            try {
                initLightType(lightType2);
                json.put(JSON_LIGHT_TYPE, lightType2);
            } catch (JSONException e) {
                Log.i("Connect", "setHueLifxSettings json failed");
                return;
            }
        }
        if (lightIpAddresses2 != null) {
            initLightIpAddresses(lightIpAddresses2);
            String asciiHex = "";
            for (InetAddress inetAddress : lightIpAddresses2) {
                asciiHex = asciiHex + byteArrayToAsciiHex(inetAddress.getAddress(), inetAddress.getAddress().length);
            }
            json.put(JSON_LIGHT_IPADDRESSES, asciiHex);
        }
        if (lightSectorAssignments2 != null) {
            initLightSectorAssignments(lightSectorAssignments2);
            json.put(JSON_LIGHT_SECTOR_ASSIGNMENTS, byteArrayToAsciiHex(lightSectorAssignments2, lightSectorAssignments2.length));
        }
        if (hueBridgeUsername2 != null) {
            initHueBridgeUsername(hueBridgeUsername2);
            json.put(JSON_HUE_BRIDGE_USERNAME, hueBridgeUsername2);
        }
        if (hueBulbIds2 != null) {
            initHueBulbIds(hueBulbIds2);
            json.put(JSON_HUE_BULB_IDS, byteArrayToAsciiHex(hueBulbIds2, hueBulbIds2.length));
        }
        if (hueBridgeClientKey2 != null) {
            initHueBridgeClientKey(hueBridgeClientKey2);
            json.put(JSON_HUE_BRIDGE_CLIENT_KEY, byteArrayToAsciiHex(hueBridgeClientKey2, hueBridgeClientKey2.length));
        }
        if (hueBridgeGroupNumber2 >= 0) {
            initHueBridgeGroupNumber(hueBridgeGroupNumber2);
            json.put(JSON_HUE_BRIDGE_GROUP_NUMBER, hueBridgeGroupNumber2);
        }
        if (hueBridgeGroupName2 != null) {
            initHueBridgeGroupName(hueBridgeGroupName2);
            json.put(JSON_HUE_BRIDGE_GROUP_NAME, hueBridgeGroupName2);
        }
        if (lightNames2 != null) {
            initLightNames(lightNames2);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                for (String lightName : lightNames2) {
                    byteArrayOutputStream.write(Arrays.copyOf(lightName.getBytes("UTF-8"), 32));
                }
            } catch (IOException e2) {
            }
            json.put(JSON_LIGHT_NAMES, byteArrayToAsciiHex(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.toByteArray().length));
        }
        Log.i("Connect", "json: " + json.toString());
        new Thread(new Runnable() {
            public void run() {
                HttpURLConnection httpURLConnection = null;
                try {
                    httpURLConnection = (HttpURLConnection) new URL("http://" + Connect.this.getIp() + "/api/connect/state").openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod("PUT");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(json.toString().getBytes("UTF-8"));
                    outputStream.close();
                    Log.i("Connect", "responseCode " + httpURLConnection.getResponseCode());
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                } catch (IOException e) {
                    Log.i("Connect", "setHueLifxSettings error: " + e.toString());
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                } catch (Throwable th) {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    throw th;
                }
            }
        }).start();
    }

    public void readHueLifxSettings() {
        Log.i("Connect", "readHueLifxSettings");
        new Thread(new Runnable() {
            public void run() {
                HttpURLConnection httpURLConnection = null;
                try {
                    httpURLConnection = (HttpURLConnection) new URL("http://" + Connect.this.getIp() + "/api/connect/state").openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    int responseCode = httpURLConnection.getResponseCode();
                    Log.i("Connect", "responseCode " + responseCode);
                    if (responseCode == 200) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                        StringBuffer response = new StringBuffer();
                        while (true) {
                            String inputLine = in.readLine();
                            if (inputLine == null) {
                                break;
                            }
                            response.append(inputLine);
                        }
                        in.close();
                        Log.i("Connect", "readHueLifxSettings: " + response.toString());
                        try {
                            JSONObject rootObject = new JSONObject(response.toString());
                            int tempInt = rootObject.optInt(Connect.JSON_LIGHT_TYPE, -1);
                            if (tempInt != -1) {
                                Connect.this.initLightType(tempInt);
                            }
                            String asciiHex = rootObject.optString(Connect.JSON_LIGHT_IPADDRESSES, null);
                            if (asciiHex != null) {
                                InetAddress[] ipAddresses = new InetAddress[10];
                                byte[] tempBytes = Connect.asciiHexToByteArray(asciiHex, asciiHex.length());
                                for (int lightCount = 0; lightCount < 10; lightCount++) {
                                    try {
                                        ipAddresses[lightCount] = InetAddress.getByAddress(Arrays.copyOfRange(tempBytes, lightCount * 4, (lightCount * 4) + 4));
                                    } catch (UnknownHostException e) {
                                    }
                                }
                                Connect.this.initLightIpAddresses(ipAddresses);
                            }
                            String asciiHex2 = rootObject.optString(Connect.JSON_LIGHT_SECTOR_ASSIGNMENTS, null);
                            if (asciiHex2 != null) {
                                Connect.this.initLightSectorAssignments(Connect.asciiHexToByteArray(asciiHex2, asciiHex2.length()));
                            }
                            String tempString = rootObject.optString(Connect.JSON_HUE_BRIDGE_USERNAME, null);
                            if (asciiHex2 != null) {
                                Connect.this.initHueBridgeUsername(tempString.trim());
                            }
                            String asciiHex3 = rootObject.optString(Connect.JSON_HUE_BULB_IDS, null);
                            if (asciiHex3 != null) {
                                Connect.this.initHueBulbIds(Connect.asciiHexToByteArray(asciiHex3, asciiHex3.length()));
                            }
                            String asciiHex4 = rootObject.optString(Connect.JSON_HUE_BRIDGE_CLIENT_KEY, null);
                            if (asciiHex4 != null) {
                                Connect.this.initHueBridgeClientKey(Connect.asciiHexToByteArray(asciiHex4, asciiHex4.length()));
                            }
                            int tempInt2 = rootObject.optInt(Connect.JSON_HUE_BRIDGE_GROUP_NUMBER, -1);
                            if (tempInt2 != -1) {
                                Connect.this.initHueBridgeGroupNumber(tempInt2);
                            }
                            String tempString2 = rootObject.optString(Connect.JSON_HUE_BRIDGE_GROUP_NAME, null);
                            if (asciiHex4 != null) {
                                Connect.this.initHueBridgeGroupName(tempString2.trim());
                            }
                            String asciiHex5 = rootObject.optString(Connect.JSON_LIGHT_NAMES, null);
                            if (asciiHex5 != null) {
                                String[] lightNames = new String[10];
                                byte[] tempBytes2 = Connect.asciiHexToByteArray(asciiHex5, asciiHex5.length());
                                for (int lightCount2 = 0; lightCount2 < 10; lightCount2++) {
                                    try {
                                        lightNames[lightCount2] = new String(Arrays.copyOfRange(tempBytes2, lightCount2 * 32, (lightCount2 * 32) + 32), "UTF-8").trim();
                                    } catch (IOException e2) {
                                    }
                                }
                                Connect.this.initLightNames(lightNames);
                            }
                            Connect.this.hueLifxSettingsReceived = true;
                        } catch (JSONException e3) {
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                                return;
                            }
                            return;
                        }
                    } else {
                        Log.i("Connect", "readHueLifxSettings, did not receive 200");
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                } catch (IOException e4) {
                    Log.i("Connect", "readHueLifxSettings error: " + e4.toString());
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                } catch (Throwable th) {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    throw th;
                }
            }
        }).start();
    }

    public void stopEsp32Drivers() {
        Log.i("Connect", "stopEsp32Drivers");
        sendUDPWrite(1, 23, new byte[]{0}, false);
    }

    public boolean isHueLifxSettingsReceived() {
        return this.hueLifxSettingsReceived;
    }

    public boolean isEmailReceived() {
        return this.emailReceived;
    }

    public void parsePayload(byte[] payload) {
        Log.i("Connect", "parsePayload, " + payload.length + " bytes");
        try {
            String name = new String(Arrays.copyOfRange(payload, 0, 16), "UTF-8");
            if (name.isEmpty()) {
                name = "Connect";
            }
            this.name = name;
            String groupName = new String(Arrays.copyOfRange(payload, 16, 32), "UTF-8");
            if (groupName.isEmpty()) {
                groupName = "Group";
            }
            this.groupName = groupName;
        } catch (UnsupportedEncodingException e) {
        }
        this.groupNumber = payload[32] & 255;
        this.mode = payload[33] & 255;
        this.brightness = payload[34] & 255;
        this.ambientColor = Arrays.copyOfRange(payload, 35, 38);
        this.fadeRate = payload[41] & 255;
        this.espFirmwareVersion = Arrays.copyOfRange(payload, 57, 59);
        this.ambientModeType = payload[59] & 255;
        this.ambientShowType = payload[60] & 255;
        this.hdmiInput = payload[61] & 255;
        this.displayAnimationEnabled = payload[62] & 255;
        this.ambientLightAutoAdjustEnabled = payload[63] & 255;
        this.microphoneAudioBroadcastEnabled = payload[64] & 255;
        this.irEnabled = payload[65] & 255;
        this.irLearningMode = payload[66] & 255;
        this.irManifest = Arrays.copyOfRange(payload, 67, 107);
        try {
            this.thingName = new String(Arrays.copyOfRange(payload, 115, 178), "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            this.thingName = "";
        }
    }

    private static byte decodeAsciiCharToNibble(char c) {
        if (c >= '0' && c <= '9') {
            return (byte) (c - '0');
        }
        if (c >= 'A' && c <= 'F') {
            return (byte) ((c - 'A') + 10);
        }
        if (c < 'a' || c > 'f') {
            return 0;
        }
        return (byte) ((c - 'a') + 10);
    }

    /* access modifiers changed from: private */
    public static byte[] asciiHexToByteArray(String inString, int inLength) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int outIndex = 0;
        int inIndex = 0;
        while (inIndex < inLength) {
            out.write((decodeAsciiCharToNibble(inString.charAt(inIndex)) << 4) | decodeAsciiCharToNibble(inString.charAt(inIndex + 1)));
            inIndex += 2;
            outIndex++;
        }
        return out.toByteArray();
    }

    private static char encodeNibbleToAsciiChar(byte b) {
        if (b < 10) {
            return (char) (b + 48);
        }
        if (b <= 15) {
            return (char) ((b - 10) + 65);
        }
        return '0';
    }

    private static String byteArrayToAsciiHex(byte[] in, int inLength) {
        String outString = "";
        int outIndex = 0;
        for (int inIndex = 0; inIndex < inLength; inIndex++) {
            outString = (outString + encodeNibbleToAsciiChar((byte) (((byte) (in[inIndex] >> 4)) & 15))) + encodeNibbleToAsciiChar((byte) (in[inIndex] & 15));
            outIndex += 2;
        }
        return outString;
    }
}
