package com.amazonaws.util;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.mobileconnectors.cognitoauth.util.ClientConstants;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpUtils {
    private static final Pattern DECODED_CHARACTERS_PATTERN;
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final Pattern ENCODED_CHARACTERS_PATTERN;
    private static final int HTTP_STATUS_OK = 200;
    private static final int PORT_HTTP = 80;
    private static final int PORT_HTTPS = 443;

    static {
        StringBuilder pattern = new StringBuilder();
        pattern.append(Pattern.quote("+")).append("|").append(Pattern.quote("*")).append("|").append(Pattern.quote("%7E")).append("|").append(Pattern.quote("%2F"));
        ENCODED_CHARACTERS_PATTERN = Pattern.compile(pattern.toString());
        StringBuilder decodePattern = new StringBuilder();
        decodePattern.append(Pattern.quote("%2A")).append("|").append(Pattern.quote("%2B")).append("|");
        DECODED_CHARACTERS_PATTERN = Pattern.compile(decodePattern.toString());
    }

    public static String urlEncode(String value, boolean path) {
        if (value == null) {
            return "";
        }
        try {
            String encoded = URLEncoder.encode(value, DEFAULT_ENCODING);
            Matcher matcher = ENCODED_CHARACTERS_PATTERN.matcher(encoded);
            StringBuffer buffer = new StringBuffer(encoded.length());
            while (matcher.find()) {
                String replacement = matcher.group(0);
                if ("+".equals(replacement)) {
                    replacement = "%20";
                } else if ("*".equals(replacement)) {
                    replacement = "%2A";
                } else if ("%7E".equals(replacement)) {
                    replacement = "~";
                } else if (path && "%2F".equals(replacement)) {
                    replacement = "/";
                }
                matcher.appendReplacement(buffer, replacement);
            }
            matcher.appendTail(buffer);
            return buffer.toString();
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String urlDecode(String value) {
        if (value == null) {
            return null;
        }
        try {
            return URLDecoder.decode(value, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static boolean isUsingNonDefaultPort(URI uri) {
        String scheme = StringUtils.lowerCase(uri.getScheme());
        int port = uri.getPort();
        if (port <= 0) {
            return false;
        }
        if ("http".equals(scheme) && port == 80) {
            return false;
        }
        if (!ClientConstants.DOMAIN_SCHEME.equals(scheme) || port != PORT_HTTPS) {
            return true;
        }
        return false;
    }

    public static boolean usePayloadForQueryParameters(Request<?> request) {
        boolean requestHasNoPayload;
        boolean requestIsPOST = HttpMethodName.POST.equals(request.getHttpMethod());
        if (request.getContent() == null) {
            requestHasNoPayload = true;
        } else {
            requestHasNoPayload = false;
        }
        if (!requestIsPOST || !requestHasNoPayload) {
            return false;
        }
        return true;
    }

    public static String encodeParameters(Request<?> request) {
        if (request.getParameters().isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        try {
            for (Entry<String, String> entry : request.getParameters().entrySet()) {
                String encodedName = URLEncoder.encode((String) entry.getKey(), DEFAULT_ENCODING);
                String value = (String) entry.getValue();
                String encodedValue = value == null ? "" : URLEncoder.encode(value, DEFAULT_ENCODING);
                if (!first) {
                    sb.append("&");
                } else {
                    first = false;
                }
                sb.append(encodedName).append("=").append(encodedValue);
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String appendUri(String baseUri, String path) {
        return appendUri(baseUri, path, false);
    }

    public static String appendUri(String baseUri, String path, boolean escapeDoubleSlash) {
        String resultUri = baseUri;
        if (path != null && path.length() > 0) {
            if (path.startsWith("/")) {
                if (resultUri.endsWith("/")) {
                    resultUri = resultUri.substring(0, resultUri.length() - 1);
                }
            } else if (!resultUri.endsWith("/")) {
                resultUri = resultUri + "/";
            }
            String encodedPath = urlEncode(path, true);
            if (escapeDoubleSlash) {
                encodedPath = encodedPath.replace("//", "/%2F");
            }
            return resultUri + encodedPath;
        } else if (!resultUri.endsWith("/")) {
            return resultUri + "/";
        } else {
            return resultUri;
        }
    }

    public static InputStream fetchFile(URI uri, ClientConfiguration config) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setConnectTimeout(getConnectionTimeout(config));
        connection.setReadTimeout(getSocketTimeout(config));
        connection.addRequestProperty(HttpHeader.USER_AGENT, getUserAgent(config));
        if (connection.getResponseCode() == 200) {
            return connection.getInputStream();
        }
        InputStream is = connection.getErrorStream();
        if (is != null) {
            is.close();
        }
        connection.disconnect();
        throw new IOException("Error fetching file from " + uri + ": " + connection.getResponseMessage());
    }

    static String getUserAgent(ClientConfiguration config) {
        String userAgent = null;
        if (config != null) {
            userAgent = config.getUserAgent();
        }
        if (userAgent == null) {
            return ClientConfiguration.DEFAULT_USER_AGENT;
        }
        if (!ClientConfiguration.DEFAULT_USER_AGENT.equals(userAgent)) {
            return userAgent + ", " + ClientConfiguration.DEFAULT_USER_AGENT;
        }
        return userAgent;
    }

    static int getConnectionTimeout(ClientConfiguration config) {
        if (config != null) {
            return config.getConnectionTimeout();
        }
        return 15000;
    }

    static int getSocketTimeout(ClientConfiguration config) {
        if (config != null) {
            return config.getSocketTimeout();
        }
        return 15000;
    }
}
