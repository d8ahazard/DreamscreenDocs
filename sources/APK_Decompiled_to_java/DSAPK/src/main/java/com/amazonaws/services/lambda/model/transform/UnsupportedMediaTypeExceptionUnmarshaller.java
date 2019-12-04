package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.lambda.model.UnsupportedMediaTypeException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class UnsupportedMediaTypeExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public UnsupportedMediaTypeExceptionUnmarshaller() {
        super(UnsupportedMediaTypeException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("UnsupportedMediaTypeException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        UnsupportedMediaTypeException e = (UnsupportedMediaTypeException) super.unmarshall(error);
        e.setErrorCode("UnsupportedMediaTypeException");
        e.setType(String.valueOf(error.get("Type")));
        return e;
    }
}
