package android.support.design.widget;

import android.graphics.Outline;
import androidx.annotation.RequiresApi;

import com.google.android.material.internal.CircularBorderDrawable;

@RequiresApi(21)
class CircularBorderDrawableLollipop extends CircularBorderDrawable {
    CircularBorderDrawableLollipop() {
    }

    public void getOutline(Outline outline) {
        copyBounds(this.mRect);
        outline.setOval(this.mRect);
    }
}
