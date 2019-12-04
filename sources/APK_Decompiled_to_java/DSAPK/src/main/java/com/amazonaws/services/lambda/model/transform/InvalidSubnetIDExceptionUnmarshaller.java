package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.lambda.model.InvalidSubnetIDException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class InvalidSubnetIDExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public InvalidSubnetIDExceptionUnmarshaller() {
        super(InvalidSubnetIDException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("InvalidSubnetIDException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        InvalidSubnetIDException e = (InvalidSubnetIDException) super.unmarshall(error);
        e.setErrorCode("InvalidSubnetIDException");
        e.setType(String.valueOf(error.get("Type")));
        return e;
    }
}
