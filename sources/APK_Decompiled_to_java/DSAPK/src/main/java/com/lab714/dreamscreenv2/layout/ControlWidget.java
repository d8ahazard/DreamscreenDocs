package com.lab714.dreamscreenv2.layout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import androidx.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import com.lab714.dreamscreenv2.R;
import com.lab714.dreamscreenv2.devices.Light;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ControlWidget extends AppWidgetProvider {
    protected static final String DREAMSCREEN_WIDGET_IDS_KEY = "dreamscreen_widget_ids_key";
    private static final String PREFS_NAME = "com.lab714.dreamscreenv2.layout.ControlWidget";
    private static final int dreamScreenPort = 8888;
    private static final String hdmiButtonTapped = "hdmiButtonTappedTag";
    private static final String modeButtonTapped = "modeButtonTappedTag";
    private static final String refreshButtonTapped = "refreshButtonTappedTag";
    private static final String tag = "ControlWidget";

    private class UDPUnicast extends AsyncTask<byte[], Void, Void> {
        InetAddress lightsUnicastIP;

        public UDPUnicast(String ipAddress) {
            try {
                this.lightsUnicastIP = InetAddress.getByName(ipAddress);
            } catch (UnknownHostException e) {
                Log.i(ControlWidget.tag, execute(new byte[0][]).toString());
            }
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(byte[]... bytes) {
            try {
                DatagramSocket s = new DatagramSocket();
                byte[] command = bytes[0];
                s.send(new DatagramPacket(command, command.length, this.lightsUnicastIP, ControlWidget.dreamScreenPort));
                s.close();
            } catch (SocketException socketException) {
                Log.i(ControlWidget.tag, "sending unicast socketException-" + socketException.toString());
            } catch (Exception e) {
                Log.i(ControlWidget.tag, "sending unicast error-" + e.toString());
            }
            return null;
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.i(tag, "updateAppWidget " + appWidgetId);
        String ipAddress = getPreferenceString(context, "ip");
        int productId = getPreferenceInt(context, "productId");
        Log.i(tag, ipAddress + " " + productId);
        RemoteViews controlWidgetRemoteView = new RemoteViews(context.getPackageName(), R.layout.control_widget);
        controlWidgetRemoteView.setOnClickPendingIntent(R.id.refreshButton, getPendingSelfIntent(context, refreshButtonTapped, appWidgetId));
        controlWidgetRemoteView.setOnClickPendingIntent(R.id.modeButton, getPendingSelfIntent(context, modeButtonTapped, appWidgetId));
        controlWidgetRemoteView.setOnClickPendingIntent(R.id.hdmiButton, getPendingSelfIntent(context, hdmiButtonTapped, appWidgetId));
        if (ipAddress == null || productId == -1) {
            Log.i(tag, "invalid attributes. displaying buttons as disabled");
            controlWidgetRemoteView.setBoolean(R.id.modeButton, "setEnabled", false);
            controlWidgetRemoteView.setBoolean(R.id.hdmiButton, "setEnabled", false);
        } else {
            Log.i(tag, "valid attributes. displaying buttons as enabled");
            controlWidgetRemoteView.setBoolean(R.id.modeButton, "setEnabled", true);
            if (productId == 1 || productId == 2) {
                controlWidgetRemoteView.setBoolean(R.id.hdmiButton, "setEnabled", true);
            } else {
                controlWidgetRemoteView.setBoolean(R.id.hdmiButton, "setEnabled", false);
            }
        }
        appWidgetManager.updateAppWidget(appWidgetId, controlWidgetRemoteView);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.i(tag, "onUpdate, count: " + appWidgetIds.length);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0085, code lost:
        if (r10.equals(modeButtonTapped) != false) goto L_0x0074;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onReceive(android.content.Context r13, android.content.Intent r14) {
        /*
            r12 = this;
            r9 = 2
            r8 = 1
            r6 = 0
            super.onReceive(r13, r14)
            java.lang.String r7 = r14.getAction()
            if (r7 != 0) goto L_0x000d
        L_0x000c:
            return
        L_0x000d:
            java.lang.String r7 = "appWidgetId"
            int r0 = r14.getIntExtra(r7, r6)
            java.lang.String r7 = "ControlWidget"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "onReceive "
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r11 = r14.getAction()
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.String r11 = " "
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r0)
            java.lang.String r10 = r10.toString()
            android.util.Log.i(r7, r10)
            java.lang.String r7 = "dreamscreen_widget_ids_key"
            boolean r7 = r14.hasExtra(r7)
            if (r7 == 0) goto L_0x005f
            java.lang.String r7 = r14.getAction()
            java.lang.String r10 = "android.appwidget.action.APPWIDGET_UPDATE"
            boolean r7 = r7.equals(r10)
            if (r7 == 0) goto L_0x005f
            android.os.Bundle r6 = r14.getExtras()
            java.lang.String r7 = "dreamscreen_widget_ids_key"
            int[] r2 = r6.getIntArray(r7)
            android.appwidget.AppWidgetManager r6 = android.appwidget.AppWidgetManager.getInstance(r13)
            r12.onUpdate(r13, r6, r2)
            goto L_0x000c
        L_0x005f:
            if (r0 == 0) goto L_0x011f
            java.lang.String r7 = "ip"
            java.lang.String r3 = getPreferenceString(r13, r7)
            java.lang.String r10 = r14.getAction()
            r7 = -1
            int r11 = r10.hashCode()
            switch(r11) {
                case -1790859775: goto L_0x0092;
                case -1690485852: goto L_0x0088;
                case -1248487879: goto L_0x007f;
                default: goto L_0x0073;
            }
        L_0x0073:
            r6 = r7
        L_0x0074:
            switch(r6) {
                case 0: goto L_0x009c;
                case 1: goto L_0x00c4;
                case 2: goto L_0x00eb;
                default: goto L_0x0077;
            }
        L_0x0077:
            java.lang.String r6 = "ControlWidget"
            java.lang.String r7 = "received unrecognized action"
            android.util.Log.i(r6, r7)
            goto L_0x000c
        L_0x007f:
            java.lang.String r11 = "modeButtonTappedTag"
            boolean r10 = r10.equals(r11)
            if (r10 == 0) goto L_0x0073
            goto L_0x0074
        L_0x0088:
            java.lang.String r6 = "hdmiButtonTappedTag"
            boolean r6 = r10.equals(r6)
            if (r6 == 0) goto L_0x0073
            r6 = r8
            goto L_0x0074
        L_0x0092:
            java.lang.String r6 = "refreshButtonTappedTag"
            boolean r6 = r10.equals(r6)
            if (r6 == 0) goto L_0x0073
            r6 = r9
            goto L_0x0074
        L_0x009c:
            if (r3 != 0) goto L_0x00a7
            java.lang.String r6 = "ControlWidget"
            java.lang.String r7 = "ipAddress null, canceling setMode"
            android.util.Log.i(r6, r7)
            goto L_0x000c
        L_0x00a7:
            java.lang.String r6 = "ControlWidget"
            java.lang.String r7 = "modeButtonTapped"
            android.util.Log.i(r6, r7)
            java.lang.String r6 = "mode"
            int r5 = getPreferenceInt(r13, r6)
            int r5 = r5 + 1
            r6 = 3
            if (r5 <= r6) goto L_0x00ba
            r5 = 0
        L_0x00ba:
            r12.setMode(r5, r3)
            java.lang.String r6 = "mode"
            setPreferenceInt(r13, r6, r5)
            goto L_0x000c
        L_0x00c4:
            if (r3 != 0) goto L_0x00cf
            java.lang.String r6 = "ControlWidget"
            java.lang.String r7 = "ipAddress null, canceling setHdmiInput"
            android.util.Log.i(r6, r7)
            goto L_0x000c
        L_0x00cf:
            java.lang.String r6 = "ControlWidget"
            java.lang.String r7 = "hdmiButtonTapped"
            android.util.Log.i(r6, r7)
            java.lang.String r6 = "hdmiInput"
            int r1 = getPreferenceInt(r13, r6)
            int r1 = r1 + 1
            if (r1 <= r9) goto L_0x00e1
            r1 = 0
        L_0x00e1:
            r12.setHdmiInput(r1, r3)
            java.lang.String r6 = "hdmiInput"
            setPreferenceInt(r13, r6, r1)
            goto L_0x000c
        L_0x00eb:
            java.lang.String r6 = "ControlWidget"
            java.lang.String r7 = "refreshButtonTapped, starting app"
            android.util.Log.i(r6, r7)
            java.lang.String r6 = "redirect_widget_settings"
            setPreferenceInt(r13, r6, r8)
            android.content.Intent r4 = new android.content.Intent
            java.lang.String r6 = "android.intent.action.MAIN"
            r4.<init>(r6)
            java.lang.String r6 = "android.intent.category.LAUNCHER"
            r4.addCategory(r6)
            android.content.ComponentName r6 = new android.content.ComponentName
            java.lang.String r7 = r13.getPackageName()
            java.lang.Class<com.lab714.dreamscreenv2.MainActivity> r8 = com.lab714.dreamscreenv2.MainActivity.class
            java.lang.String r8 = r8.getName()
            r6.<init>(r7, r8)
            r4.setComponent(r6)
            r6 = 268435456(0x10000000, float:2.5243549E-29)
            r4.setFlags(r6)
            r13.startActivity(r4)
            goto L_0x000c
        L_0x011f:
            java.lang.String r6 = "ControlWidget"
            java.lang.String r7 = "onReceive not handled"
            android.util.Log.i(r6, r7)
            goto L_0x000c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lab714.dreamscreenv2.layout.ControlWidget.onReceive(android.content.Context, android.content.Intent):void");
    }

    static PendingIntent getPendingSelfIntent(Context context, String action, int appWidgetId) {
        Intent intent = new Intent(context, ControlWidget.class);
        intent.setAction(action);
        intent.putExtra("appWidgetId", appWidgetId);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    protected static void updateAllDreamScreenWidgets(Context context) {
        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, ControlWidget.class));
        Intent updateIntent = new Intent(context, ControlWidget.class);
        updateIntent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        updateIntent.putExtra(DREAMSCREEN_WIDGET_IDS_KEY, ids);
        context.sendBroadcast(updateIntent);
    }

    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.i(tag, "onDeleted");
        deletePreferenceKey(context, "ip");
        deletePreferenceKey(context, "productId");
    }

    public static void setPreferenceString(Context context, String key, String value) {
        Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(key, value);
        prefs.apply();
    }

    public static void setPreferenceInt(Context context, String key, int value) {
        Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putInt(key, value);
        prefs.apply();
    }

    @Nullable
    public static String getPreferenceString(Context context, String key) {
        return context.getSharedPreferences(PREFS_NAME, 0).getString(key, null);
    }

    public static int getPreferenceInt(Context context, String key) {
        return context.getSharedPreferences(PREFS_NAME, 0).getInt(key, -1);
    }

    public static void deletePreferenceKey(Context context, String key) {
        Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(key);
        prefs.apply();
    }

    public void setMode(int mode, String ipAddress) {
        Log.i(tag, "setMode " + mode + " " + ipAddress);
        sendUDPWrite(3, 1, new byte[]{(byte) mode}, ipAddress);
    }

    public void setHdmiInput(int hdmiInput, String ipAddress) {
        Log.i(tag, "setHdmiInput " + hdmiInput + " " + ipAddress);
        sendUDPWrite(3, 32, new byte[]{(byte) hdmiInput}, ipAddress);
    }

    /* access modifiers changed from: protected */
    public void sendUDPWrite(byte command1, byte command2, byte[] payload, String ipAddress) {
        ByteArrayOutputStream response = new ByteArrayOutputStream();
        response.write(252);
        response.write((byte) (payload.length + 5));
        response.write(-1);
        response.write(17);
        response.write(command1);
        response.write(command2);
        for (byte b : payload) {
            response.write(b);
        }
        response.write(Light.uartComm_calculate_crc8(response.toByteArray()));
        new UDPUnicast(ipAddress).execute(new byte[][]{response.toByteArray()});
    }
}
