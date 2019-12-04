package androidx.core.app;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat.Action;
import android.util.Log;
import android.util.SparseArray;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RequiresApi(16)
class NotificationCompatJellybean {
    static final String EXTRA_ALLOW_GENERATED_REPLIES = "android.support.allowGeneratedReplies";
    static final String EXTRA_DATA_ONLY_REMOTE_INPUTS = "android.support.dataRemoteInputs";
    private static final String KEY_ACTION_INTENT = "actionIntent";
    private static final String KEY_ALLOWED_DATA_TYPES = "allowedDataTypes";
    private static final String KEY_ALLOW_FREE_FORM_INPUT = "allowFreeFormInput";
    private static final String KEY_CHOICES = "choices";
    private static final String KEY_DATA_ONLY_REMOTE_INPUTS = "dataOnlyRemoteInputs";
    private static final String KEY_EXTRAS = "extras";
    private static final String KEY_ICON = "icon";
    private static final String KEY_LABEL = "label";
    private static final String KEY_REMOTE_INPUTS = "remoteInputs";
    private static final String KEY_RESULT_KEY = "resultKey";
    private static final String KEY_TITLE = "title";
    public static final String TAG = "NotificationCompat";
    private static Class<?> sActionClass;
    private static Field sActionIconField;
    private static Field sActionIntentField;
    private static Field sActionTitleField;
    private static boolean sActionsAccessFailed;
    private static Field sActionsField;
    private static final Object sActionsLock = new Object();
    private static Field sExtrasField;
    private static boolean sExtrasFieldAccessFailed;
    private static final Object sExtrasLock = new Object();

    NotificationCompatJellybean() {
    }

    public static SparseArray<Bundle> buildActionExtrasMap(List<Bundle> actionExtrasList) {
        SparseArray<Bundle> actionExtrasMap = null;
        int count = actionExtrasList.size();
        for (int i = 0; i < count; i++) {
            Bundle actionExtras = (Bundle) actionExtrasList.get(i);
            if (actionExtras != null) {
                if (actionExtrasMap == null) {
                    actionExtrasMap = new SparseArray<>();
                }
                actionExtrasMap.put(i, actionExtras);
            }
        }
        return actionExtrasMap;
    }

    public static Bundle getExtras(Notification notif) {
        synchronized (sExtrasLock) {
            if (sExtrasFieldAccessFailed) {
                return null;
            }
            try {
                if (sExtrasField == null) {
                    Field extrasField = Notification.class.getDeclaredField(KEY_EXTRAS);
                    if (!Bundle.class.isAssignableFrom(extrasField.getType())) {
                        Log.e(TAG, "Notification.extras field is not of type Bundle");
                        sExtrasFieldAccessFailed = true;
                        return null;
                    }
                    extrasField.setAccessible(true);
                    sExtrasField = extrasField;
                }
                Bundle extras = (Bundle) sExtrasField.get(notif);
                if (extras == null) {
                    extras = new Bundle();
                    sExtrasField.set(notif, extras);
                }
                return extras;
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Unable to access notification extras", e);
                sExtrasFieldAccessFailed = true;
                return null;
            } catch (NoSuchFieldException e2) {
                Log.e(TAG, "Unable to access notification extras", e2);
                sExtrasFieldAccessFailed = true;
                return null;
            }
        }
    }

    public static Action readAction(int icon, CharSequence title, PendingIntent actionIntent, Bundle extras) {
        RemoteInput[] remoteInputs = null;
        RemoteInput[] dataOnlyRemoteInputs = null;
        boolean allowGeneratedReplies = false;
        if (extras != null) {
            remoteInputs = fromBundleArray(getBundleArrayFromBundle(extras, NotificationCompatExtras.EXTRA_REMOTE_INPUTS));
            dataOnlyRemoteInputs = fromBundleArray(getBundleArrayFromBundle(extras, EXTRA_DATA_ONLY_REMOTE_INPUTS));
            allowGeneratedReplies = extras.getBoolean(EXTRA_ALLOW_GENERATED_REPLIES);
        }
        return new Action(icon, title, actionIntent, extras, remoteInputs, dataOnlyRemoteInputs, allowGeneratedReplies);
    }

    public static Bundle writeActionAndGetExtras(Builder builder, Action action) {
        builder.addAction(action.getIcon(), action.getTitle(), action.getActionIntent());
        Bundle actionExtras = new Bundle(action.getExtras());
        if (action.getRemoteInputs() != null) {
            actionExtras.putParcelableArray(NotificationCompatExtras.EXTRA_REMOTE_INPUTS, toBundleArray(action.getRemoteInputs()));
        }
        if (action.getDataOnlyRemoteInputs() != null) {
            actionExtras.putParcelableArray(EXTRA_DATA_ONLY_REMOTE_INPUTS, toBundleArray(action.getDataOnlyRemoteInputs()));
        }
        actionExtras.putBoolean(EXTRA_ALLOW_GENERATED_REPLIES, action.getAllowGeneratedReplies());
        return actionExtras;
    }

