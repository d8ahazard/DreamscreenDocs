package com.amazonaws.auth;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClient;
import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityRequest;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityResult;
import com.amazonaws.services.cognitoidentity.model.ResourceNotFoundException;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient;
import com.amazonaws.services.securitytoken.model.AssumeRoleWithWebIdentityRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CognitoCredentialsProvider implements AWSCredentialsProvider {
    public static final int DEFAULT_DURATION_SECONDS = 3600;
    public static final int DEFAULT_THRESHOLD_SECONDS = 500;
    protected String authRoleArn;
    private AmazonCognitoIdentity cib;
    protected ReentrantReadWriteLock credentialsLock;
    protected String customRoleArn;
    private final AWSCognitoIdentityProvider identityProvider;
    protected int refreshThreshold;
    protected AWSSecurityTokenService securityTokenService;
    protected AWSSessionCredentials sessionCredentials;
    protected Date sessionCredentialsExpiration;
    protected int sessionDuration;
    protected String token;
    protected String unauthRoleArn;
    protected boolean useEnhancedFlow;

    public CognitoCredentialsProvider(String accountId, String identityPoolId, String unauthRoleArn2, String authRoleArn2, Regions region) {
        this(accountId, identityPoolId, unauthRoleArn2, authRoleArn2, region, new ClientConfiguration());
    }

    public CognitoCredentialsProvider(String accountId, String identityPoolId, String unauthRoleArn2, String authRoleArn2, Regions region, ClientConfiguration clientConfiguration) {
        AmazonCognitoIdentityClient amazonCognitoIdentityClient = new AmazonCognitoIdentityClient((AWSCredentials) new AnonymousAWSCredentials(), clientConfiguration);
        AWSSecurityTokenServiceClient aWSSecurityTokenServiceClient = (unauthRoleArn2 == null && authRoleArn2 == null) ? null : new AWSSecurityTokenServiceClient((AWSCredentials) new AnonymousAWSCredentials(), clientConfiguration);
        this(accountId, identityPoolId, unauthRoleArn2, authRoleArn2, amazonCognitoIdentityClient, (AWSSecurityTokenService) aWSSecurityTokenServiceClient);
        this.cib.setRegion(Region.getRegion(region));
    }

    public CognitoCredentialsProvider(AWSConfiguration awsConfiguration) {
        this((String) null, getIdentityPoolId(awsConfiguration), (String) null, (String) null, getRegions(awsConfiguration), getClientConfiguration(awsConfiguration));
    }

    private static String getIdentityPoolId(AWSConfiguration awsConfiguration) {
        try {
            return awsConfiguration.optJsonObject("CredentialsProvider").optJSONObject("CognitoIdentity").getJSONObject(awsConfiguration.getConfiguration()).getString("PoolId");
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to read CognitoIdentity please check your setup or awsconfiguration.json file", e);
        }
    }

    private static Regions getRegions(AWSConfiguration awsConfiguration) {
        try {
            return Regions.fromName(awsConfiguration.optJsonObject("CredentialsProvider").optJSONObject("CognitoIdentity").getJSONObject(awsConfiguration.getConfiguration()).getString("Region"));
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to read CognitoIdentity please check your setup or awsconfiguration.json file", e);
        }
    }

    private static ClientConfiguration getClientConfiguration(AWSConfiguration awsConfiguration) {
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setUserAgent(awsConfiguration.getUserAgent());
        return clientConfig;
    }

    public CognitoCredentialsProvider(String identityPoolId, Regions region) {
        this((String) null, identityPoolId, (String) null, (String) null, region, new ClientConfiguration());
    }

    public CognitoCredentialsProvider(String identityPoolId, Regions region, ClientConfiguration clientConfiguration) {
        this((String) null, identityPoolId, (String) null, (String) null, region, clientConfiguration);
    }

    public CognitoCredentialsProvider(String accountId, String identityPoolId, String unauthRoleArn2, String authRoleArn2, AmazonCognitoIdentityClient cibClient, AWSSecurityTokenService stsClient) {
        this.cib = cibClient;
        this.securityTokenService = stsClient;
        this.unauthRoleArn = unauthRoleArn2;
        this.authRoleArn = authRoleArn2;
        this.sessionDuration = 3600;
        this.refreshThreshold = 500;
        this.useEnhancedFlow = unauthRoleArn2 == null && authRoleArn2 == null;
        if (this.useEnhancedFlow) {
            this.identityProvider = new AWSEnhancedCognitoIdentityProvider(accountId, identityPoolId, (AmazonCognitoIdentity) cibClient);
        } else {
            this.identityProvider = new AWSBasicCognitoIdentityProvider(accountId, identityPoolId, (AmazonCognitoIdentity) cibClient);
        }
        this.credentialsLock = new ReentrantReadWriteLock(true);
    }

    public CognitoCredentialsProvider(AWSCognitoIdentityProvider provider, String unauthArn, String authArn) {
        this(provider, unauthArn, authArn, new AWSSecurityTokenServiceClient((AWSCredentials) new AnonymousAWSCredentials(), new ClientConfiguration()));
    }

    public CognitoCredentialsProvider(AWSCognitoIdentityProvider provider, String unauthArn, String authArn, AWSSecurityTokenService stsClient) {
        this.identityProvider = provider;
        this.unauthRoleArn = unauthArn;
        this.authRoleArn = authArn;
        this.securityTokenService = stsClient;
        this.sessionDuration = 3600;
        this.refreshThreshold = 500;
        this.useEnhancedFlow = false;
        this.credentialsLock = new ReentrantReadWriteLock(true);
    }

    public CognitoCredentialsProvider(AWSCognitoIdentityProvider provider, Regions region) {
        this(provider, region, new ClientConfiguration());
    }

    public CognitoCredentialsProvider(AWSCognitoIdentityProvider provider, Regions region, ClientConfiguration clientConfiguration) {
        this(provider, new AmazonCognitoIdentityClient((AWSCredentials) new AnonymousAWSCredentials(), clientConfiguration));
        this.cib.setRegion(Region.getRegion(region));
    }

    public CognitoCredentialsProvider(AWSCognitoIdentityProvider provider, AmazonCognitoIdentityClient cib2) {
        this.cib = cib2;
        this.identityProvider = provider;
        this.unauthRoleArn = null;
        this.authRoleArn = null;
        this.securityTokenService = null;
        this.sessionDuration = 3600;
        this.refreshThreshold = 500;
        this.useEnhancedFlow = true;
        this.credentialsLock = new ReentrantReadWriteLock(true);
    }

    public String getIdentityId() {
        return this.identityProvider.getIdentityId();
    }

    public String getToken() {
        return this.identityProvider.getToken();
    }

    public AWSIdentityProvider getIdentityProvider() {
        return this.identityProvider;
    }

    public void setSessionCredentialsExpiration(Date expiration) {
        this.credentialsLock.writeLock().lock();
        try {
            this.sessionCredentialsExpiration = expiration;
        } finally {
            this.credentialsLock.writeLock().unlock();
        }
    }

    public Date getSessionCredentitalsExpiration() {
        this.credentialsLock.readLock().lock();
        try {
            return this.sessionCredentialsExpiration;
        } finally {
            this.credentialsLock.readLock().unlock();
        }
    }

    public String getIdentityPoolId() {
        return this.identityProvider.getIdentityPoolId();
    }

    public AWSSessionCredentials getCredentials() {
        this.credentialsLock.writeLock().lock();
        try {
            if (needsNewSession()) {
                startSession();
            }
            return this.sessionCredentials;
        } finally {
            this.credentialsLock.writeLock().unlock();
        }
    }

    public void setSessionDuration(int sessionDuration2) {
        this.sessionDuration = sessionDuration2;
    }

    public CognitoCredentialsProvider withSessionDuration(int sessionDuration2) {
        setSessionDuration(sessionDuration2);
        return this;
    }

    public int getSessionDuration() {
        return this.sessionDuration;
    }

    public void setRefreshThreshold(int refreshThreshold2) {
        this.refreshThreshold = refreshThreshold2;
    }

    public CognitoCredentialsProvider withRefreshThreshold(int refreshThreshold2) {
        setRefreshThreshold(refreshThreshold2);
        return this;
    }

    public int getRefreshThreshold() {
        return this.refreshThreshold;
    }

    /* access modifiers changed from: protected */
    public void setIdentityId(String identityId) {
        this.identityProvider.identityChanged(identityId);
    }

    public void setLogins(Map<String, String> logins) {
        this.credentialsLock.writeLock().lock();
        try {
            this.identityProvider.setLogins(logins);
            clearCredentials();
        } finally {
            this.credentialsLock.writeLock().unlock();
        }
    }

    public String getCustomRoleArn() {
        return this.customRoleArn;
    }

    public void setCustomRoleArn(String customRoleArn2) {
        this.customRoleArn = customRoleArn2;
    }

    public AWSCredentialsProvider withLogins(Map<String, String> logins) {
        setLogins(logins);
        return this;
    }

    public Map<String, String> getLogins() {
        return this.identityProvider.getLogins();
    }

    public void refresh() {
        this.credentialsLock.writeLock().lock();
        try {
            startSession();
        } finally {
            this.credentialsLock.writeLock().unlock();
        }
    }

    public void clear() {
        this.credentialsLock.writeLock().lock();
        try {
            clearCredentials();
            setIdentityId(null);
            this.identityProvider.setLogins(new HashMap());
        } finally {
            this.credentialsLock.writeLock().unlock();
        }
    }

    public void clearCredentials() {
        this.credentialsLock.writeLock().lock();
        try {
            this.sessionCredentials = null;
            this.sessionCredentialsExpiration = null;
        } finally {
            this.credentialsLock.writeLock().unlock();
        }
    }

    /* access modifiers changed from: protected */
    public void startSession() {
        try {
            this.token = this.identityProvider.refresh();
        } catch (ResourceNotFoundException e) {
            this.token = retryRefresh();
        } catch (AmazonServiceException ase) {
            if (ase.getErrorCode().equals("ValidationException")) {
                this.token = retryRefresh();
            } else {
                throw ase;
            }
        }
        if (this.useEnhancedFlow) {
            populateCredentialsWithCognito(this.token);
        } else {
            populateCredentialsWithSts(this.token);
        }
    }

    private String retryRefresh() {
        setIdentityId(null);
        this.token = this.identityProvider.refresh();
        return this.token;
    }

    private GetCredentialsForIdentityResult retryGetCredentialsForIdentity() {
        Map<String, String> logins;
        this.token = retryRefresh();
        if (this.token == null || this.token.isEmpty()) {
            logins = getLogins();
        } else {
            logins = new HashMap<>();
            logins.put("cognito-identity.amazonaws.com", this.token);
        }
        return this.cib.getCredentialsForIdentity(new GetCredentialsForIdentityRequest().withIdentityId(getIdentityId()).withLogins(logins).withCustomRoleArn(this.customRoleArn));
    }

    private void populateCredentialsWithCognito(String token2) {
        Map<String, String> logins;
        GetCredentialsForIdentityResult result;
        if (token2 == null || token2.isEmpty()) {
            logins = getLogins();
        } else {
            logins = new HashMap<>();
            logins.put("cognito-identity.amazonaws.com", token2);
        }
        try {
            result = this.cib.getCredentialsForIdentity(new GetCredentialsForIdentityRequest().withIdentityId(getIdentityId()).withLogins(logins).withCustomRoleArn(this.customRoleArn));
        } catch (ResourceNotFoundException e) {
            result = retryGetCredentialsForIdentity();
        } catch (AmazonServiceException ase) {
            if (ase.getErrorCode().equals("ValidationException")) {
                result = retryGetCredentialsForIdentity();
            } else {
                throw ase;
            }
        }
        Credentials credentials = result.getCredentials();
        this.sessionCredentials = new BasicSessionCredentials(credentials.getAccessKeyId(), credentials.getSecretKey(), credentials.getSessionToken());
        setSessionCredentialsExpiration(credentials.getExpiration());
        if (!result.getIdentityId().equals(getIdentityId())) {
            setIdentityId(result.getIdentityId());
        }
    }

    private void populateCredentialsWithSts(String token2) {
        AssumeRoleWithWebIdentityRequest sessionTokenRequest = new AssumeRoleWithWebIdentityRequest().withWebIdentityToken(token2).withRoleArn(this.identityProvider.isAuthenticated() ? this.authRoleArn : this.unauthRoleArn).withRoleSessionName("ProviderSession").withDurationSeconds(Integer.valueOf(this.sessionDuration));
        appendUserAgent(sessionTokenRequest, getUserAgent());
        com.amazonaws.services.securitytoken.model.Credentials stsCredentials = this.securityTokenService.assumeRoleWithWebIdentity(sessionTokenRequest).getCredentials();
        this.sessionCredentials = new BasicSessionCredentials(stsCredentials.getAccessKeyId(), stsCredentials.getSecretAccessKey(), stsCredentials.getSessionToken());
        setSessionCredentialsExpiration(stsCredentials.getExpiration());
    }

    /* access modifiers changed from: protected */
    public boolean needsNewSession() {
        if (this.sessionCredentials == null) {
            return true;
        }
        if (this.sessionCredentialsExpiration.getTime() - (System.currentTimeMillis() - ((long) (SDKGlobalConfiguration.getGlobalTimeOffset() * 1000))) >= ((long) (this.refreshThreshold * 1000))) {
            return false;
        }
        return true;
    }

    private void appendUserAgent(AmazonWebServiceRequest request, String userAgent) {
        request.getRequestClientOptions().appendUserAgent(userAgent);
    }

    /* access modifiers changed from: protected */
    public String getUserAgent() {
        return "";
    }

    public void registerIdentityChangedListener(IdentityChangedListener listener) {
        this.identityProvider.registerIdentityChangedListener(listener);
    }

    public void unregisterIdentityChangedListener(IdentityChangedListener listener) {
        this.identityProvider.unregisterIdentityChangedListener(listener);
    }
}
