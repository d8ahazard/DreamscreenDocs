package com.amazonaws.internal;

import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReleasableInputStream extends SdkFilterInputStream implements Releasable {
    private static final Log log = LogFactory.getLog(ReleasableInputStream.class);
    private boolean closeDisabled;

    protected ReleasableInputStream(InputStream is) {
        super(is);
    }

    public final void close() {
        if (!this.closeDisabled) {
            doRelease();
        }
    }

    public final void release() {
        doRelease();
    }

    private void doRelease() {
        try {
            this.in.close();
        } catch (Exception ex) {
            if (log.isDebugEnabled()) {
                log.debug("FYI", ex);
            }
        }
        if (this.in instanceof Releasable) {
            ((Releasable) this.in).release();
        }
        abortIfNeeded();
    }

    public final boolean isCloseDisabled() {
        return this.closeDisabled;
    }

    public final <T extends ReleasableInputStream> T disableClose() {
        this.closeDisabled = true;
        return this;
    }

    public static ReleasableInputStream wrap(InputStream is) {
        if (is instanceof ReleasableInputStream) {
            return (ReleasableInputStream) is;
        }
        if (is instanceof FileInputStream) {
            return ResettableInputStream.newResettableInputStream((FileInputStream) is);
        }
        return new ReleasableInputStream(is);
    }
}
