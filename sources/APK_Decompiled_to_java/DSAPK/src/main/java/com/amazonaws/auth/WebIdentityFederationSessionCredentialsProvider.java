package com.amazonaws.auth;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient;
import com.amazonaws.services.securitytoken.model.AssumeRoleWithWebIdentityRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleWithWebIdentityResult;
import com.amazonaws.services.securitytoken.model.Credentials;
import java.util.Date;

public class WebIdentityFederationSessionCredentialsProvider implements AWSCredentialsProvider {
    public static final int DEFAULT_DURATION_SECONDS = 3600;
    public static final int DEFAULT_THRESHOLD_SECONDS = 500;
    private int refreshThreshold;
    private final String roleArn;
    private final AWSSecurityTokenService securityTokenService;
    private AWSSessionCredentials sessionCredentials;
    private Date sessionCredentialsExpiration;
    private int sessionDuration;
    private String subjectFromWIF;
    private final String wifProvider;
    private final String wifToken;

    public WebIdentityFederationSessionCredentialsProvider(String wifToken2, String wifProvider2, String roleArn2) {
        this(wifToken2, wifProvider2, roleArn2, new ClientConfiguration());
    }

    public WebIdentityFederationSessionCredentialsProvider(String wifToken2, String wifProvider2, String roleArn2, ClientConfiguration clientConfiguration) {
        this(wifToken2, wifProvider2, roleArn2, (AWSSecurityTokenService) new AWSSecurityTokenServiceClient((AWSCredentials) new AnonymousAWSCredentials(), clientConfiguration));
    }

    public WebIdentityFederationSessionCredentialsProvider(String wifToken2, String wifProvider2, String roleArn2, AWSSecurityTokenService stsClient) {
        this.securityTokenService = stsClient;
        this.wifProvider = wifProvider2;
        this.wifToken = wifToken2;
        this.roleArn = roleArn2;
        this.sessionDuration = 3600;
        this.refreshThreshold = 500;
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

    public void setSessionDuration(int sessionDuration2) {
        this.sessionDuration = sessionDuration2;
    }

    public WebIdentityFederationSessionCredentialsProvider withSessionDuration(int sessionDuration2) {
        setSessionDuration(sessionDuration2);
        return this;
    }

    public int getSessionDuration() {
        return this.sessionDuration;
    }

    public void setRefreshThreshold(int refreshThreshold2) {
        this.refreshThreshold = refreshThreshold2;
    }

    public WebIdentityFederationSessionCredentialsProvider withRefreshThreshold(int refreshThreshold2) {
        setRefreshThreshold(refreshThreshold2);
        return this;
    }

    public int getRefreshThreshold() {
        return this.refreshThreshold;
    }

    public String getSubjectFromWIF() {
        return this.subjectFromWIF;
    }

    private void startSession() {
        AssumeRoleWithWebIdentityResult sessionTokenResult = this.securityTokenService.assumeRoleWithWebIdentity(new AssumeRoleWithWebIdentityRequest().withWebIdentityToken(this.wifToken).withProviderId(this.wifProvider).withRoleArn(this.roleArn).withRoleSessionName("ProviderSession").withDurationSeconds(Integer.valueOf(this.sessionDuration)));
        Credentials stsCredentials = sessionTokenResult.getCredentials();
        this.subjectFromWIF = sessionTokenResult.getSubjectFromWebIdentityToken();
        this.sessionCredentials = new BasicSessionCredentials(stsCredentials.getAccessKeyId(), stsCredentials.getSecretAccessKey(), stsCredentials.getSessionToken());
        this.sessionCredentialsExpiration = stsCredentials.getExpiration();
    }

    private boolean needsNewSession() {
        if (this.sessionCredentials != null && this.sessionCredentialsExpiration.getTime() - System.currentTimeMillis() >= ((long) (this.refreshThreshold * 1000))) {
            return false;
        }
        return true;
    }
}
