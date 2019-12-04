package com.amazonaws.http;

import java.io.IOException;

public interface HttpClient {
    HttpResponse execute(HttpRequest httpRequest) throws IOException;

    void shutdown();
}
