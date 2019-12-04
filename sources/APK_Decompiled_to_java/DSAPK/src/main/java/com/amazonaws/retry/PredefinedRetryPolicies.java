package com.amazonaws.retry;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.retry.RetryPolicy.BackoffStrategy;
import com.amazonaws.retry.RetryPolicy.RetryCondition;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Random;

public class PredefinedRetryPolicies {
    private static final int BASE_DELAY_IN_MILLISECONDS = 100;
    public static final RetryPolicy DEFAULT = getDefaultRetryPolicy();
    public static final BackoffStrategy DEFAULT_BACKOFF_STRATEGY = new SDKDefaultBackoffStrategy(100, MAX_BACKOFF_IN_MILLISECONDS);
    public static final int DEFAULT_MAX_ERROR_RETRY = 3;
    public static final RetryCondition DEFAULT_RETRY_CONDITION = new SDKDefaultRetryCondition();
    public static final RetryPolicy DYNAMODB_DEFAULT = getDynamoDBDefaultRetryPolicy();
    public static final int DYNAMODB_DEFAULT_MAX_ERROR_RETRY = 10;
    private static final int MAX_BACKOFF_IN_MILLISECONDS = 20000;
    public static final RetryPolicy NO_RETRY_POLICY = new RetryPolicy(RetryCondition.NO_RETRY_CONDITION, BackoffStrategy.NO_DELAY, 0, false);

    private static final class SDKDefaultBackoffStrategy implements BackoffStrategy {
        private final int baseDelayMs;
        private final int maxDelayMs;
        private final Random random;

        private SDKDefaultBackoffStrategy(int baseDelayMs2, int maxDelayMs2) {
            this.random = new Random();
            this.baseDelayMs = baseDelayMs2;
            this.maxDelayMs = maxDelayMs2;
        }

        public final long delayBeforeNextRetry(AmazonWebServiceRequest originalRequest, AmazonClientException exception, int retries) {
            if (retries <= 0) {
                return 0;
            }
            return (long) this.random.nextInt(Math.min(this.maxDelayMs, (1 << retries) * this.baseDelayMs));
        }
    }

    public static class SDKDefaultRetryCondition implements RetryCondition {
        public boolean shouldRetry(AmazonWebServiceRequest originalRequest, AmazonClientException exception, int retriesAttempted) {
            if ((exception.getCause() instanceof IOException) && !(exception.getCause() instanceof InterruptedIOException)) {
                return true;
            }
            if (exception instanceof AmazonServiceException) {
                AmazonServiceException ase = (AmazonServiceException) exception;
                int statusCode = ase.getStatusCode();
                if (statusCode == 500 || statusCode == 503 || statusCode == 502 || statusCode == 504 || RetryUtils.isThrottlingException(ase) || RetryUtils.isClockSkewError(ase)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static RetryPolicy getDefaultRetryPolicy() {
        return new RetryPolicy(DEFAULT_RETRY_CONDITION, DEFAULT_BACKOFF_STRATEGY, 3, true);
    }

    public static RetryPolicy getDynamoDBDefaultRetryPolicy() {
        return new RetryPolicy(DEFAULT_RETRY_CONDITION, DEFAULT_BACKOFF_STRATEGY, 10, true);
    }

    public static RetryPolicy getDefaultRetryPolicyWithCustomMaxRetries(int maxErrorRetry) {
        return new RetryPolicy(DEFAULT_RETRY_CONDITION, DEFAULT_BACKOFF_STRATEGY, maxErrorRetry, false);
    }

    public static RetryPolicy getDynamoDBDefaultRetryPolicyWithCustomMaxRetries(int maxErrorRetry) {
        return new RetryPolicy(DEFAULT_RETRY_CONDITION, DEFAULT_BACKOFF_STRATEGY, maxErrorRetry, false);
    }
}
