package com.amazonaws.services.securitytoken.model;

import java.io.Serializable;

public class FederatedUser implements Serializable {
    private String arn;
    private String federatedUserId;

    public FederatedUser() {
    }

    public FederatedUser(String federatedUserId2, String arn2) {
        setFederatedUserId(federatedUserId2);
        setArn(arn2);
    }

    public String getFederatedUserId() {
        return this.federatedUserId;
    }

    public void setFederatedUserId(String federatedUserId2) {
        this.federatedUserId = federatedUserId2;
    }

    public FederatedUser withFederatedUserId(String federatedUserId2) {
        this.federatedUserId = federatedUserId2;
        return this;
    }

    public String getArn() {
        return this.arn;
    }

    public void setArn(String arn2) {
        this.arn = arn2;
    }

    public FederatedUser withArn(String arn2) {
        this.arn = arn2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getFederatedUserId() != null) {
            sb.append("FederatedUserId: " + getFederatedUserId() + ",");
        }
        if (getArn() != null) {
            sb.append("Arn: " + getArn());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((getFederatedUserId() == null ? 0 : getFederatedUserId().hashCode()) + 31) * 31;
        if (getArn() != null) {
            i = getArn().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof FederatedUser)) {
            return false;
        }
        FederatedUser other = (FederatedUser) obj;
        if ((other.getFederatedUserId() == null) ^ (getFederatedUserId() == null)) {
            return false;
        }
        if (other.getFederatedUserId() != null && !other.getFederatedUserId().equals(getFederatedUserId())) {
            return false;
        }
        if (other.getArn() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getArn() == null)) {
            return false;
        }
        if (other.getArn() == null || other.getArn().equals(getArn())) {
            return true;
        }
        return false;
    }
}
