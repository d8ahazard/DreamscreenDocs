package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.securitytoken.model.MalformedPolicyDocumentException;
import com.amazonaws.transform.StandardErrorUnmarshaller;
import org.w3c.dom.Node;

public class MalformedPolicyDocumentExceptionUnmarshaller extends StandardErrorUnmarshaller {
    public MalformedPolicyDocumentExceptionUnmarshaller() {
        super(MalformedPolicyDocumentException.class);
    }

    public AmazonServiceException unmarshall(Node node) throws Exception {
        String errorCode = parseErrorCode(node);
        if (errorCode == null || !errorCode.equals("MalformedPolicyDocument")) {
            return null;
        }
        return (MalformedPolicyDocumentException) super.unmarshall(node);
    }
}
