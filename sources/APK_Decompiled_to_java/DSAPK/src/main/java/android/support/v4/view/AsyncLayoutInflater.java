package androidx.core.view;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.core.util.Pools.SynchronizedPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.concurrent.ArrayBlockingQueue;

public final class AsyncLayoutInflater {
    private static final String TAG = "AsyncLayoutInflater";
    Handler mHandler;
    private Callback mHandlerCallback = new Callback() {
        public boolean handleMessage(Message msg) {
            InflateRequest request = (InflateRequest) msg.obj;
            if (request.view == null) {
                request.view = androidx.asynclayoutinflater.view.AsyncLayoutInflater.this.mInflater.inflate(request.resid, request.parent, false);
            }
            request.callback.onInflateFinished(request.view, request.resid, request.parent);
            androidx.asynclayoutinflater.view.AsyncLayoutInflater.this.mInflateThread.releaseRequest(request);
            return true;
        }
    };
    InflateThread mInflateThread;
    LayoutInflater mInflater;

    private static class BasicInflater extends LayoutInflater {
        private static final String[] sClassPrefixList = {"android.widget.", "android.webkit.", "android.app."};

        BasicInflater(Context context) {
            super(context);
        }

        public LayoutInflater cloneInContext(Context newContext) {
            return new BasicInflater(newContext);
        }

        /* access modifiers changed from: protected */
        public View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
            String[] strArr = sClassPrefixList;
            int length = strArr.length;
            int i = 0;
            while (i < length) {
                try {
                    View view = createView(name, strArr[i], attrs);
                    if (view != null) {
                        return view;
                    }
                    i++;
                } catch (ClassNotFoundException e) {
                }
            }
            return super.onCreateView(name, attrs);
        }
    }

    private static class InflateRequest {
        OnInflateFinishedListener callback;
        androidx.asynclayoutinflater.view.AsyncLayoutInflater inflater;
        ViewGroup parent;
        int resid;
        View view;

        InflateRequest() {
        }
    }

    private static class InflateThread extends Thread {
        private static final InflateThread sInstance = new InflateThread();
        private ArrayBlockingQueue<InflateRequest> mQueue = new ArrayBlockingQueue<>(10);
        private SynchronizedPool<InflateRequest> mRequestPool = new SynchronizedPool<>(10);

        private InflateThread() {
        }

        static {
            sInstance.start();
        }

        public static InflateThread getInstance() {
            return sInstance;
        }

        public void runInner() {
            try {
                InflateRequest request = (InflateRequest) this.mQueue.take();
                try {
                    request.view = request.inflater.mInflater.inflate(request.resid, request.parent, false);
                } catch (RuntimeException ex) {
                    Log.w(androidx.asynclayoutinflater.view.AsyncLayoutInflater.TAG, "Failed to inflate resource in the background! Retrying on the UI thread", ex);
                }
                Message.obtain(request.inflater.mHandler, 0, request).sendToTarget();
            } catch (InterruptedException ex2) {
                Log.w(androidx.asynclayoutinflater.view.AsyncLayoutInflater.TAG, ex2);
            }
        }

        public void run() {
            while (true) {
                runInner();
            }
        }

        public InflateRequest obtainRequest() {
            InflateRequest obj = (InflateRequest) this.mRequestPool.acquire();
            if (obj == null) {
                return new InflateRequest();
            }
            return obj;
        }

        public void releaseRequest(InflateRequest obj) {
            obj.callback = null;
            obj.inflater = null;
            obj.parent = null;
            obj.resid = 0;
            obj.view = null;
            this.mRequestPool.release(obj);
        }

        public void enqueue(InflateRequest request) {
            try {
                this.mQueue.put(request);
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to enqueue async inflate request", e);
            }
        }
    }

    public interface OnInflateFinishedListener {
        void onInflateFinished(@NonNull View view, @LayoutRes int i, @Nullable ViewGroup viewGroup);
    }

    public AsyncLayoutInflater(@NonNull Context context) {
        this.mInflater = new BasicInflater(context);
        this.mHandler = new Handler(this.mHandlerCallback);
        this.mInflateThread = InflateThread.getInstance();
    }

    @UiThread
    public void inflate(@LayoutRes int resid, @Nullable ViewGroup parent, @NonNull OnInflateFinishedListener callback) {
        if (callback == null) {
            throw new NullPointerException("callback argument may not be null!");
        }
        InflateRequest request = this.mInflateThread.obtainRequest();
        request.inflater = this;
        request.resid = resid;
        request.parent = parent;
        request.callback = callback;
        this.mInflateThread.enqueue(request);
    }
}
