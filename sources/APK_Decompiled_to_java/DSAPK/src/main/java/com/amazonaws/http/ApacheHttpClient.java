package com.amazonaws.http;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.http.HttpResponse.Builder;
import com.amazonaws.mobileconnectors.cognitoauth.util.ClientConstants;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

public class ApacheHttpClient implements HttpClient {
    private final HttpClient httpClient;
    private HttpParams params = null;

    public ApacheHttpClient(ClientConfiguration config) {
        this.httpClient = new HttpClientFactory().createHttpClient(config);
        this.httpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        ((SSLSocketFactory) this.httpClient.getConnectionManager().getSchemeRegistry().getScheme(ClientConstants.DOMAIN_SCHEME).getSocketFactory()).setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public HttpResponse execute(HttpRequest request) throws IOException {
        Header[] allHeaders;
        HttpResponse httpResponse = this.httpClient.execute(createHttpRequest(request));
        String statusText = httpResponse.getStatusLine().getReasonPhrase();
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        InputStream content = null;
        if (httpResponse.getEntity() != null) {
            content = httpResponse.getEntity().getContent();
        }
        Builder builder = HttpResponse.builder().statusCode(statusCode).statusText(statusText).content(content);
        for (Header header : httpResponse.getAllHeaders()) {
            builder.header(header.getName(), header.getValue());
        }
        return builder.build();
    }

    public void shutdown() {
        this.httpClient.getConnectionManager().shutdown();
    }

    private HttpUriRequest createHttpRequest(HttpRequest request) {
        HttpHead httpHead;
        String method = request.getMethod();
        if (ClientConstants.HTTP_REQUEST_TYPE_POST.equals(method)) {
            HttpHead httpPost = new HttpPost(request.getUri());
            if (request.getContent() != null) {
                httpPost.setEntity(new InputStreamEntity(request.getContent(), request.getContentLength()));
            }
            httpHead = httpPost;
        } else if ("GET".equals(method)) {
            httpHead = new HttpGet(request.getUri());
        } else if ("PUT".equals(method)) {
            HttpHead httpPut = new HttpPut(request.getUri());
            if (request.getContent() != null) {
                httpPut.setEntity(new InputStreamEntity(request.getContent(), request.getContentLength()));
            }
            httpHead = httpPut;
        } else if ("DELETE".equals(method)) {
            httpHead = new HttpDelete(request.getUri());
        } else if ("HEAD".equals(method)) {
            httpHead = new HttpHead(request.getUri());
        } else {
            throw new UnsupportedOperationException("Unsupported method: " + method);
        }
        if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
            for (Entry<String, String> header : request.getHeaders().entrySet()) {
                String key = (String) header.getKey();
                if (!key.equals(HttpHeader.CONTENT_LENGTH) && !key.equals(HttpHeader.HOST)) {
                    httpHead.addHeader((String) header.getKey(), (String) header.getValue());
                }
            }
        }
        if (this.params == null) {
            this.params = new BasicHttpParams();
            this.params.setParameter("http.protocol.handle-redirects", Boolean.valueOf(false));
        }
        httpHead.setParams(this.params);
        return httpHead;
    }
}
