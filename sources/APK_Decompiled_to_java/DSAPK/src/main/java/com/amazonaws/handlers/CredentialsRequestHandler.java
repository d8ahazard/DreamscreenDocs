package com.amazonaws.handlers;

import com.amazonaws.auth.AWSCredentials;

public abstract class CredentialsRequestHandler extends RequestHandler2 {
    protected AWSCredentials awsCredentials;

    public void setCredentials(AWSCredentials awsCredentials2) {
        this.awsCredentials = awsCredentials2;
    }
}
