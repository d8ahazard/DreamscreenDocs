package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.lambda.model.InvalidRequestContentException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class InvalidRequestContentExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public InvalidRequestContentExceptionUnmarshaller() {
        super(InvalidRequestContentException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("InvalidRequestContentException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        InvalidRequestContentException e = (InvalidRequestContentException) super.unmarshall(error);
        e.setErrorCode("InvalidRequestContentException");
        e.setType(String.valueOf(error.get("Type")));
        return e;
    }
}
