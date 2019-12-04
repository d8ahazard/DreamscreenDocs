package com.amazonaws.auth;

public class BasicSessionCredentials implements AWSSessionCredentials {
    private final String awsAccessKey;
    private final String awsSecretKey;
    private final String sessionToken;

    public BasicSessionCredentials(String awsAccessKey2, String awsSecretKey2, String sessionToken2) {
        this.awsAccessKey = awsAccessKey2;
        this.awsSecretKey = awsSecretKey2;
        this.sessionToken = sessionToken2;
    }

    public String getAWSAccessKeyId() {
        return this.awsAccessKey;
    }

    public String getAWSSecretKey() {
        return this.awsSecretKey;
    }

    public String getSessionToken() {
        return this.sessionToken;
    }
}
