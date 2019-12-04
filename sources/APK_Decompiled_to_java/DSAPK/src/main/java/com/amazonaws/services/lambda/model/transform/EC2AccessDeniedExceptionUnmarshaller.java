package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.lambda.model.EC2AccessDeniedException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class EC2AccessDeniedExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public EC2AccessDeniedExceptionUnmarshaller() {
        super(EC2AccessDeniedException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("EC2AccessDeniedException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        EC2AccessDeniedException e = (EC2AccessDeniedException) super.unmarshall(error);
        e.setErrorCode("EC2AccessDeniedException");
        e.setType(String.valueOf(error.get("Type")));
        return e;
    }
}
