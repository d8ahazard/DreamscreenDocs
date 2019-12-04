package com.amazonaws.regions;

import com.amazonaws.AmazonWebServiceClient;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import java.util.HashMap;
import java.util.Map;

public final class Region {
    private static final String DEFAULT_DOMAIN = "amazonaws.com";
    private final String domain;
    private final Map<String, Boolean> httpSupport = new HashMap();
    private final Map<String, Boolean> httpsSupport = new HashMap();
    private final String name;
    private final Map<String, String> serviceEndpoints = new HashMap();

    Region(String name2, String domain2) {
        this.name = name2;
        if (domain2 == null || domain2.isEmpty()) {
            this.domain = DEFAULT_DOMAIN;
        } else {
            this.domain = domain2;
        }
    }

    public static Region getRegion(Regions region) {
        return RegionUtils.getRegion(region.getName());
    }

    public static Region getRegion(String regionString) {
        return RegionUtils.getRegion(regionString);
    }

    public String getName() {
        return this.name;
    }

    public String getDomain() {
        return this.domain;
    }

    /* access modifiers changed from: 0000 */
    public Map<String, String> getServiceEndpoints() {
        return this.serviceEndpoints;
    }

    /* access modifiers changed from: 0000 */
    public Map<String, Boolean> getHttpSupport() {
        return this.httpSupport;
    }

    /* access modifiers changed from: 0000 */
    public Map<String, Boolean> getHttpsSupport() {
        return this.httpsSupport;
    }

    public String getServiceEndpoint(String serviceName) {
        return (String) this.serviceEndpoints.get(serviceName);
    }

    public boolean isServiceSupported(String serviceName) {
        return this.serviceEndpoints.containsKey(serviceName);
    }

    public boolean hasHttpsEndpoint(String serviceName) {
        return this.httpsSupport.containsKey(serviceName) && ((Boolean) this.httpsSupport.get(serviceName)).booleanValue();
    }

    public boolean hasHttpEndpoint(String serviceName) {
        return this.httpSupport.containsKey(serviceName) && ((Boolean) this.httpSupport.get(serviceName)).booleanValue();
    }

    public <T extends AmazonWebServiceClient> T createClient(Class<T> serviceClass, AWSCredentialsProvider credentials, ClientConfiguration config) {
        T client;
        if (credentials == null && config == null) {
            try {
                client = (AmazonWebServiceClient) serviceClass.getConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (Exception e) {
                throw new RuntimeException("Couldn't instantiate instance of " + serviceClass, e);
            }
        } else if (credentials == null) {
            client = (AmazonWebServiceClient) serviceClass.getConstructor(new Class[]{ClientConfiguration.class}).newInstance(new Object[]{config});
        } else if (config == null) {
            client = (AmazonWebServiceClient) serviceClass.getConstructor(new Class[]{AWSCredentialsProvider.class}).newInstance(new Object[]{credentials});
        } else {
            client = (AmazonWebServiceClient) serviceClass.getConstructor(new Class[]{AWSCredentialsProvider.class, ClientConfiguration.class}).newInstance(new Object[]{credentials, config});
        }
        client.setRegion(this);
        return client;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Region)) {
            return false;
        }
        return getName().equals(((Region) obj).getName());
    }

    public int hashCode() {
        return getName().hashCode();
    }

    public String toString() {
        return getName();
    }
}
