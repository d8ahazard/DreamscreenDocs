package com.philips.lighting.hue.sdk.wrapper.uicallback;

public class UICallbackDelegator {
    private static UICallbackDelegator instance = null;
    private UICallbackHandler mHandler = null;

    private UICallbackDelegator() {
    }

    public static UICallbackDelegator getInstance() {
        if (instance == null) {
            instance = new UICallbackDelegator();
        }
        return instance;
    }

    public UICallbackHandler getHandler() {
        return this.mHandler;
    }

    public void setHandler(UICallbackHandler handler) {
        this.mHandler = handler;
    }

    static boolean perform(Runnable task) {
        if (getInstance().getHandler() != null) {
            return getInstance().getHandler().post(task);
        }
        task.run();
        return true;
    }
}
