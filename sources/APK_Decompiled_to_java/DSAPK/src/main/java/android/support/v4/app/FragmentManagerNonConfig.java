package androidx.core.app;

import androidx.fragment.app.Fragment;

import java.util.List;

public class FragmentManagerNonConfig {
    private final List<androidx.fragment.app.FragmentManagerNonConfig> mChildNonConfigs;
    private final List<Fragment> mFragments;

    FragmentManagerNonConfig(List<Fragment> fragments, List<androidx.fragment.app.FragmentManagerNonConfig> childNonConfigs) {
        this.mFragments = fragments;
        this.mChildNonConfigs = childNonConfigs;
    }

    /* access modifiers changed from: 0000 */
    public List<Fragment> getFragments() {
        return this.mFragments;
    }

    /* access modifiers changed from: 0000 */
    public List<androidx.fragment.app.FragmentManagerNonConfig> getChildNonConfigs() {
        return this.mChildNonConfigs;
    }
}
