package com.amazonaws.transform;

public interface Unmarshaller<T, R> {
    T unmarshall(R r) throws Exception;
}
