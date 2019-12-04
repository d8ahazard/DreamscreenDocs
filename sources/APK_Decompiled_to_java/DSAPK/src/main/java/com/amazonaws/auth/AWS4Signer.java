package com.amazonaws.auth;

import com.amazonaws.AmazonClientException;
import com.amazonaws.Request;
import com.amazonaws.http.HttpHeader;
import com.amazonaws.util.AwsHostNameUtils;
import com.amazonaws.util.BinaryUtils;
import com.amazonaws.util.DateUtils;
import com.amazonaws.util.HttpUtils;
import com.amazonaws.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AWS4Signer extends AbstractAWSSigner implements ServiceAwareSigner, RegionAwareSigner, Presigner {
    protected static final String ALGORITHM = "AWS4-HMAC-SHA256";
    private static final String DATE_PATTERN = "yyyyMMdd";
    private static final long MAX_EXPIRATION_TIME_IN_SECONDS = 604800;
    private static final long MILLISEC = 1000;
    protected static final String TERMINATOR = "aws4_request";
    private static final String TIME_PATTERN = "yyyyMMdd'T'HHmmss'Z'";
    protected static final Log log = LogFactory.getLog(AWS4Signer.class);
    protected boolean doubleUrlEncode;
    protected Date overriddenDate;
    protected String regionName;
    protected String serviceName;

    protected static class HeaderSigningResult {
        private final String dateTime;
        private final byte[] kSigning;
        private final String scope;
        private final byte[] signature;

        public HeaderSigningResult(String dateTime2, String scope2, byte[] kSigning2, byte[] signature2) {
            this.dateTime = dateTime2;
            this.scope = scope2;
            this.kSigning = kSigning2;
            this.signature = signature2;
        }

        public String getDateTime() {
            return this.dateTime;
        }

        public String getScope() {
            return this.scope;
        }

        public byte[] getKSigning() {
            byte[] kSigningCopy = new byte[this.kSigning.length];
            System.arraycopy(this.kSigning, 0, kSigningCopy, 0, this.kSigning.length);
            return kSigningCopy;
        }

        public byte[] getSignature() {
            byte[] signatureCopy = new byte[this.signature.length];
            System.arraycopy(this.signature, 0, signatureCopy, 0, this.signature.length);
            return signatureCopy;
        }
    }

    public AWS4Signer() {
        this(true);
    }

    public AWS4Signer(boolean doubleUrlEncoding) {
        this.doubleUrlEncode = doubleUrlEncoding;
    }

    public void sign(Request<?> request, AWSCredentials credentials) {
        if (!(credentials instanceof AnonymousAWSCredentials)) {
            AWSCredentials sanitizedCredentials = sanitizeCredentials(credentials);
            if (sanitizedCredentials instanceof AWSSessionCredentials) {
                addSessionCredentials(request, (AWSSessionCredentials) sanitizedCredentials);
            }
            addHostHeader(request);
            long dateMilli = getDateFromRequest(request);
            String dateStamp = getDateStamp(dateMilli);
            String scope = getScope(request, dateStamp);
            String contentSha256 = calculateContentHash(request);
            String timeStamp = getTimeStamp(dateMilli);
            request.addHeader("X-Amz-Date", timeStamp);
            if (request.getHeaders().get("x-amz-content-sha256") != null && "required".equals(request.getHeaders().get("x-amz-content-sha256"))) {
                request.addHeader("x-amz-content-sha256", contentSha256);
            }
            String signingCredentials = sanitizedCredentials.getAWSAccessKeyId() + "/" + scope;
            HeaderSigningResult headerSigningResult = computeSignature(request, dateStamp, timeStamp, ALGORITHM, contentSha256, sanitizedCredentials);
            String credentialsAuthorizationHeader = "Credential=" + signingCredentials;
            String signedHeadersAuthorizationHeader = "SignedHeaders=" + getSignedHeadersString(request);
            Request<?> request2 = request;
            request2.addHeader("Authorization", "AWS4-HMAC-SHA256 " + credentialsAuthorizationHeader + ", " + signedHeadersAuthorizationHeader + ", " + ("Signature=" + BinaryUtils.toHex(headerSigningResult.getSignature())));
            processRequestPayload(request, headerSigningResult);
        }
    }

    public void setServiceName(String serviceName2) {
        this.serviceName = serviceName2;
    }

    public void setRegionName(String regionName2) {
        this.regionName = regionName2;
    }

    /* access modifiers changed from: protected */
    public void addSessionCredentials(Request<?> request, AWSSessionCredentials credentials) {
        request.addHeader("x-amz-security-token", credentials.getSessionToken());
    }

    /* access modifiers changed from: protected */
    public String extractRegionName(URI endpoint) {
        if (this.regionName != null) {
            return this.regionName;
        }
        return AwsHostNameUtils.parseRegionName(endpoint.getHost(), this.serviceName);
    }

    /* access modifiers changed from: protected */
    public String extractServiceName(URI endpoint) {
        if (this.serviceName != null) {
            return this.serviceName;
        }
        return AwsHostNameUtils.parseServiceName(endpoint);
    }

    /* access modifiers changed from: 0000 */
    public void overrideDate(Date overriddenDate2) {
        this.overriddenDate = overriddenDate2;
    }

    /* access modifiers changed from: protected */
    public String getCanonicalizedHeaderString(Request<?> request) {
        List<String> sortedHeaders = new ArrayList<>();
        sortedHeaders.addAll(request.getHeaders().keySet());
        Collections.sort(sortedHeaders, String.CASE_INSENSITIVE_ORDER);
        StringBuilder buffer = new StringBuilder();
        for (String header : sortedHeaders) {
            if (needsSign(header)) {
                String value = (String) request.getHeaders().get(header);
                buffer.append(StringUtils.lowerCase(header).replaceAll("\\s+", " ")).append(":");
                if (value != null) {
                    buffer.append(value.replaceAll("\\s+", " "));
                }
                buffer.append("\n");
            }
        }
        return buffer.toString();
    }

    /* access modifiers changed from: protected */
    public String getSignedHeadersString(Request<?> request) {
        List<String> sortedHeaders = new ArrayList<>();
        sortedHeaders.addAll(request.getHeaders().keySet());
        Collections.sort(sortedHeaders, String.CASE_INSENSITIVE_ORDER);
        StringBuilder buffer = new StringBuilder();
        for (String header : sortedHeaders) {
            if (needsSign(header)) {
                if (buffer.length() > 0) {
                    buffer.append(";");
                }
                buffer.append(StringUtils.lowerCase(header));
            }
        }
        return buffer.toString();
    }

    /* access modifiers changed from: protected */
    public String getCanonicalRequest(Request<?> request, String contentSha256) {
        String canonicalRequest = request.getHttpMethod().toString() + "\n" + getCanonicalizedResourcePath(HttpUtils.appendUri(request.getEndpoint().getPath(), request.getResourcePath()), this.doubleUrlEncode) + "\n" + getCanonicalizedQueryString(request) + "\n" + getCanonicalizedHeaderString(request) + "\n" + getSignedHeadersString(request) + "\n" + contentSha256;
        log.debug("AWS4 Canonical Request: '\"" + canonicalRequest + "\"");
        return canonicalRequest;
    }

    /* access modifiers changed from: protected */
    public String getStringToSign(String algorithm, String dateTime, String scope, String canonicalRequest) {
        String stringToSign = algorithm + "\n" + dateTime + "\n" + scope + "\n" + BinaryUtils.toHex(hash(canonicalRequest));
        log.debug("AWS4 String to Sign: '\"" + stringToSign + "\"");
        return stringToSign;
    }

    /* access modifiers changed from: protected */
    public final HeaderSigningResult computeSignature(Request<?> request, String dateStamp, String timeStamp, String algorithm, String contentSha256, AWSCredentials sanitizedCredentials) {
        String regionName2 = extractRegionName(request.getEndpoint());
        String serviceName2 = extractServiceName(request.getEndpoint());
        String scope = dateStamp + "/" + regionName2 + "/" + serviceName2 + "/" + TERMINATOR;
        String stringToSign = getStringToSign(algorithm, timeStamp, scope, getCanonicalRequest(request, contentSha256));
        byte[] kSigning = sign(TERMINATOR, sign(serviceName2, sign(regionName2, sign(dateStamp, ("AWS4" + sanitizedCredentials.getAWSSecretKey()).getBytes(StringUtils.UTF8), SigningAlgorithm.HmacSHA256), SigningAlgorithm.HmacSHA256), SigningAlgorithm.HmacSHA256), SigningAlgorithm.HmacSHA256);
        return new HeaderSigningResult(timeStamp, scope, kSigning, sign(stringToSign.getBytes(StringUtils.UTF8), kSigning, SigningAlgorithm.HmacSHA256));
    }

    /* access modifiers changed from: protected */
    public final String getTimeStamp(long dateMilli) {
        return DateUtils.format("yyyyMMdd'T'HHmmss'Z'", new Date(dateMilli));
    }

    /* access modifiers changed from: protected */
    public final String getDateStamp(long dateMilli) {
        return DateUtils.format(DATE_PATTERN, new Date(dateMilli));
    }

    /* access modifiers changed from: protected */
    public final long getDateFromRequest(Request<?> request) {
        Date date = getSignatureDate(getTimeOffset(request));
        if (this.overriddenDate != null) {
            date = this.overriddenDate;
        }
        return date.getTime();
    }

    /* access modifiers changed from: protected */
    public void addHostHeader(Request<?> request) {
        String hostHeader = request.getEndpoint().getHost();
        if (HttpUtils.isUsingNonDefaultPort(request.getEndpoint())) {
            hostHeader = hostHeader + ":" + request.getEndpoint().getPort();
        }
        request.addHeader(HttpHeader.HOST, hostHeader);
    }

    /* access modifiers changed from: protected */
    public String getScope(Request<?> request, String dateStamp) {
        String regionName2 = extractRegionName(request.getEndpoint());
        return dateStamp + "/" + regionName2 + "/" + extractServiceName(request.getEndpoint()) + "/" + TERMINATOR;
    }

    /* access modifiers changed from: protected */
    public String calculateContentHash(Request<?> request) {
        InputStream payloadStream = getBinaryRequestPayloadStream(request);
        payloadStream.mark(-1);
        String contentSha256 = BinaryUtils.toHex(hash(payloadStream));
        try {
            payloadStream.reset();
            return contentSha256;
        } catch (IOException e) {
            throw new AmazonClientException("Unable to reset stream after calculating AWS4 signature", e);
        }
    }

    /* access modifiers changed from: protected */
    public void processRequestPayload(Request<?> request, HeaderSigningResult headerSigningResult) {
    }

    public void presignRequest(Request<?> request, AWSCredentials credentials, Date expiration) {
        if (!(credentials instanceof AnonymousAWSCredentials)) {
            long expirationInSeconds = MAX_EXPIRATION_TIME_IN_SECONDS;
            if (expiration != null) {
                expirationInSeconds = (expiration.getTime() - System.currentTimeMillis()) / MILLISEC;
            }
            if (expirationInSeconds > MAX_EXPIRATION_TIME_IN_SECONDS) {
                throw new AmazonClientException("Requests that are pre-signed by SigV4 algorithm are valid for at most 7 days. The expiration date set on the current request [" + getTimeStamp(expiration.getTime()) + "] has exceeded this limit.");
            }
            addHostHeader(request);
            AWSCredentials sanitizedCredentials = sanitizeCredentials(credentials);
            if (sanitizedCredentials instanceof AWSSessionCredentials) {
                request.addParameter("X-Amz-Security-Token", ((AWSSessionCredentials) sanitizedCredentials).getSessionToken());
            }
            long dateMilli = getDateFromRequest(request);
            String dateStamp = getDateStamp(dateMilli);
            String signingCredentials = sanitizedCredentials.getAWSAccessKeyId() + "/" + getScope(request, dateStamp);
            String timeStamp = getTimeStamp(dateMilli);
            request.addParameter("X-Amz-Algorithm", ALGORITHM);
            request.addParameter("X-Amz-Date", timeStamp);
            request.addParameter("X-Amz-SignedHeaders", getSignedHeadersString(request));
            request.addParameter("X-Amz-Expires", Long.toString(expirationInSeconds));
            request.addParameter("X-Amz-Credential", signingCredentials);
            Request<?> request2 = request;
            Request<?> request3 = request;
            request3.addParameter("X-Amz-Signature", BinaryUtils.toHex(computeSignature(request2, dateStamp, timeStamp, ALGORITHM, calculateContentHashPresign(request), sanitizedCredentials).getSignature()));
        }
    }

    /* access modifiers changed from: protected */
    public String calculateContentHashPresign(Request<?> request) {
        return calculateContentHash(request);
    }

    /* access modifiers changed from: 0000 */
    public boolean needsSign(String header) {
        return "date".equalsIgnoreCase(header) || "Content-MD5".equalsIgnoreCase(header) || "host".equalsIgnoreCase(header) || header.startsWith("x-amz") || header.startsWith("X-Amz");
    }
}
