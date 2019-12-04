package com.amazonaws.util;

import com.amazonaws.AmazonWebServiceClient;
import com.amazonaws.internal.SdkFilterInputStream;
import java.io.InputStream;

public class ServiceClientHolderInputStream extends SdkFilterInputStream {
    private AmazonWebServiceClient client;

    public ServiceClientHolderInputStream(InputStream in, AmazonWebServiceClient client2) {
        super(in);
        this.client = client2;
    }
}
