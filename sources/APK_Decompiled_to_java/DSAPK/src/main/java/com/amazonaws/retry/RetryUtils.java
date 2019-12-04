package com.amazonaws.retry;

import com.amazonaws.AbortedException;
import com.amazonaws.AmazonServiceException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;

public class RetryUtils {
    public static boolean isThrottlingException(AmazonServiceException ase) {
        if (ase == null) {
            return false;
        }
        String errorCode = ase.getErrorCode();
        if ("Throttling".equals(errorCode) || "ThrottlingException".equals(errorCode) || "ProvisionedThroughputExceededException".equals(errorCode)) {
            return true;
        }
        return false;
    }

    public static boolean isRequestEntityTooLargeException(AmazonServiceException ase) {
        if (ase == null) {
            return false;
        }
        return "Request entity too large".equals(ase.getErrorCode());
    }

    public static boolean isClockSkewError(AmazonServiceException ase) {
        if (ase == null) {
            return false;
        }
        String errorCode = ase.getErrorCode();
        if ("RequestTimeTooSkewed".equals(errorCode) || "RequestExpired".equals(errorCode) || "InvalidSignatureException".equals(errorCode) || "SignatureDoesNotMatch".equals(errorCode)) {
            return true;
        }
        return false;
    }

    public static boolean isInterrupted(Throwable error) {
        if (error instanceof AbortedException) {
            return true;
        }
        if (error.getCause() != null) {
            Throwable cause = error.getCause();
            if (cause instanceof InterruptedException) {
                return true;
            }
            if ((cause instanceof InterruptedIOException) && !(cause instanceof SocketTimeoutException)) {
                return true;
            }
        }
        return false;
    }
}
