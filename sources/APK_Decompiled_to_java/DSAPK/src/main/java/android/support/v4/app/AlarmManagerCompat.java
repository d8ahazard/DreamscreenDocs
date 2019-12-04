package androidx.core.app;

import android.app.AlarmManager;
import android.app.AlarmManager.AlarmClockInfo;
import android.app.PendingIntent;
import android.os.Build.VERSION;
import androidx.annotation.NonNull;

public final class AlarmManagerCompat {
    public static void setAlarmClock(@NonNull AlarmManager alarmManager, long triggerTime, @NonNull PendingIntent showIntent, @NonNull PendingIntent operation) {
        if (VERSION.SDK_INT >= 21) {
            alarmManager.setAlarmClock(new AlarmClockInfo(triggerTime, showIntent), operation);
        } else {
            setExact(alarmManager, 0, triggerTime, operation);
        }
    }

    public static void setAndAllowWhileIdle(@NonNull AlarmManager alarmManager, int type, long triggerAtMillis, @NonNull PendingIntent operation) {
        if (VERSION.SDK_INT >= 23) {
            alarmManager.setAndAllowWhileIdle(type, triggerAtMillis, operation);
        } else {
            alarmManager.set(type, triggerAtMillis, operation);
        }
    }

    public static void setExact(@NonNull AlarmManager alarmManager, int type, long triggerAtMillis, @NonNull PendingIntent operation) {
        if (VERSION.SDK_INT >= 19) {
            alarmManager.setExact(type, triggerAtMillis, operation);
        } else {
            alarmManager.set(type, triggerAtMillis, operation);
        }
    }

    public static void setExactAndAllowWhileIdle(@NonNull AlarmManager alarmManager, int type, long triggerAtMillis, @NonNull PendingIntent operation) {
        if (VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(type, triggerAtMillis, operation);
        } else {
            setExact(alarmManager, type, triggerAtMillis, operation);
        }
    }

    private AlarmManagerCompat() {
    }
}
