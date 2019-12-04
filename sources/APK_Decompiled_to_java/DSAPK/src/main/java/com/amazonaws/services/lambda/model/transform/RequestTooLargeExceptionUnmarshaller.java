package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.lambda.model.RequestTooLargeException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class RequestTooLargeExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public RequestTooLargeExceptionUnmarshaller() {
        super(RequestTooLargeException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("RequestTooLargeException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        RequestTooLargeException e = (RequestTooLargeException) super.unmarshall(error);
        e.setErrorCode("RequestTooLargeException");
        e.setType(String.valueOf(error.get("Type")));
        return e;
    }
}
