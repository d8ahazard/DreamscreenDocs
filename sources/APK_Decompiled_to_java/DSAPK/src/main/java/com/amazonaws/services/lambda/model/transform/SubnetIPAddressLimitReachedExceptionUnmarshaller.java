package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.lambda.model.SubnetIPAddressLimitReachedException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class SubnetIPAddressLimitReachedExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public SubnetIPAddressLimitReachedExceptionUnmarshaller() {
        super(SubnetIPAddressLimitReachedException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("SubnetIPAddressLimitReachedException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        SubnetIPAddressLimitReachedException e = (SubnetIPAddressLimitReachedException) super.unmarshall(error);
        e.setErrorCode("SubnetIPAddressLimitReachedException");
        e.setType(String.valueOf(error.get("Type")));
        return e;
    }
}
