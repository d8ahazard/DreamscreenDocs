package androidx.transition;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;

@RequiresApi(18)
class ViewGroupOverlayApi18 implements ViewGroupOverlayImpl {
    private final ViewGroupOverlay mViewGroupOverlay;

    ViewGroupOverlayApi18(@NonNull ViewGroup group) {
        this.mViewGroupOverlay = group.getOverlay();
    }

    public void add(@NonNull Drawable drawable) {
        this.mViewGroupOverlay.add(drawable);
    }

    public void clear() {
        this.mViewGroupOverlay.clear();
    }

    public void remove(@NonNull Drawable drawable) {
        this.mViewGroupOverlay.remove(drawable);
    }

    public void add(@NonNull View view) {
        this.mViewGroupOverlay.add(view);
    }

    public void remove(@NonNull View view) {
        this.mViewGroupOverlay.remove(view);
    }
}
