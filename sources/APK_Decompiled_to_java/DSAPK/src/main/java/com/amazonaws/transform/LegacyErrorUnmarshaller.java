package com.amazonaws.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonServiceException.ErrorType;
import com.amazonaws.util.XpathUtils;
import org.w3c.dom.Node;

public class LegacyErrorUnmarshaller implements Unmarshaller<AmazonServiceException, Node> {
    private final Class<? extends AmazonServiceException> exceptionClass;

    public LegacyErrorUnmarshaller() {
        this(AmazonServiceException.class);
    }

    protected LegacyErrorUnmarshaller(Class<? extends AmazonServiceException> exceptionClass2) {
        this.exceptionClass = exceptionClass2;
    }

    public AmazonServiceException unmarshall(Node in) throws Exception {
        String errorCode = parseErrorCode(in);
        String message = XpathUtils.asString("Response/Errors/Error/Message", in);
        String requestId = XpathUtils.asString("Response/RequestID", in);
        String errorType = XpathUtils.asString("Response/Errors/Error/Type", in);
        AmazonServiceException ase = (AmazonServiceException) this.exceptionClass.getConstructor(new Class[]{String.class}).newInstance(new Object[]{message});
        ase.setErrorCode(errorCode);
        ase.setRequestId(requestId);
        if (errorType == null) {
            ase.setErrorType(ErrorType.Unknown);
        } else if ("server".equalsIgnoreCase(errorType)) {
            ase.setErrorType(ErrorType.Service);
        } else if ("client".equalsIgnoreCase(errorType)) {
            ase.setErrorType(ErrorType.Client);
        }
        return ase;
    }

    public String parseErrorCode(Node in) throws Exception {
        return XpathUtils.asString("Response/Errors/Error/Code", in);
    }

    public String getErrorPropertyPath(String property) {
        return "Response/Errors/Error/" + property;
    }
}
