package androidx.appcompat.widget;

import android.os.Bundle;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAccessibilityDelegate extends AccessibilityDelegateCompat {
    final AccessibilityDelegateCompat mItemDelegate = new ItemDelegate(this);
    final RecyclerView mRecyclerView;

    public static class ItemDelegate extends AccessibilityDelegateCompat {
        final androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate mRecyclerViewDelegate;

        public ItemDelegate(androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate recyclerViewDelegate) {
            this.mRecyclerViewDelegate = recyclerViewDelegate;
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            if (!this.mRecyclerViewDelegate.shouldIgnore() && this.mRecyclerViewDelegate.mRecyclerView.getLayoutManager() != null) {
                this.mRecyclerViewDelegate.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfoForItem(host, info);
            }
        }

        public boolean performAccessibilityAction(View host, int action, Bundle args) {
            if (super.performAccessibilityAction(host, action, args)) {
                return true;
            }
            if (this.mRecyclerViewDelegate.shouldIgnore() || this.mRecyclerViewDelegate.mRecyclerView.getLayoutManager() == null) {
                return false;
            }
            return this.mRecyclerViewDelegate.mRecyclerView.getLayoutManager().performAccessibilityActionForItem(host, action, args);
        }
    }

    public RecyclerViewAccessibilityDelegate(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    /* access modifiers changed from: 0000 */
    public boolean shouldIgnore() {
        return this.mRecyclerView.hasPendingAdapterUpdates();
    }

    public boolean performAccessibilityAction(View host, int action, Bundle args) {
        if (super.performAccessibilityAction(host, action, args)) {
            return true;
        }
        if (shouldIgnore() || this.mRecyclerView.getLayoutManager() == null) {
            return false;
        }
        return this.mRecyclerView.getLayoutManager().performAccessibilityAction(action, args);
    }

    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfo(host, info);
        info.setClassName(RecyclerView.class.getName());
        if (!shouldIgnore() && this.mRecyclerView.getLayoutManager() != null) {
            this.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfo(info);
        }
    }

    public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(host, event);
        event.setClassName(RecyclerView.class.getName());
        if ((host instanceof RecyclerView) && !shouldIgnore()) {
            RecyclerView rv = (RecyclerView) host;
            if (rv.getLayoutManager() != null) {
                rv.getLayoutManager().onInitializeAccessibilityEvent(event);
            }
        }
    }

    public AccessibilityDelegateCompat getItemDelegate() {
        return this.mItemDelegate;
    }
}
