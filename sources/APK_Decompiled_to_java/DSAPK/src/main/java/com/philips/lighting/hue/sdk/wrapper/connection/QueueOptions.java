package com.philips.lighting.hue.sdk.wrapper.connection;

public class QueueOptions {
    private CacheType cacheType;
    private boolean ignoreSendingTooFast;
    private boolean optimizerEnabled;
    private boolean waitForResponse;

    public QueueOptions(boolean optimizerEnabled2, boolean ignoreSendingTooFast2, boolean waitForResponse2, CacheType cacheType2) {
        this.optimizerEnabled = true;
        this.ignoreSendingTooFast = false;
        this.waitForResponse = true;
        this.cacheType = CacheType.NORMAL;
        this.optimizerEnabled = optimizerEnabled2;
        this.ignoreSendingTooFast = ignoreSendingTooFast2;
        this.waitForResponse = waitForResponse2;
        this.cacheType = cacheType2;
    }

    public QueueOptions(boolean optimizerEnabled2, boolean ignoreSendingTooFast2, boolean waitForResponse2) {
        this(optimizerEnabled2, ignoreSendingTooFast2, waitForResponse2, CacheType.NORMAL);
    }

    public QueueOptions() {
        this.optimizerEnabled = true;
        this.ignoreSendingTooFast = false;
        this.waitForResponse = true;
        this.cacheType = CacheType.NORMAL;
    }

    public boolean isOptimizerEnabled() {
        return this.optimizerEnabled;
    }

    public void setOptimizerEnabled(boolean optimizerEnabled2) {
        this.optimizerEnabled = optimizerEnabled2;
    }

    public boolean isIgnoreSendingTooFast() {
        return this.ignoreSendingTooFast;
    }

    public void setIgnoreSendingTooFast(boolean ignoreSendingTooFast2) {
        this.ignoreSendingTooFast = ignoreSendingTooFast2;
    }

    public boolean isWaitForResponse() {
        return this.waitForResponse;
    }

    public void setWaitForResponse(boolean waitForResponse2) {
        this.waitForResponse = waitForResponse2;
    }

    public void setCacheType(CacheType type) {
        this.cacheType = type;
    }

    public CacheType getCacheType() {
        return this.cacheType;
    }

    private int getCacheTypeAsInt() {
        return this.cacheType.getValue();
    }

    public int hashCode() {
        int hashCode;
        int i;
        int i2;
        int i3 = 1231;
        if (this.cacheType == null) {
            hashCode = 0;
        } else {
            hashCode = this.cacheType.hashCode();
        }
        int i4 = (hashCode + 31) * 31;
        if (this.ignoreSendingTooFast) {
            i = 1231;
        } else {
            i = 1237;
        }
        int i5 = (i4 + i) * 31;
        if (this.optimizerEnabled) {
            i2 = 1231;
        } else {
            i2 = 1237;
        }
        int i6 = (i5 + i2) * 31;
        if (!this.waitForResponse) {
            i3 = 1237;
        }
        return i6 + i3;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        QueueOptions other = (QueueOptions) obj;
        if (this.cacheType != other.cacheType) {
            return false;
        }
        if (this.ignoreSendingTooFast != other.ignoreSendingTooFast) {
            return false;
        }
        if (this.optimizerEnabled != other.optimizerEnabled) {
            return false;
        }
        if (this.waitForResponse != other.waitForResponse) {
            return false;
        }
        return true;
    }
}
