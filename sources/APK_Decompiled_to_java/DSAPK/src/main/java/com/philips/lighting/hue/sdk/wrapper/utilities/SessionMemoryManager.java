package com.philips.lighting.hue.sdk.wrapper.utilities;

import com.philips.lighting.hue.sdk.wrapper.SessionObject;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

public class SessionMemoryManager {
    private static SessionMemoryManager _instance = null;
    /* access modifiers changed from: private */
    public ReferenceQueue<SessionObject> _referenceQueue;
    /* access modifiers changed from: private */
    public List<SessionPhantomReference> _referenceSessions;
    /* access modifiers changed from: private */
    public boolean _threadIsRunning;

    /* access modifiers changed from: private */
    public native void destroyNativeSession(long j);

    private SessionMemoryManager() {
        this._referenceQueue = null;
        this._referenceSessions = null;
        this._threadIsRunning = false;
        this._referenceQueue = new ReferenceQueue<>();
        this._referenceSessions = new ArrayList();
    }

    private static synchronized SessionMemoryManager getInstance() {
        SessionMemoryManager sessionMemoryManager;
        synchronized (SessionMemoryManager.class) {
            if (_instance == null) {
                _instance = new SessionMemoryManager();
            }
            sessionMemoryManager = _instance;
        }
        return sessionMemoryManager;
    }

    public static void addSession(SessionObject session) {
        SessionMemoryManager bmm = getInstance();
        bmm.getReferenceSessions().add(new SessionPhantomReference(session, bmm.getReferenceQueue()));
        bmm.startReferenceRemovalThread();
    }

    private synchronized void startReferenceRemovalThread() {
        if (!this._threadIsRunning) {
            this._threadIsRunning = true;
            Thread refQueueHandler = new Thread() {
                public void run() {
                    while (SessionMemoryManager.this._threadIsRunning) {
                        try {
                            SessionPhantomReference casper = (SessionPhantomReference) SessionMemoryManager.this._referenceQueue.remove();
                            SessionMemoryManager.this.destroyNativeSession(casper.getSessionKey());
                            SessionMemoryManager.this._referenceSessions.remove(casper);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            refQueueHandler.setDaemon(true);
            refQueueHandler.start();
        }
    }

    private ReferenceQueue<SessionObject> getReferenceQueue() {
        return this._referenceQueue;
    }

    private List<SessionPhantomReference> getReferenceSessions() {
        return this._referenceSessions;
    }

    public static int getSessionCount() {
        return getInstance().getReferenceSessions().size();
    }
}
