package com.amazonaws.auth;

public interface AWSSessionCredentials extends AWSCredentials {
    String getSessionToken();
}
