package com.amazonaws.auth;

public interface AWSCredentialsProvider {
    AWSCredentials getCredentials();

    void refresh();
}
