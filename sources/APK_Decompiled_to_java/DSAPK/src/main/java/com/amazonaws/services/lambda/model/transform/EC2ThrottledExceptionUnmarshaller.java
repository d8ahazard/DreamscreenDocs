package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.lambda.model.EC2ThrottledException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class EC2ThrottledExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public EC2ThrottledExceptionUnmarshaller() {
        super(EC2ThrottledException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("EC2ThrottledException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        EC2ThrottledException e = (EC2ThrottledException) super.unmarshall(error);
        e.setErrorCode("EC2ThrottledException");
        e.setType(String.valueOf(error.get("Type")));
        return e;
    }
}
