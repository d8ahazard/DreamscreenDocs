package com.amazonaws.auth;

public interface RegionAwareSigner extends Signer {
    void setRegionName(String str);
}
