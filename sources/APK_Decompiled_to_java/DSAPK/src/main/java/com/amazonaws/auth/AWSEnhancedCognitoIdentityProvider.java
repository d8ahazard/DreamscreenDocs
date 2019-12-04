package com.amazonaws.auth;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClient;

public final class AWSEnhancedCognitoIdentityProvider extends AWSAbstractCognitoIdentityProvider {
    public AWSEnhancedCognitoIdentityProvider(String accountId, String identityPoolId) {
        this(accountId, identityPoolId, new ClientConfiguration());
    }

    public AWSEnhancedCognitoIdentityProvider(String accountId, String identityPoolId, ClientConfiguration clientConfiguration) {
        this(accountId, identityPoolId, (AmazonCognitoIdentity) new AmazonCognitoIdentityClient((AWSCredentials) new AnonymousAWSCredentials(), clientConfiguration));
    }

    public AWSEnhancedCognitoIdentityProvider(String accountId, String identityPoolId, AmazonCognitoIdentity cibClient) {
        super(accountId, identityPoolId, cibClient);
    }

    public String getProviderName() {
        return "Cognito";
    }

    public String refresh() {
        getIdentityId();
        return null;
    }
}
