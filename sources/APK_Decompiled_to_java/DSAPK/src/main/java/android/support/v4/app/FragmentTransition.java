package androidx.core.app;

import android.graphics.Rect;
import android.os.Build.VERSION;
import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransitionImpl;
import androidx.fragment.app.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class FragmentTransition {
    private static final int[] INVERSE_OPS = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8};
    private static final FragmentTransitionImpl PLATFORM_IMPL = (VERSION.SDK_INT >= 21 ? new FragmentTransitionCompat21() : null);
    private static final FragmentTransitionImpl SUPPORT_IMPL = resolveSupportImpl();

    static class FragmentContainerTransition {
        public Fragment firstOut;
        public boolean firstOutIsPop;
        public BackStackRecord firstOutTransaction;
        public Fragment lastIn;
        public boolean lastInIsPop;
        public BackStackRecord lastInTransaction;

        FragmentContainerTransition() {
        }
    }

    FragmentTransition() {
    }

    private static FragmentTransitionImpl resolveSupportImpl() {
        try {
            return (FragmentTransitionImpl) Class.forName("android.support.transition.FragmentTransitionSupport").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }

    static void startTransitions(FragmentManagerImpl fragmentManager, ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex, boolean isReordered) {
        if (fragmentManager.mCurState >= 1) {
            SparseArray<FragmentContainerTransition> transitioningFragments = new SparseArray<>();
            for (int i = startIndex; i < endIndex; i++) {
                BackStackRecord record = (BackStackRecord) records.get(i);
                if (((Boolean) isRecordPop.get(i)).booleanValue()) {
                    calculatePopFragments(record, transitioningFragments, isReordered);
                } else {
                    calculateFragments(record, transitioningFragments, isReordered);
                }
            }
            if (transitioningFragments.size() != 0) {
                View nonExistentView = new View(fragmentManager.mHost.getContext());
                int numContainers = transitioningFragments.size();
                for (int i2 = 0; i2 < numContainers; i2++) {
                    int containerId = transitioningFragments.keyAt(i2);
                    ArrayMap<String, String> nameOverrides = calculateNameOverrides(containerId, records, isRecordPop, startIndex, endIndex);
                    FragmentContainerTransition containerTransition = (FragmentContainerTransition) transitioningFragments.valueAt(i2);
                    if (isReordered) {
                        configureTransitionsReordered(fragmentManager, containerId, containerTransition, nonExistentView, nameOverrides);
                    } else {
                        configureTransitionsOrdered(fragmentManager, containerId, containerTransition, nonExistentView, nameOverrides);
                    }
                }
            }
        }
    }

    private static ArrayMap<String, String> calculateNameOverrides(int containerId, ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex) {
        ArrayList<String> sources;
        ArrayList<String> targets;
        ArrayMap<String, String> nameOverrides = new ArrayMap<>();
        for (int recordNum = endIndex - 1; recordNum >= startIndex; recordNum--) {
            BackStackRecord record = (BackStackRecord) records.get(recordNum);
            if (record.interactsWith(containerId)) {
                boolean isPop = ((Boolean) isRecordPop.get(recordNum)).booleanValue();
                if (record.mSharedElementSourceNames != null) {
                    int numSharedElements = record.mSharedElementSourceNames.size();
                    if (isPop) {
                        targets = record.mSharedElementSourceNames;
                        sources = record.mSharedElementTargetNames;
                    } else {
                        sources = record.mSharedElementSourceNames;
                        targets = record.mSharedElementTargetNames;
                    }
                    for (int i = 0; i < numSharedElements; i++) {
                        String sourceName = (String) sources.get(i);
                        String targetName = (String) targets.get(i);
                        String previousTarget = (String) nameOverrides.remove(targetName);
                        if (previousTarget != null) {
                            nameOverrides.put(sourceName, previousTarget);
                        } else {
                            nameOverrides.put(sourceName, targetName);
                        }
                    }
                }
            }
        }
        return nameOverrides;
    }

    private static void configureTransitionsReordered(FragmentManagerImpl fragmentManager, int containerId, FragmentContainerTransition fragments, View nonExistentView, ArrayMap<String, String> nameOverrides) {
        ViewGroup sceneRoot = null;
        if (fragmentManager.mContainer.onHasView()) {
            sceneRoot = (ViewGroup) fragmentManager.mContainer.onFindViewById(containerId);
        }
        if (sceneRoot != null) {
            Fragment inFragment = fragments.lastIn;
            Fragment outFragment = fragments.firstOut;
            FragmentTransitionImpl impl = chooseImpl(outFragment, inFragment);
            if (impl != null) {
                boolean inIsPop = fragments.lastInIsPop;
                boolean outIsPop = fragments.firstOutIsPop;
                ArrayList<View> sharedElementsIn = new ArrayList<>();
                ArrayList<View> sharedElementsOut = new ArrayList<>();
                Object enterTransition = getEnterTransition(impl, inFragment, inIsPop);
                Object exitTransition = getExitTransition(impl, outFragment, outIsPop);
                Object sharedElementTransition = configureSharedElementsReordered(impl, sceneRoot, nonExistentView, nameOverrides, fragments, sharedElementsOut, sharedElementsIn, enterTransition, exitTransition);
                if (enterTransition != null || sharedElementTransition != null || exitTransition != null) {
                    ArrayList<View> exitingViews = configureEnteringExitingViews(impl, exitTransition, outFragment, sharedElementsOut, nonExistentView);
                    ArrayList<View> enteringViews = configureEnteringExitingViews(impl, enterTransition, inFragment, sharedElementsIn, nonExistentView);
                    setViewVisibility(enteringViews, 4);
                    Object transition = mergeTransitions(impl, enterTransition, exitTransition, sharedElementTransition, inFragment, inIsPop);
                    if (transition != null) {
                        replaceHide(impl, exitTransition, outFragment, exitingViews);
                        ArrayList<String> inNames = impl.prepareSetNameOverridesReordered(sharedElementsIn);
                        impl.scheduleRemoveTargets(transition, enterTransition, enteringViews, exitTransition, exitingViews, sharedElementTransition, sharedElementsIn);
                        impl.beginDelayedTransition(sceneRoot, transition);
                        impl.setNameOverridesReordered(sceneRoot, sharedElementsOut, sharedElementsIn, inNames, nameOverrides);
                        setViewVisibility(enteringViews, 0);
                        impl.swapSharedElementTargets(sharedElementTransition, sharedElementsOut, sharedElementsIn);
                    }
                }
            }
        }
    }

    private static void replaceHide(FragmentTransitionImpl impl, Object exitTransition, Fragment exitingFragment, final ArrayList<View> exitingViews) {
        if (exitingFragment != null && exitTransition != null && exitingFragment.mAdded && exitingFragment.mHidden && exitingFragment.mHiddenChanged) {
            exitingFragment.setHideReplaced(true);
            impl.scheduleHideFragmentView(exitTransition, exitingFragment.getView(), exitingViews);
            OneShotPreDrawListener.add(exitingFragment.mContainer, new Runnable() {
                public void run() {
                    androidx.fragment.app.FragmentTransition.setViewVisibility(exitingViews, 4);
                }
            });
        }
    }

    private static void configureTransitionsOrdered(FragmentManagerImpl fragmentManager, int containerId, FragmentContainerTransition fragments, View nonExistentView, ArrayMap<String, String> nameOverrides) {
        ViewGroup sceneRoot = null;
        if (fragmentManager.mContainer.onHasView()) {
            sceneRoot = (ViewGroup) fragmentManager.mContainer.onFindViewById(containerId);
        }
        if (sceneRoot != null) {
            Fragment inFragment = fragments.lastIn;
            Fragment outFragment = fragments.firstOut;
            FragmentTransitionImpl impl = chooseImpl(outFragment, inFragment);
            if (impl != null) {
                boolean inIsPop = fragments.lastInIsPop;
                boolean outIsPop = fragments.firstOutIsPop;
                Object enterTransition = getEnterTransition(impl, inFragment, inIsPop);
                Object exitTransition = getExitTransition(impl, outFragment, outIsPop);
                ArrayList<View> sharedElementsOut = new ArrayList<>();
                ArrayList<View> sharedElementsIn = new ArrayList<>();
                Object sharedElementTransition = configureSharedElementsOrdered(impl, sceneRoot, nonExistentView, nameOverrides, fragments, sharedElementsOut, sharedElementsIn, enterTransition, exitTransition);
                if (enterTransition != null || sharedElementTransition != null || exitTransition != null) {
                    ArrayList<View> exitingViews = configureEnteringExitingViews(impl, exitTransition, outFragment, sharedElementsOut, nonExistentView);
                    if (exitingViews == null || exitingViews.isEmpty()) {
                        exitTransition = null;
                    }
                    impl.addTarget(enterTransition, nonExistentView);
                    Object transition = mergeTransitions(impl, enterTransition, exitTransition, sharedElementTransition, inFragment, fragments.lastInIsPop);
                    if (transition != null) {
                        ArrayList<View> enteringViews = new ArrayList<>();
                        impl.scheduleRemoveTargets(transition, enterTransition, enteringViews, exitTransition, exitingViews, sharedElementTransition, sharedElementsIn);
                        scheduleTargetChange(impl, sceneRoot, inFragment, nonExistentView, sharedElementsIn, enterTransition, enteringViews, exitTransition, exitingViews);
                        impl.setNameOverridesOrdered(sceneRoot, sharedElementsIn, nameOverrides);
                        impl.beginDelayedTransition(sceneRoot, transition);
                        impl.scheduleNameReset(sceneRoot, sharedElementsIn, nameOverrides);
                    }
                }
            }
        }
    }

    private static void scheduleTargetChange(FragmentTransitionImpl impl, ViewGroup sceneRoot, Fragment inFragment, View nonExistentView, ArrayList<View> sharedElementsIn, Object enterTransition, ArrayList<View> enteringViews, Object exitTransition, ArrayList<View> exitingViews) {
        final Object obj = enterTransition;
        final FragmentTransitionImpl fragmentTransitionImpl = impl;
        final View view = nonExistentView;
        final Fragment fragment = inFragment;
        final ArrayList<View> arrayList = sharedElementsIn;
        final ArrayList<View> arrayList2 = enteringViews;
        final ArrayList<View> arrayList3 = exitingViews;
        final Object obj2 = exitTransition;
        OneShotPreDrawListener.add(sceneRoot, new Runnable() {
            public void run() {
                if (obj != null) {
                    fragmentTransitionImpl.removeTarget(obj, view);
                    arrayList2.addAll(androidx.fragment.app.FragmentTransition.configureEnteringExitingViews(fragmentTransitionImpl, obj, fragment, arrayList, view));
                }
                if (arrayList3 != null) {
                    if (obj2 != null) {
                        ArrayList<View> tempExiting = new ArrayList<>();
                        tempExiting.add(view);
                        fragmentTransitionImpl.replaceTargets(obj2, arrayList3, tempExiting);
                    }
                    arrayList3.clear();
                    arrayList3.add(view);
                }
            }
        });
    }

    private static FragmentTransitionImpl chooseImpl(Fragment outFragment, Fragment inFragment) {
        ArrayList<Object> transitions = new ArrayList<>();
        if (outFragment != null) {
            Object exitTransition = outFragment.getExitTransition();
            if (exitTransition != null) {
                transitions.add(exitTransition);
            }
            Object returnTransition = outFragment.getReturnTransition();
            if (returnTransition != null) {
                transitions.add(returnTransition);
            }
            Object sharedReturnTransition = outFragment.getSharedElementReturnTransition();
            if (sharedReturnTransition != null) {
                transitions.add(sharedReturnTransition);
            }
        }
        if (inFragment != null) {
            Object enterTransition = inFragment.getEnterTransition();
            if (enterTransition != null) {
                transitions.add(enterTransition);
            }
            Object reenterTransition = inFragment.getReenterTransition();
            if (reenterTransition != null) {
                transitions.add(reenterTransition);
            }
            Object sharedEnterTransition = inFragment.getSharedElementEnterTransition();
            if (sharedEnterTransition != null) {
                transitions.add(sharedEnterTransition);
            }
        }
        if (transitions.isEmpty()) {
            return null;
        }
        if (PLATFORM_IMPL != null && canHandleAll(PLATFORM_IMPL, transitions)) {
            return PLATFORM_IMPL;
        }
        if (SUPPORT_IMPL != null && canHandleAll(SUPPORT_IMPL, transitions)) {
            return SUPPORT_IMPL;
        }
        if (PLATFORM_IMPL == null && SUPPORT_IMPL == null) {
            return null;
        }
        throw new IllegalArgumentException("Invalid Transition types");
    }

    private static boolean canHandleAll(FragmentTransitionImpl impl, List<Object> transitions) {
        int size = transitions.size();
        for (int i = 0; i < size; i++) {
            if (!impl.canHandle(transitions.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static Object getSharedElementTransition(FragmentTransitionImpl impl, Fragment inFragment, Fragment outFragment, boolean isPop) {
        Object sharedElementEnterTransition;
        if (inFragment == null || outFragment == null) {
            return null;
        }
        if (isPop) {
            sharedElementEnterTransition = outFragment.getSharedElementReturnTransition();
        } else {
            sharedElementEnterTransition = inFragment.getSharedElementEnterTransition();
        }
        return impl.wrapTransitionInSet(impl.cloneTransition(sharedElementEnterTransition));
    }

    private static Object getEnterTransition(FragmentTransitionImpl impl, Fragment inFragment, boolean isPop) {
        Object enterTransition;
        if (inFragment == null) {
            return null;
        }
        if (isPop) {
            enterTransition = inFragment.getReenterTransition();
        } else {
            enterTransition = inFragment.getEnterTransition();
        }
        return impl.cloneTransition(enterTransition);
    }

    private static Object getExitTransition(FragmentTransitionImpl impl, Fragment outFragment, boolean isPop) {
        Object exitTransition;
        if (outFragment == null) {
            return null;
        }
        if (isPop) {
            exitTransition = outFragment.getReturnTransition();
        } else {
            exitTransition = outFragment.getExitTransition();
        }
        return impl.cloneTransition(exitTransition);
    }

    private static Object configureSharedElementsReordered(FragmentTransitionImpl impl, ViewGroup sceneRoot, View nonExistentView, ArrayMap<String, String> nameOverrides, FragmentContainerTransition fragments, ArrayList<View> sharedElementsOut, ArrayList<View> sharedElementsIn, Object enterTransition, Object exitTransition) {
        Object sharedElementTransition;
        final Rect epicenter;
        final View epicenterView;
        final Fragment inFragment = fragments.lastIn;
        final Fragment outFragment = fragments.firstOut;
        if (inFragment != null) {
            inFragment.getView().setVisibility(0);
        }
        if (inFragment == null || outFragment == null) {
            return null;
        }
        final boolean inIsPop = fragments.lastInIsPop;
        if (nameOverrides.isEmpty()) {
            sharedElementTransition = null;
        } else {
            sharedElementTransition = getSharedElementTransition(impl, inFragment, outFragment, inIsPop);
        }
        ArrayMap<String, View> outSharedElements = captureOutSharedElements(impl, nameOverrides, sharedElementTransition, fragments);
        final ArrayMap<String, View> inSharedElements = captureInSharedElements(impl, nameOverrides, sharedElementTransition, fragments);
        if (nameOverrides.isEmpty()) {
            sharedElementTransition = null;
            if (outSharedElements != null) {
                outSharedElements.clear();
            }
            if (inSharedElements != null) {
                inSharedElements.clear();
            }
        } else {
            addSharedElementsWithMatchingNames(sharedElementsOut, outSharedElements, nameOverrides.keySet());
            addSharedElementsWithMatchingNames(sharedElementsIn, inSharedElements, nameOverrides.values());
        }
        if (enterTransition == null && exitTransition == null && sharedElementTransition == null) {
            return null;
        }
        callSharedElementStartEnd(inFragment, outFragment, inIsPop, outSharedElements, true);
        if (sharedElementTransition != null) {
            sharedElementsIn.add(nonExistentView);
            impl.setSharedElementTargets(sharedElementTransition, nonExistentView, sharedElementsOut);
            setOutEpicenter(impl, sharedElementTransition, exitTransition, outSharedElements, fragments.firstOutIsPop, fragments.firstOutTransaction);
            epicenter = new Rect();
            epicenterView = getInEpicenterView(inSharedElements, fragments, enterTransition, inIsPop);
            if (epicenterView != null) {
                impl.setEpicenter(enterTransition, epicenter);
            }
        } else {
            epicenter = null;
            epicenterView = null;
        }
        final FragmentTransitionImpl fragmentTransitionImpl = impl;
        OneShotPreDrawListener.add(sceneRoot, new Runnable() {
            public void run() {
                androidx.fragment.app.FragmentTransition.callSharedElementStartEnd(inFragment, outFragment, inIsPop, inSharedElements, false);
                if (epicenterView != null) {
                    fragmentTransitionImpl.getBoundsOnScreen(epicenterView, epicenter);
                }
            }
        });
        return sharedElementTransition;
    }

    private static void addSharedElementsWithMatchingNames(ArrayList<View> views, ArrayMap<String, View> sharedElements, Collection<String> nameOverridesSet) {
        for (int i = sharedElements.size() - 1; i >= 0; i--) {
            View view = (View) sharedElements.valueAt(i);
            if (nameOverridesSet.contains(ViewCompat.getTransitionName(view))) {
                views.add(view);
            }
        }
    }

    private static Object configureSharedElementsOrdered(FragmentTransitionImpl impl, ViewGroup sceneRoot, View nonExistentView, ArrayMap<String, String> nameOverrides, FragmentContainerTransition fragments, ArrayList<View> sharedElementsOut, ArrayList<View> sharedElementsIn, Object enterTransition, Object exitTransition) {
        Object sharedElementTransition;
        Object sharedElementTransition2;
        final Rect inEpicenter;
        final Fragment inFragment = fragments.lastIn;
        final Fragment outFragment = fragments.firstOut;
        if (inFragment == null || outFragment == null) {
            return null;
        }
        final boolean inIsPop = fragments.lastInIsPop;
        if (nameOverrides.isEmpty()) {
            sharedElementTransition = null;
        } else {
            sharedElementTransition = getSharedElementTransition(impl, inFragment, outFragment, inIsPop);
        }
        ArrayMap<String, View> outSharedElements = captureOutSharedElements(impl, nameOverrides, sharedElementTransition, fragments);
        if (nameOverrides.isEmpty()) {
            sharedElementTransition2 = null;
        } else {
            sharedElementsOut.addAll(outSharedElements.values());
            sharedElementTransition2 = sharedElementTransition;
        }
        if (enterTransition == null && exitTransition == null && sharedElementTransition2 == null) {
            return null;
        }
        callSharedElementStartEnd(inFragment, outFragment, inIsPop, outSharedElements, true);
        if (sharedElementTransition2 != null) {
            inEpicenter = new Rect();
            impl.setSharedElementTargets(sharedElementTransition2, nonExistentView, sharedElementsOut);
            setOutEpicenter(impl, sharedElementTransition2, exitTransition, outSharedElements, fragments.firstOutIsPop, fragments.firstOutTransaction);
            if (enterTransition != null) {
                impl.setEpicenter(enterTransition, inEpicenter);
            }
        } else {
            inEpicenter = null;
        }
        final FragmentTransitionImpl fragmentTransitionImpl = impl;
        final ArrayMap<String, String> arrayMap = nameOverrides;
        final Object obj = sharedElementTransition2;
        final FragmentContainerTransition fragmentContainerTransition = fragments;
        final ArrayList<View> arrayList = sharedElementsIn;
        final View view = nonExistentView;
        final ArrayList<View> arrayList2 = sharedElementsOut;
        final Object obj2 = enterTransition;
        OneShotPreDrawListener.add(sceneRoot, new Runnable() {
            public void run() {
                ArrayMap<String, View> inSharedElements = androidx.fragment.app.FragmentTransition.captureInSharedElements(fragmentTransitionImpl, arrayMap, obj, fragmentContainerTransition);
                if (inSharedElements != null) {
                    arrayList.addAll(inSharedElements.values());
                    arrayList.add(view);
                }
                androidx.fragment.app.FragmentTransition.callSharedElementStartEnd(inFragment, outFragment, inIsPop, inSharedElements, false);
                if (obj != null) {
                    fragmentTransitionImpl.swapSharedElementTargets(obj, arrayList2, arrayList);
                    View inEpicenterView = androidx.fragment.app.FragmentTransition.getInEpicenterView(inSharedElements, fragmentContainerTransition, obj2, inIsPop);
                    if (inEpicenterView != null) {
                        fragmentTransitionImpl.getBoundsOnScreen(inEpicenterView, inEpicenter);
                    }
                }
            }
        });
        return sharedElementTransition2;
    }

    private static ArrayMap<String, View> captureOutSharedElements(FragmentTransitionImpl impl, ArrayMap<String, String> nameOverrides, Object sharedElementTransition, FragmentContainerTransition fragments) {
        SharedElementCallback sharedElementCallback;
        ArrayList<String> names;
        if (nameOverrides.isEmpty() || sharedElementTransition == null) {
            nameOverrides.clear();
            return null;
        }
        Fragment outFragment = fragments.firstOut;
        ArrayMap<String, View> outSharedElements = new ArrayMap<>();
        impl.findNamedViews(outSharedElements, outFragment.getView());
        BackStackRecord outTransaction = fragments.firstOutTransaction;
        if (fragments.firstOutIsPop) {
            sharedElementCallback = outFragment.getEnterTransitionCallback();
            names = outTransaction.mSharedElementTargetNames;
        } else {
            sharedElementCallback = outFragment.getExitTransitionCallback();
            names = outTransaction.mSharedElementSourceNames;
        }
        outSharedElements.retainAll(names);
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(names, outSharedElements);
            for (int i = names.size() - 1; i >= 0; i--) {
                String name = (String) names.get(i);
                View view = (View) outSharedElements.get(name);
                if (view == null) {
                    nameOverrides.remove(name);
                } else if (!name.equals(ViewCompat.getTransitionName(view))) {
                    nameOverrides.put(ViewCompat.getTransitionName(view), (String) nameOverrides.remove(name));
                }
            }
            return outSharedElements;
        }
        nameOverrides.retainAll(outSharedElements.keySet());
        return outSharedElements;
    }

    /* access modifiers changed from: private */
    public static ArrayMap<String, View> captureInSharedElements(FragmentTransitionImpl impl, ArrayMap<String, String> nameOverrides, Object sharedElementTransition, FragmentContainerTransition fragments) {
        SharedElementCallback sharedElementCallback;
        ArrayList<String> names;
        Fragment inFragment = fragments.lastIn;
        View fragmentView = inFragment.getView();
        if (nameOverrides.isEmpty() || sharedElementTransition == null || fragmentView == null) {
            nameOverrides.clear();
            return null;
        }
        ArrayMap<String, View> inSharedElements = new ArrayMap<>();
        impl.findNamedViews(inSharedElements, fragmentView);
        BackStackRecord inTransaction = fragments.lastInTransaction;
        if (fragments.lastInIsPop) {
            sharedElementCallback = inFragment.getExitTransitionCallback();
            names = inTransaction.mSharedElementSourceNames;
        } else {
            sharedElementCallback = inFragment.getEnterTransitionCallback();
            names = inTransaction.mSharedElementTargetNames;
        }
        if (names != null) {
            inSharedElements.retainAll(names);
        }
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(names, inSharedElements);
            for (int i = names.size() - 1; i >= 0; i--) {
                String name = (String) names.get(i);
                View view = (View) inSharedElements.get(name);
                if (view == null) {
                    String key = findKeyForValue(nameOverrides, name);
                    if (key != null) {
                        nameOverrides.remove(key);
                    }
                } else if (!name.equals(ViewCompat.getTransitionName(view))) {
                    String key2 = findKeyForValue(nameOverrides, name);
                    if (key2 != null) {
                        nameOverrides.put(key2, ViewCompat.getTransitionName(view));
                    }
                }
            }
            return inSharedElements;
        }
        retainValues(nameOverrides, inSharedElements);
        return inSharedElements;
    }

    private static String findKeyForValue(ArrayMap<String, String> map, String value) {
        int numElements = map.size();
        for (int i = 0; i < numElements; i++) {
            if (value.equals(map.valueAt(i))) {
                return (String) map.keyAt(i);
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public static View getInEpicenterView(ArrayMap<String, View> inSharedElements, FragmentContainerTransition fragments, Object enterTransition, boolean inIsPop) {
        String targetName;
        BackStackRecord inTransaction = fragments.lastInTransaction;
        if (enterTransition == null || inSharedElements == null || inTransaction.mSharedElementSourceNames == null || inTransaction.mSharedElementSourceNames.isEmpty()) {
            return null;
        }
        if (inIsPop) {
            targetName = (String) inTransaction.mSharedElementSourceNames.get(0);
        } else {
            targetName = (String) inTransaction.mSharedElementTargetNames.get(0);
        }
        return (View) inSharedElements.get(targetName);
    }

    private static void setOutEpicenter(FragmentTransitionImpl impl, Object sharedElementTransition, Object exitTransition, ArrayMap<String, View> outSharedElements, boolean outIsPop, BackStackRecord outTransaction) {
        String sourceName;
        if (outTransaction.mSharedElementSourceNames != null && !outTransaction.mSharedElementSourceNames.isEmpty()) {
            if (outIsPop) {
                sourceName = (String) outTransaction.mSharedElementTargetNames.get(0);
            } else {
                sourceName = (String) outTransaction.mSharedElementSourceNames.get(0);
            }
            View outEpicenterView = (View) outSharedElements.get(sourceName);
            impl.setEpicenter(sharedElementTransition, outEpicenterView);
            if (exitTransition != null) {
                impl.setEpicenter(exitTransition, outEpicenterView);
            }
        }
    }

    private static void retainValues(ArrayMap<String, String> nameOverrides, ArrayMap<String, View> namedViews) {
        for (int i = nameOverrides.size() - 1; i >= 0; i--) {
            if (!namedViews.containsKey((String) nameOverrides.valueAt(i))) {
                nameOverrides.removeAt(i);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void callSharedElementStartEnd(Fragment inFragment, Fragment outFragment, boolean isPop, ArrayMap<String, View> sharedElements, boolean isStart) {
        SharedElementCallback sharedElementCallback;
        if (isPop) {
            sharedElementCallback = outFragment.getEnterTransitionCallback();
        } else {
            sharedElementCallback = inFragment.getEnterTransitionCallback();
        }
        if (sharedElementCallback != null) {
            ArrayList<View> views = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
            int count = sharedElements == null ? 0 : sharedElements.size();
            for (int i = 0; i < count; i++) {
                names.add(sharedElements.keyAt(i));
                views.add(sharedElements.valueAt(i));
            }
            if (isStart) {
                sharedElementCallback.onSharedElementStart(names, views, null);
            } else {
                sharedElementCallback.onSharedElementEnd(names, views, null);
            }
        }
    }

    /* access modifiers changed from: private */
    public static ArrayList<View> configureEnteringExitingViews(FragmentTransitionImpl impl, Object transition, Fragment fragment, ArrayList<View> sharedElements, View nonExistentView) {
        ArrayList<View> viewList = null;
        if (transition != null) {
            viewList = new ArrayList<>();
            View root = fragment.getView();
            if (root != null) {
                impl.captureTransitioningViews(viewList, root);
            }
            if (sharedElements != null) {
                viewList.removeAll(sharedElements);
            }
            if (!viewList.isEmpty()) {
                viewList.add(nonExistentView);
                impl.addTargets(transition, viewList);
            }
        }
        return viewList;
    }

    /* access modifiers changed from: private */
    public static void setViewVisibility(ArrayList<View> views, int visibility) {
        if (views != null) {
            for (int i = views.size() - 1; i >= 0; i--) {
                ((View) views.get(i)).setVisibility(visibility);
            }
        }
    }

    private static Object mergeTransitions(FragmentTransitionImpl impl, Object enterTransition, Object exitTransition, Object sharedElementTransition, Fragment inFragment, boolean isPop) {
        boolean overlap = true;
        if (!(enterTransition == null || exitTransition == null || inFragment == null)) {
            overlap = isPop ? inFragment.getAllowReturnTransitionOverlap() : inFragment.getAllowEnterTransitionOverlap();
        }
        if (overlap) {
            return impl.mergeTransitionsTogether(exitTransition, enterTransition, sharedElementTransition);
        }
        return impl.mergeTransitionsInSequence(exitTransition, enterTransition, sharedElementTransition);
    }

    public static void calculateFragments(BackStackRecord transaction, SparseArray<FragmentContainerTransition> transitioningFragments, boolean isReordered) {
        int numOps = transaction.mOps.size();
        for (int opNum = 0; opNum < numOps; opNum++) {
            addToFirstInLastOut(transaction, (Op) transaction.mOps.get(opNum), transitioningFragments, false, isReordered);
        }
    }

    public static void calculatePopFragments(BackStackRecord transaction, SparseArray<FragmentContainerTransition> transitioningFragments, boolean isReordered) {
        if (transaction.mManager.mContainer.onHasView()) {
            for (int opNum = transaction.mOps.size() - 1; opNum >= 0; opNum--) {
                addToFirstInLastOut(transaction, (Op) transaction.mOps.get(opNum), transitioningFragments, true, isReordered);
            }
        }
    }

    static boolean supportsTransition() {
        return (PLATFORM_IMPL == null && SUPPORT_IMPL == null) ? false : true;
    }

    private static void addToFirstInLastOut(BackStackRecord transaction, Op op, SparseArray<FragmentContainerTransition> transitioningFragments, boolean isPop, boolean isReorderedTransaction) {
        Fragment fragment = op.fragment;
        if (fragment != null) {
            int containerId = fragment.mContainerId;
            if (containerId != 0) {
                boolean setLastIn = false;
                boolean wasRemoved = false;
                boolean setFirstOut = false;
                boolean wasAdded = false;
                switch (isPop ? INVERSE_OPS[op.cmd] : op.cmd) {
                    case 1:
                    case 7:
                        boolean setLastIn2 = isReorderedTransaction ? fragment.mIsNewlyAdded : !fragment.mAdded && !fragment.mHidden;
                        wasAdded = true;
                        break;
                    case 3:
                    case 6:
                        boolean setFirstOut2 = isReorderedTransaction ? !fragment.mAdded && fragment.mView != null && fragment.mView.getVisibility() == 0 && fragment.mPostponedAlpha >= 0.0f : fragment.mAdded && !fragment.mHidden;
                        wasRemoved = true;
                        break;
                    case 4:
                        setFirstOut = isReorderedTransaction ? fragment.mHiddenChanged && fragment.mAdded && fragment.mHidden : fragment.mAdded && !fragment.mHidden;
                        wasRemoved = true;
                        break;
                    case 5:
                        setLastIn = isReorderedTransaction ? fragment.mHiddenChanged && !fragment.mHidden && fragment.mAdded : fragment.mHidden;
                        wasAdded = true;
                        break;
                }
                FragmentContainerTransition containerTransition = (FragmentContainerTransition) transitioningFragments.get(containerId);
                if (setLastIn) {
                    containerTransition = ensureContainer(containerTransition, transitioningFragments, containerId);
                    containerTransition.lastIn = fragment;
                    containerTransition.lastInIsPop = isPop;
                    containerTransition.lastInTransaction = transaction;
                }
                if (!isReorderedTransaction && wasAdded) {
                    if (containerTransition != null && containerTransition.firstOut == fragment) {
                        containerTransition.firstOut = null;
                    }
                    FragmentManagerImpl manager = transaction.mManager;
                    if (fragment.mState < 1 && manager.mCurState >= 1 && !transaction.mReorderingAllowed) {
                        manager.makeActive(fragment);
                        manager.moveToState(fragment, 1, 0, 0, false);
                    }
                }
                if (setFirstOut && (containerTransition == null || containerTransition.firstOut == null)) {
                    containerTransition = ensureContainer(containerTransition, transitioningFragments, containerId);
                    containerTransition.firstOut = fragment;
                    containerTransition.firstOutIsPop = isPop;
                    containerTransition.firstOutTransaction = transaction;
                }
                if (!isReorderedTransaction && wasRemoved && containerTransition != null && containerTransition.lastIn == fragment) {
                    containerTransition.lastIn = null;
                }
            }
        }
    }

    private static FragmentContainerTransition ensureContainer(FragmentContainerTransition containerTransition, SparseArray<FragmentContainerTransition> transitioningFragments, int containerId) {
        if (containerTransition != null) {
            return containerTransition;
        }
        FragmentContainerTransition containerTransition2 = new FragmentContainerTransition();
        transitioningFragments.put(containerId, containerTransition2);
        return containerTransition2;
    }
}
