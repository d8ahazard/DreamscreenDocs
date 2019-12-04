package androidx.transition;

import android.animation.Animator;
import android.graphics.Matrix;
import androidx.annotation.RequiresApi;
import android.widget.ImageView;

@RequiresApi(14)
interface ImageViewUtilsImpl {
    void animateTransform(ImageView imageView, Matrix matrix);

    void reserveEndAnimateTransform(ImageView imageView, Animator animator);

    void startAnimateTransform(ImageView imageView);
}
