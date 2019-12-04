package com.amazonaws.mobile.config;

import android.content.Context;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;

public class AWSConfiguration {
    private static final String DEFAULT = "Default";
    private static final String DEFAULT_IDENTIFIER = "awsconfiguration";
    private String configName;
    private JSONObject mJSONObject;

    public AWSConfiguration(Context context) {
        this(context, getConfigResourceId(context));
    }

    private static int getConfigResourceId(Context context) {
        try {
            return context.getResources().getIdentifier(DEFAULT_IDENTIFIER, "raw", context.getPackageName());
        } catch (Exception e) {
            throw new RuntimeException("Failed to read awsconfiguration.json please check that it is correctly formed.", e);
        }
    }

    public AWSConfiguration(Context context, int configResourceId) {
        this(context, configResourceId, DEFAULT);
    }

    public AWSConfiguration(Context context, int configResourceId, String configName2) {
        this.configName = configName2;
        readInputJson(context, configResourceId);
    }

    private void readInputJson(Context context, int resourceId) {
        try {
            Scanner in = new Scanner(context.getResources().openRawResource(resourceId));
            StringBuilder sb = new StringBuilder();
            while (in.hasNextLine()) {
                sb.append(in.nextLine());
            }
            in.close();
            this.mJSONObject = new JSONObject(sb.toString());
        } catch (Exception je) {
            throw new RuntimeException("Failed to read awsconfiguration.json please check that it is correctly formed.", je);
        }
    }

    public JSONObject optJsonObject(String key) {
        try {
            JSONObject value = this.mJSONObject.getJSONObject(key);
            if (value.has(this.configName)) {
                value = value.getJSONObject(this.configName);
            }
            return new JSONObject(value.toString());
        } catch (JSONException e) {
            return null;
        }
    }

    public String getUserAgent() {
        try {
            return this.mJSONObject.getString("UserAgent");
        } catch (JSONException e) {
            return "";
        }
    }

    public void setConfiguration(String configurationName) {
        this.configName = configurationName;
    }

    public String getConfiguration() {
        return this.configName;
    }

    public String toString() {
        return this.mJSONObject.toString();
    }
}
