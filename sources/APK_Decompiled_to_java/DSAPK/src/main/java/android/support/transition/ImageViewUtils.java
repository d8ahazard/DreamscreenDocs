package androidx.transition;

import android.animation.Animator;
import android.graphics.Matrix;
import android.os.Build.VERSION;
import android.widget.ImageView;

class ImageViewUtils {
    private static final ImageViewUtilsImpl IMPL;

    ImageViewUtils() {
    }

    static {
        if (VERSION.SDK_INT >= 21) {
            IMPL = new ImageViewUtilsApi21();
        } else {
            IMPL = new ImageViewUtilsApi14();
        }
    }

    static void startAnimateTransform(ImageView view) {
        IMPL.startAnimateTransform(view);
    }

    static void animateTransform(ImageView view, Matrix matrix) {
        IMPL.animateTransform(view, matrix);
    }

    static void reserveEndAnimateTransform(ImageView view, Animator animator) {
        IMPL.reserveEndAnimateTransform(view, animator);
    }
}
