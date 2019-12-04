package androidx.core.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.HashMap;

public final class LocalBroadcastManager {
    private static final boolean DEBUG = false;
    static final int MSG_EXEC_PENDING_BROADCASTS = 1;
    private static final String TAG = "LocalBroadcastManager";
    private static androidx.localbroadcastmanager.content.LocalBroadcastManager mInstance;
    private static final Object mLock = new Object();
    private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap<>();
    private final Context mAppContext;
    private final Handler mHandler;
    private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList<>();
    private final HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> mReceivers = new HashMap<>();

    private static final class BroadcastRecord {
        final Intent intent;
        final ArrayList<ReceiverRecord> receivers;

        BroadcastRecord(Intent _intent, ArrayList<ReceiverRecord> _receivers) {
            this.intent = _intent;
            this.receivers = _receivers;
        }
    }

    private static final class ReceiverRecord {
        boolean broadcasting;
        boolean dead;
        final IntentFilter filter;
        final BroadcastReceiver receiver;

        ReceiverRecord(IntentFilter _filter, BroadcastReceiver _receiver) {
            this.filter = _filter;
            this.receiver = _receiver;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder(128);
            builder.append("Receiver{");
            builder.append(this.receiver);
            builder.append(" filter=");
            builder.append(this.filter);
            if (this.dead) {
                builder.append(" DEAD");
            }
            builder.append("}");
            return builder.toString();
        }
    }

