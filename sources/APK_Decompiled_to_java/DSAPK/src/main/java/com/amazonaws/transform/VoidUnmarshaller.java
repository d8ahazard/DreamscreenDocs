package com.amazonaws.transform;

import org.w3c.dom.Node;

public class VoidUnmarshaller implements Unmarshaller<Void, Node> {
    public Void unmarshall(Node in) throws Exception {
        return null;
    }
}
