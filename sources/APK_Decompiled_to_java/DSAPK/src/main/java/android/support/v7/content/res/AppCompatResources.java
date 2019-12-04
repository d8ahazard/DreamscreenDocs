package androidx.appcompat.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatDrawableManager;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;

import androidx.core.content.res.ColorStateListInflaterCompat;

import java.util.WeakHashMap;

public final class AppCompatResources {
    private static final String LOG_TAG = "AppCompatResources";
    private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal<>();
    private static final Object sColorStateCacheLock = new Object();
    private static final WeakHashMap<Context, SparseArray<ColorStateListCacheEntry>> sColorStateCaches = new WeakHashMap<>(0);

    private static class ColorStateListCacheEntry {
        final Configuration configuration;
        final ColorStateList value;

        ColorStateListCacheEntry(@NonNull ColorStateList value2, @NonNull Configuration configuration2) {
            this.value = value2;
            this.configuration = configuration2;
        }
    }

    private AppCompatResources() {
    }

    public static ColorStateList getColorStateList(@NonNull Context context, @ColorRes int resId) {
        if (VERSION.SDK_INT >= 23) {
            return context.getColorStateList(resId);
        }
        ColorStateList csl = getCachedColorStateList(context, resId);
        if (csl != null) {
            return csl;
        }
        ColorStateList csl2 = inflateColorStateList(context, resId);
        if (csl2 == null) {
            return ContextCompat.getColorStateList(context, resId);
        }
        addColorStateListToCache(context, resId, csl2);
        return csl2;
    }

    @Nullable
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int resId) {
        return AppCompatDrawableManager.get().getDrawable(context, resId);
    }

    @Nullable
    private static ColorStateList inflateColorStateList(Context context, int resId) {
        ColorStateList colorStateList = null;
        if (isColorInt(context, resId)) {
            return colorStateList;
        }
        Resources r = context.getResources();
        try {
            return ColorStateListInflaterCompat.createFromXml(r, r.getXml(resId), context.getTheme());
        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to inflate ColorStateList, leaving it to the framework", e);
            return colorStateList;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        return null;
     */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.content.res.ColorStateList getCachedColorStateList(@androidx.annotation.NonNull android.content.Context r5, @androidx.annotation.ColorRes int r6) {
        /*
            java.lang.Object r3 = sColorStateCacheLock
            monitor-enter(r3)
            java.util.WeakHashMap<android.content.Context, android.util.SparseArray<android.support.v7.content.res.AppCompatResources$ColorStateListCacheEntry>> r2 = sColorStateCaches     // Catch:{ all -> 0x0035 }
            java.lang.Object r0 = r2.get(r5)     // Catch:{ all -> 0x0035 }
            android.util.SparseArray r0 = (android.util.SparseArray) r0     // Catch:{ all -> 0x0035 }
            if (r0 == 0) goto L_0x0032
            int r2 = r0.size()     // Catch:{ all -> 0x0035 }
            if (r2 <= 0) goto L_0x0032
            java.lang.Object r1 = r0.get(r6)     // Catch:{ all -> 0x0035 }
            android.support.v7.content.res.AppCompatResources$ColorStateListCacheEntry r1 = (android.support.v7.content.res.AppCompatResources.ColorStateListCacheEntry) r1     // Catch:{ all -> 0x0035 }
            if (r1 == 0) goto L_0x0032
            android.content.res.Configuration r2 = r1.configuration     // Catch:{ all -> 0x0035 }
            android.content.res.Resources r4 = r5.getResources()     // Catch:{ all -> 0x0035 }
            android.content.res.Configuration r4 = r4.getConfiguration()     // Catch:{ all -> 0x0035 }
            boolean r2 = r2.equals(r4)     // Catch:{ all -> 0x0035 }
            if (r2 == 0) goto L_0x002f
            android.content.res.ColorStateList r2 = r1.value     // Catch:{ all -> 0x0035 }
            monitor-exit(r3)     // Catch:{ all -> 0x0035 }
        L_0x002e:
            return r2
        L_0x002f:
            r0.remove(r6)     // Catch:{ all -> 0x0035 }
        L_0x0032:
            monitor-exit(r3)     // Catch:{ all -> 0x0035 }
            r2 = 0
            goto L_0x002e
        L_0x0035:
            r2 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0035 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.content.res.AppCompatResources.getCachedColorStateList(android.content.Context, int):android.content.res.ColorStateList");
    }

    private static void addColorStateListToCache(@NonNull Context context, @ColorRes int resId, @NonNull ColorStateList value) {
        synchronized (sColorStateCacheLock) {
            SparseArray<ColorStateListCacheEntry> entries = (SparseArray) sColorStateCaches.get(context);
            if (entries == null) {
                entries = new SparseArray<>();
                sColorStateCaches.put(context, entries);
            }
            entries.append(resId, new ColorStateListCacheEntry(value, context.getResources().getConfiguration()));
        }
    }

    private static boolean isColorInt(@NonNull Context context, @ColorRes int resId) {
        Resources r = context.getResources();
        TypedValue value = getTypedValue();
        r.getValue(resId, value, true);
        if (value.type < 28 || value.type > 31) {
            return false;
        }
        return true;
    }

    @NonNull
    private static TypedValue getTypedValue() {
        TypedValue tv = (TypedValue) TL_TYPED_VALUE.get();
        if (tv != null) {
            return tv;
        }
        TypedValue tv2 = new TypedValue();
        TL_TYPED_VALUE.set(tv2);
        return tv2;
    }
}
