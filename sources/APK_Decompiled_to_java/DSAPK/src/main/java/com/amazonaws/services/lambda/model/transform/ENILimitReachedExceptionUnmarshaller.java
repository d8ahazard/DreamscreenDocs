package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.lambda.model.ENILimitReachedException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class ENILimitReachedExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public ENILimitReachedExceptionUnmarshaller() {
        super(ENILimitReachedException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("ENILimitReachedException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        ENILimitReachedException e = (ENILimitReachedException) super.unmarshall(error);
        e.setErrorCode("ENILimitReachedException");
        e.setType(String.valueOf(error.get("Type")));
        return e;
    }
}
