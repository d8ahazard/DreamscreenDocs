package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.lambda.model.KMSNotFoundException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class KMSNotFoundExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public KMSNotFoundExceptionUnmarshaller() {
        super(KMSNotFoundException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("KMSNotFoundException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        KMSNotFoundException e = (KMSNotFoundException) super.unmarshall(error);
        e.setErrorCode("KMSNotFoundException");
        e.setType(String.valueOf(error.get("Type")));
        return e;
    }
}
