package com.amazonaws.http;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonServiceException.ErrorType;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.XpathUtils;
import java.io.IOException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class DefaultErrorResponseHandler implements HttpResponseHandler<AmazonServiceException> {
    private static final Log log = LogFactory.getLog(DefaultErrorResponseHandler.class);
    private List<Unmarshaller<AmazonServiceException, Node>> unmarshallerList;

    public DefaultErrorResponseHandler(List<Unmarshaller<AmazonServiceException, Node>> unmarshallerList2) {
        this.unmarshallerList = unmarshallerList2;
    }

    public AmazonServiceException handle(HttpResponse errorResponse) throws Exception {
        String str = "";
        try {
            String content = IOUtils.toString(errorResponse.getContent());
            try {
                Document document = XpathUtils.documentFrom(content);
                for (Unmarshaller<AmazonServiceException, Node> unmarshaller : this.unmarshallerList) {
                    AmazonServiceException ase = (AmazonServiceException) unmarshaller.unmarshall(document);
                    if (ase != null) {
                        ase.setStatusCode(errorResponse.getStatusCode());
                        return ase;
                    }
                }
                throw new AmazonClientException("Unable to unmarshall error response from service");
            } catch (Exception e) {
                return newAmazonServiceException(String.format("Unable to unmarshall error response (%s)", new Object[]{content}), errorResponse, e);
            }
        } catch (IOException ex) {
            if (log.isDebugEnabled()) {
                log.debug("Failed in reading the error response", ex);
            }
            return newAmazonServiceException("Unable to unmarshall error response", errorResponse, ex);
        }
    }

    private AmazonServiceException newAmazonServiceException(String errmsg, HttpResponse httpResponse, Exception readFailure) {
        AmazonServiceException exception = new AmazonServiceException(errmsg, readFailure);
        int statusCode = httpResponse.getStatusCode();
        exception.setErrorCode(statusCode + " " + httpResponse.getStatusText());
        exception.setErrorType(ErrorType.Unknown);
        exception.setStatusCode(statusCode);
        return exception;
    }

    public boolean needsConnectionLeftOpen() {
        return false;
    }
}
