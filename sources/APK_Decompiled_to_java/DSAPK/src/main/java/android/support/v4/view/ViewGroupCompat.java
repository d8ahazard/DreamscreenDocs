package androidx.core.view;

import android.os.Build.VERSION;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import android.support.compat.R;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

public final class ViewGroupCompat {
    static final ViewGroupCompatBaseImpl IMPL;
    public static final int LAYOUT_MODE_CLIP_BOUNDS = 0;
    public static final int LAYOUT_MODE_OPTICAL_BOUNDS = 1;

    @RequiresApi(18)
    static class ViewGroupCompatApi18Impl extends ViewGroupCompatBaseImpl {
        ViewGroupCompatApi18Impl() {
        }

        public int getLayoutMode(ViewGroup group) {
            return group.getLayoutMode();
        }

        public void setLayoutMode(ViewGroup group, int mode) {
            group.setLayoutMode(mode);
        }
    }

    @RequiresApi(21)
    static class ViewGroupCompatApi21Impl extends ViewGroupCompatApi18Impl {
        ViewGroupCompatApi21Impl() {
        }

        public void setTransitionGroup(ViewGroup group, boolean isTransitionGroup) {
            group.setTransitionGroup(isTransitionGroup);
        }

        public boolean isTransitionGroup(ViewGroup group) {
            return group.isTransitionGroup();
        }

        public int getNestedScrollAxes(ViewGroup group) {
            return group.getNestedScrollAxes();
        }
    }

    static class ViewGroupCompatBaseImpl {
        ViewGroupCompatBaseImpl() {
        }

        public int getLayoutMode(ViewGroup group) {
            return 0;
        }

        public void setLayoutMode(ViewGroup group, int mode) {
        }

        public void setTransitionGroup(ViewGroup group, boolean isTransitionGroup) {
            group.setTag(R.id.tag_transition_group, Boolean.valueOf(isTransitionGroup));
        }

        public boolean isTransitionGroup(ViewGroup group) {
            Boolean explicit = (Boolean) group.getTag(R.id.tag_transition_group);
            return ((explicit == null || !explicit.booleanValue()) && group.getBackground() == null && ViewCompat.getTransitionName(group) == null) ? false : true;
        }

        public int getNestedScrollAxes(ViewGroup group) {
            if (group instanceof NestedScrollingParent) {
                return ((NestedScrollingParent) group).getNestedScrollAxes();
            }
            return 0;
        }
    }

    static {
        if (VERSION.SDK_INT >= 21) {
            IMPL = new ViewGroupCompatApi21Impl();
        } else if (VERSION.SDK_INT >= 18) {
            IMPL = new ViewGroupCompatApi18Impl();
        } else {
            IMPL = new ViewGroupCompatBaseImpl();
        }
    }

    private ViewGroupCompat() {
    }

    @Deprecated
    public static boolean onRequestSendAccessibilityEvent(ViewGroup group, View child, AccessibilityEvent event) {
        return group.onRequestSendAccessibilityEvent(child, event);
    }

    @Deprecated
    public static void setMotionEventSplittingEnabled(ViewGroup group, boolean split) {
        group.setMotionEventSplittingEnabled(split);
    }

    public static int getLayoutMode(ViewGroup group) {
        return IMPL.getLayoutMode(group);
    }

    public static void setLayoutMode(ViewGroup group, int mode) {
        IMPL.setLayoutMode(group, mode);
    }

    public static void setTransitionGroup(ViewGroup group, boolean isTransitionGroup) {
        IMPL.setTransitionGroup(group, isTransitionGroup);
    }

    public static boolean isTransitionGroup(ViewGroup group) {
        return IMPL.isTransitionGroup(group);
    }

    public static int getNestedScrollAxes(@NonNull ViewGroup group) {
        return IMPL.getNestedScrollAxes(group);
    }
}
