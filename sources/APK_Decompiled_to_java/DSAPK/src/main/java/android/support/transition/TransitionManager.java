package androidx.transition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.core.view.ViewCompat;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public class TransitionManager {
    private static final String LOG_TAG = "TransitionManager";
    private static Transition sDefaultTransition = new AutoTransition();
    /* access modifiers changed from: private */
    public static ArrayList<ViewGroup> sPendingTransitions = new ArrayList<>();
    private static ThreadLocal<WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>>> sRunningTransitions = new ThreadLocal<>();
    private ArrayMap<Scene, ArrayMap<Scene, Transition>> mScenePairTransitions = new ArrayMap<>();
    private ArrayMap<Scene, Transition> mSceneTransitions = new ArrayMap<>();

    private static class MultiListener implements OnPreDrawListener, OnAttachStateChangeListener {
        ViewGroup mSceneRoot;
        Transition mTransition;

        MultiListener(Transition transition, ViewGroup sceneRoot) {
            this.mTransition = transition;
            this.mSceneRoot = sceneRoot;
        }

        private void removeListeners() {
            this.mSceneRoot.getViewTreeObserver().removeOnPreDrawListener(this);
            this.mSceneRoot.removeOnAttachStateChangeListener(this);
        }

        public void onViewAttachedToWindow(View v) {
        }

        public void onViewDetachedFromWindow(View v) {
            removeListeners();
            TransitionManager.sPendingTransitions.remove(this.mSceneRoot);
            ArrayList<Transition> runningTransitions = (ArrayList) TransitionManager.getRunningTransitions().get(this.mSceneRoot);
            if (runningTransitions != null && runningTransitions.size() > 0) {
                Iterator it = runningTransitions.iterator();
                while (it.hasNext()) {
                    ((Transition) it.next()).resume(this.mSceneRoot);
                }
            }
            this.mTransition.clearValues(true);
        }

        public boolean onPreDraw() {
            removeListeners();
            if (TransitionManager.sPendingTransitions.remove(this.mSceneRoot)) {
                final ArrayMap<ViewGroup, ArrayList<Transition>> runningTransitions = TransitionManager.getRunningTransitions();
                ArrayList<Transition> currentTransitions = (ArrayList) runningTransitions.get(this.mSceneRoot);
                ArrayList<Transition> previousRunningTransitions = null;
                if (currentTransitions == null) {
                    currentTransitions = new ArrayList<>();
                    runningTransitions.put(this.mSceneRoot, currentTransitions);
                } else if (currentTransitions.size() > 0) {
                    previousRunningTransitions = new ArrayList<>(currentTransitions);
                }
                currentTransitions.add(this.mTransition);
                this.mTransition.addListener(new TransitionListenerAdapter() {
                    public void onTransitionEnd(@NonNull Transition transition) {
                        ((ArrayList) runningTransitions.get(MultiListener.this.mSceneRoot)).remove(transition);
                    }
                });
                this.mTransition.captureValues(this.mSceneRoot, false);
                if (previousRunningTransitions != null) {
                    Iterator it = previousRunningTransitions.iterator();
                    while (it.hasNext()) {
                        ((Transition) it.next()).resume(this.mSceneRoot);
                    }
                }
                this.mTransition.playTransition(this.mSceneRoot);
            }
            return true;
        }
    }

    public void setTransition(@NonNull Scene scene, @Nullable Transition transition) {
        this.mSceneTransitions.put(scene, transition);
    }

    public void setTransition(@NonNull Scene fromScene, @NonNull Scene toScene, @Nullable Transition transition) {
        ArrayMap<Scene, Transition> sceneTransitionMap = (ArrayMap) this.mScenePairTransitions.get(toScene);
        if (sceneTransitionMap == null) {
            sceneTransitionMap = new ArrayMap<>();
            this.mScenePairTransitions.put(toScene, sceneTransitionMap);
        }
        sceneTransitionMap.put(fromScene, transition);
    }

    private Transition getTransition(Scene scene) {
        ViewGroup sceneRoot = scene.getSceneRoot();
        if (sceneRoot != null) {
            Scene currScene = Scene.getCurrentScene(sceneRoot);
            if (currScene != null) {
                ArrayMap<Scene, Transition> sceneTransitionMap = (ArrayMap) this.mScenePairTransitions.get(scene);
                if (sceneTransitionMap != null) {
                    Transition transition = (Transition) sceneTransitionMap.get(currScene);
                    if (transition != null) {
                        return transition;
                    }
                }
            }
        }
        Transition transition2 = (Transition) this.mSceneTransitions.get(scene);
        return transition2 != null ? transition2 : sDefaultTransition;
    }

    private static void changeScene(Scene scene, Transition transition) {
        ViewGroup sceneRoot = scene.getSceneRoot();
        if (sPendingTransitions.contains(sceneRoot)) {
            return;
        }
        if (transition == null) {
            scene.enter();
            return;
        }
        sPendingTransitions.add(sceneRoot);
        Transition transitionClone = transition.clone();
        transitionClone.setSceneRoot(sceneRoot);
        Scene oldScene = Scene.getCurrentScene(sceneRoot);
        if (oldScene != null && oldScene.isCreatedFromLayoutResource()) {
            transitionClone.setCanRemoveViews(true);
        }
        sceneChangeSetup(sceneRoot, transitionClone);
        scene.enter();
        sceneChangeRunTransition(sceneRoot, transitionClone);
    }

    static ArrayMap<ViewGroup, ArrayList<Transition>> getRunningTransitions() {
        WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>> runningTransitions = (WeakReference) sRunningTransitions.get();
        if (runningTransitions == null || runningTransitions.get() == null) {
            runningTransitions = new WeakReference<>(new ArrayMap<>());
            sRunningTransitions.set(runningTransitions);
        }
        return (ArrayMap) runningTransitions.get();
    }

    private static void sceneChangeRunTransition(ViewGroup sceneRoot, Transition transition) {
        if (transition != null && sceneRoot != null) {
            MultiListener listener = new MultiListener(transition, sceneRoot);
            sceneRoot.addOnAttachStateChangeListener(listener);
            sceneRoot.getViewTreeObserver().addOnPreDrawListener(listener);
        }
    }

    private static void sceneChangeSetup(ViewGroup sceneRoot, Transition transition) {
        ArrayList<Transition> runningTransitions = (ArrayList) getRunningTransitions().get(sceneRoot);
        if (runningTransitions != null && runningTransitions.size() > 0) {
            Iterator it = runningTransitions.iterator();
            while (it.hasNext()) {
                ((Transition) it.next()).pause(sceneRoot);
            }
        }
        if (transition != null) {
            transition.captureValues(sceneRoot, true);
        }
        Scene previousScene = Scene.getCurrentScene(sceneRoot);
        if (previousScene != null) {
            previousScene.exit();
        }
    }

    public void transitionTo(@NonNull Scene scene) {
        changeScene(scene, getTransition(scene));
    }

    public static void go(@NonNull Scene scene) {
        changeScene(scene, sDefaultTransition);
    }

    public static void go(@NonNull Scene scene, @Nullable Transition transition) {
        changeScene(scene, transition);
    }

    public static void beginDelayedTransition(@NonNull ViewGroup sceneRoot) {
        beginDelayedTransition(sceneRoot, null);
    }

    public static void beginDelayedTransition(@NonNull ViewGroup sceneRoot, @Nullable Transition transition) {
        if (!sPendingTransitions.contains(sceneRoot) && ViewCompat.isLaidOut(sceneRoot)) {
            sPendingTransitions.add(sceneRoot);
            if (transition == null) {
                transition = sDefaultTransition;
            }
            Transition transitionClone = transition.clone();
            sceneChangeSetup(sceneRoot, transitionClone);
            Scene.setCurrentScene(sceneRoot, null);
            sceneChangeRunTransition(sceneRoot, transitionClone);
        }
    }

    public static void endTransitions(ViewGroup sceneRoot) {
        sPendingTransitions.remove(sceneRoot);
        ArrayList<Transition> runningTransitions = (ArrayList) getRunningTransitions().get(sceneRoot);
        if (runningTransitions != null && !runningTransitions.isEmpty()) {
            ArrayList<Transition> copy = new ArrayList<>(runningTransitions);
            for (int i = copy.size() - 1; i >= 0; i--) {
                ((Transition) copy.get(i)).forceToEnd(sceneRoot);
            }
        }
    }
}
