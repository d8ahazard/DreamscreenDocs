package com.amazonaws.http;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Request;
import com.amazonaws.util.HttpUtils;
import com.amazonaws.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HttpRequestFactory {
    private static final String DEFAULT_ENCODING = "UTF-8";

    public HttpRequest createHttpRequest(Request<?> request, ClientConfiguration clientConfiguration, ExecutionContext context) {
        String uri = HttpUtils.appendUri(request.getEndpoint().toString(), request.getResourcePath(), true);
        String encodedParams = HttpUtils.encodeParameters(request);
        HttpMethodName method = request.getHttpMethod();
        boolean putParamsInUri = !(method == HttpMethodName.POST) || (request.getContent() != null);
        if (encodedParams != null && putParamsInUri) {
            uri = uri + "?" + encodedParams;
        }
        Map<String, String> headers = new HashMap<>();
        configureHeaders(headers, request, context, clientConfiguration);
        InputStream is = request.getContent();
        if (method == HttpMethodName.PATCH) {
            method = HttpMethodName.POST;
            headers.put("X-HTTP-Method-Override", HttpMethodName.PATCH.toString());
        }
        if (method == HttpMethodName.POST && request.getContent() == null && encodedParams != null) {
            byte[] contentBytes = encodedParams.getBytes(StringUtils.UTF8);
            is = new ByteArrayInputStream(contentBytes);
            headers.put(HttpHeader.CONTENT_LENGTH, String.valueOf(contentBytes.length));
        }
        if (!clientConfiguration.isEnableGzip() || headers.get("Accept-Encoding") != null) {
            headers.put("Accept-Encoding", "identity");
        } else {
            headers.put("Accept-Encoding", "gzip");
        }
        HttpRequest httpRequest = new HttpRequest(method.toString(), URI.create(uri), headers, is);
        httpRequest.setStreaming(request.isStreaming());
        return httpRequest;
    }

    private void configureHeaders(Map<String, String> headers, Request<?> request, ExecutionContext context, ClientConfiguration clientConfiguration) {
        URI endpoint = request.getEndpoint();
        String hostHeader = endpoint.getHost();
        if (HttpUtils.isUsingNonDefaultPort(endpoint)) {
            hostHeader = hostHeader + ":" + endpoint.getPort();
        }
        headers.put(HttpHeader.HOST, hostHeader);
        for (Entry<String, String> entry : request.getHeaders().entrySet()) {
            headers.put(entry.getKey(), entry.getValue());
        }
        if (headers.get("Content-Type") == null || ((String) headers.get("Content-Type")).isEmpty()) {
            headers.put("Content-Type", "application/x-www-form-urlencoded; charset=" + StringUtils.lowerCase(DEFAULT_ENCODING));
        }
        if (context != null && context.getContextUserAgent() != null) {
            headers.put(HttpHeader.USER_AGENT, createUserAgentString(clientConfiguration, context.getContextUserAgent()));
        }
    }

    private String createUserAgentString(ClientConfiguration clientConfiguration, String contextUserAgent) {
        if (clientConfiguration.getUserAgent().contains(contextUserAgent)) {
            return clientConfiguration.getUserAgent();
        }
        return clientConfiguration.getUserAgent() + " " + contextUserAgent;
    }
}
