package com.amazonaws.auth;

public interface AWSCredentials {
    String getAWSAccessKeyId();

    String getAWSSecretKey();
}
