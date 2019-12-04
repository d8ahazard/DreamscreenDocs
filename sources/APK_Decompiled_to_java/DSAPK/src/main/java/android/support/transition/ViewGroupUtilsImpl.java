package androidx.transition;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import android.view.ViewGroup;

@RequiresApi(14)
interface ViewGroupUtilsImpl {
    ViewGroupOverlayImpl getOverlay(@NonNull ViewGroup viewGroup);

    void suppressLayout(@NonNull ViewGroup viewGroup, boolean z);
}
