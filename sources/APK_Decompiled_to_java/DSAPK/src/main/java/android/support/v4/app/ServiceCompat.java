package androidx.core.app;

import android.app.Service;
import android.os.Build.VERSION;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class ServiceCompat {
    public static final int START_STICKY = 1;
    public static final int STOP_FOREGROUND_DETACH = 2;
    public static final int STOP_FOREGROUND_REMOVE = 1;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StopForegroundFlags {
    }

    private ServiceCompat() {
    }

    public static void stopForeground(@NonNull Service service, int flags) {
        if (VERSION.SDK_INT >= 24) {
            service.stopForeground(flags);
        } else {
            service.stopForeground((flags & 1) != 0);
        }
    }
}
