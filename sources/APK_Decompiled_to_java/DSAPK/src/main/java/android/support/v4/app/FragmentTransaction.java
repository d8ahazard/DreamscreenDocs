package androidx.core.app;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import android.view.View;

import androidx.fragment.app.Fragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class FragmentTransaction {
    public static final int TRANSIT_ENTER_MASK = 4096;
    public static final int TRANSIT_EXIT_MASK = 8192;
    public static final int TRANSIT_FRAGMENT_CLOSE = 8194;
    public static final int TRANSIT_FRAGMENT_FADE = 4099;
    public static final int TRANSIT_FRAGMENT_OPEN = 4097;
    public static final int TRANSIT_NONE = 0;
    public static final int TRANSIT_UNSET = -1;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Transit {
    }

    public abstract androidx.fragment.app.FragmentTransaction add(@IdRes int i, Fragment fragment);

    public abstract androidx.fragment.app.FragmentTransaction add(@IdRes int i, Fragment fragment, @Nullable String str);

    public abstract androidx.fragment.app.FragmentTransaction add(Fragment fragment, String str);

    public abstract androidx.fragment.app.FragmentTransaction addSharedElement(View view, String str);

    public abstract androidx.fragment.app.FragmentTransaction addToBackStack(@Nullable String str);

    public abstract androidx.fragment.app.FragmentTransaction attach(Fragment fragment);

    public abstract int commit();

    public abstract int commitAllowingStateLoss();

    public abstract void commitNow();

    public abstract void commitNowAllowingStateLoss();

    public abstract androidx.fragment.app.FragmentTransaction detach(Fragment fragment);

    public abstract androidx.fragment.app.FragmentTransaction disallowAddToBackStack();

    public abstract androidx.fragment.app.FragmentTransaction hide(Fragment fragment);

    public abstract boolean isAddToBackStackAllowed();

    public abstract boolean isEmpty();

    public abstract androidx.fragment.app.FragmentTransaction remove(Fragment fragment);

    public abstract androidx.fragment.app.FragmentTransaction replace(@IdRes int i, Fragment fragment);

    public abstract androidx.fragment.app.FragmentTransaction replace(@IdRes int i, Fragment fragment, @Nullable String str);

    public abstract androidx.fragment.app.FragmentTransaction runOnCommit(Runnable runnable);

    @Deprecated
    public abstract androidx.fragment.app.FragmentTransaction setAllowOptimization(boolean z);

    public abstract androidx.fragment.app.FragmentTransaction setBreadCrumbShortTitle(@StringRes int i);

    public abstract androidx.fragment.app.FragmentTransaction setBreadCrumbShortTitle(CharSequence charSequence);

    public abstract androidx.fragment.app.FragmentTransaction setBreadCrumbTitle(@StringRes int i);

    public abstract androidx.fragment.app.FragmentTransaction setBreadCrumbTitle(CharSequence charSequence);

    public abstract androidx.fragment.app.FragmentTransaction setCustomAnimations(@AnimRes @AnimatorRes int i, @AnimRes @AnimatorRes int i2);

    public abstract androidx.fragment.app.FragmentTransaction setCustomAnimations(@AnimRes @AnimatorRes int i, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4);

    public abstract androidx.fragment.app.FragmentTransaction setPrimaryNavigationFragment(Fragment fragment);

    public abstract androidx.fragment.app.FragmentTransaction setReorderingAllowed(boolean z);

    public abstract androidx.fragment.app.FragmentTransaction setTransition(int i);

    public abstract androidx.fragment.app.FragmentTransaction setTransitionStyle(@StyleRes int i);

    public abstract androidx.fragment.app.FragmentTransaction show(Fragment fragment);
}
