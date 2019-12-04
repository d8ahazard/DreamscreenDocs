package com.amazonaws.auth;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.Credentials;
import java.util.Date;

public class STSAssumeRoleSessionCredentialsProvider implements AWSCredentialsProvider {
    public static final int DEFAULT_DURATION_SECONDS = 900;
    private static final int EXPIRY_TIME_MILLIS = 60000;
    private String roleArn;
    private String roleSessionName;
    private final AWSSecurityTokenService securityTokenService;
    private AWSSessionCredentials sessionCredentials;
    private Date sessionCredentialsExpiration;

    public STSAssumeRoleSessionCredentialsProvider(String roleArn2, String roleSessionName2) {
        this.roleArn = roleArn2;
        this.roleSessionName = roleSessionName2;
        this.securityTokenService = new AWSSecurityTokenServiceClient();
    }

    public STSAssumeRoleSessionCredentialsProvider(AWSCredentials longLivedCredentials, String roleArn2, String roleSessionName2) {
        this(longLivedCredentials, roleArn2, roleSessionName2, new ClientConfiguration());
    }

    public STSAssumeRoleSessionCredentialsProvider(AWSCredentials longLivedCredentials, String roleArn2, String roleSessionName2, ClientConfiguration clientConfiguration) {
        this.roleArn = roleArn2;
        this.roleSessionName = roleSessionName2;
        this.securityTokenService = new AWSSecurityTokenServiceClient(longLivedCredentials, clientConfiguration);
    }

    public STSAssumeRoleSessionCredentialsProvider(AWSCredentialsProvider longLivedCredentialsProvider, String roleArn2, String roleSessionName2) {
        this.roleArn = roleArn2;
        this.roleSessionName = roleSessionName2;
        this.securityTokenService = new AWSSecurityTokenServiceClient(longLivedCredentialsProvider);
    }

    public STSAssumeRoleSessionCredentialsProvider(AWSCredentialsProvider longLivedCredentialsProvider, String roleArn2, String roleSessionName2, ClientConfiguration clientConfiguration) {
        this.roleArn = roleArn2;
        this.roleSessionName = roleSessionName2;
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
        Credentials stsCredentials = this.securityTokenService.assumeRole(new AssumeRoleRequest().withRoleArn(this.roleArn).withDurationSeconds(Integer.valueOf(DEFAULT_DURATION_SECONDS)).withRoleSessionName(this.roleSessionName)).getCredentials();
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
