package android.support.design.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;

class VisibilityAwareImageButton extends AppCompatImageButton {
    private int mUserSetVisibility;

    public VisibilityAwareImageButton(Context context) {
        this(context, null);
    }

    public VisibilityAwareImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VisibilityAwareImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mUserSetVisibility = getVisibility();
    }

    public void setVisibility(int visibility) {
        internalSetVisibility(visibility, true);
    }

    /* access modifiers changed from: 0000 */
    public final void internalSetVisibility(int visibility, boolean fromUser) {
        super.setVisibility(visibility);
        if (fromUser) {
            this.mUserSetVisibility = visibility;
        }
    }

    /* access modifiers changed from: 0000 */
    public final int getUserSetVisibility() {
        return this.mUserSetVisibility;
    }
}
