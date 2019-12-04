package com.amazonaws.util;

import com.amazonaws.AmazonClientException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.Request;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map.Entry;

public class RuntimeHttpUtils {
    private static final String COMMA = ", ";
    private static final String SPACE = " ";

    public static URI toUri(String endpoint, ClientConfiguration config) {
        if (config != null) {
            return toUri(endpoint, config.getProtocol());
        }
        throw new IllegalArgumentException("ClientConfiguration cannot be null");
    }

    public static URI toUri(String endpoint, Protocol protocol) {
        if (endpoint == null) {
            throw new IllegalArgumentException("endpoint cannot be null");
        }
        if (!endpoint.contains("://")) {
            endpoint = protocol.toString() + "://" + endpoint;
        }
        try {
            return new URI(endpoint);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static URL convertRequestToUrl(Request<?> request, boolean removeLeadingSlashInResourcePath, boolean urlEncode) {
        String resourcePath;
        if (urlEncode) {
            resourcePath = HttpUtils.urlEncode(request.getResourcePath(), true);
        } else {
            resourcePath = request.getResourcePath();
        }
        if (removeLeadingSlashInResourcePath && resourcePath.startsWith("/")) {
            resourcePath = resourcePath.substring(1);
        }
        String urlPath = ("/" + resourcePath).replaceAll("(?<=/)/", "%2F");
        StringBuilder url = new StringBuilder(request.getEndpoint().toString());
        url.append(urlPath);
        StringBuilder queryParams = new StringBuilder();
        for (Entry<String, String> entry : request.getParameters().entrySet()) {
            queryParams = queryParams.length() > 0 ? queryParams.append("&") : queryParams.append("?");
            queryParams.append(HttpUtils.urlEncode((String) entry.getKey(), false)).append("=").append(HttpUtils.urlEncode((String) entry.getValue(), false));
        }
        url.append(queryParams.toString());
        try {
            return new URL(url.toString());
        } catch (MalformedURLException e) {
            throw new AmazonClientException("Unable to convert request to well formed URL: " + e.getMessage(), e);
        }
    }
}
