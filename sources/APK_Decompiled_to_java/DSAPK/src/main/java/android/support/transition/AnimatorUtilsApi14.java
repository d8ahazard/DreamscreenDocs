package androidx.transition;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;

@RequiresApi(14)
class AnimatorUtilsApi14 implements AnimatorUtilsImpl {

    interface AnimatorPauseListenerCompat {
        void onAnimationPause(Animator animator);

        void onAnimationResume(Animator animator);
    }

    AnimatorUtilsApi14() {
    }

    public void addPauseListener(@NonNull Animator animator, @NonNull AnimatorListenerAdapter listener) {
    }

    public void pause(@NonNull Animator animator) {
        ArrayList<AnimatorListener> listeners = animator.getListeners();
        if (listeners != null) {
            int size = listeners.size();
            for (int i = 0; i < size; i++) {
                AnimatorListener listener = (AnimatorListener) listeners.get(i);
                if (listener instanceof AnimatorPauseListenerCompat) {
                    ((AnimatorPauseListenerCompat) listener).onAnimationPause(animator);
                }
            }
        }
    }

    public void resume(@NonNull Animator animator) {
        ArrayList<AnimatorListener> listeners = animator.getListeners();
        if (listeners != null) {
            int size = listeners.size();
            for (int i = 0; i < size; i++) {
                AnimatorListener listener = (AnimatorListener) listeners.get(i);
                if (listener instanceof AnimatorPauseListenerCompat) {
                    ((AnimatorPauseListenerCompat) listener).onAnimationResume(animator);
                }
            }
        }
    }
}
