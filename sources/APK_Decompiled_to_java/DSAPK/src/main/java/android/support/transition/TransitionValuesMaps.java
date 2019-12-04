package androidx.transition;

import androidx.annotation.RequiresApi;
import androidx.collection.ArrayMap;
import androidx.collection.LongSparseArray;
import android.util.SparseArray;
import android.view.View;

@RequiresApi(14)
class TransitionValuesMaps {
    final SparseArray<View> mIdValues = new SparseArray<>();
    final LongSparseArray<View> mItemIdValues = new LongSparseArray<>();
    final ArrayMap<String, View> mNameValues = new ArrayMap<>();
    final ArrayMap<View, TransitionValues> mViewValues = new ArrayMap<>();

    TransitionValuesMaps() {
    }
}
