package com.amazonaws.mobileconnectors.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import com.amazonaws.AmazonClientException;
import com.amazonaws.util.Base64;
import com.amazonaws.util.StringUtils;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

public class ClientContext {
    private static final Log LOGGER = LogFactory.getLog(ClientContext.class);
    static final String SHARED_PREFERENCES = "com.amazonaws.common";
    private String base64String;
    private final JSONObject json;

    public ClientContext(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context can't be null");
        }
        this.json = new JSONObject();
        try {
            this.json.put("client", getClientInfo(context)).put("env", getDeviceInfo(context));
        } catch (JSONException e) {
            throw new AmazonClientException("Failed to build client context", e);
        }
    }

    public static String getInstallationId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES, 0);
        String installationId = sp.getString("installation_id", null);
        if (installationId != null) {
            return installationId;
        }
        String installationId2 = UUID.randomUUID().toString();
        sp.edit().putString("installation_id", installationId2).commit();
        return installationId2;
    }

    static JSONObject getClientInfo(Context context) throws JSONException {
        JSONObject client = new JSONObject();
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            client.put("installation_id", getInstallationId(context)).put("app_version_name", packageInfo.versionName).put("app_version_code", String.valueOf(packageInfo.versionCode)).put("app_package_name", packageInfo.packageName);
            CharSequence title = packageManager.getApplicationLabel(applicationInfo);
            client.put("app_title", title == null ? "Unknown" : title.toString());
        } catch (NameNotFoundException e) {
            LOGGER.warn("Failed to load package info: " + context.getPackageName(), e);
        }
        return client;
    }

    static JSONObject getDeviceInfo(Context context) throws JSONException {
        return new JSONObject().put("platform", "Android").put("model", Build.MODEL).put("make", Build.MANUFACTURER).put("platform_version", VERSION.RELEASE).put("locale", Locale.getDefault().toString());
    }

    public void putCustomContext(Map<String, String> map) {
        this.base64String = null;
        try {
            this.json.put("custom", new JSONObject(map));
        } catch (JSONException e) {
            throw new AmazonClientException("Failed to add user defined context", e);
        }
    }

    public void putServiceContext(String service, Map<String, String> map) {
        this.base64String = null;
        try {
            if (!this.json.has("services")) {
                this.json.put("services", new JSONObject());
            }
            this.json.getJSONObject("services").put(service, new JSONObject(map));
        } catch (JSONException e) {
            throw new AmazonClientException("Failed to add service context", e);
        }
    }

    public String toBase64String() {
        if (this.base64String == null) {
            synchronized (this) {
                if (this.base64String == null) {
                    this.base64String = Base64.encodeAsString(this.json.toString().getBytes(StringUtils.UTF8));
                }
            }
        }
        return this.base64String;
    }

    /* access modifiers changed from: 0000 */
    public JSONObject getJson() {
        return this.json;
    }
}
