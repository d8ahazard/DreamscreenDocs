package androidx.core.provider;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import androidx.annotation.GuardedBy;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.annotation.VisibleForTesting;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@RestrictTo({Scope.LIBRARY_GROUP})
public class SelfDestructiveThread {
    private static final int MSG_DESTRUCTION = 0;
    private static final int MSG_INVOKE_RUNNABLE = 1;
    private Callback mCallback = new Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    SelfDestructiveThread.this.onDestruction();
                    break;
                case 1:
                    SelfDestructiveThread.this.onInvokeRunnable((Runnable) msg.obj);
                    break;
            }
            return true;
        }
    };
    private final int mDestructAfterMillisec;
    @GuardedBy("mLock")
    private int mGeneration;
    @GuardedBy("mLock")
    private Handler mHandler;
    private final Object mLock = new Object();
    private final int mPriority;
    @GuardedBy("mLock")
    private HandlerThread mThread;
    private final String mThreadName;

    public interface ReplyCallback<T> {
        void onReply(T t);
    }

    public SelfDestructiveThread(String threadName, int priority, int destructAfterMillisec) {
        this.mThreadName = threadName;
        this.mPriority = priority;
        this.mDestructAfterMillisec = destructAfterMillisec;
        this.mGeneration = 0;
    }

    @VisibleForTesting
    public boolean isRunning() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mThread != null;
        }
        return z;
    }

    @VisibleForTesting
    public int getGeneration() {
        int i;
        synchronized (this.mLock) {
            i = this.mGeneration;
        }
        return i;
    }

    private void post(Runnable runnable) {
        synchronized (this.mLock) {
            if (this.mThread == null) {
                this.mThread = new HandlerThread(this.mThreadName, this.mPriority);
                this.mThread.start();
                this.mHandler = new Handler(this.mThread.getLooper(), this.mCallback);
                this.mGeneration++;
            }
            this.mHandler.removeMessages(0);
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, runnable));
        }
    }

    public <T> void postAndReply(final Callable<T> callable, final ReplyCallback<T> reply) {
        final Handler callingHandler = new Handler();
        post(new Runnable() {
            public void run() {
                final Object obj;
                try {
                    obj = callable.call();
                } catch (Exception e) {
                    obj = null;
                }
                callingHandler.post(new Runnable() {
                    public void run() {
                        reply.onReply(obj);
                    }
                });
            }
        });
    }

    public <T> T postAndWait(Callable<T> callable, int timeoutMillis) throws InterruptedException {
        T t;
        final ReentrantLock lock = new ReentrantLock();
        final Condition cond = lock.newCondition();
        final AtomicReference<T> holder = new AtomicReference<>();
        final AtomicBoolean running = new AtomicBoolean(true);
        final Callable<T> callable2 = callable;
        post(new Runnable() {
            public void run() {
                try {
                    holder.set(callable2.call());
                } catch (Exception e) {
                }
                lock.lock();
                try {
                    running.set(false);
                    cond.signal();
                } finally {
                    lock.unlock();
                }
            }
        });
        lock.lock();
        try {
            if (!running.get()) {
                t = holder.get();
            } else {
                long remaining = TimeUnit.MILLISECONDS.toNanos((long) timeoutMillis);
                do {
                    try {
                        remaining = cond.awaitNanos(remaining);
                    } catch (InterruptedException e) {
                    }
                    if (!running.get()) {
                        t = holder.get();
                        lock.unlock();
                    }
                } while (remaining > 0);
                throw new InterruptedException("timeout");
            }
            return t;
        } finally {
            lock.unlock();
        }
    }

    /* access modifiers changed from: private */
    public void onInvokeRunnable(Runnable runnable) {
        runnable.run();
        synchronized (this.mLock) {
            this.mHandler.removeMessages(0);
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0), (long) this.mDestructAfterMillisec);
        }
    }

    /* access modifiers changed from: private */
    public void onDestruction() {
        synchronized (this.mLock) {
            if (!this.mHandler.hasMessages(1)) {
                this.mThread.quit();
                this.mThread = null;
                this.mHandler = null;
            }
        }
    }
}
