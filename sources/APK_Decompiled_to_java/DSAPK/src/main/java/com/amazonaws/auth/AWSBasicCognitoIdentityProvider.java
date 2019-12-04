package com.amazonaws.auth;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClient;

public class AWSBasicCognitoIdentityProvider extends AWSAbstractCognitoIdentityProvider {
    public AWSBasicCognitoIdentityProvider(String accountId, String identityPoolId) {
        this(accountId, identityPoolId, new ClientConfiguration());
    }

    public AWSBasicCognitoIdentityProvider(String accountId, String identityPoolId, ClientConfiguration clientConfiguration) {
        this(accountId, identityPoolId, (AmazonCognitoIdentity) new AmazonCognitoIdentityClient((AWSCredentials) new AnonymousAWSCredentials(), clientConfiguration));
    }

    public AWSBasicCognitoIdentityProvider(String accountId, String identityPoolId, AmazonCognitoIdentity cibClient) {
        super(accountId, identityPoolId, cibClient);
    }

    public String getProviderName() {
        return "Cognito";
    }

    public String refresh() {
        setToken(null);
        return super.refresh();
    }
}
