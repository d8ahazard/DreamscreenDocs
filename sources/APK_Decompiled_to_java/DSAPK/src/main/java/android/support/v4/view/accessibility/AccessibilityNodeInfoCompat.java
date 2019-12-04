package androidx.core.view.accessibility;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction;
import android.view.accessibility.AccessibilityNodeInfo.CollectionInfo;
import android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo;
import android.view.accessibility.AccessibilityNodeInfo.RangeInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccessibilityNodeInfoCompat {
    public static final int ACTION_ACCESSIBILITY_FOCUS = 64;
    public static final String ACTION_ARGUMENT_COLUMN_INT = "android.view.accessibility.action.ARGUMENT_COLUMN_INT";
    public static final String ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN";
    public static final String ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING";
    public static final String ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT";
    public static final String ACTION_ARGUMENT_PROGRESS_VALUE = "android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE";
    public static final String ACTION_ARGUMENT_ROW_INT = "android.view.accessibility.action.ARGUMENT_ROW_INT";
    public static final String ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT";
    public static final String ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT";
    public static final String ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE = "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE";
    public static final int ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128;
    public static final int ACTION_CLEAR_FOCUS = 2;
    public static final int ACTION_CLEAR_SELECTION = 8;
    public static final int ACTION_CLICK = 16;
    public static final int ACTION_COLLAPSE = 524288;
    public static final int ACTION_COPY = 16384;
    public static final int ACTION_CUT = 65536;
    public static final int ACTION_DISMISS = 1048576;
    public static final int ACTION_EXPAND = 262144;
    public static final int ACTION_FOCUS = 1;
    public static final int ACTION_LONG_CLICK = 32;
    public static final int ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256;
    public static final int ACTION_NEXT_HTML_ELEMENT = 1024;
    public static final int ACTION_PASTE = 32768;
    public static final int ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512;
    public static final int ACTION_PREVIOUS_HTML_ELEMENT = 2048;
    public static final int ACTION_SCROLL_BACKWARD = 8192;
    public static final int ACTION_SCROLL_FORWARD = 4096;
    public static final int ACTION_SELECT = 4;
    public static final int ACTION_SET_SELECTION = 131072;
    public static final int ACTION_SET_TEXT = 2097152;
    public static final int FOCUS_ACCESSIBILITY = 2;
    public static final int FOCUS_INPUT = 1;
    public static final int MOVEMENT_GRANULARITY_CHARACTER = 1;
    public static final int MOVEMENT_GRANULARITY_LINE = 4;
    public static final int MOVEMENT_GRANULARITY_PAGE = 16;
    public static final int MOVEMENT_GRANULARITY_PARAGRAPH = 8;
    public static final int MOVEMENT_GRANULARITY_WORD = 2;
    private static final String ROLE_DESCRIPTION_KEY = "AccessibilityNodeInfo.roleDescription";
    private final AccessibilityNodeInfo mInfo;
    @RestrictTo({Scope.LIBRARY_GROUP})
    public int mParentVirtualDescendantId = -1;

    public static class AccessibilityActionCompat {
        public static final AccessibilityActionCompat ACTION_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(64, null);
        public static final AccessibilityActionCompat ACTION_CLEAR_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(128, null);
        public static final AccessibilityActionCompat ACTION_CLEAR_FOCUS = new AccessibilityActionCompat(2, null);
        public static final AccessibilityActionCompat ACTION_CLEAR_SELECTION = new AccessibilityActionCompat(8, null);
        public static final AccessibilityActionCompat ACTION_CLICK = new AccessibilityActionCompat(16, null);
        public static final AccessibilityActionCompat ACTION_COLLAPSE = new AccessibilityActionCompat(524288, null);
        public static final AccessibilityActionCompat ACTION_CONTEXT_CLICK;
        public static final AccessibilityActionCompat ACTION_COPY = new AccessibilityActionCompat(16384, null);
        public static final AccessibilityActionCompat ACTION_CUT = new AccessibilityActionCompat(65536, null);
        public static final AccessibilityActionCompat ACTION_DISMISS = new AccessibilityActionCompat(1048576, null);
        public static final AccessibilityActionCompat ACTION_EXPAND = new AccessibilityActionCompat(262144, null);
        public static final AccessibilityActionCompat ACTION_FOCUS = new AccessibilityActionCompat(1, null);
        public static final AccessibilityActionCompat ACTION_LONG_CLICK = new AccessibilityActionCompat(32, null);
        public static final AccessibilityActionCompat ACTION_NEXT_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(256, null);
        public static final AccessibilityActionCompat ACTION_NEXT_HTML_ELEMENT = new AccessibilityActionCompat(1024, null);
        public static final AccessibilityActionCompat ACTION_PASTE = new AccessibilityActionCompat(32768, null);
        public static final AccessibilityActionCompat ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(512, null);
        public static final AccessibilityActionCompat ACTION_PREVIOUS_HTML_ELEMENT = new AccessibilityActionCompat(2048, null);
        public static final AccessibilityActionCompat ACTION_SCROLL_BACKWARD = new AccessibilityActionCompat(8192, null);
        public static final AccessibilityActionCompat ACTION_SCROLL_DOWN;
        public static final AccessibilityActionCompat ACTION_SCROLL_FORWARD = new AccessibilityActionCompat(4096, null);
        public static final AccessibilityActionCompat ACTION_SCROLL_LEFT;
        public static final AccessibilityActionCompat ACTION_SCROLL_RIGHT;
        public static final AccessibilityActionCompat ACTION_SCROLL_TO_POSITION = new AccessibilityActionCompat(VERSION.SDK_INT >= 23 ? AccessibilityAction.ACTION_SCROLL_TO_POSITION : null);
        public static final AccessibilityActionCompat ACTION_SCROLL_UP;
        public static final AccessibilityActionCompat ACTION_SELECT = new AccessibilityActionCompat(4, null);
        public static final AccessibilityActionCompat ACTION_SET_PROGRESS;
        public static final AccessibilityActionCompat ACTION_SET_SELECTION = new AccessibilityActionCompat(131072, null);
        public static final AccessibilityActionCompat ACTION_SET_TEXT = new AccessibilityActionCompat(2097152, null);
        public static final AccessibilityActionCompat ACTION_SHOW_ON_SCREEN;
        final Object mAction;

        static {
            AccessibilityAction accessibilityAction;
            AccessibilityAction accessibilityAction2;
            AccessibilityAction accessibilityAction3;
            AccessibilityAction accessibilityAction4;
            AccessibilityAction accessibilityAction5;
            AccessibilityAction accessibilityAction6;
            AccessibilityAction accessibilityAction7 = null;
            if (VERSION.SDK_INT >= 23) {
                accessibilityAction = AccessibilityAction.ACTION_SHOW_ON_SCREEN;
            } else {
                accessibilityAction = null;
            }
            ACTION_SHOW_ON_SCREEN = new AccessibilityActionCompat(accessibilityAction);
            if (VERSION.SDK_INT >= 23) {
                accessibilityAction2 = AccessibilityAction.ACTION_SCROLL_UP;
            } else {
                accessibilityAction2 = null;
            }
            ACTION_SCROLL_UP = new AccessibilityActionCompat(accessibilityAction2);
            if (VERSION.SDK_INT >= 23) {
                accessibilityAction3 = AccessibilityAction.ACTION_SCROLL_LEFT;
            } else {
                accessibilityAction3 = null;
            }
            ACTION_SCROLL_LEFT = new AccessibilityActionCompat(accessibilityAction3);
            if (VERSION.SDK_INT >= 23) {
                accessibilityAction4 = AccessibilityAction.ACTION_SCROLL_DOWN;
            } else {
                accessibilityAction4 = null;
            }
            ACTION_SCROLL_DOWN = new AccessibilityActionCompat(accessibilityAction4);
            if (VERSION.SDK_INT >= 23) {
                accessibilityAction5 = AccessibilityAction.ACTION_SCROLL_RIGHT;
            } else {
                accessibilityAction5 = null;
            }
            ACTION_SCROLL_RIGHT = new AccessibilityActionCompat(accessibilityAction5);
            if (VERSION.SDK_INT >= 23) {
                accessibilityAction6 = AccessibilityAction.ACTION_CONTEXT_CLICK;
            } else {
                accessibilityAction6 = null;
            }
            ACTION_CONTEXT_CLICK = new AccessibilityActionCompat(accessibilityAction6);
            if (VERSION.SDK_INT >= 24) {
                accessibilityAction7 = AccessibilityAction.ACTION_SET_PROGRESS;
            }
            ACTION_SET_PROGRESS = new AccessibilityActionCompat(accessibilityAction7);
        }

        public AccessibilityActionCompat(int actionId, CharSequence label) {
            this(VERSION.SDK_INT >= 21 ? new AccessibilityAction(actionId, label) : null);
        }

        AccessibilityActionCompat(Object action) {
            this.mAction = action;
        }

        public int getId() {
            if (VERSION.SDK_INT >= 21) {
                return ((AccessibilityAction) this.mAction).getId();
            }
            return 0;
        }

        public CharSequence getLabel() {
            if (VERSION.SDK_INT >= 21) {
                return ((AccessibilityAction) this.mAction).getLabel();
            }
            return null;
        }
    }

    public static class CollectionInfoCompat {
        public static final int SELECTION_MODE_MULTIPLE = 2;
        public static final int SELECTION_MODE_NONE = 0;
        public static final int SELECTION_MODE_SINGLE = 1;
        final Object mInfo;

        public static CollectionInfoCompat obtain(int rowCount, int columnCount, boolean hierarchical, int selectionMode) {
            if (VERSION.SDK_INT >= 21) {
                return new CollectionInfoCompat(CollectionInfo.obtain(rowCount, columnCount, hierarchical, selectionMode));
            }
            if (VERSION.SDK_INT >= 19) {
                return new CollectionInfoCompat(CollectionInfo.obtain(rowCount, columnCount, hierarchical));
            }
            return new CollectionInfoCompat(null);
        }

        public static CollectionInfoCompat obtain(int rowCount, int columnCount, boolean hierarchical) {
            if (VERSION.SDK_INT >= 19) {
                return new CollectionInfoCompat(CollectionInfo.obtain(rowCount, columnCount, hierarchical));
            }
            return new CollectionInfoCompat(null);
        }

        CollectionInfoCompat(Object info) {
            this.mInfo = info;
        }

        public int getColumnCount() {
            if (VERSION.SDK_INT >= 19) {
                return ((CollectionInfo) this.mInfo).getColumnCount();
            }
            return 0;
        }

        public int getRowCount() {
            if (VERSION.SDK_INT >= 19) {
                return ((CollectionInfo) this.mInfo).getRowCount();
            }
            return 0;
        }

        public boolean isHierarchical() {
            if (VERSION.SDK_INT >= 19) {
                return ((CollectionInfo) this.mInfo).isHierarchical();
            }
            return false;
        }

        public int getSelectionMode() {
            if (VERSION.SDK_INT >= 21) {
                return ((CollectionInfo) this.mInfo).getSelectionMode();
            }
            return 0;
        }
    }

    public static class CollectionItemInfoCompat {
        final Object mInfo;

        public static CollectionItemInfoCompat obtain(int rowIndex, int rowSpan, int columnIndex, int columnSpan, boolean heading, boolean selected) {
            if (VERSION.SDK_INT >= 21) {
                return new CollectionItemInfoCompat(CollectionItemInfo.obtain(rowIndex, rowSpan, columnIndex, columnSpan, heading, selected));
            }
            if (VERSION.SDK_INT >= 19) {
                return new CollectionItemInfoCompat(CollectionItemInfo.obtain(rowIndex, rowSpan, columnIndex, columnSpan, heading));
            }
            return new CollectionItemInfoCompat(null);
        }

        public static CollectionItemInfoCompat obtain(int rowIndex, int rowSpan, int columnIndex, int columnSpan, boolean heading) {
            if (VERSION.SDK_INT >= 19) {
                return new CollectionItemInfoCompat(CollectionItemInfo.obtain(rowIndex, rowSpan, columnIndex, columnSpan, heading));
            }
            return new CollectionItemInfoCompat(null);
        }

        CollectionItemInfoCompat(Object info) {
            this.mInfo = info;
        }

        public int getColumnIndex() {
            if (VERSION.SDK_INT >= 19) {
                return ((CollectionItemInfo) this.mInfo).getColumnIndex();
            }
            return 0;
        }

        public int getColumnSpan() {
            if (VERSION.SDK_INT >= 19) {
                return ((CollectionItemInfo) this.mInfo).getColumnSpan();
            }
            return 0;
        }

        public int getRowIndex() {
            if (VERSION.SDK_INT >= 19) {
                return ((CollectionItemInfo) this.mInfo).getRowIndex();
            }
            return 0;
        }

        public int getRowSpan() {
            if (VERSION.SDK_INT >= 19) {
                return ((CollectionItemInfo) this.mInfo).getRowSpan();
            }
            return 0;
        }

        public boolean isHeading() {
            if (VERSION.SDK_INT >= 19) {
                return ((CollectionItemInfo) this.mInfo).isHeading();
            }
            return false;
        }

        public boolean isSelected() {
            if (VERSION.SDK_INT >= 21) {
                return ((CollectionItemInfo) this.mInfo).isSelected();
            }
            return false;
        }
    }

    public static class RangeInfoCompat {
        public static final int RANGE_TYPE_FLOAT = 1;
        public static final int RANGE_TYPE_INT = 0;
        public static final int RANGE_TYPE_PERCENT = 2;
        final Object mInfo;

        public static RangeInfoCompat obtain(int type, float min, float max, float current) {
            if (VERSION.SDK_INT >= 19) {
                return new RangeInfoCompat(RangeInfo.obtain(type, min, max, current));
            }
            return new RangeInfoCompat(null);
        }

        RangeInfoCompat(Object info) {
            this.mInfo = info;
        }

        public float getCurrent() {
            if (VERSION.SDK_INT >= 19) {
                return ((RangeInfo) this.mInfo).getCurrent();
            }
            return 0.0f;
        }

        public float getMax() {
            if (VERSION.SDK_INT >= 19) {
                return ((RangeInfo) this.mInfo).getMax();
            }
            return 0.0f;
        }

        public float getMin() {
            if (VERSION.SDK_INT >= 19) {
                return ((RangeInfo) this.mInfo).getMin();
            }
            return 0.0f;
        }

        public int getType() {
            if (VERSION.SDK_INT >= 19) {
                return ((RangeInfo) this.mInfo).getType();
            }
            return 0;
        }
    }

    static AccessibilityNodeInfoCompat wrapNonNullInstance(Object object) {
        if (object != null) {
            return new AccessibilityNodeInfoCompat(object);
        }
        return null;
    }

    @Deprecated
    public AccessibilityNodeInfoCompat(Object info) {
        this.mInfo = (AccessibilityNodeInfo) info;
    }

    private AccessibilityNodeInfoCompat(AccessibilityNodeInfo info) {
        this.mInfo = info;
    }

    public static AccessibilityNodeInfoCompat wrap(@NonNull AccessibilityNodeInfo info) {
        return new AccessibilityNodeInfoCompat(info);
    }

    public AccessibilityNodeInfo unwrap() {
        return this.mInfo;
    }

    @Deprecated
    public Object getInfo() {
        return this.mInfo;
    }

    public static AccessibilityNodeInfoCompat obtain(View source) {
        return wrap(AccessibilityNodeInfo.obtain(source));
    }

    public static AccessibilityNodeInfoCompat obtain(View root, int virtualDescendantId) {
        if (VERSION.SDK_INT >= 16) {
            return wrapNonNullInstance(AccessibilityNodeInfo.obtain(root, virtualDescendantId));
        }
        return null;
    }

    public static AccessibilityNodeInfoCompat obtain() {
        return wrap(AccessibilityNodeInfo.obtain());
    }

    public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat info) {
        return wrap(AccessibilityNodeInfo.obtain(info.mInfo));
    }

    public void setSource(View source) {
        this.mInfo.setSource(source);
    }

    public void setSource(View root, int virtualDescendantId) {
        if (VERSION.SDK_INT >= 16) {
            this.mInfo.setSource(root, virtualDescendantId);
        }
    }

    public AccessibilityNodeInfoCompat findFocus(int focus) {
        if (VERSION.SDK_INT >= 16) {
            return wrapNonNullInstance(this.mInfo.findFocus(focus));
        }
        return null;
    }

    public AccessibilityNodeInfoCompat focusSearch(int direction) {
        if (VERSION.SDK_INT >= 16) {
            return wrapNonNullInstance(this.mInfo.focusSearch(direction));
        }
        return null;
    }

    public int getWindowId() {
        return this.mInfo.getWindowId();
    }

    public int getChildCount() {
        return this.mInfo.getChildCount();
    }

    public AccessibilityNodeInfoCompat getChild(int index) {
        return wrapNonNullInstance(this.mInfo.getChild(index));
    }

    public void addChild(View child) {
        this.mInfo.addChild(child);
    }

    public void addChild(View root, int virtualDescendantId) {
        if (VERSION.SDK_INT >= 16) {
            this.mInfo.addChild(root, virtualDescendantId);
        }
    }

    public boolean removeChild(View child) {
        if (VERSION.SDK_INT >= 21) {
            return this.mInfo.removeChild(child);
        }
        return false;
    }

    public boolean removeChild(View root, int virtualDescendantId) {
        if (VERSION.SDK_INT >= 21) {
            return this.mInfo.removeChild(root, virtualDescendantId);
        }
        return false;
    }

    public int getActions() {
        return this.mInfo.getActions();
    }

    public void addAction(int action) {
        this.mInfo.addAction(action);
    }

    public void addAction(AccessibilityActionCompat action) {
        if (VERSION.SDK_INT >= 21) {
            this.mInfo.addAction((AccessibilityAction) action.mAction);
        }
    }

    public boolean removeAction(AccessibilityActionCompat action) {
        if (VERSION.SDK_INT >= 21) {
            return this.mInfo.removeAction((AccessibilityAction) action.mAction);
        }
        return false;
    }

    public boolean performAction(int action) {
        return this.mInfo.performAction(action);
    }

    public boolean performAction(int action, Bundle arguments) {
        if (VERSION.SDK_INT >= 16) {
            return this.mInfo.performAction(action, arguments);
        }
        return false;
    }

    public void setMovementGranularities(int granularities) {
        if (VERSION.SDK_INT >= 16) {
            this.mInfo.setMovementGranularities(granularities);
        }
    }

    public int getMovementGranularities() {
        if (VERSION.SDK_INT >= 16) {
            return this.mInfo.getMovementGranularities();
        }
        return 0;
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String text) {
        List<AccessibilityNodeInfoCompat> result = new ArrayList<>();
        List<AccessibilityNodeInfo> infos = this.mInfo.findAccessibilityNodeInfosByText(text);
        int infoCount = infos.size();
        for (int i = 0; i < infoCount; i++) {
            result.add(wrap((AccessibilityNodeInfo) infos.get(i)));
        }
        return result;
    }

    public AccessibilityNodeInfoCompat getParent() {
        return wrapNonNullInstance(this.mInfo.getParent());
    }

    public void setParent(View parent) {
        this.mInfo.setParent(parent);
    }

    public void setParent(View root, int virtualDescendantId) {
        this.mParentVirtualDescendantId = virtualDescendantId;
        if (VERSION.SDK_INT >= 16) {
            this.mInfo.setParent(root, virtualDescendantId);
        }
    }

    public void getBoundsInParent(Rect outBounds) {
        this.mInfo.getBoundsInParent(outBounds);
    }

    public void setBoundsInParent(Rect bounds) {
        this.mInfo.setBoundsInParent(bounds);
    }

    public void getBoundsInScreen(Rect outBounds) {
        this.mInfo.getBoundsInScreen(outBounds);
    }

    public void setBoundsInScreen(Rect bounds) {
        this.mInfo.setBoundsInScreen(bounds);
    }

    public boolean isCheckable() {
        return this.mInfo.isCheckable();
    }

    public void setCheckable(boolean checkable) {
        this.mInfo.setCheckable(checkable);
    }

    public boolean isChecked() {
        return this.mInfo.isChecked();
    }

    public void setChecked(boolean checked) {
        this.mInfo.setChecked(checked);
    }

    public boolean isFocusable() {
        return this.mInfo.isFocusable();
    }

    public void setFocusable(boolean focusable) {
        this.mInfo.setFocusable(focusable);
    }

    public boolean isFocused() {
        return this.mInfo.isFocused();
    }

    public void setFocused(boolean focused) {
        this.mInfo.setFocused(focused);
    }

    public boolean isVisibleToUser() {
        if (VERSION.SDK_INT >= 16) {
            return this.mInfo.isVisibleToUser();
        }
        return false;
    }

    public void setVisibleToUser(boolean visibleToUser) {
        if (VERSION.SDK_INT >= 16) {
            this.mInfo.setVisibleToUser(visibleToUser);
        }
    }

    public boolean isAccessibilityFocused() {
        if (VERSION.SDK_INT >= 16) {
            return this.mInfo.isAccessibilityFocused();
        }
        return false;
    }

    public void setAccessibilityFocused(boolean focused) {
        if (VERSION.SDK_INT >= 16) {
            this.mInfo.setAccessibilityFocused(focused);
        }
    }

    public boolean isSelected() {
        return this.mInfo.isSelected();
    }

    public void setSelected(boolean selected) {
        this.mInfo.setSelected(selected);
    }

    public boolean isClickable() {
        return this.mInfo.isClickable();
    }

    public void setClickable(boolean clickable) {
        this.mInfo.setClickable(clickable);
    }

    public boolean isLongClickable() {
        return this.mInfo.isLongClickable();
    }

    public void setLongClickable(boolean longClickable) {
        this.mInfo.setLongClickable(longClickable);
    }

    public boolean isEnabled() {
        return this.mInfo.isEnabled();
    }

    public void setEnabled(boolean enabled) {
        this.mInfo.setEnabled(enabled);
    }

    public boolean isPassword() {
        return this.mInfo.isPassword();
    }

    public void setPassword(boolean password) {
        this.mInfo.setPassword(password);
    }

    public boolean isScrollable() {
        return this.mInfo.isScrollable();
    }

    public void setScrollable(boolean scrollable) {
        this.mInfo.setScrollable(scrollable);
    }

    public boolean isImportantForAccessibility() {
        if (VERSION.SDK_INT >= 24) {
            return this.mInfo.isImportantForAccessibility();
        }
        return true;
    }

    public void setImportantForAccessibility(boolean important) {
        if (VERSION.SDK_INT >= 24) {
            this.mInfo.setImportantForAccessibility(important);
        }
    }

    public CharSequence getPackageName() {
        return this.mInfo.getPackageName();
    }

    public void setPackageName(CharSequence packageName) {
        this.mInfo.setPackageName(packageName);
    }

    public CharSequence getClassName() {
        return this.mInfo.getClassName();
    }

    public void setClassName(CharSequence className) {
        this.mInfo.setClassName(className);
    }

    public CharSequence getText() {
        return this.mInfo.getText();
    }

    public void setText(CharSequence text) {
        this.mInfo.setText(text);
    }

    public CharSequence getContentDescription() {
        return this.mInfo.getContentDescription();
    }

    public void setContentDescription(CharSequence contentDescription) {
        this.mInfo.setContentDescription(contentDescription);
    }

    public void recycle() {
        this.mInfo.recycle();
    }

    public void setViewIdResourceName(String viewId) {
        if (VERSION.SDK_INT >= 18) {
            this.mInfo.setViewIdResourceName(viewId);
        }
    }

    public String getViewIdResourceName() {
        if (VERSION.SDK_INT >= 18) {
            return this.mInfo.getViewIdResourceName();
        }
        return null;
    }

    public int getLiveRegion() {
        if (VERSION.SDK_INT >= 19) {
            return this.mInfo.getLiveRegion();
        }
        return 0;
    }

    public void setLiveRegion(int mode) {
        if (VERSION.SDK_INT >= 19) {
            this.mInfo.setLiveRegion(mode);
        }
    }

    public int getDrawingOrder() {
        if (VERSION.SDK_INT >= 24) {
            return this.mInfo.getDrawingOrder();
        }
        return 0;
    }

    public void setDrawingOrder(int drawingOrderInParent) {
        if (VERSION.SDK_INT >= 24) {
            this.mInfo.setDrawingOrder(drawingOrderInParent);
        }
    }

    public CollectionInfoCompat getCollectionInfo() {
        if (VERSION.SDK_INT >= 19) {
            CollectionInfo info = this.mInfo.getCollectionInfo();
            if (info != null) {
                return new CollectionInfoCompat(info);
            }
        }
        return null;
    }

    public void setCollectionInfo(Object collectionInfo) {
        if (VERSION.SDK_INT >= 19) {
            this.mInfo.setCollectionInfo((CollectionInfo) ((CollectionInfoCompat) collectionInfo).mInfo);
        }
    }

    public void setCollectionItemInfo(Object collectionItemInfo) {
        if (VERSION.SDK_INT >= 19) {
            this.mInfo.setCollectionItemInfo((CollectionItemInfo) ((CollectionItemInfoCompat) collectionItemInfo).mInfo);
        }
    }

    public CollectionItemInfoCompat getCollectionItemInfo() {
        if (VERSION.SDK_INT >= 19) {
            CollectionItemInfo info = this.mInfo.getCollectionItemInfo();
            if (info != null) {
                return new CollectionItemInfoCompat(info);
            }
        }
        return null;
    }

    public RangeInfoCompat getRangeInfo() {
        if (VERSION.SDK_INT >= 19) {
            RangeInfo info = this.mInfo.getRangeInfo();
            if (info != null) {
                return new RangeInfoCompat(info);
            }
        }
        return null;
    }

    public void setRangeInfo(RangeInfoCompat rangeInfo) {
        if (VERSION.SDK_INT >= 19) {
            this.mInfo.setRangeInfo((RangeInfo) rangeInfo.mInfo);
        }
    }

    public List<AccessibilityActionCompat> getActionList() {
        List<Object> actions = null;
        if (VERSION.SDK_INT >= 21) {
            actions = this.mInfo.getActionList();
        }
        if (actions == null) {
            return Collections.emptyList();
        }
        List<AccessibilityActionCompat> result = new ArrayList<>();
        int actionCount = actions.size();
        for (int i = 0; i < actionCount; i++) {
            result.add(new AccessibilityActionCompat(actions.get(i)));
        }
        return result;
    }

    public void setContentInvalid(boolean contentInvalid) {
        if (VERSION.SDK_INT >= 19) {
            this.mInfo.setContentInvalid(contentInvalid);
        }
    }

    public boolean isContentInvalid() {
        if (VERSION.SDK_INT >= 19) {
            return this.mInfo.isContentInvalid();
        }
        return false;
    }

    public boolean isContextClickable() {
        if (VERSION.SDK_INT >= 23) {
            return this.mInfo.isContextClickable();
        }
        return false;
    }

    public void setContextClickable(boolean contextClickable) {
        if (VERSION.SDK_INT >= 23) {
            this.mInfo.setContextClickable(contextClickable);
        }
    }

    public void setError(CharSequence error) {
        if (VERSION.SDK_INT >= 21) {
            this.mInfo.setError(error);
        }
    }

    public CharSequence getError() {
        if (VERSION.SDK_INT >= 21) {
            return this.mInfo.getError();
        }
        return null;
    }

    public void setLabelFor(View labeled) {
        if (VERSION.SDK_INT >= 17) {
            this.mInfo.setLabelFor(labeled);
        }
    }

    public void setLabelFor(View root, int virtualDescendantId) {
        if (VERSION.SDK_INT >= 17) {
            this.mInfo.setLabelFor(root, virtualDescendantId);
        }
    }

    public AccessibilityNodeInfoCompat getLabelFor() {
        if (VERSION.SDK_INT >= 17) {
            return wrapNonNullInstance(this.mInfo.getLabelFor());
        }
        return null;
    }

    public void setLabeledBy(View label) {
        if (VERSION.SDK_INT >= 17) {
            this.mInfo.setLabeledBy(label);
        }
    }

    public void setLabeledBy(View root, int virtualDescendantId) {
        if (VERSION.SDK_INT >= 17) {
            this.mInfo.setLabeledBy(root, virtualDescendantId);
        }
    }

    public AccessibilityNodeInfoCompat getLabeledBy() {
        if (VERSION.SDK_INT >= 17) {
            return wrapNonNullInstance(this.mInfo.getLabeledBy());
        }
        return null;
    }

    public boolean canOpenPopup() {
        if (VERSION.SDK_INT >= 19) {
            return this.mInfo.canOpenPopup();
        }
        return false;
    }

    public void setCanOpenPopup(boolean opensPopup) {
        if (VERSION.SDK_INT >= 19) {
            this.mInfo.setCanOpenPopup(opensPopup);
        }
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByViewId(String viewId) {
        if (VERSION.SDK_INT < 18) {
            return Collections.emptyList();
        }
        List<AccessibilityNodeInfo> nodes = this.mInfo.findAccessibilityNodeInfosByViewId(viewId);
        List<AccessibilityNodeInfoCompat> result = new ArrayList<>();
        for (AccessibilityNodeInfo node : nodes) {
            result.add(wrap(node));
        }
        return result;
    }

    public Bundle getExtras() {
        if (VERSION.SDK_INT >= 19) {
            return this.mInfo.getExtras();
        }
        return new Bundle();
    }

    public int getInputType() {
        if (VERSION.SDK_INT >= 19) {
            return this.mInfo.getInputType();
        }
        return 0;
    }

    public void setInputType(int inputType) {
        if (VERSION.SDK_INT >= 19) {
            this.mInfo.setInputType(inputType);
        }
    }

    public void setMaxTextLength(int max) {
        if (VERSION.SDK_INT >= 21) {
            this.mInfo.setMaxTextLength(max);
        }
    }

    public int getMaxTextLength() {
        if (VERSION.SDK_INT >= 21) {
            return this.mInfo.getMaxTextLength();
        }
        return -1;
    }

    public void setTextSelection(int start, int end) {
        if (VERSION.SDK_INT >= 18) {
            this.mInfo.setTextSelection(start, end);
        }
    }

    public int getTextSelectionStart() {
        if (VERSION.SDK_INT >= 18) {
            return this.mInfo.getTextSelectionStart();
        }
        return -1;
    }

    public int getTextSelectionEnd() {
        if (VERSION.SDK_INT >= 18) {
            return this.mInfo.getTextSelectionEnd();
        }
        return -1;
    }

    public AccessibilityNodeInfoCompat getTraversalBefore() {
        if (VERSION.SDK_INT >= 22) {
            return wrapNonNullInstance(this.mInfo.getTraversalBefore());
        }
        return null;
    }

    public void setTraversalBefore(View view) {
        if (VERSION.SDK_INT >= 22) {
            this.mInfo.setTraversalBefore(view);
        }
    }

    public void setTraversalBefore(View root, int virtualDescendantId) {
        if (VERSION.SDK_INT >= 22) {
            this.mInfo.setTraversalBefore(root, virtualDescendantId);
        }
    }

    public AccessibilityNodeInfoCompat getTraversalAfter() {
        if (VERSION.SDK_INT >= 22) {
            return wrapNonNullInstance(this.mInfo.getTraversalAfter());
        }
        return null;
    }

    public void setTraversalAfter(View view) {
        if (VERSION.SDK_INT >= 22) {
            this.mInfo.setTraversalAfter(view);
        }
    }

    public void setTraversalAfter(View root, int virtualDescendantId) {
        if (VERSION.SDK_INT >= 22) {
            this.mInfo.setTraversalAfter(root, virtualDescendantId);
        }
    }

    public AccessibilityWindowInfoCompat getWindow() {
        if (VERSION.SDK_INT >= 21) {
            return AccessibilityWindowInfoCompat.wrapNonNullInstance(this.mInfo.getWindow());
        }
        return null;
    }

    public boolean isDismissable() {
        if (VERSION.SDK_INT >= 19) {
            return this.mInfo.isDismissable();
        }
        return false;
    }

    public void setDismissable(boolean dismissable) {
        if (VERSION.SDK_INT >= 19) {
            this.mInfo.setDismissable(dismissable);
        }
    }

    public boolean isEditable() {
        if (VERSION.SDK_INT >= 18) {
            return this.mInfo.isEditable();
        }
        return false;
    }

    public void setEditable(boolean editable) {
        if (VERSION.SDK_INT >= 18) {
            this.mInfo.setEditable(editable);
        }
    }

    public boolean isMultiLine() {
        if (VERSION.SDK_INT >= 19) {
            return this.mInfo.isMultiLine();
        }
        return false;
    }

    public void setMultiLine(boolean multiLine) {
        if (VERSION.SDK_INT >= 19) {
            this.mInfo.setMultiLine(multiLine);
        }
    }

    public boolean refresh() {
        if (VERSION.SDK_INT >= 18) {
            return this.mInfo.refresh();
        }
        return false;
    }

    @Nullable
    public CharSequence getRoleDescription() {
        if (VERSION.SDK_INT >= 19) {
            return this.mInfo.getExtras().getCharSequence(ROLE_DESCRIPTION_KEY);
        }
        return null;
    }

    public void setRoleDescription(@Nullable CharSequence roleDescription) {
        if (VERSION.SDK_INT >= 19) {
            this.mInfo.getExtras().putCharSequence(ROLE_DESCRIPTION_KEY, roleDescription);
        }
    }

    public int hashCode() {
        if (this.mInfo == null) {
            return 0;
        }
        return this.mInfo.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AccessibilityNodeInfoCompat other = (AccessibilityNodeInfoCompat) obj;
        if (this.mInfo == null) {
            if (other.mInfo != null) {
                return false;
            }
            return true;
        } else if (!this.mInfo.equals(other.mInfo)) {
            return false;
        } else {
            return true;
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        Rect bounds = new Rect();
        getBoundsInParent(bounds);
        builder.append("; boundsInParent: " + bounds);
        getBoundsInScreen(bounds);
        builder.append("; boundsInScreen: " + bounds);
        builder.append("; packageName: ").append(getPackageName());
        builder.append("; className: ").append(getClassName());
        builder.append("; text: ").append(getText());
        builder.append("; contentDescription: ").append(getContentDescription());
        builder.append("; viewId: ").append(getViewIdResourceName());
        builder.append("; checkable: ").append(isCheckable());
        builder.append("; checked: ").append(isChecked());
        builder.append("; focusable: ").append(isFocusable());
        builder.append("; focused: ").append(isFocused());
        builder.append("; selected: ").append(isSelected());
        builder.append("; clickable: ").append(isClickable());
        builder.append("; longClickable: ").append(isLongClickable());
        builder.append("; enabled: ").append(isEnabled());
        builder.append("; password: ").append(isPassword());
        builder.append("; scrollable: " + isScrollable());
        builder.append("; [");
        int actionBits = getActions();
        while (actionBits != 0) {
            int action = 1 << Integer.numberOfTrailingZeros(actionBits);
            actionBits &= action ^ -1;
            builder.append(getActionSymbolicName(action));
            if (actionBits != 0) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    private static String getActionSymbolicName(int action) {
        switch (action) {
            case 1:
                return "ACTION_FOCUS";
            case 2:
                return "ACTION_CLEAR_FOCUS";
            case 4:
                return "ACTION_SELECT";
            case 8:
                return "ACTION_CLEAR_SELECTION";
            case 16:
                return "ACTION_CLICK";
            case 32:
                return "ACTION_LONG_CLICK";
            case 64:
                return "ACTION_ACCESSIBILITY_FOCUS";
            case 128:
                return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
            case 256:
                return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
            case 512:
                return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
            case 1024:
                return "ACTION_NEXT_HTML_ELEMENT";
            case 2048:
                return "ACTION_PREVIOUS_HTML_ELEMENT";
            case 4096:
                return "ACTION_SCROLL_FORWARD";
            case 8192:
                return "ACTION_SCROLL_BACKWARD";
            case 16384:
                return "ACTION_COPY";
            case 32768:
                return "ACTION_PASTE";
            case 65536:
                return "ACTION_CUT";
            case 131072:
                return "ACTION_SET_SELECTION";
            default:
                return "ACTION_UNKNOWN";
        }
    }
}
