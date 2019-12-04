package com.amazonaws.mobileconnectors.lambdainvoker;

import android.content.Context;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.util.ClientContext;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClient;
import java.lang.reflect.Proxy;
import org.json.JSONObject;

public class LambdaInvokerFactory {
    private final ClientContext clientContext;
    private final AWSLambda lambda;

    public static class Builder {
        private AWSConfiguration awsConfig;
        private ClientConfiguration clientConfig;
        private ClientContext clientContext;
        private Context context;
        private AWSLambda lambda;
        private AWSCredentialsProvider provider;
        private Regions region;

        protected Builder() {
        }

        public Builder context(Context context2) {
            this.context = context2;
            return this;
        }

        public Builder region(Regions region2) {
            this.region = region2;
            return this;
        }

        public Builder credentialsProvider(AWSCredentialsProvider provider2) {
            this.provider = provider2;
            return this;
        }

        public Builder clientConfiguration(ClientConfiguration clientConfig2) {
            this.clientConfig = clientConfig2;
            return this;
        }

        public Builder clientContext(ClientContext clientContext2) {
            this.clientContext = clientContext2;
            return this;
        }

        public Builder lambdaClient(AWSLambda lambda2) {
            this.lambda = lambda2;
            return this;
        }

        public Builder awsConfiguration(AWSConfiguration awsConfig2) {
            this.awsConfig = awsConfig2;
            return this;
        }

        public LambdaInvokerFactory build() {
            if (this.clientConfig == null) {
                if (this.context == null) {
                    throw new IllegalArgumentException("Context or ClientContext are required please set using .context(context) or .clientContext(clientContext)");
                }
                this.clientContext = new ClientContext(this.context);
            }
            if (this.lambda == null) {
                if (this.provider == null) {
                    throw new IllegalArgumentException("AWSCredentialsProvider is required please set using .credentialsProvider(creds)");
                } else if (this.clientConfig == null) {
                    this.clientConfig = new ClientConfiguration();
                }
            }
            if (this.awsConfig != null) {
                try {
                    if (this.clientConfig != null) {
                        String userAgent = this.clientConfig.getUserAgent();
                        if (userAgent == null || userAgent.trim().isEmpty()) {
                            this.clientConfig.setUserAgent(this.awsConfig.getUserAgent());
                        } else {
                            this.clientConfig.setUserAgent(userAgent + "/" + this.awsConfig.getUserAgent());
                        }
                    }
                    JSONObject lambdaConfig = this.awsConfig.optJsonObject("LambdaInvoker");
                    if (this.region != null) {
                        this.region = Regions.fromName(lambdaConfig.getString("Region"));
                    }
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to read LambdaInvoker please check your setup or awsconfiguration.json file", e);
                }
            }
            if (this.lambda == null) {
                this.lambda = new AWSLambdaClient(this.provider, this.clientConfig);
            }
            if (this.region != null) {
                this.lambda.setRegion(Region.getRegion(this.region));
            }
            return new LambdaInvokerFactory(this.lambda, this.clientContext);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public LambdaInvokerFactory(Context context, Regions region, AWSCredentialsProvider provider) {
        this(context, region, provider, new ClientConfiguration());
    }

    public LambdaInvokerFactory(Context context, Regions region, AWSCredentialsProvider provider, ClientConfiguration clientConfiguration) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        } else if (provider == null) {
            throw new IllegalArgumentException("provider can't be null");
        } else {
            this.lambda = new AWSLambdaClient(provider, clientConfiguration);
            this.lambda.setRegion(Region.getRegion(region));
            this.clientContext = new ClientContext(context);
        }
    }

    LambdaInvokerFactory(AWSLambda lambda2, ClientContext clientContext2) {
        this.lambda = lambda2;
        this.clientContext = clientContext2;
    }

    public <T> T build(Class<T> interfaceClass) {
        return build(interfaceClass, new LambdaJsonBinder());
    }

    public <T> T build(Class<T> interfaceClass, LambdaDataBinder binder) {
        return interfaceClass.cast(Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new LambdaInvocationHandler(this.lambda, binder, this.clientContext)));
    }

    public ClientContext getClientContext() {
        return this.clientContext;
    }
}
