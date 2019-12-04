package com.amazonaws.auth;

import com.amazonaws.AmazonClientException;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AWSCredentialsProviderChain implements AWSCredentialsProvider {
    private static final Log log = LogFactory.getLog(AWSCredentialsProviderChain.class);
    private List<AWSCredentialsProvider> credentialsProviders = new LinkedList();
    private AWSCredentialsProvider lastUsedProvider;
    private boolean reuseLastProvider = true;

    public AWSCredentialsProviderChain(AWSCredentialsProvider... credentialsProviders2) {
        if (credentialsProviders2 == null || credentialsProviders2.length == 0) {
            throw new IllegalArgumentException("No credential providers specified");
        }
        for (AWSCredentialsProvider provider : credentialsProviders2) {
            this.credentialsProviders.add(provider);
        }
    }

    public boolean getReuseLastProvider() {
        return this.reuseLastProvider;
    }

    public void setReuseLastProvider(boolean b) {
        this.reuseLastProvider = b;
    }

    public AWSCredentials getCredentials() {
        if (this.reuseLastProvider && this.lastUsedProvider != null) {
            return this.lastUsedProvider.getCredentials();
        }
        for (AWSCredentialsProvider provider : this.credentialsProviders) {
            try {
                AWSCredentials credentials = provider.getCredentials();
                if (!(credentials.getAWSAccessKeyId() == null || credentials.getAWSSecretKey() == null)) {
                    log.debug("Loading credentials from " + provider.toString());
                    this.lastUsedProvider = provider;
                    return credentials;
                }
            } catch (Exception e) {
                log.debug("Unable to load credentials from " + provider.toString() + ": " + e.getMessage());
            }
        }
        throw new AmazonClientException("Unable to load AWS credentials from any provider in the chain");
    }

    public void refresh() {
        for (AWSCredentialsProvider provider : this.credentialsProviders) {
            provider.refresh();
        }
    }
}
