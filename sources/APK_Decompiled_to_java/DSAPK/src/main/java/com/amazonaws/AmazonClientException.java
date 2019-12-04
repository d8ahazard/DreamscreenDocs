package com.amazonaws;

public class AmazonClientException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public AmazonClientException(String message, Throwable t) {
        super(message, t);
    }

    public AmazonClientException(String message) {
        super(message);
    }

    public AmazonClientException(Throwable throwable) {
        super(throwable);
    }

    public boolean isRetryable() {
        return true;
    }
}
