package com.amazonaws.auth;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import java.util.Date;

public class STSSessionCredentialsProvider implements AWSCredentialsProvider {
    public static final int DEFAULT_DURATION_SECONDS = 3600;
    private final AWSSecurityTokenService securityTokenService;
    private AWSSessionCredentials sessionCredentials;
    private Date sessionCredentialsExpiration;

    public STSSessionCredentialsProvider(AWSCredentials longLivedCredentials) {
        this(longLivedCredentials, new ClientConfiguration());
    }

    public STSSessionCredentialsProvider(AWSCredentials longLivedCredentials, ClientConfiguration clientConfiguration) {
        this.securityTokenService = new AWSSecurityTokenServiceClient(longLivedCredentials, clientConfiguration);
    }

    public STSSessionCredentialsProvider(AWSCredentialsProvider longLivedCredentialsProvider) {
        this.securityTokenService = new AWSSecurityTokenServiceClient(longLivedCredentialsProvider);
    }

    public STSSessionCredentialsProvider(AWSCredentialsProvider longLivedCredentialsProvider, ClientConfiguration clientConfiguration) {
        this.securityTokenService = new AWSSecurityTokenServiceClient(longLivedCredentialsProvider, clientConfiguration);
    }

    public void setSTSClientEndpoint(String endpoint) {
        this.securityTokenService.setEndpoint(endpoint);
        this.sessionCredentials = null;
    }

    public AWSCredentials getCredentials() {
        if (needsNewSession()) {
            startSession();
        }
        return this.sessionCredentials;
    }

    public void refresh() {
        startSession();
    }

    private void startSession() {
        Credentials stsCredentials = this.securityTokenService.getSessionToken(new GetSessionTokenRequest().withDurationSeconds(Integer.valueOf(3600))).getCredentials();
        this.sessionCredentials = new BasicSessionCredentials(stsCredentials.getAccessKeyId(), stsCredentials.getSecretAccessKey(), stsCredentials.getSessionToken());
        this.sessionCredentialsExpiration = stsCredentials.getExpiration();
    }

    private boolean needsNewSession() {
        if (this.sessionCredentials != null && this.sessionCredentialsExpiration.getTime() - System.currentTimeMillis() >= 60000) {
            return false;
        }
        return true;
    }
}
