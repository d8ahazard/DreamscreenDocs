package androidx.transition;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import android.view.View;

@RequiresApi(18)
class ViewUtilsApi18 extends ViewUtilsApi14 {
    ViewUtilsApi18() {
    }

    public ViewOverlayImpl getOverlay(@NonNull View view) {
        return new ViewOverlayApi18(view);
    }

    public WindowIdImpl getWindowId(@NonNull View view) {
        return new WindowIdApi18(view);
    }
}
