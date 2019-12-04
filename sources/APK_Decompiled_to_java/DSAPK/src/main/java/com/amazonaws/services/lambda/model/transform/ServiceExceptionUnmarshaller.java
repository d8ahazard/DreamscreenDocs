package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.lambda.model.ServiceException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class ServiceExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public ServiceExceptionUnmarshaller() {
        super(ServiceException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("ServiceException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        ServiceException e = (ServiceException) super.unmarshall(error);
        e.setErrorCode("ServiceException");
        e.setType(String.valueOf(error.get("Type")));
        return e;
    }
}
