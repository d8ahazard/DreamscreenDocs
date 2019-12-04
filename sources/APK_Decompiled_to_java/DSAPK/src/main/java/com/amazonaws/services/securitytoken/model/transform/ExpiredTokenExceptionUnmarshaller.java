package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.securitytoken.model.ExpiredTokenException;
import com.amazonaws.transform.StandardErrorUnmarshaller;
import org.w3c.dom.Node;

public class ExpiredTokenExceptionUnmarshaller extends StandardErrorUnmarshaller {
    public ExpiredTokenExceptionUnmarshaller() {
        super(ExpiredTokenException.class);
    }

    public AmazonServiceException unmarshall(Node node) throws Exception {
        String errorCode = parseErrorCode(node);
        if (errorCode == null || !errorCode.equals("ExpiredTokenException")) {
            return null;
        }
        return (ExpiredTokenException) super.unmarshall(node);
    }
}
