package com.philips.lighting.hue.sdk.wrapper.uicallback;

public abstract class CallbackWrapper {
    protected boolean runOnUI = true;

    protected CallbackWrapper() {
    }

    /* access modifiers changed from: protected */
    public boolean post(Runnable task) {
        if (this.runOnUI) {
            return UICallbackDelegator.perform(task);
        }
        task.run();
        return true;
    }
}
