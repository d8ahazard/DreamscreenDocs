package com.amazonaws.http;

import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.internal.CRC32MismatchException;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.transform.VoidJsonUnmarshaller;
import com.amazonaws.util.CRC32ChecksumCalculatingInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonReader;
import com.amazonaws.util.json.JsonUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JsonResponseHandler<T> implements HttpResponseHandler<AmazonWebServiceResponse<T>> {
    private static final Log log = LogFactory.getLog("com.amazonaws.request");
    public boolean needsConnectionLeftOpen = false;
    private Unmarshaller<T, JsonUnmarshallerContext> responseUnmarshaller;

    public JsonResponseHandler(Unmarshaller<T, JsonUnmarshallerContext> responseUnmarshaller2) {
        this.responseUnmarshaller = responseUnmarshaller2;
        if (this.responseUnmarshaller == null) {
            this.responseUnmarshaller = new VoidJsonUnmarshaller();
        }
    }

    public AmazonWebServiceResponse<T> handle(HttpResponse response) throws Exception {
        log.trace("Parsing service response JSON");
        String crc32Checksum = (String) response.getHeaders().get("x-amz-crc32");
        CRC32ChecksumCalculatingInputStream crc32ChecksumInputStream = 0;
        InputStream content = response.getRawContent();
        if (content == 0) {
            content = new ByteArrayInputStream("{}".getBytes(StringUtils.UTF8));
        }
        log.debug("CRC32Checksum = " + crc32Checksum);
        Log log2 = log;
        Log log3 = log2;
        log3.debug("content encoding = " + ((String) response.getHeaders().get("Content-Encoding")));
        if (crc32Checksum != null) {
            CRC32ChecksumCalculatingInputStream crc32ChecksumInputStream2 = new CRC32ChecksumCalculatingInputStream(content);
            if ("gzip".equals(response.getHeaders().get("Content-Encoding"))) {
                InputStream content2 = new GZIPInputStream(crc32ChecksumInputStream2);
                crc32ChecksumInputStream = crc32ChecksumInputStream2;
                content = content2;
            } else {
                content = crc32ChecksumInputStream2;
                crc32ChecksumInputStream = crc32ChecksumInputStream2;
            }
        }
        AwsJsonReader jsonReader = JsonUtils.getJsonReader(new InputStreamReader(content, StringUtils.UTF8));
        try {
            AmazonWebServiceResponse<T> awsResponse = new AmazonWebServiceResponse<>();
            T result = this.responseUnmarshaller.unmarshall(new JsonUnmarshallerContext(jsonReader, response));
            if (crc32Checksum != null) {
                if (crc32ChecksumInputStream.getCRC32Checksum() != Long.parseLong(crc32Checksum)) {
                    throw new CRC32MismatchException("Client calculated crc32 checksum didn't match that calculated by server side");
                }
            }
            awsResponse.setResult(result);
            Map<String, String> metadata = new HashMap<>();
            metadata.put(ResponseMetadata.AWS_REQUEST_ID, response.getHeaders().get("x-amzn-RequestId"));
            awsResponse.setResponseMetadata(new ResponseMetadata(metadata));
            log.trace("Done parsing service response");
            if (!this.needsConnectionLeftOpen) {
                try {
                    jsonReader.close();
                } catch (IOException e) {
                    log.warn("Error closing json parser", e);
                }
            }
            return awsResponse;
        } catch (Throwable th) {
            if (!this.needsConnectionLeftOpen) {
                try {
                    jsonReader.close();
                } catch (IOException e2) {
                    log.warn("Error closing json parser", e2);
                }
            }
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void registerAdditionalMetadataExpressions(JsonUnmarshallerContext unmarshallerContext) {
    }

    public boolean needsConnectionLeftOpen() {
        return this.needsConnectionLeftOpen;
    }
}
