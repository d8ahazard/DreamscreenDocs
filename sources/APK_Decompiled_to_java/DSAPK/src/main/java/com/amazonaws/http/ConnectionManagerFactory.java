package com.amazonaws.http;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.mobileconnectors.cognitoauth.util.ClientConstants;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;

class ConnectionManagerFactory {
    private static final int DEFAULT_HTTPS_PORT = 443;
    private static final int DEFAULT_HTTP_PORT = 80;

    ConnectionManagerFactory() {
    }

    public static ThreadSafeClientConnManager createThreadSafeClientConnManager(ClientConfiguration config, HttpParams httpClientParams) {
        ConnManagerParams.setMaxConnectionsPerRoute(httpClientParams, new ConnPerRouteBean(config.getMaxConnections()));
        ConnManagerParams.setMaxTotalConnections(httpClientParams, config.getMaxConnections());
        SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
        sslSocketFactory.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        registry.register(new Scheme(ClientConstants.DOMAIN_SCHEME, sslSocketFactory, DEFAULT_HTTPS_PORT));
        ThreadSafeClientConnManager connectionManager = new ThreadSafeClientConnManager(httpClientParams, registry);
        if (config.useReaper()) {
            IdleConnectionReaper.registerConnectionManager(connectionManager);
        }
        return connectionManager;
    }
}
