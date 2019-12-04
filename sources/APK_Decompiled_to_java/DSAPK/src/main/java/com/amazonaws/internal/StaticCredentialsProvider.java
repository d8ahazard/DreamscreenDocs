package com.amazonaws.internal;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;

public class StaticCredentialsProvider implements AWSCredentialsProvider {
    private final AWSCredentials credentials;

    public StaticCredentialsProvider(AWSCredentials credentials2) {
        this.credentials = credentials2;
    }

    public AWSCredentials getCredentials() {
        return this.credentials;
    }

    public void refresh() {
    }
}
