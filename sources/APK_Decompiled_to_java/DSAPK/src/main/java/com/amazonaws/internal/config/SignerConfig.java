package com.amazonaws.internal.config;

public class SignerConfig {
    private final String signerType;

    SignerConfig(String signerType2) {
        this.signerType = signerType2;
    }

    SignerConfig(SignerConfig from) {
        this.signerType = from.getSignerType();
    }

    public String getSignerType() {
        return this.signerType;
    }

    public String toString() {
        return this.signerType;
    }
}
