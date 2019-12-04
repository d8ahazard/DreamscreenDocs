package com.amazonaws.auth;

public interface AWSRefreshableSessionCredentials extends AWSSessionCredentials {
    void refreshCredentials();
}
