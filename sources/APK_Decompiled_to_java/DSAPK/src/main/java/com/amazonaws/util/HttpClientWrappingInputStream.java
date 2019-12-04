package com.amazonaws.util;

import com.amazonaws.internal.SdkFilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.client.HttpClient;

class HttpClientWrappingInputStream extends SdkFilterInputStream {
    private final HttpClient client;

    public HttpClientWrappingInputStream(HttpClient client2, InputStream stream) {
        super(stream);
        this.client = client2;
    }

    public void close() throws IOException {
        try {
            super.close();
        } finally {
            this.client.getConnectionManager().shutdown();
        }
    }
}
