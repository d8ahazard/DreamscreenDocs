package com.amazonaws.auth;

public enum SignatureVersion {
    V1("1"),
    V2("2");
    
    private String value;

    private SignatureVersion(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }
}
