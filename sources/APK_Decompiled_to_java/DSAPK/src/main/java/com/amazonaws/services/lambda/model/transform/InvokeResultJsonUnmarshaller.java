package com.amazonaws.services.lambda.model.transform;

import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.IOUtils;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class InvokeResultJsonUnmarshaller implements Unmarshaller<InvokeResult, JsonUnmarshallerContext> {
    private static final ByteBuffer EMPTY_BYTEBUFFER = ByteBuffer.allocate(0);
    private static InvokeResultJsonUnmarshaller instance;

    public InvokeResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        InvokeResult invokeResult = new InvokeResult();
        if (context.getHeader("X-Amz-Function-Error") != null) {
            invokeResult.setFunctionError(context.getHeader("X-Amz-Function-Error"));
        }
        if (context.getHeader("X-Amz-Log-Result") != null) {
            invokeResult.setLogResult(context.getHeader("X-Amz-Log-Result"));
        }
        invokeResult.setStatusCode(Integer.valueOf(context.getHttpResponse().getStatusCode()));
        ByteBuffer payload = EMPTY_BYTEBUFFER;
        InputStream content = context.getHttpResponse().getContent();
        if (content != null) {
            payload = ByteBuffer.wrap(IOUtils.toByteArray(content));
        }
        invokeResult.setPayload(payload);
        return invokeResult;
    }

    public static InvokeResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new InvokeResultJsonUnmarshaller();
        }
        return instance;
    }
}
