package com.amazonaws;

import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.retry.RetryPolicy;
import com.amazonaws.util.VersionInfoUtils;
import java.net.InetAddress;
import javax.net.ssl.TrustManager;

public class ClientConfiguration {
    public static final int DEFAULT_CONNECTION_TIMEOUT = 15000;
    public static final int DEFAULT_MAX_CONNECTIONS = 10;
    public static final RetryPolicy DEFAULT_RETRY_POLICY = PredefinedRetryPolicies.DEFAULT;
    public static final int DEFAULT_SOCKET_TIMEOUT = 15000;
    public static final String DEFAULT_USER_AGENT = VersionInfoUtils.getUserAgent();
    public static final boolean DEFAULT_USE_REAPER = true;
    private int connectionTimeout = 15000;
    private boolean curlLogging = false;
    private boolean enableGzip = false;
    private InetAddress localAddress;
    private int maxConnections = 10;
    private int maxErrorRetry = -1;
    private boolean preemptiveBasicProxyAuth;
    private Protocol protocol = Protocol.HTTPS;
    @Deprecated
    private String proxyDomain = null;
    private String proxyHost = null;
    private String proxyPassword = null;
    private int proxyPort = -1;
    private String proxyUsername = null;
    @Deprecated
    private String proxyWorkstation = null;
    private RetryPolicy retryPolicy = DEFAULT_RETRY_POLICY;
    private String signerOverride;
    private int socketReceiveBufferSizeHint = 0;
    private int socketSendBufferSizeHint = 0;
    private int socketTimeout = 15000;
    private TrustManager trustManager = null;
    private boolean useReaper = true;
    private String userAgent = DEFAULT_USER_AGENT;

    public ClientConfiguration() {
    }

