package com.amazonaws.mobileconnectors.lambdainvoker;

public interface LambdaDataBinder {
    <T> T deserialize(byte[] bArr, Class<T> cls);

    byte[] serialize(Object obj);
}
