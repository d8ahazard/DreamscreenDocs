package androidx.core.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

public class AppLaunchChecker {
    private static final String KEY_STARTED_FROM_LAUNCHER = "startedFromLauncher";
    private static final String SHARED_PREFS_NAME = "android.support.AppLaunchChecker";

    public static boolean hasStartedFromLauncher(@NonNull Context context) {
        return context.getSharedPreferences(SHARED_PREFS_NAME, 0).getBoolean(KEY_STARTED_FROM_LAUNCHER, false);
    }

    public static void onActivityCreate(@NonNull AppCompatActivity activity) {
        SharedPreferences sp = activity.getSharedPreferences(SHARED_PREFS_NAME, 0);
        if (!sp.getBoolean(KEY_STARTED_FROM_LAUNCHER, false)) {
            Intent launchIntent = activity.getIntent();
            if (launchIntent != null && "android.intent.action.MAIN".equals(launchIntent.getAction())) {
                if (launchIntent.hasCategory("android.intent.category.LAUNCHER") || launchIntent.hasCategory(IntentCompat.CATEGORY_LEANBACK_LAUNCHER)) {
                    sp.edit().putBoolean(KEY_STARTED_FROM_LAUNCHER, true).apply();
                }
            }
        }
    }
}
