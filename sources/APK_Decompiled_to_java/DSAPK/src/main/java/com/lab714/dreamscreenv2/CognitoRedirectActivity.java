package com.lab714.dreamscreenv2;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import com.amazonaws.mobileconnectors.cognitoauth.util.ClientConstants;

public class CognitoRedirectActivity extends AppCompatActivity {
    private static final String dsv2Prefs = "dsv2Prefs";
    private static final String tag = "CognitoRedirectActivity";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_cognito_redirect);
        Log.i(tag, "onCreate");
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Intent activityIntent = getIntent();
        Log.i(tag, "onResume " + activityIntent.getData());
        Uri appRedirectUri = Uri.parse(getString(R.string.app_signout_redirect));
        if (activityIntent.getData() != null && appRedirectUri.getHost().equals(activityIntent.getData().getHost())) {
            Editor editor = getSharedPreferences(dsv2Prefs, 0).edit();
            editor.putString(ClientConstants.DOMAIN_QUERY_PARAM_REDIRECT_URI, activityIntent.getData().toString());
            editor.commit();
        }
        finish();
    }
}
