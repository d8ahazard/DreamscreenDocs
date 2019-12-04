package com.amazonaws;

public class AmazonWebServiceResponse<T> {
    private ResponseMetadata responseMetadata;
    private T result;

    public T getResult() {
        return this.result;
    }

    public void setResult(T result2) {
        this.result = result2;
    }

    public void setResponseMetadata(ResponseMetadata responseMetadata2) {
        this.responseMetadata = responseMetadata2;
    }

    public ResponseMetadata getResponseMetadata() {
        return this.responseMetadata;
    }

    public String getRequestId() {
        if (this.responseMetadata == null) {
            return null;
        }
        return this.responseMetadata.getRequestId();
    }
}
