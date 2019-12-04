package androidx.core.app;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import androidx.collection.SimpleArrayMap;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentHostCallback;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManagerNonConfig;

import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class FragmentController {
    private final FragmentHostCallback<?> mHost;

    public static final androidx.fragment.app.FragmentController createController(FragmentHostCallback<?> callbacks) {
        return new androidx.fragment.app.FragmentController(callbacks);
    }

    private FragmentController(FragmentHostCallback<?> callbacks) {
        this.mHost = callbacks;
    }

    public FragmentManager getSupportFragmentManager() {
        return this.mHost.getFragmentManagerImpl();
    }

    public androidx.loader.app.LoaderManager getSupportLoaderManager() {
        return this.mHost.getLoaderManagerImpl();
    }

    @Nullable
    public Fragment findFragmentByWho(String who) {
        return this.mHost.mFragmentManager.findFragmentByWho(who);
    }

    public int getActiveFragmentsCount() {
        return this.mHost.mFragmentManager.getActiveFragmentCount();
    }

    public List<Fragment> getActiveFragments(List<Fragment> list) {
        return this.mHost.mFragmentManager.getActiveFragments();
    }

    public void attachHost(Fragment parent) {
        this.mHost.mFragmentManager.attachController(this.mHost, this.mHost, parent);
    }

    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return this.mHost.mFragmentManager.onCreateView(parent, name, context, attrs);
    }

    public void noteStateNotSaved() {
        this.mHost.mFragmentManager.noteStateNotSaved();
    }

    public Parcelable saveAllState() {
        return this.mHost.mFragmentManager.saveAllState();
    }

    @Deprecated
    public void restoreAllState(Parcelable state, List<Fragment> nonConfigList) {
        this.mHost.mFragmentManager.restoreAllState(state, new FragmentManagerNonConfig(nonConfigList, null));
    }

    public void restoreAllState(Parcelable state, FragmentManagerNonConfig nonConfig) {
        this.mHost.mFragmentManager.restoreAllState(state, nonConfig);
    }

    @Deprecated
    public List<Fragment> retainNonConfig() {
        FragmentManagerNonConfig nonconf = this.mHost.mFragmentManager.retainNonConfig();
        if (nonconf != null) {
            return nonconf.getFragments();
        }
        return null;
    }

    public FragmentManagerNonConfig retainNestedNonConfig() {
        return this.mHost.mFragmentManager.retainNonConfig();
    }

    public void dispatchCreate() {
        this.mHost.mFragmentManager.dispatchCreate();
    }

    public void dispatchActivityCreated() {
        this.mHost.mFragmentManager.dispatchActivityCreated();
    }

    public void dispatchStart() {
        this.mHost.mFragmentManager.dispatchStart();
    }

    public void dispatchResume() {
        this.mHost.mFragmentManager.dispatchResume();
    }

    public void dispatchPause() {
        this.mHost.mFragmentManager.dispatchPause();
    }

    public void dispatchStop() {
        this.mHost.mFragmentManager.dispatchStop();
    }

    public void dispatchReallyStop() {
        this.mHost.mFragmentManager.dispatchReallyStop();
    }

    public void dispatchDestroyView() {
        this.mHost.mFragmentManager.dispatchDestroyView();
    }

    public void dispatchDestroy() {
        this.mHost.mFragmentManager.dispatchDestroy();
    }

    public void dispatchMultiWindowModeChanged(boolean isInMultiWindowMode) {
        this.mHost.mFragmentManager.dispatchMultiWindowModeChanged(isInMultiWindowMode);
    }

    public void dispatchPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        this.mHost.mFragmentManager.dispatchPictureInPictureModeChanged(isInPictureInPictureMode);
    }

    public void dispatchConfigurationChanged(Configuration newConfig) {
        this.mHost.mFragmentManager.dispatchConfigurationChanged(newConfig);
    }

    public void dispatchLowMemory() {
        this.mHost.mFragmentManager.dispatchLowMemory();
    }

    public boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        return this.mHost.mFragmentManager.dispatchCreateOptionsMenu(menu, inflater);
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu) {
        return this.mHost.mFragmentManager.dispatchPrepareOptionsMenu(menu);
    }

    public boolean dispatchOptionsItemSelected(MenuItem item) {
        return this.mHost.mFragmentManager.dispatchOptionsItemSelected(item);
    }

    public boolean dispatchContextItemSelected(MenuItem item) {
        return this.mHost.mFragmentManager.dispatchContextItemSelected(item);
    }

    public void dispatchOptionsMenuClosed(Menu menu) {
        this.mHost.mFragmentManager.dispatchOptionsMenuClosed(menu);
    }

    public boolean execPendingActions() {
        return this.mHost.mFragmentManager.execPendingActions();
    }

    public void doLoaderStart() {
        this.mHost.doLoaderStart();
    }

    public void doLoaderStop(boolean retain) {
        this.mHost.doLoaderStop(retain);
    }

    public void doLoaderRetain() {
        this.mHost.doLoaderRetain();
    }

    public void doLoaderDestroy() {
        this.mHost.doLoaderDestroy();
    }

    public void reportLoaderStart() {
        this.mHost.reportLoaderStart();
    }

    public SimpleArrayMap<String, LoaderManager> retainLoaderNonConfig() {
        return this.mHost.retainLoaderNonConfig();
    }

    public void restoreLoaderNonConfig(SimpleArrayMap<String, LoaderManager> loaderManagers) {
        this.mHost.restoreLoaderNonConfig(loaderManagers);
    }

    public void dumpLoaders(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        this.mHost.dumpLoaders(prefix, fd, writer, args);
    }
}
