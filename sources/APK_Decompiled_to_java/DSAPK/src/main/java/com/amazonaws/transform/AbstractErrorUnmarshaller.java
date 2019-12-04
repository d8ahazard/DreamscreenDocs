package com.amazonaws.transform;

import com.amazonaws.AmazonServiceException;

public abstract class AbstractErrorUnmarshaller<T> implements Unmarshaller<AmazonServiceException, T> {
    protected final Class<? extends AmazonServiceException> exceptionClass;

    public AbstractErrorUnmarshaller() {
        this(AmazonServiceException.class);
    }

    public AbstractErrorUnmarshaller(Class<? extends AmazonServiceException> exceptionClass2) {
        this.exceptionClass = exceptionClass2;
    }

    /* access modifiers changed from: protected */
    public AmazonServiceException newException(String message) throws Exception {
        return (AmazonServiceException) this.exceptionClass.getConstructor(new Class[]{String.class}).newInstance(new Object[]{message});
    }
}
