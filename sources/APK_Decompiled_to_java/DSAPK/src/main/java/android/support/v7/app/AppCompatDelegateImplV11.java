package androidx.appcompat.app;

import android.content.Context;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;

@RequiresApi(14)
class AppCompatDelegateImplV11 extends AppCompatDelegateImplV9 {
    AppCompatDelegateImplV11(Context context, Window window, AppCompatCallback callback) {
        super(context, window, callback);
    }

    public boolean hasWindowFeature(int featureId) {
        return super.hasWindowFeature(featureId) || this.mWindow.hasFeature(featureId);
    }

    /* access modifiers changed from: 0000 */
    public View callActivityOnCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return null;
    }
}
