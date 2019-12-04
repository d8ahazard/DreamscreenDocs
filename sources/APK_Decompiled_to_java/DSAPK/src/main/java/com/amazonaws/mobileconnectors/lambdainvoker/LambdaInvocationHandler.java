package com.amazonaws.mobileconnectors.lambdainvoker;

import com.amazonaws.mobileconnectors.util.ClientContext;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.model.LogType;
import com.amazonaws.util.Base64;
import com.amazonaws.util.StringUtils;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class LambdaInvocationHandler implements InvocationHandler {
    private static final Log LOGGER = LogFactory.getLog(LambdaInvocationHandler.class);
    private final LambdaDataBinder binder;
    private final ClientContext clientContext;
    private final AWSLambda lambda;

    public LambdaInvocationHandler(AWSLambda lambda2, LambdaDataBinder binder2, ClientContext clientContext2) {
        this.lambda = lambda2;
        this.binder = binder2;
        this.clientContext = clientContext2;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        validateInterfaceMethod(method, args);
        return processInvokeResult(method, this.lambda.invoke(buildInvokeRequest(method, (args == null || args.length == 0) ? null : args[0])));
    }

    /* access modifiers changed from: 0000 */
    public void validateInterfaceMethod(Method method, Object[] args) {
        if (method.getAnnotation(LambdaFunction.class) == null) {
            throw new UnsupportedOperationException("No LambdaFunction annotation for method " + method.getName());
        } else if (args != null && args.length > 1) {
            throw new UnsupportedOperationException("LambdaFunctions take either 0 or 1 arguments.");
        }
    }

    /* access modifiers changed from: 0000 */
    public InvokeRequest buildInvokeRequest(Method method, Object object) throws IOException {
        LambdaFunction lambdaFunction = (LambdaFunction) method.getAnnotation(LambdaFunction.class);
        InvokeRequest invokeRequest = new InvokeRequest();
        if (lambdaFunction.functionName().isEmpty()) {
            invokeRequest.setFunctionName(method.getName());
        } else {
            invokeRequest.setFunctionName(lambdaFunction.functionName());
        }
        invokeRequest.setLogType(lambdaFunction.logType());
        if (!LogType.None.equals(lambdaFunction.logType())) {
            invokeRequest.setInvocationType(InvocationType.RequestResponse);
        } else {
            invokeRequest.setInvocationType(lambdaFunction.invocationType());
        }
        if (!lambdaFunction.qualifier().isEmpty()) {
            invokeRequest.setQualifier(lambdaFunction.qualifier());
        }
        if (this.clientContext != null) {
            invokeRequest.setClientContext(this.clientContext.toBase64String());
        }
        invokeRequest.setPayload(ByteBuffer.wrap(this.binder.serialize(object)));
        return invokeRequest;
    }

    /* access modifiers changed from: 0000 */
    public Object processInvokeResult(Method method, InvokeResult invokeResult) throws IOException {
        if (invokeResult.getLogResult() != null) {
            LOGGER.debug(method.getName() + " log: " + new String(Base64.decode(invokeResult.getLogResult()), StringUtils.UTF8));
        }
        if (invokeResult.getFunctionError() != null) {
            throw new LambdaFunctionException(invokeResult.getFunctionError(), new String(invokeResult.getPayload().array(), StringUtils.UTF8));
        } else if (invokeResult.getStatusCode().intValue() == 204 || Void.TYPE.equals(method.getReturnType())) {
            return null;
        } else {
            return this.binder.deserialize(invokeResult.getPayload().array(), method.getReturnType());
        }
    }
}
