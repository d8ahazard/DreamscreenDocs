package com.amazonaws.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;

public class JsonErrorUnmarshaller extends AbstractErrorUnmarshaller<JsonErrorResponse> {
    public JsonErrorUnmarshaller() {
    }

    protected JsonErrorUnmarshaller(Class<? extends AmazonServiceException> exceptionClass) {
        super(exceptionClass);
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        String message = error.getMessage();
        String errorCode = error.getErrorCode();
        if ((message == null || message.isEmpty()) && (errorCode == null || errorCode.isEmpty())) {
            throw new AmazonClientException("Neither error message nor error code is found in the error response payload.");
        }
        AmazonServiceException ase = newException(message);
        ase.setErrorCode(errorCode);
        return ase;
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return true;
    }
}
