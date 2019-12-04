package androidx.core.app;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.core.app.NotificationCompat.Action;
import androidx.core.app.NotificationCompat.Style;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestrictTo({Scope.LIBRARY_GROUP})
class NotificationCompatBuilder implements NotificationBuilderWithBuilderAccessor {
    private final List<Bundle> mActionExtrasList = new ArrayList();
    private RemoteViews mBigContentView;
    private final Builder mBuilder;
    private final NotificationCompat.Builder mBuilderCompat;
    private RemoteViews mContentView;
    private final Bundle mExtras = new Bundle();
    private int mGroupAlertBehavior;
    private RemoteViews mHeadsUpContentView;

    NotificationCompatBuilder(NotificationCompat.Builder b) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4 = false;
        this.mBuilderCompat = b;
        if (VERSION.SDK_INT >= 26) {
            this.mBuilder = new Builder(b.mContext, b.mChannelId);
        } else {
            this.mBuilder = new Builder(b.mContext);
        }
        Notification n = b.mNotification;
        Builder lights = this.mBuilder.setWhen(n.when).setSmallIcon(n.icon, n.iconLevel).setContent(n.contentView).setTicker(n.tickerText, b.mTickerView).setSound(n.sound, n.audioStreamType).setVibrate(n.vibrate).setLights(n.ledARGB, n.ledOnMS, n.ledOffMS);
        if ((n.flags & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        Builder ongoing = lights.setOngoing(z);
        if ((n.flags & 8) != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        Builder onlyAlertOnce = ongoing.setOnlyAlertOnce(z2);
        if ((n.flags & 16) != 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        Builder deleteIntent = onlyAlertOnce.setAutoCancel(z3).setDefaults(n.defaults).setContentTitle(b.mContentTitle).setContentText(b.mContentText).setContentInfo(b.mContentInfo).setContentIntent(b.mContentIntent).setDeleteIntent(n.deleteIntent);
        PendingIntent pendingIntent = b.mFullScreenIntent;
        if ((n.flags & 128) != 0) {
            z4 = true;
        }
        deleteIntent.setFullScreenIntent(pendingIntent, z4).setLargeIcon(b.mLargeIcon).setNumber(b.mNumber).setProgress(b.mProgressMax, b.mProgress, b.mProgressIndeterminate);
        if (VERSION.SDK_INT >= 16) {
            this.mBuilder.setSubText(b.mSubText).setUsesChronometer(b.mUseChronometer).setPriority(b.mPriority);
            Iterator it = b.mActions.iterator();
            while (it.hasNext()) {
                addAction((Action) it.next());
            }
            if (b.mExtras != null) {
                this.mExtras.putAll(b.mExtras);
            }
            if (VERSION.SDK_INT < 20) {
                if (b.mLocalOnly) {
                    this.mExtras.putBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY, true);
                }
                if (b.mGroupKey != null) {
                    this.mExtras.putString(NotificationCompatExtras.EXTRA_GROUP_KEY, b.mGroupKey);
                    if (b.mGroupSummary) {
                        this.mExtras.putBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY, true);
                    } else {
                        this.mExtras.putBoolean(NotificationManagerCompat.EXTRA_USE_SIDE_CHANNEL, true);
                    }
                }
                if (b.mSortKey != null) {
                    this.mExtras.putString(NotificationCompatExtras.EXTRA_SORT_KEY, b.mSortKey);
                }
            }
            this.mContentView = b.mContentView;
            this.mBigContentView = b.mBigContentView;
        }
        if (VERSION.SDK_INT >= 19) {
            this.mBuilder.setShowWhen(b.mShowWhen);
            if (VERSION.SDK_INT < 21 && b.mPeople != null && !b.mPeople.isEmpty()) {
                this.mExtras.putStringArray(NotificationCompat.EXTRA_PEOPLE, (String[]) b.mPeople.toArray(new String[b.mPeople.size()]));
            }
        }
        if (VERSION.SDK_INT >= 20) {
            this.mBuilder.setLocalOnly(b.mLocalOnly).setGroup(b.mGroupKey).setGroupSummary(b.mGroupSummary).setSortKey(b.mSortKey);
            this.mGroupAlertBehavior = b.mGroupAlertBehavior;
        }
        if (VERSION.SDK_INT >= 21) {
            this.mBuilder.setCategory(b.mCategory).setColor(b.mColor).setVisibility(b.mVisibility).setPublicVersion(b.mPublicVersion);
            Iterator it2 = b.mPeople.iterator();
            while (it2.hasNext()) {
                this.mBuilder.addPerson((String) it2.next());
            }
            this.mHeadsUpContentView = b.mHeadsUpContentView;
        }
        if (VERSION.SDK_INT >= 24) {
            this.mBuilder.setExtras(b.mExtras).setRemoteInputHistory(b.mRemoteInputHistory);
            if (b.mContentView != null) {
                this.mBuilder.setCustomContentView(b.mContentView);
            }
            if (b.mBigContentView != null) {
                this.mBuilder.setCustomBigContentView(b.mBigContentView);
            }
            if (b.mHeadsUpContentView != null) {
                this.mBuilder.setCustomHeadsUpContentView(b.mHeadsUpContentView);
            }
        }
        if (VERSION.SDK_INT >= 26) {
            this.mBuilder.setBadgeIconType(b.mBadgeIcon).setShortcutId(b.mShortcutId).setTimeoutAfter(b.mTimeout).setGroupAlertBehavior(b.mGroupAlertBehavior);
            if (b.mColorizedSet) {
                this.mBuilder.setColorized(b.mColorized);
            }
        }
    }

    public Builder getBuilder() {
        return this.mBuilder;
    }

    public Notification build() {
        Style style = this.mBuilderCompat.mStyle;
        if (style != null) {
            style.apply(this);
        }
        RemoteViews styleContentView = style != null ? style.makeContentView(this) : null;
        Notification n = buildInternal();
        if (styleContentView != null) {
            n.contentView = styleContentView;
        } else if (this.mBuilderCompat.mContentView != null) {
            n.contentView = this.mBuilderCompat.mContentView;
        }
        if (VERSION.SDK_INT >= 16 && style != null) {
            RemoteViews styleBigContentView = style.makeBigContentView(this);
            if (styleBigContentView != null) {
                n.bigContentView = styleBigContentView;
            }
        }
        if (VERSION.SDK_INT >= 21 && style != null) {
            RemoteViews styleHeadsUpContentView = this.mBuilderCompat.mStyle.makeHeadsUpContentView(this);
            if (styleHeadsUpContentView != null) {
                n.headsUpContentView = styleHeadsUpContentView;
            }
        }
        if (VERSION.SDK_INT >= 16 && style != null) {
            Bundle extras = NotificationCompat.getExtras(n);
            if (extras != null) {
                style.addCompatExtras(extras);
            }
        }
        return n;
    }

    private void addAction(Action action) {
        Bundle actionExtras;
        if (VERSION.SDK_INT >= 20) {
            Notification.Action.Builder actionBuilder = new Notification.Action.Builder(action.getIcon(), action.getTitle(), action.getActionIntent());
            if (action.getRemoteInputs() != null) {
                for (RemoteInput remoteInput : RemoteInput.fromCompat(action.getRemoteInputs())) {
                    actionBuilder.addRemoteInput(remoteInput);
                }
            }
            if (action.getExtras() != null) {
                actionExtras = new Bundle(action.getExtras());
            } else {
                actionExtras = new Bundle();
            }
            actionExtras.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
            if (VERSION.SDK_INT >= 24) {
                actionBuilder.setAllowGeneratedReplies(action.getAllowGeneratedReplies());
            }
            actionBuilder.addExtras(actionExtras);
            this.mBuilder.addAction(actionBuilder.build());
        } else if (VERSION.SDK_INT >= 16) {
            this.mActionExtrasList.add(NotificationCompatJellybean.writeActionAndGetExtras(this.mBuilder, action));
        }
    }

    /* access modifiers changed from: protected */
    public Notification buildInternal() {
        if (VERSION.SDK_INT >= 26) {
            return this.mBuilder.build();
        }
        if (VERSION.SDK_INT >= 24) {
            Notification notification = this.mBuilder.build();
            if (this.mGroupAlertBehavior == 0) {
                return notification;
            }
            if (!(notification.getGroup() == null || (notification.flags & 512) == 0 || this.mGroupAlertBehavior != 2)) {
                removeSoundAndVibration(notification);
            }
            if (notification.getGroup() == null || (notification.flags & 512) != 0 || this.mGroupAlertBehavior != 1) {
                return notification;
            }
            removeSoundAndVibration(notification);
            return notification;
        } else if (VERSION.SDK_INT >= 21) {
            this.mBuilder.setExtras(this.mExtras);
            Notification notification2 = this.mBuilder.build();
            if (this.mContentView != null) {
                notification2.contentView = this.mContentView;
            }
            if (this.mBigContentView != null) {
                notification2.bigContentView = this.mBigContentView;
            }
            if (this.mHeadsUpContentView != null) {
                notification2.headsUpContentView = this.mHeadsUpContentView;
            }
            if (this.mGroupAlertBehavior == 0) {
                return notification2;
            }
            if (!(notification2.getGroup() == null || (notification2.flags & 512) == 0 || this.mGroupAlertBehavior != 2)) {
                removeSoundAndVibration(notification2);
            }
            if (notification2.getGroup() == null || (notification2.flags & 512) != 0 || this.mGroupAlertBehavior != 1) {
                return notification2;
            }
            removeSoundAndVibration(notification2);
            return notification2;
        } else if (VERSION.SDK_INT >= 20) {
            this.mBuilder.setExtras(this.mExtras);
            Notification notification3 = this.mBuilder.build();
            if (this.mContentView != null) {
                notification3.contentView = this.mContentView;
            }
            if (this.mBigContentView != null) {
                notification3.bigContentView = this.mBigContentView;
            }
            if (this.mGroupAlertBehavior == 0) {
                return notification3;
            }
            if (!(notification3.getGroup() == null || (notification3.flags & 512) == 0 || this.mGroupAlertBehavior != 2)) {
                removeSoundAndVibration(notification3);
            }
            if (notification3.getGroup() == null || (notification3.flags & 512) != 0 || this.mGroupAlertBehavior != 1) {
                return notification3;
            }
            removeSoundAndVibration(notification3);
            return notification3;
        } else if (VERSION.SDK_INT >= 19) {
            SparseArray<Bundle> actionExtrasMap = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
            if (actionExtrasMap != null) {
                this.mExtras.putSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS, actionExtrasMap);
            }
            this.mBuilder.setExtras(this.mExtras);
            Notification notification4 = this.mBuilder.build();
            if (this.mContentView != null) {
                notification4.contentView = this.mContentView;
            }
            if (this.mBigContentView == null) {
                return notification4;
            }
            notification4.bigContentView = this.mBigContentView;
            return notification4;
        } else if (VERSION.SDK_INT < 16) {
            return this.mBuilder.getNotification();
        } else {
            Notification notification5 = this.mBuilder.build();
            Bundle extras = NotificationCompat.getExtras(notification5);
            Bundle mergeBundle = new Bundle(this.mExtras);
            for (String key : this.mExtras.keySet()) {
                if (extras.containsKey(key)) {
                    mergeBundle.remove(key);
                }
            }
            extras.putAll(mergeBundle);
            SparseArray<Bundle> actionExtrasMap2 = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
            if (actionExtrasMap2 != null) {
                NotificationCompat.getExtras(notification5).putSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS, actionExtrasMap2);
            }
            if (this.mContentView != null) {
                notification5.contentView = this.mContentView;
            }
            if (this.mBigContentView == null) {
                return notification5;
            }
            notification5.bigContentView = this.mBigContentView;
            return notification5;
        }
    }

    private void removeSoundAndVibration(Notification notification) {
        notification.sound = null;
        notification.vibrate = null;
        notification.defaults &= -2;
        notification.defaults &= -3;
    }
}
