package com.amazonaws.event;

import com.amazonaws.internal.SdkFilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProgressReportingInputStream extends SdkFilterInputStream {
    private static final int NOTIFICATION_THRESHOLD = 8192;
    private boolean fireCompletedEvent;
    private final ProgressListenerCallbackExecutor listenerCallbackExecutor;
    private int unnotifiedByteCount;

    public ProgressReportingInputStream(InputStream in, ProgressListenerCallbackExecutor listenerCallbackExecutor2) {
        super(in);
        this.listenerCallbackExecutor = listenerCallbackExecutor2;
    }

    public void setFireCompletedEvent(boolean fireCompletedEvent2) {
        this.fireCompletedEvent = fireCompletedEvent2;
    }

    public boolean getFireCompletedEvent() {
        return this.fireCompletedEvent;
    }

    public int read() throws IOException {
        int data = super.read();
        if (data == -1) {
            notifyCompleted();
        } else {
            notify(1);
        }
        return data;
    }

    public void reset() throws IOException {
        super.reset();
        ProgressEvent event = new ProgressEvent((long) this.unnotifiedByteCount);
        event.setEventCode(32);
        this.listenerCallbackExecutor.progressChanged(event);
        this.unnotifiedByteCount = 0;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int bytesRead = super.read(b, off, len);
        if (bytesRead == -1) {
            notifyCompleted();
        }
        if (bytesRead != -1) {
            notify(bytesRead);
        }
        return bytesRead;
    }

    public void close() throws IOException {
        if (this.unnotifiedByteCount > 0) {
            this.listenerCallbackExecutor.progressChanged(new ProgressEvent((long) this.unnotifiedByteCount));
            this.unnotifiedByteCount = 0;
        }
        super.close();
    }

    private void notifyCompleted() {
        if (this.fireCompletedEvent) {
            ProgressEvent event = new ProgressEvent((long) this.unnotifiedByteCount);
            event.setEventCode(4);
            this.unnotifiedByteCount = 0;
            this.listenerCallbackExecutor.progressChanged(event);
        }
    }

    private void notify(int bytesRead) {
        this.unnotifiedByteCount += bytesRead;
        if (this.unnotifiedByteCount >= 8192) {
            this.listenerCallbackExecutor.progressChanged(new ProgressEvent((long) this.unnotifiedByteCount));
            this.unnotifiedByteCount = 0;
        }
    }
}
