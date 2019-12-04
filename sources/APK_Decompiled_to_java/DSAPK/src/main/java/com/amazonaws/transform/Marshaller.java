package com.amazonaws.transform;

public interface Marshaller<T, R> {
    T marshall(R r) throws Exception;
}
