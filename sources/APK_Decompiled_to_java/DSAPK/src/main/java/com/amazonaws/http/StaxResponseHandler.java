package com.amazonaws.http;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.transform.StaxUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.transform.VoidStaxUnmarshaller;
import com.amazonaws.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class StaxResponseHandler<T> implements HttpResponseHandler<AmazonWebServiceResponse<T>> {
    private static final XmlPullParserFactory XML_PULL_PARSER_FACTORY;
    private static final Log log = LogFactory.getLog("com.amazonaws.request");
    private Unmarshaller<T, StaxUnmarshallerContext> responseUnmarshaller;

    static {
        try {
            XML_PULL_PARSER_FACTORY = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException xppe) {
            throw new AmazonClientException("Couldn't initialize XmlPullParserFactory", xppe);
        }
    }

    public StaxResponseHandler(Unmarshaller<T, StaxUnmarshallerContext> responseUnmarshaller2) {
        this.responseUnmarshaller = responseUnmarshaller2;
        if (this.responseUnmarshaller == null) {
            this.responseUnmarshaller = new VoidStaxUnmarshaller();
        }
    }

    public AmazonWebServiceResponse<T> handle(HttpResponse response) throws Exception {
        log.trace("Parsing service response XML");
        InputStream content = response.getContent();
        if (content == null) {
            content = new ByteArrayInputStream("<eof/>".getBytes(StringUtils.UTF8));
        }
        XmlPullParser xpp = XML_PULL_PARSER_FACTORY.newPullParser();
        xpp.setInput(content, null);
        AmazonWebServiceResponse<T> awsResponse = new AmazonWebServiceResponse<>();
        StaxUnmarshallerContext unmarshallerContext = new StaxUnmarshallerContext(xpp, response.getHeaders());
        unmarshallerContext.registerMetadataExpression("ResponseMetadata/RequestId", 2, ResponseMetadata.AWS_REQUEST_ID);
        unmarshallerContext.registerMetadataExpression("requestId", 2, ResponseMetadata.AWS_REQUEST_ID);
        registerAdditionalMetadataExpressions(unmarshallerContext);
        awsResponse.setResult(this.responseUnmarshaller.unmarshall(unmarshallerContext));
        Map<String, String> metadata = unmarshallerContext.getMetadata();
        Map<String, String> responseHeaders = response.getHeaders();
        if (!(responseHeaders == null || responseHeaders.get("x-amzn-RequestId") == null)) {
            metadata.put(ResponseMetadata.AWS_REQUEST_ID, responseHeaders.get("x-amzn-RequestId"));
        }
        awsResponse.setResponseMetadata(new ResponseMetadata(metadata));
        log.trace("Done parsing service response");
        return awsResponse;
    }

    /* access modifiers changed from: protected */
    public void registerAdditionalMetadataExpressions(StaxUnmarshallerContext unmarshallerContext) {
    }

    public boolean needsConnectionLeftOpen() {
        return false;
    }
}
