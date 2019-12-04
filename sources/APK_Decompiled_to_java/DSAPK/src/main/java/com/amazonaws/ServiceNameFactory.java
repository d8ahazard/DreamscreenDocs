package com.amazonaws;

import com.amazonaws.internal.config.HttpClientConfig;
import com.amazonaws.internal.config.InternalConfig.Factory;

enum ServiceNameFactory {
    ;

    static String getServiceName(String httpClientName) {
        HttpClientConfig clientConfig = Factory.getInternalConfig().getHttpClientConfig(httpClientName);
        if (clientConfig == null) {
            return null;
        }
        return clientConfig.getServiceName();
    }
}
