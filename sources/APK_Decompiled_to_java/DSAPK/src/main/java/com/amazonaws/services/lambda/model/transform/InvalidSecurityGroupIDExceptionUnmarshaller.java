package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.lambda.model.InvalidSecurityGroupIDException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class InvalidSecurityGroupIDExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public InvalidSecurityGroupIDExceptionUnmarshaller() {
        super(InvalidSecurityGroupIDException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("InvalidSecurityGroupIDException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        InvalidSecurityGroupIDException e = (InvalidSecurityGroupIDException) super.unmarshall(error);
        e.setErrorCode("InvalidSecurityGroupIDException");
        e.setType(String.valueOf(error.get("Type")));
        return e;
    }
}
