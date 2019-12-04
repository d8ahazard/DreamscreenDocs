package com.amazonaws.auth;

public interface AWSIdentityProvider {
    String getToken();

    String refresh();
}
