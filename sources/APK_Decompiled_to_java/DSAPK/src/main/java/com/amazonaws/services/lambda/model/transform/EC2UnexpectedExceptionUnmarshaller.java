package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.lambda.model.EC2UnexpectedException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class EC2UnexpectedExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public EC2UnexpectedExceptionUnmarshaller() {
        super(EC2UnexpectedException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("EC2UnexpectedException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        EC2UnexpectedException e = (EC2UnexpectedException) super.unmarshall(error);
        e.setErrorCode("EC2UnexpectedException");
        e.setType(String.valueOf(error.get("Type")));
        e.setEC2ErrorCode(String.valueOf(error.get("EC2ErrorCode")));
        return e;
    }
}
