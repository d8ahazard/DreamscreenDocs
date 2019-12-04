package com.amazonaws.services.securitytoken.model;

import java.io.Serializable;

public class GetFederationTokenResult implements Serializable {
    private Credentials credentials;
    private FederatedUser federatedUser;
    private Integer packedPolicySize;

    public Credentials getCredentials() {
        return this.credentials;
    }

    public void setCredentials(Credentials credentials2) {
        this.credentials = credentials2;
    }

    public GetFederationTokenResult withCredentials(Credentials credentials2) {
        this.credentials = credentials2;
        return this;
    }

    public FederatedUser getFederatedUser() {
        return this.federatedUser;
    }

    public void setFederatedUser(FederatedUser federatedUser2) {
        this.federatedUser = federatedUser2;
    }

    public GetFederationTokenResult withFederatedUser(FederatedUser federatedUser2) {
        this.federatedUser = federatedUser2;
        return this;
    }

    public Integer getPackedPolicySize() {
        return this.packedPolicySize;
    }

    public void setPackedPolicySize(Integer packedPolicySize2) {
        this.packedPolicySize = packedPolicySize2;
    }

    public GetFederationTokenResult withPackedPolicySize(Integer packedPolicySize2) {
        this.packedPolicySize = packedPolicySize2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getCredentials() != null) {
            sb.append("Credentials: " + getCredentials() + ",");
        }
        if (getFederatedUser() != null) {
            sb.append("FederatedUser: " + getFederatedUser() + ",");
        }
        if (getPackedPolicySize() != null) {
            sb.append("PackedPolicySize: " + getPackedPolicySize());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((getCredentials() == null ? 0 : getCredentials().hashCode()) + 31) * 31) + (getFederatedUser() == null ? 0 : getFederatedUser().hashCode())) * 31;
        if (getPackedPolicySize() != null) {
            i = getPackedPolicySize().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetFederationTokenResult)) {
            return false;
        }
        GetFederationTokenResult other = (GetFederationTokenResult) obj;
        if ((other.getCredentials() == null) ^ (getCredentials() == null)) {
            return false;
        }
        if (other.getCredentials() != null && !other.getCredentials().equals(getCredentials())) {
            return false;
        }
        if (other.getFederatedUser() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getFederatedUser() == null)) {
            return false;
        }
        if (other.getFederatedUser() != null && !other.getFederatedUser().equals(getFederatedUser())) {
            return false;
        }
        if (other.getPackedPolicySize() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getPackedPolicySize() == null)) {
            return false;
        }
        if (other.getPackedPolicySize() == null || other.getPackedPolicySize().equals(getPackedPolicySize())) {
            return true;
        }
        return false;
    }
}
