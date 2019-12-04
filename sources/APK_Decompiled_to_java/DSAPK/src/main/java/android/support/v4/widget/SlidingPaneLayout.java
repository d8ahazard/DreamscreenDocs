package androidx.core.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.customview.view.AbsSavedState;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ViewDragHelper;
import androidx.customview.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SlidingPaneLayout extends ViewGroup {
    private static final int DEFAULT_FADE_COLOR = -858993460;
    private static final int DEFAULT_OVERHANG_SIZE = 32;
    static final SlidingPanelLayoutImpl IMPL;
    private static final int MIN_FLING_VELOCITY = 400;
    private static final String TAG = "SlidingPaneLayout";
    private boolean mCanSlide;
    private int mCoveredFadeColor;
    final ViewDragHelper mDragHelper;
    private boolean mFirstLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    boolean mIsUnableToDrag;
    private final int mOverhangSize;
    private PanelSlideListener mPanelSlideListener;
    private int mParallaxBy;
    private float mParallaxOffset;
    final ArrayList<DisableLayerRunnable> mPostedRunnables;
    boolean mPreservedOpenState;
    private Drawable mShadowDrawableLeft;
    private Drawable mShadowDrawableRight;
    float mSlideOffset;
    int mSlideRange;
    View mSlideableView;
    private int mSliderFadeColor;
    private final Rect mTmpRect;

    class AccessibilityDelegate extends AccessibilityDelegateCompat {
        private final Rect mTmpRect = new Rect();

        AccessibilityDelegate() {
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            AccessibilityNodeInfoCompat superNode = AccessibilityNodeInfoCompat.obtain(info);
            super.onInitializeAccessibilityNodeInfo(host, superNode);
            copyNodeInfoNoChildren(info, superNode);
            superNode.recycle();
            info.setClassName(androidx.slidingpanelayout.widget.SlidingPaneLayout.class.getName());
            info.setSource(host);
            ViewParent parent = ViewCompat.getParentForAccessibility(host);
            if (parent instanceof View) {
                info.setParent((View) parent);
            }
            int childCount = androidx.slidingpanelayout.widget.SlidingPaneLayout.this.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = androidx.slidingpanelayout.widget.SlidingPaneLayout.this.getChildAt(i);
                if (!filter(child) && child.getVisibility() == 0) {
                    ViewCompat.setImportantForAccessibility(child, 1);
                    info.addChild(child);
                }
            }
        }

        public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
            super.onInitializeAccessibilityEvent(host, event);
            event.setClassName(androidx.slidingpanelayout.widget.SlidingPaneLayout.class.getName());
        }

        public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
            if (!filter(child)) {
                return super.onRequestSendAccessibilityEvent(host, child, event);
            }
            return false;
        }

        public boolean filter(View child) {
            return androidx.slidingpanelayout.widget.SlidingPaneLayout.this.isDimmed(child);
        }

        private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat dest, AccessibilityNodeInfoCompat src) {
            Rect rect = this.mTmpRect;
            src.getBoundsInParent(rect);
            dest.setBoundsInParent(rect);
            src.getBoundsInScreen(rect);
            dest.setBoundsInScreen(rect);
            dest.setVisibleToUser(src.isVisibleToUser());
            dest.setPackageName(src.getPackageName());
            dest.setClassName(src.getClassName());
            dest.setContentDescription(src.getContentDescription());
            dest.setEnabled(src.isEnabled());
            dest.setClickable(src.isClickable());
            dest.setFocusable(src.isFocusable());
            dest.setFocused(src.isFocused());
            dest.setAccessibilityFocused(src.isAccessibilityFocused());
            dest.setSelected(src.isSelected());
            dest.setLongClickable(src.isLongClickable());
            dest.addAction(src.getActions());
            dest.setMovementGranularities(src.getMovementGranularities());
        }
    }

    private class DisableLayerRunnable implements Runnable {
        final View mChildView;

        DisableLayerRunnable(View childView) {
            this.mChildView = childView;
        }

        public void run() {
            if (this.mChildView.getParent() == androidx.slidingpanelayout.widget.SlidingPaneLayout.this) {
                this.mChildView.setLayerType(0, null);
                androidx.slidingpanelayout.widget.SlidingPaneLayout.this.invalidateChildRegion(this.mChildView);
            }
            androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mPostedRunnables.remove(this);
        }
    }

    private class DragHelperCallback extends Callback {
        DragHelperCallback() {
        }

        public boolean tryCaptureView(View child, int pointerId) {
            if (androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mIsUnableToDrag) {
                return false;
            }
            return ((LayoutParams) child.getLayoutParams()).slideable;
        }

        public void onViewDragStateChanged(int state) {
            if (androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mDragHelper.getViewDragState() != 0) {
                return;
            }
            if (androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mSlideOffset == 0.0f) {
                androidx.slidingpanelayout.widget.SlidingPaneLayout.this.updateObscuredViewsVisibility(androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mSlideableView);
                androidx.slidingpanelayout.widget.SlidingPaneLayout.this.dispatchOnPanelClosed(androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mSlideableView);
                androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mPreservedOpenState = false;
                return;
            }
            androidx.slidingpanelayout.widget.SlidingPaneLayout.this.dispatchOnPanelOpened(androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mSlideableView);
            androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mPreservedOpenState = true;
        }

        public void onViewCaptured(View capturedChild, int activePointerId) {
            androidx.slidingpanelayout.widget.SlidingPaneLayout.this.setAllChildrenVisible();
        }

        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            androidx.slidingpanelayout.widget.SlidingPaneLayout.this.onPanelDragged(left);
            androidx.slidingpanelayout.widget.SlidingPaneLayout.this.invalidate();
        }

        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int left;
            LayoutParams lp = (LayoutParams) releasedChild.getLayoutParams();
            if (androidx.slidingpanelayout.widget.SlidingPaneLayout.this.isLayoutRtlSupport()) {
                int startToRight = androidx.slidingpanelayout.widget.SlidingPaneLayout.this.getPaddingRight() + lp.rightMargin;
                if (xvel < 0.0f || (xvel == 0.0f && androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mSlideOffset > 0.5f)) {
                    startToRight += androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mSlideRange;
                }
                left = (androidx.slidingpanelayout.widget.SlidingPaneLayout.this.getWidth() - startToRight) - androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mSlideableView.getWidth();
            } else {
                left = androidx.slidingpanelayout.widget.SlidingPaneLayout.this.getPaddingLeft() + lp.leftMargin;
                if (xvel > 0.0f || (xvel == 0.0f && androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mSlideOffset > 0.5f)) {
                    left += androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mSlideRange;
                }
            }
            androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mDragHelper.settleCapturedViewAt(left, releasedChild.getTop());
            androidx.slidingpanelayout.widget.SlidingPaneLayout.this.invalidate();
        }

        public int getViewHorizontalDragRange(View child) {
            return androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mSlideRange;
        }

        public int clampViewPositionHorizontal(View child, int left, int dx) {
            LayoutParams lp = (LayoutParams) androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mSlideableView.getLayoutParams();
            if (androidx.slidingpanelayout.widget.SlidingPaneLayout.this.isLayoutRtlSupport()) {
                int startBound = androidx.slidingpanelayout.widget.SlidingPaneLayout.this.getWidth() - ((androidx.slidingpanelayout.widget.SlidingPaneLayout.this.getPaddingRight() + lp.rightMargin) + androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mSlideableView.getWidth());
                return Math.max(Math.min(left, startBound), startBound - androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mSlideRange);
            }
            int startBound2 = androidx.slidingpanelayout.widget.SlidingPaneLayout.this.getPaddingLeft() + lp.leftMargin;
            return Math.min(Math.max(left, startBound2), startBound2 + androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mSlideRange);
        }

        public int clampViewPositionVertical(View child, int top, int dy) {
            return child.getTop();
        }

        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mDragHelper.captureChildView(androidx.slidingpanelayout.widget.SlidingPaneLayout.this.mSlideableView, pointerId);
        }
    }

    public static class LayoutParams extends MarginLayoutParams {
        private static final int[] ATTRS = {16843137};
        Paint dimPaint;
        boolean dimWhenOffset;
        boolean slideable;
        public float weight = 0.0f;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(@NonNull android.view.ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(@NonNull MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(@NonNull LayoutParams source) {
            super(source);
            this.weight = source.weight;
        }

        public LayoutParams(@NonNull Context c, @Nullable AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, ATTRS);
            this.weight = a.getFloat(0, 0.0f);
            a.recycle();
        }
    }

    public interface PanelSlideListener {
        void onPanelClosed(@NonNull View view);

        void onPanelOpened(@NonNull View view);

        void onPanelSlide(@NonNull View view, float f);
    }

    static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = new ClassLoaderCreator<SavedState>() {
            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, null);
            }

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in, null);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        boolean isOpen;

        SavedState(Parcelable superState) {
            super(superState);
        }

        SavedState(Parcel in, ClassLoader loader) {
            super(in, loader);
            this.isOpen = in.readInt() != 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.isOpen ? 1 : 0);
        }
    }

    public static class SimplePanelSlideListener implements PanelSlideListener {
        public void onPanelSlide(View panel, float slideOffset) {
        }

        public void onPanelOpened(View panel) {
        }

        public void onPanelClosed(View panel) {
        }
    }

    interface SlidingPanelLayoutImpl {
        void invalidateChildRegion(androidx.slidingpanelayout.widget.SlidingPaneLayout slidingPaneLayout, View view);
    }

    static class SlidingPanelLayoutImplBase implements SlidingPanelLayoutImpl {
        SlidingPanelLayoutImplBase() {
        }

        public void invalidateChildRegion(androidx.slidingpanelayout.widget.SlidingPaneLayout parent, View child) {
            ViewCompat.postInvalidateOnAnimation(parent, child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
        }
    }

    @RequiresApi(16)
    static class SlidingPanelLayoutImplJB extends SlidingPanelLayoutImplBase {
        private Method mGetDisplayList;
        private Field mRecreateDisplayList;

        SlidingPanelLayoutImplJB() {
            try {
                this.mGetDisplayList = View.class.getDeclaredMethod("getDisplayList", null);
            } catch (NoSuchMethodException e) {
                Log.e(androidx.slidingpanelayout.widget.SlidingPaneLayout.TAG, "Couldn't fetch getDisplayList method; dimming won't work right.", e);
            }
            try {
                this.mRecreateDisplayList = View.class.getDeclaredField("mRecreateDisplayList");
                this.mRecreateDisplayList.setAccessible(true);
            } catch (NoSuchFieldException e2) {
                Log.e(androidx.slidingpanelayout.widget.SlidingPaneLayout.TAG, "Couldn't fetch mRecreateDisplayList field; dimming will be slow.", e2);
            }
        }

        public void invalidateChildRegion(androidx.slidingpanelayout.widget.SlidingPaneLayout parent, View child) {
            if (this.mGetDisplayList == null || this.mRecreateDisplayList == null) {
                child.invalidate();
                return;
            }
            try {
                this.mRecreateDisplayList.setBoolean(child, true);
                this.mGetDisplayList.invoke(child, null);
            } catch (Exception e) {
                Log.e(androidx.slidingpanelayout.widget.SlidingPaneLayout.TAG, "Error refreshing display list state", e);
            }
            super.invalidateChildRegion(parent, child);
        }
    }

    @RequiresApi(17)
    static class SlidingPanelLayoutImplJBMR1 extends SlidingPanelLayoutImplBase {
        SlidingPanelLayoutImplJBMR1() {
        }

        public void invalidateChildRegion(androidx.slidingpanelayout.widget.SlidingPaneLayout parent, View child) {
            ViewCompat.setLayerPaint(child, ((LayoutParams) child.getLayoutParams()).dimPaint);
        }
    }

    static {
        if (VERSION.SDK_INT >= 17) {
            IMPL = new SlidingPanelLayoutImplJBMR1();
        } else if (VERSION.SDK_INT >= 16) {
            IMPL = new SlidingPanelLayoutImplJB();
        } else {
            IMPL = new SlidingPanelLayoutImplBase();
        }
    }

    public SlidingPaneLayout(@NonNull Context context) {
        this(context, null);
    }

    public SlidingPaneLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingPaneLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mSliderFadeColor = DEFAULT_FADE_COLOR;
        this.mFirstLayout = true;
        this.mTmpRect = new Rect();
        this.mPostedRunnables = new ArrayList<>();
        float density = context.getResources().getDisplayMetrics().density;
        this.mOverhangSize = (int) ((32.0f * density) + 0.5f);
        setWillNotDraw(false);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate());
        ViewCompat.setImportantForAccessibility(this, 1);
        this.mDragHelper = ViewDragHelper.create(this, 0.5f, new DragHelperCallback());
        this.mDragHelper.setMinVelocity(400.0f * density);
    }

    public void setParallaxDistance(int parallaxBy) {
        this.mParallaxBy = parallaxBy;
        requestLayout();
    }

    public int getParallaxDistance() {
        return this.mParallaxBy;
    }

    public void setSliderFadeColor(@ColorInt int color) {
        this.mSliderFadeColor = color;
    }

    @ColorInt
    public int getSliderFadeColor() {
        return this.mSliderFadeColor;
    }

    public void setCoveredFadeColor(@ColorInt int color) {
        this.mCoveredFadeColor = color;
    }

    @ColorInt
    public int getCoveredFadeColor() {
        return this.mCoveredFadeColor;
    }

    public void setPanelSlideListener(@Nullable PanelSlideListener listener) {
        this.mPanelSlideListener = listener;
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnPanelSlide(View panel) {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelSlide(panel, this.mSlideOffset);
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnPanelOpened(View panel) {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelOpened(panel);
        }
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: 0000 */
    public void dispatchOnPanelClosed(View panel) {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelClosed(panel);
        }
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: 0000 */
    public void updateObscuredViewsVisibility(View panel) {
        int endBound;
        int bottom;
        int top;
        int right;
        int left;
        int i;
        int i2;
        int vis;
        boolean isLayoutRtl = isLayoutRtlSupport();
        int startBound = isLayoutRtl ? getWidth() - getPaddingRight() : getPaddingLeft();
        if (isLayoutRtl) {
            endBound = getPaddingLeft();
        } else {
            endBound = getWidth() - getPaddingRight();
        }
        int topBound = getPaddingTop();
        int bottomBound = getHeight() - getPaddingBottom();
        if (panel == null || !viewIsOpaque(panel)) {
            bottom = 0;
            top = 0;
            right = 0;
            left = 0;
        } else {
            left = panel.getLeft();
            right = panel.getRight();
            top = panel.getTop();
            bottom = panel.getBottom();
        }
        int i3 = 0;
        int childCount = getChildCount();
        while (i3 < childCount) {
            View child = getChildAt(i3);
            if (child != panel) {
                if (child.getVisibility() != 8) {
                    if (isLayoutRtl) {
                        i = endBound;
                    } else {
                        i = startBound;
                    }
                    int clampedChildLeft = Math.max(i, child.getLeft());
                    int clampedChildTop = Math.max(topBound, child.getTop());
                    if (isLayoutRtl) {
                        i2 = startBound;
                    } else {
                        i2 = endBound;
                    }
                    int clampedChildRight = Math.min(i2, child.getRight());
                    int clampedChildBottom = Math.min(bottomBound, child.getBottom());
                    if (clampedChildLeft < left || clampedChildTop < top || clampedChildRight > right || clampedChildBottom > bottom) {
                        vis = 0;
                    } else {
                        vis = 4;
                    }
                    child.setVisibility(vis);
                }
                i3++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void setAllChildrenVisible() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == 4) {
                child.setVisibility(0);
            }
        }
    }

    private static boolean viewIsOpaque(View v) {
        if (v.isOpaque()) {
            return true;
        }
        if (VERSION.SDK_INT >= 18) {
            return false;
        }
        Drawable bg = v.getBackground();
        if (bg == null) {
            return false;
        }
        if (bg.getOpacity() != -1) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFirstLayout = true;
        int count = this.mPostedRunnables.size();
        for (int i = 0; i < count; i++) {
            ((DisableLayerRunnable) this.mPostedRunnables.get(i)).run();
        }
        this.mPostedRunnables.clear();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childHeightSpec;
        int childHeightSpec2;
        int childWidthSpec;
        int childHeightSpec3;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode != 1073741824) {
            if (!isInEditMode()) {
                throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
            } else if (widthMode != Integer.MIN_VALUE) {
                if (widthMode == 0) {
                    widthSize = 300;
                }
            }
        } else if (heightMode == 0) {
            if (!isInEditMode()) {
                throw new IllegalStateException("Height must not be UNSPECIFIED");
            } else if (heightMode == 0) {
                heightMode = Integer.MIN_VALUE;
                heightSize = 300;
            }
        }
        int layoutHeight = 0;
        int maxLayoutHeight = 0;
        switch (heightMode) {
            case Integer.MIN_VALUE:
                maxLayoutHeight = (heightSize - getPaddingTop()) - getPaddingBottom();
                break;
            case 1073741824:
                maxLayoutHeight = (heightSize - getPaddingTop()) - getPaddingBottom();
                layoutHeight = maxLayoutHeight;
                break;
        }
        float weightSum = 0.0f;
        boolean canSlide = false;
        int widthAvailable = (widthSize - getPaddingLeft()) - getPaddingRight();
        int widthRemaining = widthAvailable;
        int childCount = getChildCount();
        if (childCount > 2) {
            Log.e(TAG, "onMeasure: More than two child views are not supported.");
        }
        this.mSlideableView = null;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (child.getVisibility() == 8) {
                lp.dimWhenOffset = false;
            } else {
                if (lp.weight > 0.0f) {
                    weightSum += lp.weight;
                    if (lp.width == 0) {
                    }
                }
                int horizontalMargin = lp.leftMargin + lp.rightMargin;
                if (lp.width == -2) {
                    childWidthSpec = MeasureSpec.makeMeasureSpec(widthAvailable - horizontalMargin, Integer.MIN_VALUE);
                } else if (lp.width == -1) {
                    childWidthSpec = MeasureSpec.makeMeasureSpec(widthAvailable - horizontalMargin, 1073741824);
                } else {
                    childWidthSpec = MeasureSpec.makeMeasureSpec(lp.width, 1073741824);
                }
                if (lp.height == -2) {
                    childHeightSpec3 = MeasureSpec.makeMeasureSpec(maxLayoutHeight, Integer.MIN_VALUE);
                } else if (lp.height == -1) {
                    childHeightSpec3 = MeasureSpec.makeMeasureSpec(maxLayoutHeight, 1073741824);
                } else {
                    childHeightSpec3 = MeasureSpec.makeMeasureSpec(lp.height, 1073741824);
                }
                child.measure(childWidthSpec, childHeightSpec3);
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                if (heightMode == Integer.MIN_VALUE && childHeight > layoutHeight) {
                    layoutHeight = Math.min(childHeight, maxLayoutHeight);
                }
                widthRemaining -= childWidth;
                boolean z = widthRemaining < 0;
                lp.slideable = z;
                canSlide |= z;
                if (lp.slideable) {
                    this.mSlideableView = child;
                }
            }
        }
        if (canSlide || weightSum > 0.0f) {
            int fixedPanelWidthLimit = widthAvailable - this.mOverhangSize;
            for (int i2 = 0; i2 < childCount; i2++) {
                View child2 = getChildAt(i2);
                if (child2.getVisibility() != 8) {
                    LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
                    if (child2.getVisibility() != 8) {
                        boolean skippedFirstPass = lp2.width == 0 && lp2.weight > 0.0f;
                        int measuredWidth = skippedFirstPass ? 0 : child2.getMeasuredWidth();
                        if (!canSlide || child2 == this.mSlideableView) {
                            if (lp2.weight > 0.0f) {
                                if (lp2.width != 0) {
                                    childHeightSpec = MeasureSpec.makeMeasureSpec(child2.getMeasuredHeight(), 1073741824);
                                } else if (lp2.height == -2) {
                                    childHeightSpec = MeasureSpec.makeMeasureSpec(maxLayoutHeight, Integer.MIN_VALUE);
                                } else if (lp2.height == -1) {
                                    childHeightSpec = MeasureSpec.makeMeasureSpec(maxLayoutHeight, 1073741824);
                                } else {
                                    childHeightSpec = MeasureSpec.makeMeasureSpec(lp2.height, 1073741824);
                                }
                                if (canSlide) {
                                    int newWidth = widthAvailable - (lp2.leftMargin + lp2.rightMargin);
                                    int childWidthSpec2 = MeasureSpec.makeMeasureSpec(newWidth, 1073741824);
                                    if (measuredWidth != newWidth) {
                                        child2.measure(childWidthSpec2, childHeightSpec);
                                    }
                                } else {
                                    child2.measure(MeasureSpec.makeMeasureSpec(measuredWidth + ((int) ((lp2.weight * ((float) Math.max(0, widthRemaining))) / weightSum)), 1073741824), childHeightSpec);
                                }
                            }
                        } else if (lp2.width < 0 && (measuredWidth > fixedPanelWidthLimit || lp2.weight > 0.0f)) {
                            if (!skippedFirstPass) {
                                childHeightSpec2 = MeasureSpec.makeMeasureSpec(child2.getMeasuredHeight(), 1073741824);
                            } else if (lp2.height == -2) {
                                childHeightSpec2 = MeasureSpec.makeMeasureSpec(maxLayoutHeight, Integer.MIN_VALUE);
                            } else if (lp2.height == -1) {
                                childHeightSpec2 = MeasureSpec.makeMeasureSpec(maxLayoutHeight, 1073741824);
                            } else {
                                childHeightSpec2 = MeasureSpec.makeMeasureSpec(lp2.height, 1073741824);
                            }
                            child2.measure(MeasureSpec.makeMeasureSpec(fixedPanelWidthLimit, 1073741824), childHeightSpec2);
                        }
                    }
                }
            }
        }
        setMeasuredDimension(widthSize, getPaddingTop() + layoutHeight + getPaddingBottom());
        this.mCanSlide = canSlide;
        if (this.mDragHelper.getViewDragState() != 0 && !canSlide) {
            this.mDragHelper.abort();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft;
        int childRight;
        boolean isLayoutRtl = isLayoutRtlSupport();
        if (isLayoutRtl) {
            this.mDragHelper.setEdgeTrackingEnabled(2);
        } else {
            this.mDragHelper.setEdgeTrackingEnabled(1);
        }
        int width = r - l;
        int paddingStart = isLayoutRtl ? getPaddingRight() : getPaddingLeft();
        int paddingEnd = isLayoutRtl ? getPaddingLeft() : getPaddingRight();
        int paddingTop = getPaddingTop();
        int childCount = getChildCount();
        int xStart = paddingStart;
        int nextXStart = xStart;
        if (this.mFirstLayout) {
            this.mSlideOffset = (!this.mCanSlide || !this.mPreservedOpenState) ? 0.0f : 1.0f;
        }
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int childWidth = child.getMeasuredWidth();
                int offset = 0;
                if (lp.slideable) {
                    int range = (Math.min(nextXStart, (width - paddingEnd) - this.mOverhangSize) - xStart) - (lp.leftMargin + lp.rightMargin);
                    this.mSlideRange = range;
                    int lpMargin = isLayoutRtl ? lp.rightMargin : lp.leftMargin;
                    lp.dimWhenOffset = ((xStart + lpMargin) + range) + (childWidth / 2) > width - paddingEnd;
                    int pos = (int) (((float) range) * this.mSlideOffset);
                    xStart += pos + lpMargin;
                    this.mSlideOffset = ((float) pos) / ((float) this.mSlideRange);
                } else if (!this.mCanSlide || this.mParallaxBy == 0) {
                    xStart = nextXStart;
                } else {
                    offset = (int) ((1.0f - this.mSlideOffset) * ((float) this.mParallaxBy));
                    xStart = nextXStart;
                }
                if (isLayoutRtl) {
                    childRight = (width - xStart) + offset;
                    childLeft = childRight - childWidth;
                } else {
                    childLeft = xStart - offset;
                    childRight = childLeft + childWidth;
                }
                child.layout(childLeft, paddingTop, childRight, paddingTop + child.getMeasuredHeight());
                nextXStart += child.getWidth();
            }
        }
        if (this.mFirstLayout) {
            if (this.mCanSlide) {
                if (this.mParallaxBy != 0) {
                    parallaxOtherViews(this.mSlideOffset);
                }
                if (((LayoutParams) this.mSlideableView.getLayoutParams()).dimWhenOffset) {
                    dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
                }
            } else {
                for (int i2 = 0; i2 < childCount; i2++) {
                    dimChildView(getChildAt(i2), 0.0f, this.mSliderFadeColor);
                }
            }
            updateObscuredViewsVisibility(this.mSlideableView);
        }
        this.mFirstLayout = false;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw) {
            this.mFirstLayout = true;
        }
    }

    public void requestChildFocus(View child, View focused) {
        super.requestChildFocus(child, focused);
        if (!isInTouchMode() && !this.mCanSlide) {
            this.mPreservedOpenState = child == this.mSlideableView;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (!this.mCanSlide && action == 0 && getChildCount() > 1) {
            View secondChild = getChildAt(1);
            if (secondChild != null) {
                this.mPreservedOpenState = !this.mDragHelper.isViewUnder(secondChild, (int) ev.getX(), (int) ev.getY());
            }
        }
        if (!this.mCanSlide || (this.mIsUnableToDrag && action != 0)) {
            this.mDragHelper.cancel();
            return super.onInterceptTouchEvent(ev);
        } else if (action == 3 || action == 1) {
            this.mDragHelper.cancel();
            return false;
        } else {
            boolean interceptTap = false;
            switch (action) {
                case 0:
                    this.mIsUnableToDrag = false;
                    float x = ev.getX();
                    float y = ev.getY();
                    this.mInitialMotionX = x;
                    this.mInitialMotionY = y;
                    if (this.mDragHelper.isViewUnder(this.mSlideableView, (int) x, (int) y) && isDimmed(this.mSlideableView)) {
                        interceptTap = true;
                        break;
                    }
                case 2:
                    float x2 = ev.getX();
                    float y2 = ev.getY();
                    float adx = Math.abs(x2 - this.mInitialMotionX);
                    float ady = Math.abs(y2 - this.mInitialMotionY);
                    if (adx > ((float) this.mDragHelper.getTouchSlop()) && ady > adx) {
                        this.mDragHelper.cancel();
                        this.mIsUnableToDrag = true;
                        return false;
                    }
            }
            if (this.mDragHelper.shouldInterceptTouchEvent(ev) || interceptTap) {
                return true;
            }
            return false;
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (!this.mCanSlide) {
            return super.onTouchEvent(ev);
        }
        this.mDragHelper.processTouchEvent(ev);
        switch (ev.getActionMasked()) {
            case 0:
                float x = ev.getX();
                float y = ev.getY();
                this.mInitialMotionX = x;
                this.mInitialMotionY = y;
                return true;
            case 1:
                if (!isDimmed(this.mSlideableView)) {
                    return true;
                }
                float x2 = ev.getX();
                float y2 = ev.getY();
                float dx = x2 - this.mInitialMotionX;
                float dy = y2 - this.mInitialMotionY;
                int slop = this.mDragHelper.getTouchSlop();
                if ((dx * dx) + (dy * dy) >= ((float) (slop * slop)) || !this.mDragHelper.isViewUnder(this.mSlideableView, (int) x2, (int) y2)) {
                    return true;
                }
                closePane(this.mSlideableView, 0);
                return true;
            default:
                return true;
        }
    }

    private boolean closePane(View pane, int initialVelocity) {
        if (!this.mFirstLayout && !smoothSlideTo(0.0f, initialVelocity)) {
            return false;
        }
        this.mPreservedOpenState = false;
        return true;
    }

    private boolean openPane(View pane, int initialVelocity) {
        if (!this.mFirstLayout && !smoothSlideTo(1.0f, initialVelocity)) {
            return false;
        }
        this.mPreservedOpenState = true;
        return true;
    }

    @Deprecated
    public void smoothSlideOpen() {
        openPane();
    }

    public boolean openPane() {
        return openPane(this.mSlideableView, 0);
    }

    @Deprecated
    public void smoothSlideClosed() {
        closePane();
    }

    public boolean closePane() {
        return closePane(this.mSlideableView, 0);
    }

    public boolean isOpen() {
        return !this.mCanSlide || this.mSlideOffset == 1.0f;
    }

    @Deprecated
    public boolean canSlide() {
        return this.mCanSlide;
    }

    public boolean isSlideable() {
        return this.mCanSlide;
    }

    /* access modifiers changed from: 0000 */
    public void onPanelDragged(int newLeft) {
        int newStart;
        if (this.mSlideableView == null) {
            this.mSlideOffset = 0.0f;
            return;
        }
        boolean isLayoutRtl = isLayoutRtlSupport();
        LayoutParams lp = (LayoutParams) this.mSlideableView.getLayoutParams();
        int childWidth = this.mSlideableView.getWidth();
        if (isLayoutRtl) {
            newStart = (getWidth() - newLeft) - childWidth;
        } else {
            newStart = newLeft;
        }
        this.mSlideOffset = ((float) (newStart - ((isLayoutRtl ? getPaddingRight() : getPaddingLeft()) + (isLayoutRtl ? lp.rightMargin : lp.leftMargin)))) / ((float) this.mSlideRange);
        if (this.mParallaxBy != 0) {
            parallaxOtherViews(this.mSlideOffset);
        }
        if (lp.dimWhenOffset) {
            dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
        }
        dispatchOnPanelSlide(this.mSlideableView);
    }

    private void dimChildView(View v, float mag, int fadeColor) {
        LayoutParams lp = (LayoutParams) v.getLayoutParams();
        if (mag > 0.0f && fadeColor != 0) {
            int color = (((int) (((float) ((-16777216 & fadeColor) >>> 24)) * mag)) << 24) | (16777215 & fadeColor);
            if (lp.dimPaint == null) {
                lp.dimPaint = new Paint();
            }
            lp.dimPaint.setColorFilter(new PorterDuffColorFilter(color, Mode.SRC_OVER));
            if (v.getLayerType() != 2) {
                v.setLayerType(2, lp.dimPaint);
            }
            invalidateChildRegion(v);
        } else if (v.getLayerType() != 0) {
            if (lp.dimPaint != null) {
                lp.dimPaint.setColorFilter(null);
            }
            DisableLayerRunnable dlr = new DisableLayerRunnable(v);
            this.mPostedRunnables.add(dlr);
            ViewCompat.postOnAnimation(this, dlr);
        }
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View child, long drawingTime) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int save = canvas.save();
        if (this.mCanSlide && !lp.slideable && this.mSlideableView != null) {
            canvas.getClipBounds(this.mTmpRect);
            if (isLayoutRtlSupport()) {
                this.mTmpRect.left = Math.max(this.mTmpRect.left, this.mSlideableView.getRight());
            } else {
                this.mTmpRect.right = Math.min(this.mTmpRect.right, this.mSlideableView.getLeft());
            }
            canvas.clipRect(this.mTmpRect);
        }
        boolean result = super.drawChild(canvas, child, drawingTime);
        canvas.restoreToCount(save);
        return result;
    }

    /* access modifiers changed from: 0000 */
    public void invalidateChildRegion(View v) {
        IMPL.invalidateChildRegion(this, v);
    }

    /* access modifiers changed from: 0000 */
    public boolean smoothSlideTo(float slideOffset, int velocity) {
        int x;
        if (!this.mCanSlide) {
            return false;
        }
        LayoutParams lp = (LayoutParams) this.mSlideableView.getLayoutParams();
        if (isLayoutRtlSupport()) {
            x = (int) (((float) getWidth()) - ((((float) (getPaddingRight() + lp.rightMargin)) + (((float) this.mSlideRange) * slideOffset)) + ((float) this.mSlideableView.getWidth())));
        } else {
            x = (int) (((float) (getPaddingLeft() + lp.leftMargin)) + (((float) this.mSlideRange) * slideOffset));
        }
        if (!this.mDragHelper.smoothSlideViewTo(this.mSlideableView, x, this.mSlideableView.getTop())) {
            return false;
        }
        setAllChildrenVisible();
        ViewCompat.postInvalidateOnAnimation(this);
        return true;
    }

    public void computeScroll() {
        if (!this.mDragHelper.continueSettling(true)) {
            return;
        }
        if (!this.mCanSlide) {
            this.mDragHelper.abort();
        } else {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Deprecated
    public void setShadowDrawable(Drawable d) {
        setShadowDrawableLeft(d);
    }

    public void setShadowDrawableLeft(@Nullable Drawable d) {
        this.mShadowDrawableLeft = d;
    }

    public void setShadowDrawableRight(@Nullable Drawable d) {
        this.mShadowDrawableRight = d;
    }

    @Deprecated
    public void setShadowResource(@DrawableRes int resId) {
        setShadowDrawable(getResources().getDrawable(resId));
    }

    public void setShadowResourceLeft(int resId) {
        setShadowDrawableLeft(ContextCompat.getDrawable(getContext(), resId));
    }

    public void setShadowResourceRight(int resId) {
        setShadowDrawableRight(ContextCompat.getDrawable(getContext(), resId));
    }

    public void draw(Canvas c) {
        Drawable shadowDrawable;
        int right;
        int left;
        super.draw(c);
        if (isLayoutRtlSupport()) {
            shadowDrawable = this.mShadowDrawableRight;
        } else {
            shadowDrawable = this.mShadowDrawableLeft;
        }
        View shadowView = getChildCount() > 1 ? getChildAt(1) : null;
        if (shadowView != null && shadowDrawable != null) {
            int top = shadowView.getTop();
            int bottom = shadowView.getBottom();
            int shadowWidth = shadowDrawable.getIntrinsicWidth();
            if (isLayoutRtlSupport()) {
                left = shadowView.getRight();
                right = left + shadowWidth;
            } else {
                right = shadowView.getLeft();
                left = right - shadowWidth;
            }
            shadowDrawable.setBounds(left, top, right, bottom);
            shadowDrawable.draw(c);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:8:0x0020  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void parallaxOtherViews(float r13) {
        /*
            r12 = this;
            r11 = 1065353216(0x3f800000, float:1.0)
            boolean r4 = r12.isLayoutRtlSupport()
            android.view.View r9 = r12.mSlideableView
            android.view.ViewGroup$LayoutParams r7 = r9.getLayoutParams()
            android.support.v4.widget.SlidingPaneLayout$LayoutParams r7 = (android.support.v4.widget.SlidingPaneLayout.LayoutParams) r7
            boolean r9 = r7.dimWhenOffset
            if (r9 == 0) goto L_0x002e
            if (r4 == 0) goto L_0x002b
            int r9 = r7.rightMargin
        L_0x0016:
            if (r9 > 0) goto L_0x002e
            r1 = 1
        L_0x0019:
            int r0 = r12.getChildCount()
            r3 = 0
        L_0x001e:
            if (r3 >= r0) goto L_0x005c
            android.view.View r8 = r12.getChildAt(r3)
            android.view.View r9 = r12.mSlideableView
            if (r8 != r9) goto L_0x0030
        L_0x0028:
            int r3 = r3 + 1
            goto L_0x001e
        L_0x002b:
            int r9 = r7.leftMargin
            goto L_0x0016
        L_0x002e:
            r1 = 0
            goto L_0x0019
        L_0x0030:
            float r9 = r12.mParallaxOffset
            float r9 = r11 - r9
            int r10 = r12.mParallaxBy
            float r10 = (float) r10
            float r9 = r9 * r10
            int r6 = (int) r9
            r12.mParallaxOffset = r13
            float r9 = r11 - r13
            int r10 = r12.mParallaxBy
            float r10 = (float) r10
            float r9 = r9 * r10
            int r5 = (int) r9
            int r2 = r6 - r5
            if (r4 == 0) goto L_0x0047
            int r2 = -r2
        L_0x0047:
            r8.offsetLeftAndRight(r2)
            if (r1 == 0) goto L_0x0028
            if (r4 == 0) goto L_0x0057
            float r9 = r12.mParallaxOffset
            float r9 = r9 - r11
        L_0x0051:
            int r10 = r12.mCoveredFadeColor
            r12.dimChildView(r8, r9, r10)
            goto L_0x0028
        L_0x0057:
            float r9 = r12.mParallaxOffset
            float r9 = r11 - r9
            goto L_0x0051
        L_0x005c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.SlidingPaneLayout.parallaxOtherViews(float):void");
    }

    /* access modifiers changed from: protected */
    public boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            int scrollX = v.getScrollX();
            int scrollY = v.getScrollY();
            for (int i = group.getChildCount() - 1; i >= 0; i--) {
                View child = group.getChildAt(i);
                if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight() && y + scrollY >= child.getTop() && y + scrollY < child.getBottom()) {
                    if (canScroll(child, true, dx, (x + scrollX) - child.getLeft(), (y + scrollY) - child.getTop())) {
                        return true;
                    }
                }
            }
        }
        if (checkV) {
            if (!isLayoutRtlSupport()) {
                dx = -dx;
            }
            if (v.canScrollHorizontally(dx)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean isDimmed(View child) {
        if (child == null) {
            return false;
        }
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        if (!this.mCanSlide || !lp.dimWhenOffset || this.mSlideOffset <= 0.0f) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return p instanceof MarginLayoutParams ? new LayoutParams((MarginLayoutParams) p) : new LayoutParams(p);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return (p instanceof LayoutParams) && super.checkLayoutParams(p);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.isOpen = isSlideable() ? isOpen() : this.mPreservedOpenState;
        return ss;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        if (ss.isOpen) {
            openPane();
        } else {
            closePane();
        }
        this.mPreservedOpenState = ss.isOpen;
    }

    /* access modifiers changed from: 0000 */
    public boolean isLayoutRtlSupport() {
        return ViewCompat.getLayoutDirection(this) == 1;
    }
}
