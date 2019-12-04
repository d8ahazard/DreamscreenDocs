package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.lambda.model.InvalidZipFileException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class InvalidZipFileExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public InvalidZipFileExceptionUnmarshaller() {
        super(InvalidZipFileException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("InvalidZipFileException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        InvalidZipFileException e = (InvalidZipFileException) super.unmarshall(error);
        e.setErrorCode("InvalidZipFileException");
        e.setType(String.valueOf(error.get("Type")));
        return e;
    }
}
