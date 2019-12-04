package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.securitytoken.model.IDPCommunicationErrorException;
import com.amazonaws.transform.StandardErrorUnmarshaller;
import org.w3c.dom.Node;

public class IDPCommunicationErrorExceptionUnmarshaller extends StandardErrorUnmarshaller {
    public IDPCommunicationErrorExceptionUnmarshaller() {
        super(IDPCommunicationErrorException.class);
    }

    public AmazonServiceException unmarshall(Node node) throws Exception {
        String errorCode = parseErrorCode(node);
        if (errorCode == null || !errorCode.equals("IDPCommunicationError")) {
            return null;
        }
        return (IDPCommunicationErrorException) super.unmarshall(node);
    }
}
