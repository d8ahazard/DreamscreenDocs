package com.amazonaws.http.impl.client;

import com.amazonaws.http.conn.ClientConnectionManagerFactory;
import com.amazonaws.http.protocol.SdkHttpRequestExecutor;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpRequestExecutor;

public class SdkHttpClient extends DefaultHttpClient {
    public SdkHttpClient(ClientConnectionManager conman, HttpParams params) {
        super(ClientConnectionManagerFactory.wrap(conman), params);
    }

    /* access modifiers changed from: protected */
    public HttpRequestExecutor createRequestExecutor() {
        return new SdkHttpRequestExecutor();
    }
}
