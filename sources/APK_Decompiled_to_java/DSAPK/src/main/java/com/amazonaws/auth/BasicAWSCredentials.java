package com.amazonaws.auth;

public class BasicAWSCredentials implements AWSCredentials {
    private final String accessKey;
    private final String secretKey;

    public BasicAWSCredentials(String accessKey2, String secretKey2) {
        if (accessKey2 == null) {
            throw new IllegalArgumentException("Access key cannot be null.");
        } else if (secretKey2 == null) {
            throw new IllegalArgumentException("Secret key cannot be null.");
        } else {
            this.accessKey = accessKey2;
            this.secretKey = secretKey2;
        }
    }

    public String getAWSAccessKeyId() {
        return this.accessKey;
    }

    public String getAWSSecretKey() {
        return this.secretKey;
    }
}
