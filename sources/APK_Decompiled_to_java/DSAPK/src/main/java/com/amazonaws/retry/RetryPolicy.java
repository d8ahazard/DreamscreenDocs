package com.amazonaws.retry;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonWebServiceRequest;

public final class RetryPolicy {
    private final BackoffStrategy backoffStrategy;
    private final boolean honorMaxErrorRetryInClientConfig;
    private final int maxErrorRetry;
    private final RetryCondition retryCondition;

    public interface BackoffStrategy {
        public static final BackoffStrategy NO_DELAY = new BackoffStrategy() {
            public long delayBeforeNextRetry(AmazonWebServiceRequest originalRequest, AmazonClientException exception, int retriesAttempted) {
                return 0;
            }
        };

        long delayBeforeNextRetry(AmazonWebServiceRequest amazonWebServiceRequest, AmazonClientException amazonClientException, int i);
    }

    public interface RetryCondition {
        public static final RetryCondition NO_RETRY_CONDITION = new RetryCondition() {
            public boolean shouldRetry(AmazonWebServiceRequest originalRequest, AmazonClientException exception, int retriesAttempted) {
                return false;
            }
        };

        boolean shouldRetry(AmazonWebServiceRequest amazonWebServiceRequest, AmazonClientException amazonClientException, int i);
    }

    public RetryPolicy(RetryCondition retryCondition2, BackoffStrategy backoffStrategy2, int maxErrorRetry2, boolean honorMaxErrorRetryInClientConfig2) {
        if (retryCondition2 == null) {
            retryCondition2 = PredefinedRetryPolicies.DEFAULT_RETRY_CONDITION;
        }
        if (backoffStrategy2 == null) {
            backoffStrategy2 = PredefinedRetryPolicies.DEFAULT_BACKOFF_STRATEGY;
        }
        if (maxErrorRetry2 < 0) {
            throw new IllegalArgumentException("Please provide a non-negative value for maxErrorRetry.");
        }
        this.retryCondition = retryCondition2;
        this.backoffStrategy = backoffStrategy2;
        this.maxErrorRetry = maxErrorRetry2;
        this.honorMaxErrorRetryInClientConfig = honorMaxErrorRetryInClientConfig2;
    }

    public RetryCondition getRetryCondition() {
        return this.retryCondition;
    }

    public BackoffStrategy getBackoffStrategy() {
        return this.backoffStrategy;
    }

    public int getMaxErrorRetry() {
        return this.maxErrorRetry;
    }

    public boolean isMaxErrorRetryInClientConfigHonored() {
        return this.honorMaxErrorRetryInClientConfig;
    }
}
