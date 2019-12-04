package com.amazonaws.auth;

import com.amazonaws.AmazonClientException;
import com.amazonaws.SDKGlobalConfiguration;

@Deprecated
public class SystemPropertiesCredentialsProvider implements AWSCredentialsProvider {
    public AWSCredentials getCredentials() {
        if (System.getProperty(SDKGlobalConfiguration.ACCESS_KEY_SYSTEM_PROPERTY) != null && System.getProperty(SDKGlobalConfiguration.SECRET_KEY_SYSTEM_PROPERTY) != null) {
            return new BasicAWSCredentials(System.getProperty(SDKGlobalConfiguration.ACCESS_KEY_SYSTEM_PROPERTY), System.getProperty(SDKGlobalConfiguration.SECRET_KEY_SYSTEM_PROPERTY));
        }
        throw new AmazonClientException("Unable to load AWS credentials from Java system properties (aws.accessKeyId and aws.secretKey)");
    }

    public void refresh() {
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
