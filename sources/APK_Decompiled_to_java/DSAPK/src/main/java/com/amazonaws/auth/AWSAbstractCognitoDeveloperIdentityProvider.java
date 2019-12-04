package com.amazonaws.auth;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClient;

public abstract class AWSAbstractCognitoDeveloperIdentityProvider extends AWSAbstractCognitoIdentityProvider {
    public abstract String getProviderName();

    @Deprecated
    public AWSAbstractCognitoDeveloperIdentityProvider(String accountId, String identityPoolId) {
        this(accountId, identityPoolId, new ClientConfiguration());
    }

    public AWSAbstractCognitoDeveloperIdentityProvider(String accountId, String identityPoolId, Regions region) {
        this(accountId, identityPoolId, new ClientConfiguration(), region);
    }

    @Deprecated
    public AWSAbstractCognitoDeveloperIdentityProvider(String accountId, String identityPoolId, ClientConfiguration clientConfiguration) {
        this(accountId, identityPoolId, (AmazonCognitoIdentity) new AmazonCognitoIdentityClient((AWSCredentials) new AnonymousAWSCredentials(), clientConfiguration));
    }

    public AWSAbstractCognitoDeveloperIdentityProvider(String accountId, String identityPoolId, ClientConfiguration clientConfiguration, Regions region) {
        this(accountId, identityPoolId, (AmazonCognitoIdentity) new AmazonCognitoIdentityClient((AWSCredentials) new AnonymousAWSCredentials(), clientConfiguration));
        this.cib.setRegion(Region.getRegion(region));
    }

    public AWSAbstractCognitoDeveloperIdentityProvider(String accountId, String identityPoolId, AmazonCognitoIdentity cibClient) {
        super(accountId, identityPoolId, cibClient);
    }
}
