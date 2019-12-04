package com.amazonaws.services.cognitoidentity.model;

import java.io.Serializable;

public class GetIdResult implements Serializable {
    private String identityId;

    public String getIdentityId() {
        return this.identityId;
    }

    public void setIdentityId(String identityId2) {
        this.identityId = identityId2;
    }

    public GetIdResult withIdentityId(String identityId2) {
        this.identityId = identityId2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIdentityId() != null) {
            sb.append("IdentityId: " + getIdentityId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (getIdentityId() == null ? 0 : getIdentityId().hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetIdResult)) {
            return false;
        }
        GetIdResult other = (GetIdResult) obj;
        if ((other.getIdentityId() == null) ^ (getIdentityId() == null)) {
            return false;
        }
        if (other.getIdentityId() == null || other.getIdentityId().equals(getIdentityId())) {
            return true;
        }
        return false;
    }
}
