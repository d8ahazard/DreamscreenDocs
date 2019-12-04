package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.JsonErrorResponseHandler.JsonErrorResponse;
import com.amazonaws.services.cognitoidentity.model.InvalidIdentityPoolConfigurationException;
import com.amazonaws.transform.JsonErrorUnmarshaller;

public class InvalidIdentityPoolConfigurationExceptionUnmarshaller extends JsonErrorUnmarshaller {
    public InvalidIdentityPoolConfigurationExceptionUnmarshaller() {
        super(InvalidIdentityPoolConfigurationException.class);
    }

    public boolean match(JsonErrorResponse error) throws Exception {
        return error.getErrorCode().equals("InvalidIdentityPoolConfigurationException");
    }

    public AmazonServiceException unmarshall(JsonErrorResponse error) throws Exception {
        InvalidIdentityPoolConfigurationException e = (InvalidIdentityPoolConfigurationException) super.unmarshall(error);
        e.setErrorCode("InvalidIdentityPoolConfigurationException");
        return e;
    }
}
