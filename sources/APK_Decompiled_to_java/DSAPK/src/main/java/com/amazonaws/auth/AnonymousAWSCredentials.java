package com.amazonaws.auth;

public class AnonymousAWSCredentials implements AWSCredentials {
    public String getAWSAccessKeyId() {
        return null;
    }

    public String getAWSSecretKey() {
        return null;
    }
}
