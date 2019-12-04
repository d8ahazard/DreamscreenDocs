package com.amazonaws.auth;

import com.amazonaws.AmazonClientException;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.mobileconnectors.cognitoauth.util.ClientConstants;
import com.amazonaws.util.DateUtils;
import com.amazonaws.util.HttpUtils;
import com.amazonaws.util.StringUtils;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AWS3Signer extends AbstractAWSSigner {
    private static final String AUTHORIZATION_HEADER = "X-Amzn-Authorization";
    private static final String HTTPS_SCHEME = "AWS3-HTTPS";
    private static final String HTTP_SCHEME = "AWS3";
    private static final String NONCE_HEADER = "x-amz-nonce";
    private static final Log log = LogFactory.getLog(AWS3Signer.class);
    private String overriddenDate;

    public void sign(Request<?> request, AWSCredentials credentials) {
        String stringToSign;
        byte[] bytesToSign;
        if (!(credentials instanceof AnonymousAWSCredentials)) {
            AWSCredentials sanitizedCredentials = sanitizeCredentials(credentials);
            SigningAlgorithm algorithm = SigningAlgorithm.HmacSHA256;
            String nonce = UUID.randomUUID().toString();
            String date = DateUtils.formatRFC822Date(getSignatureDate(getTimeOffset(request)));
            if (this.overriddenDate != null) {
                date = this.overriddenDate;
            }
            request.addHeader(HttpHeader.DATE, date);
            request.addHeader("X-Amz-Date", date);
            String hostHeader = request.getEndpoint().getHost();
            if (HttpUtils.isUsingNonDefaultPort(request.getEndpoint())) {
                hostHeader = hostHeader + ":" + request.getEndpoint().getPort();
            }
            request.addHeader(HttpHeader.HOST, hostHeader);
            if (sanitizedCredentials instanceof AWSSessionCredentials) {
                addSessionCredentials(request, (AWSSessionCredentials) sanitizedCredentials);
            }
            if (0 != 0) {
                request.addHeader("x-amz-nonce", nonce);
                stringToSign = date + nonce;
                bytesToSign = stringToSign.getBytes(StringUtils.UTF8);
            } else {
                stringToSign = request.getHttpMethod().toString() + "\n" + getCanonicalizedResourcePath(HttpUtils.appendUri(request.getEndpoint().getPath(), request.getResourcePath())) + "\n" + getCanonicalizedQueryString(request.getParameters()) + "\n" + getCanonicalizedHeadersForStringToSign(request) + "\n" + getRequestPayloadWithoutQueryParams(request);
                bytesToSign = hash(stringToSign);
            }
            log.debug("Calculated StringToSign: " + stringToSign);
            String signature = signAndBase64Encode(bytesToSign, sanitizedCredentials.getAWSSecretKey(), algorithm);
            StringBuilder builder = new StringBuilder();
            builder.append(0 != 0 ? HTTPS_SCHEME : HTTP_SCHEME).append(" ");
            builder.append("AWSAccessKeyId=" + sanitizedCredentials.getAWSAccessKeyId() + ",");
            builder.append("Algorithm=" + algorithm.toString() + ",");
            if (0 == 0) {
                builder.append(getSignedHeadersComponent(request) + ",");
            }
            builder.append("Signature=" + signature);
            request.addHeader(AUTHORIZATION_HEADER, builder.toString());
        }
    }

    private String getSignedHeadersComponent(Request<?> request) {
        StringBuilder builder = new StringBuilder();
        builder.append("SignedHeaders=");
        boolean first = true;
        for (String header : getHeadersForStringToSign(request)) {
            if (!first) {
                builder.append(";");
            }
            builder.append(header);
            first = false;
        }
        return builder.toString();
    }

    /* access modifiers changed from: protected */
    public List<String> getHeadersForStringToSign(Request<?> request) {
        List<String> headersToSign = new ArrayList<>();
        for (Entry<String, String> entry : request.getHeaders().entrySet()) {
            String key = (String) entry.getKey();
            String lowerCaseKey = StringUtils.lowerCase(key);
            if (lowerCaseKey.startsWith("x-amz") || "host".equals(lowerCaseKey)) {
                headersToSign.add(key);
            }
        }
        Collections.sort(headersToSign);
        return headersToSign;
    }

    /* access modifiers changed from: 0000 */
    public void overrideDate(String date) {
        this.overriddenDate = date;
    }

    /* access modifiers changed from: protected */
    public String getCanonicalizedHeadersForStringToSign(Request<?> request) {
        List<String> headersToSign = getHeadersForStringToSign(request);
        for (int i = 0; i < headersToSign.size(); i++) {
            headersToSign.set(i, StringUtils.lowerCase((String) headersToSign.get(i)));
        }
        SortedMap<String, String> sortedHeaderMap = new TreeMap<>();
        for (Entry<String, String> entry : request.getHeaders().entrySet()) {
            if (headersToSign.contains(StringUtils.lowerCase((String) entry.getKey()))) {
                sortedHeaderMap.put(StringUtils.lowerCase((String) entry.getKey()), entry.getValue());
            }
        }
        StringBuilder builder = new StringBuilder();
        for (Entry<String, String> entry2 : sortedHeaderMap.entrySet()) {
            builder.append(StringUtils.lowerCase((String) entry2.getKey())).append(":").append((String) entry2.getValue()).append("\n");
        }
        return builder.toString();
    }

    /* access modifiers changed from: 0000 */
    public boolean shouldUseHttpsScheme(Request<?> request) {
        try {
            String protocol = StringUtils.lowerCase(request.getEndpoint().toURL().getProtocol());
            if ("http".equals(protocol)) {
                return false;
            }
            if (ClientConstants.DOMAIN_SCHEME.equals(protocol)) {
                return true;
            }
            throw new AmazonClientException("Unknown request endpoint protocol encountered while signing request: " + protocol);
        } catch (MalformedURLException e) {
            throw new AmazonClientException("Unable to parse request endpoint during signing", e);
        }
    }

    /* access modifiers changed from: protected */
    public void addSessionCredentials(Request<?> request, AWSSessionCredentials credentials) {
        request.addHeader("x-amz-security-token", credentials.getSessionToken());
    }
}
