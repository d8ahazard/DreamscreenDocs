package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.annotation.StringRes;
import android.support.design.R;
import android.support.design.internal.SnackbarContentLayout;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.BaseTransientBottomBar.BaseCallback;
import com.google.android.material.snackbar.BaseTransientBottomBar.ContentViewCallback;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

public final class Snackbar extends BaseTransientBottomBar<Snackbar> {
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_LONG = 0;
    public static final int LENGTH_SHORT = -1;
    @Nullable
    private BaseCallback<com.google.android.material.snackbar.Snackbar> mCallback;

    public static class Callback extends BaseCallback<com.google.android.material.snackbar.Snackbar> {
        public static final int DISMISS_EVENT_ACTION = 1;
        public static final int DISMISS_EVENT_CONSECUTIVE = 4;
        public static final int DISMISS_EVENT_MANUAL = 3;
        public static final int DISMISS_EVENT_SWIPE = 0;
        public static final int DISMISS_EVENT_TIMEOUT = 2;

        public void onShown(com.google.android.material.snackbar.Snackbar sb) {
        }

        public void onDismissed(com.google.android.material.snackbar.Snackbar transientBottomBar, int event) {
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final class SnackbarLayout extends SnackbarBaseLayout {
        public SnackbarLayout(Context context) {
            super(context);
        }

        public SnackbarLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int childCount = getChildCount();
            int availableWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (child.getLayoutParams().width == -1) {
                    child.measure(MeasureSpec.makeMeasureSpec(availableWidth, 1073741824), MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(), 1073741824));
                }
            }
        }
    }

    private Snackbar(ViewGroup parent, View content, ContentViewCallback contentViewCallback) {
        super(parent, content, contentViewCallback);
    }

    @NonNull
    public static com.google.android.material.snackbar.Snackbar make(@NonNull View view, @NonNull CharSequence text, int duration) {
        ViewGroup parent = findSuitableParent(view);
        if (parent == null) {
            throw new IllegalArgumentException("No suitable parent found from the given view. Please provide a valid view.");
        }
        SnackbarContentLayout content = (SnackbarContentLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.design_layout_snackbar_include, parent, false);
        com.google.android.material.snackbar.Snackbar snackbar = new com.google.android.material.snackbar.Snackbar(parent, content, content);
        snackbar.setText(text);
        snackbar.setDuration(duration);
        return snackbar;
    }

    @NonNull
    public static com.google.android.material.snackbar.Snackbar make(@NonNull View view, @StringRes int resId, int duration) {
        return make(view, view.getResources().getText(resId), duration);
    }

    private static ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;
        while (!(view instanceof CoordinatorLayout)) {
            if (view instanceof FrameLayout) {
                if (view.getId() == 16908290) {
                    return (ViewGroup) view;
                }
                fallback = (ViewGroup) view;
            }
            if (view != null) {
                ViewParent parent = view.getParent();
                if (parent instanceof View) {
                    view = (View) parent;
                    continue;
                } else {
                    view = null;
                    continue;
                }
            }
            if (view == null) {
                return fallback;
            }
        }
        return (ViewGroup) view;
    }

    @NonNull
    public com.google.android.material.snackbar.Snackbar setText(@NonNull CharSequence message) {
        ((SnackbarContentLayout) this.mView.getChildAt(0)).getMessageView().setText(message);
        return this;
    }

    @NonNull
    public com.google.android.material.snackbar.Snackbar setText(@StringRes int resId) {
        return setText(getContext().getText(resId));
    }

    @NonNull
    public com.google.android.material.snackbar.Snackbar setAction(@StringRes int resId, OnClickListener listener) {
        return setAction(getContext().getText(resId), listener);
    }

    @NonNull
    public com.google.android.material.snackbar.Snackbar setAction(CharSequence text, final OnClickListener listener) {
        TextView tv = ((SnackbarContentLayout) this.mView.getChildAt(0)).getActionView();
        if (TextUtils.isEmpty(text) || listener == null) {
            tv.setVisibility(8);
            tv.setOnClickListener(null);
        } else {
            tv.setVisibility(0);
            tv.setText(text);
            tv.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    listener.onClick(view);
                    com.google.android.material.snackbar.Snackbar.this.dispatchDismiss(1);
                }
            });
        }
        return this;
    }

    @NonNull
    public com.google.android.material.snackbar.Snackbar setActionTextColor(ColorStateList colors) {
        ((SnackbarContentLayout) this.mView.getChildAt(0)).getActionView().setTextColor(colors);
        return this;
    }

    @NonNull
    public com.google.android.material.snackbar.Snackbar setActionTextColor(@ColorInt int color) {
        ((SnackbarContentLayout) this.mView.getChildAt(0)).getActionView().setTextColor(color);
        return this;
    }

    @Deprecated
    @NonNull
    public com.google.android.material.snackbar.Snackbar setCallback(Callback callback) {
        if (this.mCallback != null) {
            removeCallback(this.mCallback);
        }
        if (callback != null) {
            addCallback(callback);
        }
        this.mCallback = callback;
        return this;
    }
}
