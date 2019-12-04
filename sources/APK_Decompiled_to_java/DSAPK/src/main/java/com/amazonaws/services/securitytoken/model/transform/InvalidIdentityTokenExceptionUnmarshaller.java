package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.securitytoken.model.InvalidIdentityTokenException;
import com.amazonaws.transform.StandardErrorUnmarshaller;
import org.w3c.dom.Node;

public class InvalidIdentityTokenExceptionUnmarshaller extends StandardErrorUnmarshaller {
    public InvalidIdentityTokenExceptionUnmarshaller() {
        super(InvalidIdentityTokenException.class);
    }

    public AmazonServiceException unmarshall(Node node) throws Exception {
        String errorCode = parseErrorCode(node);
        if (errorCode == null || !errorCode.equals("InvalidIdentityToken")) {
            return null;
        }
        return (InvalidIdentityTokenException) super.unmarshall(node);
    }
}
