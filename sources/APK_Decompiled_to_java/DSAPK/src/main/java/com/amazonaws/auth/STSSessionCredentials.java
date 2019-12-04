package com.amazonaws.auth;

import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;

@Deprecated
public class STSSessionCredentials implements AWSRefreshableSessionCredentials {
    public static final int DEFAULT_DURATION_SECONDS = 3600;
    private final AWSSecurityTokenService securityTokenService;
    private Credentials sessionCredentials;
    private final int sessionDurationSeconds;

    public STSSessionCredentials(AWSCredentials credentials) {
        this(credentials, 3600);
    }

    public STSSessionCredentials(AWSCredentials credentials, int sessionDurationSeconds2) {
        this.securityTokenService = new AWSSecurityTokenServiceClient(credentials);
        this.sessionDurationSeconds = sessionDurationSeconds2;
    }

    public STSSessionCredentials(AWSSecurityTokenService stsClient) {
        this(stsClient, 3600);
    }

    public STSSessionCredentials(AWSSecurityTokenService stsClient, int sessionDuratinSeconds) {
        this.securityTokenService = stsClient;
        this.sessionDurationSeconds = sessionDuratinSeconds;
    }

    public synchronized String getAWSAccessKeyId() {
        return getSessionCredentials().getAccessKeyId();
    }

    public synchronized String getAWSSecretKey() {
        return getSessionCredentials().getSecretAccessKey();
    }

    public synchronized String getSessionToken() {
        return getSessionCredentials().getSessionToken();
    }

    public synchronized AWSSessionCredentials getImmutableCredentials() {
        Credentials creds;
        creds = getSessionCredentials();
        return new BasicSessionCredentials(creds.getAccessKeyId(), creds.getSecretAccessKey(), creds.getSessionToken());
    }

    public synchronized void refreshCredentials() {
        this.sessionCredentials = this.securityTokenService.getSessionToken(new GetSessionTokenRequest().withDurationSeconds(Integer.valueOf(this.sessionDurationSeconds))).getCredentials();
    }

    private synchronized Credentials getSessionCredentials() {
        if (needsNewSession()) {
            refreshCredentials();
        }
        return this.sessionCredentials;
    }

    private boolean needsNewSession() {
        if (this.sessionCredentials != null && this.sessionCredentials.getExpiration().getTime() - System.currentTimeMillis() >= 60000) {
            return false;
        }
        return true;
    }
}
