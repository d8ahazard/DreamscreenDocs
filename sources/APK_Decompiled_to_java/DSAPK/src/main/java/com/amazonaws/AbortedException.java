package com.amazonaws;

public class AbortedException extends AmazonClientException {
    private static final long serialVersionUID = 1;

    public AbortedException(String message, Throwable t) {
        super(message, t);
    }

    public AbortedException(Throwable t) {
        super("", t);
    }

    public AbortedException(String message) {
        super(message);
    }

    public AbortedException() {
        super("");
    }

    public boolean isRetryable() {
        return false;
    }
}
