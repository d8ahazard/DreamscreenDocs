package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.lambda.model.KMSAccessDeniedException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class KMSAccessDeniedExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public KMSAccessDeniedExceptionUnmarshaller() {
        super(KMSAccessDeniedException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("KMSAccessDeniedException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        KMSAccessDeniedException e = (KMSAccessDeniedException) super.unmarshall(error);
        e.setErrorCode("KMSAccessDeniedException");
        e.setType(String.valueOf(error.get("Type")));
        return e;
    }
}
