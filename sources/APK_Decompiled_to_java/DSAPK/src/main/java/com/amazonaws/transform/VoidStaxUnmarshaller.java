package com.amazonaws.transform;

public class VoidStaxUnmarshaller<T> implements Unmarshaller<T, StaxUnmarshallerContext> {
    public T unmarshall(StaxUnmarshallerContext context) throws Exception {
        do {
        } while (context.nextEvent() != 1);
        return null;
    }
}
