package com.amazonaws.event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

public class ProgressListenerCallbackExecutor {
    static ExecutorService executor = createNewExecutorService();
    /* access modifiers changed from: private */
    public final ProgressListener listener;

    public static Future<?> progressChanged(final ProgressListener listener2, final ProgressEvent progressEvent) {
        if (listener2 == null) {
            return null;
        }
        return executor.submit(new Runnable() {
            public void run() {
                listener2.progressChanged(progressEvent);
            }
        });
    }

    public ProgressListenerCallbackExecutor(ProgressListener listener2) {
        this.listener = listener2;
    }

    public ProgressListenerCallbackExecutor() {
        this.listener = null;
    }

    public void progressChanged(final ProgressEvent progressEvent) {
        if (this.listener != null) {
            executor.submit(new Runnable() {
                public void run() {
                    ProgressListenerCallbackExecutor.this.listener.progressChanged(progressEvent);
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public ProgressListener getListener() {
        return this.listener;
    }

    protected static ExecutorService getExecutorService() {
        return executor;
    }

    public static ProgressListenerCallbackExecutor wrapListener(ProgressListener listener2) {
        if (listener2 == null) {
            return null;
        }
        return new ProgressListenerCallbackExecutor(listener2);
    }

    static ExecutorService createNewExecutorService() {
        return Executors.newSingleThreadExecutor(new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("android-sdk-progress-listener-callback-thread");
                t.setDaemon(true);
                return t;
            }
        });
    }
}
