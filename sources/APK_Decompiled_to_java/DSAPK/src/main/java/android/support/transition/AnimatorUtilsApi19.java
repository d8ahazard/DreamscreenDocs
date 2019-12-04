package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(19)
class AnimatorUtilsApi19 implements AnimatorUtilsImpl {
    AnimatorUtilsApi19() {
    }

    public void addPauseListener(@NonNull Animator animator, @NonNull AnimatorListenerAdapter listener) {
        animator.addPauseListener(listener);
    }

    public void pause(@NonNull Animator animator) {
        animator.pause();
    }

    public void resume(@NonNull Animator animator) {
        animator.resume();
    }
}