    public ClientConfiguration(ClientConfiguration other) {
        this.connectionTimeout = other.connectionTimeout;
        this.maxConnections = other.maxConnections;
        this.maxErrorRetry = other.maxErrorRetry;
        this.retryPolicy = other.retryPolicy;
        this.localAddress = other.localAddress;
        this.protocol = other.protocol;
        this.proxyDomain = other.proxyDomain;
        this.proxyHost = other.proxyHost;
        this.proxyPassword = other.proxyPassword;
        this.proxyPort = other.proxyPort;
        this.proxyUsername = other.proxyUsername;
        this.proxyWorkstation = other.proxyWorkstation;
        this.preemptiveBasicProxyAuth = other.preemptiveBasicProxyAuth;
        this.socketTimeout = other.socketTimeout;
        this.userAgent = other.userAgent;
        this.useReaper = other.useReaper;
        this.socketReceiveBufferSizeHint = other.socketReceiveBufferSizeHint;
        this.socketSendBufferSizeHint = other.socketSendBufferSizeHint;
        this.signerOverride = other.signerOverride;
        this.trustManager = other.trustManager;
        this.curlLogging = other.curlLogging;
        this.enableGzip = other.enableGzip;
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    public void setProtocol(Protocol protocol2) {
        this.protocol = protocol2;
    }

    public ClientConfiguration withProtocol(Protocol protocol2) {
        setProtocol(protocol2);
        return this;
    }

    public int getMaxConnections() {
        return this.maxConnections;
    }

    public void setMaxConnections(int maxConnections2) {
        this.maxConnections = maxConnections2;
    }

    public ClientConfiguration withMaxConnections(int maxConnections2) {
        setMaxConnections(maxConnections2);
        return this;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public void setUserAgent(String userAgent2) {
        this.userAgent = userAgent2;
    }

    public ClientConfiguration withUserAgent(String userAgent2) {
        setUserAgent(userAgent2);
        return this;
    }

    public InetAddress getLocalAddress() {
        return this.localAddress;
    }

    public void setLocalAddress(InetAddress localAddress2) {
        this.localAddress = localAddress2;
    }

    public ClientConfiguration withLocalAddress(InetAddress localAddress2) {
        setLocalAddress(localAddress2);
        return this;
    }

    public String getProxyHost() {
        return this.proxyHost;
    }

    public void setProxyHost(String proxyHost2) {
        this.proxyHost = proxyHost2;
    }

    public ClientConfiguration withProxyHost(String proxyHost2) {
        setProxyHost(proxyHost2);
        return this;
    }

    public int getProxyPort() {
        return this.proxyPort;
    }

    public void setProxyPort(int proxyPort2) {
        this.proxyPort = proxyPort2;
    }

    public ClientConfiguration withProxyPort(int proxyPort2) {
        setProxyPort(proxyPort2);
        return this;
    }

    public String getProxyUsername() {
        return this.proxyUsername;
    }

    public void setProxyUsername(String proxyUsername2) {
        this.proxyUsername = proxyUsername2;
    }

    public ClientConfiguration withProxyUsername(String proxyUsername2) {
        setProxyUsername(proxyUsername2);
        return this;
    }

    public String getProxyPassword() {
        return this.proxyPassword;
    }

    public void setProxyPassword(String proxyPassword2) {
        this.proxyPassword = proxyPassword2;
    }

    public ClientConfiguration withProxyPassword(String proxyPassword2) {
        setProxyPassword(proxyPassword2);
        return this;
    }

    @Deprecated
    public String getProxyDomain() {
        return this.proxyDomain;
    }

    @Deprecated
    public void setProxyDomain(String proxyDomain2) {
        this.proxyDomain = proxyDomain2;
    }

    @Deprecated
    public ClientConfiguration withProxyDomain(String proxyDomain2) {
        setProxyDomain(proxyDomain2);
        return this;
    }

    public String getProxyWorkstation() {
        return this.proxyWorkstation;
    }

    @Deprecated
    public void setProxyWorkstation(String proxyWorkstation2) {
        this.proxyWorkstation = proxyWorkstation2;
    }

    @Deprecated
    public ClientConfiguration withProxyWorkstation(String proxyWorkstation2) {
        setProxyWorkstation(proxyWorkstation2);
        return this;
    }

    public RetryPolicy getRetryPolicy() {
        return this.retryPolicy;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy2) {
        this.retryPolicy = retryPolicy2;
    }

    public ClientConfiguration withRetryPolicy(RetryPolicy retryPolicy2) {
        setRetryPolicy(retryPolicy2);
        return this;
    }

    public int getMaxErrorRetry() {
        return this.maxErrorRetry;
    }

    public void setMaxErrorRetry(int maxErrorRetry2) {
        if (maxErrorRetry2 < 0) {
            throw new IllegalArgumentException("maxErrorRetry shoud be non-negative");
        }
        this.maxErrorRetry = maxErrorRetry2;
    }

    public ClientConfiguration withMaxErrorRetry(int maxErrorRetry2) {
        setMaxErrorRetry(maxErrorRetry2);
        return this;
    }

    public int getSocketTimeout() {
        return this.socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout2) {
        this.socketTimeout = socketTimeout2;
    }

    public ClientConfiguration withSocketTimeout(int socketTimeout2) {
        setSocketTimeout(socketTimeout2);
        return this;
    }

    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout2) {
        this.connectionTimeout = connectionTimeout2;
    }

    public ClientConfiguration withConnectionTimeout(int connectionTimeout2) {
        setConnectionTimeout(connectionTimeout2);
        return this;
    }

    public boolean useReaper() {
        return this.useReaper;
    }

    public void setUseReaper(boolean use) {
        this.useReaper = use;
    }

    public ClientConfiguration withReaper(boolean use) {
        setUseReaper(use);
        return this;
    }

    public int[] getSocketBufferSizeHints() {
        return new int[]{this.socketSendBufferSizeHint, this.socketReceiveBufferSizeHint};
    }

    public void setSocketBufferSizeHints(int socketSendBufferSizeHint2, int socketReceiveBufferSizeHint2) {
        this.socketSendBufferSizeHint = socketSendBufferSizeHint2;
        this.socketReceiveBufferSizeHint = socketReceiveBufferSizeHint2;
    }

    public ClientConfiguration withSocketBufferSizeHints(int socketSendBufferSizeHint2, int socketReceiveBufferSizeHint2) {
        setSocketBufferSizeHints(socketSendBufferSizeHint2, socketReceiveBufferSizeHint2);
        return this;
    }

    public String getSignerOverride() {
        return this.signerOverride;
    }

    public void setSignerOverride(String value) {
        this.signerOverride = value;
    }

    public ClientConfiguration withSignerOverride(String value) {
        setSignerOverride(value);
        return this;
    }

    public boolean isPreemptiveBasicProxyAuth() {
        return this.preemptiveBasicProxyAuth;
    }

    public void setPreemptiveBasicProxyAuth(Boolean preemptiveBasicProxyAuth2) {
        this.preemptiveBasicProxyAuth = preemptiveBasicProxyAuth2.booleanValue();
    }

    public ClientConfiguration withPreemptiveBasicProxyAuth(boolean preemptiveBasicProxyAuth2) {
        setPreemptiveBasicProxyAuth(Boolean.valueOf(preemptiveBasicProxyAuth2));
        return this;
    }

    public TrustManager getTrustManager() {
        return this.trustManager;
    }

    public void setTrustManager(TrustManager trustManager2) {
        this.trustManager = trustManager2;
    }

    public ClientConfiguration withTrustManager(TrustManager trustManager2) {
        setTrustManager(trustManager2);
        return this;
    }

    public boolean isCurlLogging() {
        return this.curlLogging;
    }

    public void setCurlLogging(boolean curlLogging2) {
        this.curlLogging = curlLogging2;
    }

    public ClientConfiguration withCurlLogging(boolean curlLogging2) {
        this.curlLogging = curlLogging2;
        return this;
    }

    public boolean isEnableGzip() {
        return this.enableGzip;
    }

    public void setEnableGzip(boolean enableGzip2) {
        this.enableGzip = enableGzip2;
    }

    public ClientConfiguration withEnableGzip(boolean enableGzip2) {
        setEnableGzip(enableGzip2);
        return this;
    }
}
