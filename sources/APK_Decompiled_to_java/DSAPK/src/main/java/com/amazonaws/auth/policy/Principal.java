package com.amazonaws.auth.policy;

public class Principal {
    public static final Principal All = new Principal("*", "*");
    public static final Principal AllServices = new Principal("Service", "*");
    public static final Principal AllUsers = new Principal("AWS", "*");
    public static final Principal AllWebProviders = new Principal("Federated", "*");
    private final String id;
    private final String provider;

    public enum Services {
        AWSDataPipeline("datapipeline.amazonaws.com"),
        AmazonElasticTranscoder("elastictranscoder.amazonaws.com"),
        AmazonEC2("ec2.amazonaws.com"),
        AWSOpsWorks("opsworks.amazonaws.com"),
        AWSCloudHSM("cloudhsm.amazonaws.com"),
        AllServices("*");
        
        private String serviceId;

        private Services(String serviceId2) {
            this.serviceId = serviceId2;
        }

        public String getServiceId() {
            return this.serviceId;
        }

        public static Services fromString(String serviceId2) {
            Services[] values;
            if (serviceId2 != null) {
                for (Services s : values()) {
                    if (s.getServiceId().equalsIgnoreCase(serviceId2)) {
                        return s;
                    }
                }
            }
            return null;
        }
    }

    public enum WebIdentityProviders {
        Facebook("graph.facebook.com"),
        Google("accounts.google.com"),
        Amazon("www.amazon.com"),
        AllProviders("*");
        
        private String webIdentityProvider;

        private WebIdentityProviders(String webIdentityProvider2) {
            this.webIdentityProvider = webIdentityProvider2;
        }

        public String getWebIdentityProvider() {
            return this.webIdentityProvider;
        }

        public static WebIdentityProviders fromString(String webIdentityProvider2) {
            WebIdentityProviders[] values;
            if (webIdentityProvider2 != null) {
                for (WebIdentityProviders provider : values()) {
                    if (provider.getWebIdentityProvider().equalsIgnoreCase(webIdentityProvider2)) {
                        return provider;
                    }
                }
            }
            return null;
        }
    }

    public Principal(Services service) {
        if (service == null) {
            throw new IllegalArgumentException("Null AWS service name specified");
        }
        this.id = service.getServiceId();
        this.provider = "Service";
    }

    public Principal(String provider2, String id2) {
        this.provider = provider2;
        if ("AWS".equals(provider2)) {
            id2 = id2.replaceAll("-", "");
        }
        this.id = id2;
    }

    public Principal(String accountId) {
        if (accountId == null) {
            throw new IllegalArgumentException("Null AWS account ID specified");
        }
        this.id = accountId.replaceAll("-", "");
        this.provider = "AWS";
    }

    public Principal(WebIdentityProviders webIdentityProvider) {
        if (webIdentityProvider == null) {
            throw new IllegalArgumentException("Null web identity provider specified");
        }
        this.id = webIdentityProvider.getWebIdentityProvider();
        this.provider = "Federated";
    }

    public String getProvider() {
        return this.provider;
    }

    public String getId() {
        return this.id;
    }

    public int hashCode() {
        return ((this.provider.hashCode() + 31) * 31) + this.id.hashCode();
    }

    public boolean equals(Object principal) {
        if (this == principal) {
            return true;
        }
        if (principal == null) {
            return false;
        }
        if (!(principal instanceof Principal)) {
            return false;
        }
        Principal other = (Principal) principal;
        if (!getProvider().equals(other.getProvider()) || !getId().equals(other.getId())) {
            return false;
        }
        return true;
    }
}
