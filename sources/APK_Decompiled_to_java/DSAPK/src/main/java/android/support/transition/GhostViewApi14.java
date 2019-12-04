package androidx.transition;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Matrix;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.FrameLayout;

@SuppressLint({"ViewConstructor"})
@RequiresApi(14)
class GhostViewApi14 extends View implements GhostViewImpl {
    Matrix mCurrentMatrix;
    private int mDeltaX;
    private int mDeltaY;
    private final Matrix mMatrix = new Matrix();
    private final OnPreDrawListener mOnPreDrawListener = new OnPreDrawListener() {
        public boolean onPreDraw() {
            GhostViewApi14.this.mCurrentMatrix = GhostViewApi14.this.mView.getMatrix();
            ViewCompat.postInvalidateOnAnimation(GhostViewApi14.this);
            if (!(GhostViewApi14.this.mStartParent == null || GhostViewApi14.this.mStartView == null)) {
                GhostViewApi14.this.mStartParent.endViewTransition(GhostViewApi14.this.mStartView);
                ViewCompat.postInvalidateOnAnimation(GhostViewApi14.this.mStartParent);
                GhostViewApi14.this.mStartParent = null;
                GhostViewApi14.this.mStartView = null;
            }
            return true;
        }
    };
    int mReferences;
    ViewGroup mStartParent;
    View mStartView;
    final View mView;

    static class Creator implements androidx.transition.GhostViewImpl.Creator {
        Creator() {
        }

        public GhostViewImpl addGhost(View view, ViewGroup viewGroup, Matrix matrix) {
            GhostViewApi14 ghostView = GhostViewApi14.getGhostView(view);
            if (ghostView == null) {
                FrameLayout frameLayout = findFrameLayout(viewGroup);
                if (frameLayout == null) {
                    return null;
                }
                ghostView = new GhostViewApi14(view);
                frameLayout.addView(ghostView);
            }
            ghostView.mReferences++;
            return ghostView;
        }

        public void removeGhost(View view) {
            GhostViewApi14 ghostView = GhostViewApi14.getGhostView(view);
            if (ghostView != null) {
                ghostView.mReferences--;
                if (ghostView.mReferences <= 0) {
                    ViewParent parent = ghostView.getParent();
                    if (parent instanceof ViewGroup) {
                        ViewGroup group = (ViewGroup) parent;
                        group.endViewTransition(ghostView);
                        group.removeView(ghostView);
                    }
                }
            }
        }

        private static FrameLayout findFrameLayout(ViewGroup viewGroup) {
            while (!(viewGroup instanceof FrameLayout)) {
                ViewParent parent = viewGroup.getParent();
                if (!(parent instanceof ViewGroup)) {
                    return null;
                }
                viewGroup = (ViewGroup) parent;
            }
            return (FrameLayout) viewGroup;
        }
    }

    GhostViewApi14(View view) {
        super(view.getContext());
        this.mView = view;
        setLayerType(2, null);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setGhostView(this.mView, this);
        int[] location = new int[2];
        int[] viewLocation = new int[2];
        getLocationOnScreen(location);
        this.mView.getLocationOnScreen(viewLocation);
        viewLocation[0] = (int) (((float) viewLocation[0]) - this.mView.getTranslationX());
        viewLocation[1] = (int) (((float) viewLocation[1]) - this.mView.getTranslationY());
        this.mDeltaX = viewLocation[0] - location[0];
        this.mDeltaY = viewLocation[1] - location[1];
        this.mView.getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
        this.mView.setVisibility(4);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        this.mView.getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
        this.mView.setVisibility(0);
        setGhostView(this.mView, null);
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.mMatrix.set(this.mCurrentMatrix);
        this.mMatrix.postTranslate((float) this.mDeltaX, (float) this.mDeltaY);
        canvas.setMatrix(this.mMatrix);
        this.mView.draw(canvas);
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        this.mView.setVisibility(visibility == 0 ? 4 : 0);
    }

    public void reserveEndViewTransition(ViewGroup viewGroup, View view) {
        this.mStartParent = viewGroup;
        this.mStartView = view;
    }

    private static void setGhostView(@NonNull View view, GhostViewApi14 ghostView) {
        view.setTag(R.id.ghost_view, ghostView);
    }

    static GhostViewApi14 getGhostView(@NonNull View view) {
        return (GhostViewApi14) view.getTag(R.id.ghost_view);
    }
}
