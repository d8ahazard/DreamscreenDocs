package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.lambda.model.TooManyRequestsException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class TooManyRequestsExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public TooManyRequestsExceptionUnmarshaller() {
        super(TooManyRequestsException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("TooManyRequestsException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        TooManyRequestsException e = (TooManyRequestsException) super.unmarshall(error);
        e.setErrorCode("TooManyRequestsException");
        e.setRetryAfterSeconds(String.valueOf(error.get("retryAfterSeconds")));
        e.setType(String.valueOf(error.get("Type")));
        e.setReason(String.valueOf(error.get("Reason")));
        return e;
    }
}
