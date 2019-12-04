package androidx.transition;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import android.view.View;

@RequiresApi(14)
interface ViewGroupOverlayImpl extends ViewOverlayImpl {
    void add(@NonNull View view);

    void remove(@NonNull View view);
}
