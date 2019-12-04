package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.lambda.model.InvalidParameterValueException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class InvalidParameterValueExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public InvalidParameterValueExceptionUnmarshaller() {
        super(InvalidParameterValueException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("InvalidParameterValueException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        InvalidParameterValueException e = (InvalidParameterValueException) super.unmarshall(error);
        e.setErrorCode("InvalidParameterValueException");
        e.setType(String.valueOf(error.get("Type")));
        return e;
    }
}
