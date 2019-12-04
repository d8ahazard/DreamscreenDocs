package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.lambda.model.KMSDisabledException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class KMSDisabledExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public KMSDisabledExceptionUnmarshaller() {
        super(KMSDisabledException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("KMSDisabledException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        KMSDisabledException e = (KMSDisabledException) super.unmarshall(error);
        e.setErrorCode("KMSDisabledException");
        e.setType(String.valueOf(error.get("Type")));
        return e;
    }
}