    public static int getActionCount(Notification notif) {
        int i;
        synchronized (sActionsLock) {
            Object[] actionObjects = getActionObjectsLocked(notif);
            i = actionObjects != null ? actionObjects.length : 0;
        }
        return i;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static androidx.core.app.NotificationCompat.Action getAction(android.app.Notification r10, int r11) {
        /*
            java.lang.Object r8 = sActionsLock
            monitor-enter(r8)
            java.lang.Object[] r3 = getActionObjectsLocked(r10)     // Catch:{ IllegalAccessException -> 0x003c }
            if (r3 == 0) goto L_0x0047
            r2 = r3[r11]     // Catch:{ IllegalAccessException -> 0x003c }
            r0 = 0
            android.os.Bundle r5 = getExtras(r10)     // Catch:{ IllegalAccessException -> 0x003c }
            if (r5 == 0) goto L_0x0020
            java.lang.String r6 = "android.support.actionExtras"
            android.util.SparseArray r1 = r5.getSparseParcelableArray(r6)     // Catch:{ IllegalAccessException -> 0x003c }
            if (r1 == 0) goto L_0x0020
            java.lang.Object r0 = r1.get(r11)     // Catch:{ IllegalAccessException -> 0x003c }
            android.os.Bundle r0 = (android.os.Bundle) r0     // Catch:{ IllegalAccessException -> 0x003c }
        L_0x0020:
            java.lang.reflect.Field r6 = sActionIconField     // Catch:{ IllegalAccessException -> 0x003c }
            int r9 = r6.getInt(r2)     // Catch:{ IllegalAccessException -> 0x003c }
            java.lang.reflect.Field r6 = sActionTitleField     // Catch:{ IllegalAccessException -> 0x003c }
            java.lang.Object r6 = r6.get(r2)     // Catch:{ IllegalAccessException -> 0x003c }
            java.lang.CharSequence r6 = (java.lang.CharSequence) r6     // Catch:{ IllegalAccessException -> 0x003c }
            java.lang.reflect.Field r7 = sActionIntentField     // Catch:{ IllegalAccessException -> 0x003c }
            java.lang.Object r7 = r7.get(r2)     // Catch:{ IllegalAccessException -> 0x003c }
            android.app.PendingIntent r7 = (android.app.PendingIntent) r7     // Catch:{ IllegalAccessException -> 0x003c }
            android.support.v4.app.NotificationCompat$Action r6 = readAction(r9, r6, r7, r0)     // Catch:{ IllegalAccessException -> 0x003c }
            monitor-exit(r8)     // Catch:{ all -> 0x004a }
        L_0x003b:
            return r6
        L_0x003c:
            r4 = move-exception
            java.lang.String r6 = "NotificationCompat"
            java.lang.String r7 = "Unable to access notification actions"
            android.util.Log.e(r6, r7, r4)     // Catch:{ all -> 0x004a }
            r6 = 1
            sActionsAccessFailed = r6     // Catch:{ all -> 0x004a }
        L_0x0047:
            monitor-exit(r8)     // Catch:{ all -> 0x004a }
            r6 = 0
            goto L_0x003b
        L_0x004a:
            r6 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x004a }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.NotificationCompatJellybean.getAction(android.app.Notification, int):android.support.v4.app.NotificationCompat$Action");
    }

    private static Object[] getActionObjectsLocked(Notification notif) {
        synchronized (sActionsLock) {
            if (!ensureActionReflectionReadyLocked()) {
                return null;
            }
            try {
                Object[] objArr = (Object[]) sActionsField.get(notif);
                return objArr;
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Unable to access notification actions", e);
                sActionsAccessFailed = true;
                return null;
            }
        }
    }

    private static boolean ensureActionReflectionReadyLocked() {
        boolean z = true;
        if (sActionsAccessFailed) {
            return false;
        }
        try {
            if (sActionsField == null) {
                sActionClass = Class.forName("android.app.Notification$Action");
                sActionIconField = sActionClass.getDeclaredField(KEY_ICON);
                sActionTitleField = sActionClass.getDeclaredField(KEY_TITLE);
                sActionIntentField = sActionClass.getDeclaredField(KEY_ACTION_INTENT);
                sActionsField = Notification.class.getDeclaredField("actions");
                sActionsField.setAccessible(true);
            }
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Unable to access notification actions", e);
            sActionsAccessFailed = true;
        } catch (NoSuchFieldException e2) {
            Log.e(TAG, "Unable to access notification actions", e2);
            sActionsAccessFailed = true;
        }
        if (sActionsAccessFailed) {
            z = false;
        }
        return z;
    }

    static Action getActionFromBundle(Bundle bundle) {
        Bundle extras = bundle.getBundle(KEY_EXTRAS);
        boolean allowGeneratedReplies = false;
        if (extras != null) {
            allowGeneratedReplies = extras.getBoolean(EXTRA_ALLOW_GENERATED_REPLIES, false);
        }
        return new Action(bundle.getInt(KEY_ICON), bundle.getCharSequence(KEY_TITLE), (PendingIntent) bundle.getParcelable(KEY_ACTION_INTENT), bundle.getBundle(KEY_EXTRAS), fromBundleArray(getBundleArrayFromBundle(bundle, KEY_REMOTE_INPUTS)), fromBundleArray(getBundleArrayFromBundle(bundle, KEY_DATA_ONLY_REMOTE_INPUTS)), allowGeneratedReplies);
    }

    static Bundle getBundleForAction(Action action) {
        Bundle actionExtras;
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ICON, action.getIcon());
        bundle.putCharSequence(KEY_TITLE, action.getTitle());
        bundle.putParcelable(KEY_ACTION_INTENT, action.getActionIntent());
        if (action.getExtras() != null) {
            actionExtras = new Bundle(action.getExtras());
        } else {
            actionExtras = new Bundle();
        }
        actionExtras.putBoolean(EXTRA_ALLOW_GENERATED_REPLIES, action.getAllowGeneratedReplies());
        bundle.putBundle(KEY_EXTRAS, actionExtras);
        bundle.putParcelableArray(KEY_REMOTE_INPUTS, toBundleArray(action.getRemoteInputs()));
        return bundle;
    }

    private static RemoteInput fromBundle(Bundle data) {
        ArrayList<String> allowedDataTypesAsList = data.getStringArrayList(KEY_ALLOWED_DATA_TYPES);
        Set<String> allowedDataTypes = new HashSet<>();
        if (allowedDataTypesAsList != null) {
            Iterator it = allowedDataTypesAsList.iterator();
            while (it.hasNext()) {
                allowedDataTypes.add((String) it.next());
            }
        }
        return new RemoteInput(data.getString(KEY_RESULT_KEY), data.getCharSequence(KEY_LABEL), data.getCharSequenceArray(KEY_CHOICES), data.getBoolean(KEY_ALLOW_FREE_FORM_INPUT), data.getBundle(KEY_EXTRAS), allowedDataTypes);
    }

    private static Bundle toBundle(RemoteInput remoteInput) {
        Bundle data = new Bundle();
        data.putString(KEY_RESULT_KEY, remoteInput.getResultKey());
        data.putCharSequence(KEY_LABEL, remoteInput.getLabel());
        data.putCharSequenceArray(KEY_CHOICES, remoteInput.getChoices());
        data.putBoolean(KEY_ALLOW_FREE_FORM_INPUT, remoteInput.getAllowFreeFormInput());
        data.putBundle(KEY_EXTRAS, remoteInput.getExtras());
        Set<String> allowedDataTypes = remoteInput.getAllowedDataTypes();
        if (allowedDataTypes != null && !allowedDataTypes.isEmpty()) {
            ArrayList<String> allowedDataTypesAsList = new ArrayList<>(allowedDataTypes.size());
            for (String type : allowedDataTypes) {
                allowedDataTypesAsList.add(type);
            }
            data.putStringArrayList(KEY_ALLOWED_DATA_TYPES, allowedDataTypesAsList);
        }
        return data;
    }

    private static RemoteInput[] fromBundleArray(Bundle[] bundles) {
        if (bundles == null) {
            return null;
        }
        RemoteInput[] remoteInputs = new RemoteInput[bundles.length];
        for (int i = 0; i < bundles.length; i++) {
            remoteInputs[i] = fromBundle(bundles[i]);
        }
        return remoteInputs;
    }

    private static Bundle[] toBundleArray(RemoteInput[] remoteInputs) {
        if (remoteInputs == null) {
            return null;
        }
        Bundle[] bundles = new Bundle[remoteInputs.length];
        for (int i = 0; i < remoteInputs.length; i++) {
            bundles[i] = toBundle(remoteInputs[i]);
        }
        return bundles;
    }

    private static Bundle[] getBundleArrayFromBundle(Bundle bundle, String key) {
        Parcelable[] array = bundle.getParcelableArray(key);
        if ((array instanceof Bundle[]) || array == null) {
            return (Bundle[]) array;
        }
        Bundle[] typedArray = (Bundle[]) Arrays.copyOf(array, array.length, Bundle[].class);
        bundle.putParcelableArray(key, typedArray);
        return typedArray;
    }
}
