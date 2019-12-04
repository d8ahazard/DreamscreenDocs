package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.cognitoidentity.model.ResourceConflictException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class ResourceConflictExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public ResourceConflictExceptionUnmarshaller() {
        super(ResourceConflictException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("ResourceConflictException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        ResourceConflictException e = (ResourceConflictException) super.unmarshall(error);
        e.setErrorCode("ResourceConflictException");
        return e;
    }
}
