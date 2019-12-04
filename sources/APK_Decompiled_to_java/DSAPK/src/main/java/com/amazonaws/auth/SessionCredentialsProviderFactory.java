package com.amazonaws.auth;

import com.amazonaws.ClientConfiguration;
import java.util.HashMap;
import java.util.Map;

public class SessionCredentialsProviderFactory {
    private static final Map<Key, STSSessionCredentialsProvider> cache = new HashMap();

    private static final class Key {
        private final String awsAccessKeyId;
        private final String serviceEndpoint;

        public Key(String awsAccessKeyId2, String serviceEndpoint2) {
            this.awsAccessKeyId = awsAccessKeyId2;
            this.serviceEndpoint = serviceEndpoint2;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.awsAccessKeyId == null ? 0 : this.awsAccessKeyId.hashCode()) + 31) * 31;
            if (this.serviceEndpoint != null) {
                i = this.serviceEndpoint.hashCode();
            }
            return hashCode + i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Key other = (Key) obj;
            if (this.awsAccessKeyId == null) {
                if (other.awsAccessKeyId != null) {
                    return false;
                }
            } else if (!this.awsAccessKeyId.equals(other.awsAccessKeyId)) {
                return false;
            }
            if (this.serviceEndpoint == null) {
                if (other.serviceEndpoint != null) {
                    return false;
                }
                return true;
            } else if (!this.serviceEndpoint.equals(other.serviceEndpoint)) {
                return false;
            } else {
                return true;
            }
        }
    }

    public static synchronized STSSessionCredentialsProvider getSessionCredentialsProvider(AWSCredentials longTermCredentials, String serviceEndpoint, ClientConfiguration stsClientConfiguration) {
        STSSessionCredentialsProvider sTSSessionCredentialsProvider;
        synchronized (SessionCredentialsProviderFactory.class) {
            Key key = new Key(longTermCredentials.getAWSAccessKeyId(), serviceEndpoint);
            if (!cache.containsKey(key)) {
                cache.put(key, new STSSessionCredentialsProvider(longTermCredentials, stsClientConfiguration));
            }
            sTSSessionCredentialsProvider = (STSSessionCredentialsProvider) cache.get(key);
        }
        return sTSSessionCredentialsProvider;
    }
}