    @NonNull
    public static androidx.localbroadcastmanager.content.LocalBroadcastManager getInstance(@NonNull Context context) {
        androidx.localbroadcastmanager.content.LocalBroadcastManager localBroadcastManager;
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = new androidx.localbroadcastmanager.content.LocalBroadcastManager(context.getApplicationContext());
            }
            localBroadcastManager = mInstance;
        }
        return localBroadcastManager;
    }

    private LocalBroadcastManager(Context context) {
        this.mAppContext = context;
        this.mHandler = new Handler(context.getMainLooper()) {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        androidx.localbroadcastmanager.content.LocalBroadcastManager.this.executePendingBroadcasts();
                        return;
                    default:
                        super.handleMessage(msg);
                        return;
                }
            }
        };
    }

    public void registerReceiver(@NonNull BroadcastReceiver receiver, @NonNull IntentFilter filter) {
        synchronized (this.mReceivers) {
            ReceiverRecord entry = new ReceiverRecord(filter, receiver);
            ArrayList<ReceiverRecord> filters = (ArrayList) this.mReceivers.get(receiver);
            if (filters == null) {
                filters = new ArrayList<>(1);
                this.mReceivers.put(receiver, filters);
            }
            filters.add(entry);
            for (int i = 0; i < filter.countActions(); i++) {
                String action = filter.getAction(i);
                ArrayList<ReceiverRecord> entries = (ArrayList) this.mActions.get(action);
                if (entries == null) {
                    entries = new ArrayList<>(1);
                    this.mActions.put(action, entries);
                }
                entries.add(entry);
            }
        }
    }

    public void unregisterReceiver(@NonNull BroadcastReceiver receiver) {
        synchronized (this.mReceivers) {
            ArrayList<ReceiverRecord> filters = (ArrayList) this.mReceivers.remove(receiver);
            if (filters != null) {
                for (int i = filters.size() - 1; i >= 0; i--) {
                    ReceiverRecord filter = (ReceiverRecord) filters.get(i);
                    filter.dead = true;
                    for (int j = 0; j < filter.filter.countActions(); j++) {
                        String action = filter.filter.getAction(j);
                        ArrayList<ReceiverRecord> receivers = (ArrayList) this.mActions.get(action);
                        if (receivers != null) {
                            for (int k = receivers.size() - 1; k >= 0; k--) {
                                ReceiverRecord rec = (ReceiverRecord) receivers.get(k);
                                if (rec.receiver == receiver) {
                                    rec.dead = true;
                                    receivers.remove(k);
                                }
                            }
                            if (receivers.size() <= 0) {
                                this.mActions.remove(action);
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:64:?, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean sendBroadcast(@androidx.annotation.NonNull android.content.Intent r18) {
        /*
            r17 = this;
            r0 = r17
            java.util.HashMap<android.content.BroadcastReceiver, java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$ReceiverRecord>> r15 = r0.mReceivers
            monitor-enter(r15)
            java.lang.String r2 = r18.getAction()     // Catch:{ all -> 0x010b }
            r0 = r17
            android.content.Context r1 = r0.mAppContext     // Catch:{ all -> 0x010b }
            android.content.ContentResolver r1 = r1.getContentResolver()     // Catch:{ all -> 0x010b }
            r0 = r18
            java.lang.String r3 = r0.resolveTypeIfNeeded(r1)     // Catch:{ all -> 0x010b }
            android.net.Uri r5 = r18.getData()     // Catch:{ all -> 0x010b }
            java.lang.String r4 = r18.getScheme()     // Catch:{ all -> 0x010b }
            java.util.Set r6 = r18.getCategories()     // Catch:{ all -> 0x010b }
            int r1 = r18.getFlags()     // Catch:{ all -> 0x010b }
            r1 = r1 & 8
            if (r1 == 0) goto L_0x00ce
            r8 = 1
        L_0x002c:
            if (r8 == 0) goto L_0x0062
            java.lang.String r1 = "LocalBroadcastManager"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x010b }
            r7.<init>()     // Catch:{ all -> 0x010b }
            java.lang.String r16 = "Resolving type "
            r0 = r16
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            java.lang.StringBuilder r7 = r7.append(r3)     // Catch:{ all -> 0x010b }
            java.lang.String r16 = " scheme "
            r0 = r16
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            java.lang.StringBuilder r7 = r7.append(r4)     // Catch:{ all -> 0x010b }
            java.lang.String r16 = " of intent "
            r0 = r16
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            r0 = r18
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x010b }
            android.util.Log.v(r1, r7)     // Catch:{ all -> 0x010b }
        L_0x0062:
            r0 = r17
            java.util.HashMap<java.lang.String, java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$ReceiverRecord>> r1 = r0.mActions     // Catch:{ all -> 0x010b }
            java.lang.String r7 = r18.getAction()     // Catch:{ all -> 0x010b }
            java.lang.Object r9 = r1.get(r7)     // Catch:{ all -> 0x010b }
            java.util.ArrayList r9 = (java.util.ArrayList) r9     // Catch:{ all -> 0x010b }
            if (r9 == 0) goto L_0x0175
            if (r8 == 0) goto L_0x008e
            java.lang.String r1 = "LocalBroadcastManager"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x010b }
            r7.<init>()     // Catch:{ all -> 0x010b }
            java.lang.String r16 = "Action list: "
            r0 = r16
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            java.lang.StringBuilder r7 = r7.append(r9)     // Catch:{ all -> 0x010b }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x010b }
            android.util.Log.v(r1, r7)     // Catch:{ all -> 0x010b }
        L_0x008e:
            r14 = 0
            r10 = 0
        L_0x0090:
            int r1 = r9.size()     // Catch:{ all -> 0x010b }
            if (r10 >= r1) goto L_0x013c
            java.lang.Object r13 = r9.get(r10)     // Catch:{ all -> 0x010b }
            android.support.v4.content.LocalBroadcastManager$ReceiverRecord r13 = (android.support.v4.content.LocalBroadcastManager.ReceiverRecord) r13     // Catch:{ all -> 0x010b }
            if (r8 == 0) goto L_0x00be
            java.lang.String r1 = "LocalBroadcastManager"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x010b }
            r7.<init>()     // Catch:{ all -> 0x010b }
            java.lang.String r16 = "Matching against filter "
            r0 = r16
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            android.content.IntentFilter r0 = r13.filter     // Catch:{ all -> 0x010b }
            r16 = r0
            r0 = r16
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x010b }
            android.util.Log.v(r1, r7)     // Catch:{ all -> 0x010b }
        L_0x00be:
            boolean r1 = r13.broadcasting     // Catch:{ all -> 0x010b }
            if (r1 == 0) goto L_0x00d1
            if (r8 == 0) goto L_0x00cb
            java.lang.String r1 = "LocalBroadcastManager"
            java.lang.String r7 = "  Filter's target already added"
            android.util.Log.v(r1, r7)     // Catch:{ all -> 0x010b }
        L_0x00cb:
            int r10 = r10 + 1
            goto L_0x0090
        L_0x00ce:
            r8 = 0
            goto L_0x002c
        L_0x00d1:
            android.content.IntentFilter r1 = r13.filter     // Catch:{ all -> 0x010b }
            java.lang.String r7 = "LocalBroadcastManager"
            int r11 = r1.match(r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x010b }
            if (r11 < 0) goto L_0x010e
            if (r8 == 0) goto L_0x00fd
            java.lang.String r1 = "LocalBroadcastManager"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x010b }
            r7.<init>()     // Catch:{ all -> 0x010b }
            java.lang.String r16 = "  Filter matched!  match=0x"
            r0 = r16
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            java.lang.String r16 = java.lang.Integer.toHexString(r11)     // Catch:{ all -> 0x010b }
            r0 = r16
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x010b }
            android.util.Log.v(r1, r7)     // Catch:{ all -> 0x010b }
        L_0x00fd:
            if (r14 != 0) goto L_0x0104
            java.util.ArrayList r14 = new java.util.ArrayList     // Catch:{ all -> 0x010b }
            r14.<init>()     // Catch:{ all -> 0x010b }
        L_0x0104:
            r14.add(r13)     // Catch:{ all -> 0x010b }
            r1 = 1
            r13.broadcasting = r1     // Catch:{ all -> 0x010b }
            goto L_0x00cb
        L_0x010b:
            r1 = move-exception
            monitor-exit(r15)     // Catch:{ all -> 0x010b }
            throw r1
        L_0x010e:
            if (r8 == 0) goto L_0x00cb
            switch(r11) {
                case -4: goto L_0x0133;
                case -3: goto L_0x0130;
                case -2: goto L_0x0136;
                case -1: goto L_0x0139;
                default: goto L_0x0113;
            }
        L_0x0113:
            java.lang.String r12 = "unknown reason"
        L_0x0115:
            java.lang.String r1 = "LocalBroadcastManager"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x010b }
            r7.<init>()     // Catch:{ all -> 0x010b }
            java.lang.String r16 = "  Filter did not match: "
            r0 = r16
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            java.lang.StringBuilder r7 = r7.append(r12)     // Catch:{ all -> 0x010b }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x010b }
            android.util.Log.v(r1, r7)     // Catch:{ all -> 0x010b }
            goto L_0x00cb
        L_0x0130:
            java.lang.String r12 = "action"
            goto L_0x0115
        L_0x0133:
            java.lang.String r12 = "category"
            goto L_0x0115
        L_0x0136:
            java.lang.String r12 = "data"
            goto L_0x0115
        L_0x0139:
            java.lang.String r12 = "type"
            goto L_0x0115
        L_0x013c:
            if (r14 == 0) goto L_0x0175
            r10 = 0
        L_0x013f:
            int r1 = r14.size()     // Catch:{ all -> 0x010b }
            if (r10 >= r1) goto L_0x0151
            java.lang.Object r1 = r14.get(r10)     // Catch:{ all -> 0x010b }
            android.support.v4.content.LocalBroadcastManager$ReceiverRecord r1 = (android.support.v4.content.LocalBroadcastManager.ReceiverRecord) r1     // Catch:{ all -> 0x010b }
            r7 = 0
            r1.broadcasting = r7     // Catch:{ all -> 0x010b }
            int r10 = r10 + 1
            goto L_0x013f
        L_0x0151:
            r0 = r17
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$BroadcastRecord> r1 = r0.mPendingBroadcasts     // Catch:{ all -> 0x010b }
            android.support.v4.content.LocalBroadcastManager$BroadcastRecord r7 = new android.support.v4.content.LocalBroadcastManager$BroadcastRecord     // Catch:{ all -> 0x010b }
            r0 = r18
            r7.<init>(r0, r14)     // Catch:{ all -> 0x010b }
            r1.add(r7)     // Catch:{ all -> 0x010b }
            r0 = r17
            android.os.Handler r1 = r0.mHandler     // Catch:{ all -> 0x010b }
            r7 = 1
            boolean r1 = r1.hasMessages(r7)     // Catch:{ all -> 0x010b }
            if (r1 != 0) goto L_0x0172
            r0 = r17
            android.os.Handler r1 = r0.mHandler     // Catch:{ all -> 0x010b }
            r7 = 1
            r1.sendEmptyMessage(r7)     // Catch:{ all -> 0x010b }
        L_0x0172:
            r1 = 1
            monitor-exit(r15)     // Catch:{ all -> 0x010b }
        L_0x0174:
            return r1
        L_0x0175:
            monitor-exit(r15)     // Catch:{ all -> 0x010b }
            r1 = 0
            goto L_0x0174
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.content.LocalBroadcastManager.sendBroadcast(android.content.Intent):boolean");
    }

    public void sendBroadcastSync(@NonNull Intent intent) {
        if (sendBroadcast(intent)) {
            executePendingBroadcasts();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001c, code lost:
        if (r3 >= r2.length) goto L_0x0000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001e, code lost:
        r1 = r2[r3];
        r5 = r1.receivers.size();
        r4 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0027, code lost:
        if (r4 >= r5) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0029, code lost:
        r6 = (android.support.v4.content.LocalBroadcastManager.ReceiverRecord) r1.receivers.get(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0033, code lost:
        if (r6.dead != false) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0035, code lost:
        r6.receiver.onReceive(r10.mAppContext, r1.intent);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003e, code lost:
        r4 = r4 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0044, code lost:
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001a, code lost:
        r3 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void executePendingBroadcasts() {
        /*
            r10 = this;
        L_0x0000:
            java.util.HashMap<android.content.BroadcastReceiver, java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$ReceiverRecord>> r8 = r10.mReceivers
            monitor-enter(r8)
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$BroadcastRecord> r7 = r10.mPendingBroadcasts     // Catch:{ all -> 0x0041 }
            int r0 = r7.size()     // Catch:{ all -> 0x0041 }
            if (r0 > 0) goto L_0x000d
            monitor-exit(r8)     // Catch:{ all -> 0x0041 }
            return
        L_0x000d:
            android.support.v4.content.LocalBroadcastManager$BroadcastRecord[] r2 = new android.support.v4.content.LocalBroadcastManager.BroadcastRecord[r0]     // Catch:{ all -> 0x0041 }
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$BroadcastRecord> r7 = r10.mPendingBroadcasts     // Catch:{ all -> 0x0041 }
            r7.toArray(r2)     // Catch:{ all -> 0x0041 }
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$BroadcastRecord> r7 = r10.mPendingBroadcasts     // Catch:{ all -> 0x0041 }
            r7.clear()     // Catch:{ all -> 0x0041 }
            monitor-exit(r8)     // Catch:{ all -> 0x0041 }
            r3 = 0
        L_0x001b:
            int r7 = r2.length
            if (r3 >= r7) goto L_0x0000
            r1 = r2[r3]
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$ReceiverRecord> r7 = r1.receivers
            int r5 = r7.size()
            r4 = 0
        L_0x0027:
            if (r4 >= r5) goto L_0x0044
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$ReceiverRecord> r7 = r1.receivers
            java.lang.Object r6 = r7.get(r4)
            android.support.v4.content.LocalBroadcastManager$ReceiverRecord r6 = (android.support.v4.content.LocalBroadcastManager.ReceiverRecord) r6
            boolean r7 = r6.dead
            if (r7 != 0) goto L_0x003e
            android.content.BroadcastReceiver r7 = r6.receiver
            android.content.Context r8 = r10.mAppContext
            android.content.Intent r9 = r1.intent
            r7.onReceive(r8, r9)
        L_0x003e:
            int r4 = r4 + 1
            goto L_0x0027
        L_0x0041:
            r7 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x0041 }
            throw r7
        L_0x0044:
            int r3 = r3 + 1
            goto L_0x001b
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.content.LocalBroadcastManager.executePendingBroadcasts():void");
    }
}
