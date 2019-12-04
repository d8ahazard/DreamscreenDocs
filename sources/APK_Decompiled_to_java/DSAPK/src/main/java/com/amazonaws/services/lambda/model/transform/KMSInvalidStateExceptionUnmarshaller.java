package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.lambda.model.KMSInvalidStateException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class KMSInvalidStateExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public KMSInvalidStateExceptionUnmarshaller() {
        super(KMSInvalidStateException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("KMSInvalidStateException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        KMSInvalidStateException e = (KMSInvalidStateException) super.unmarshall(error);
        e.setErrorCode("KMSInvalidStateException");
        e.setType(String.valueOf(error.get("Type")));
        return e;
    }
}
