package com.amazonaws.services.securitytoken.model;

import java.io.Serializable;

public class AssumedRoleUser implements Serializable {
    private String arn;
    private String assumedRoleId;

    public String getAssumedRoleId() {
        return this.assumedRoleId;
    }

    public void setAssumedRoleId(String assumedRoleId2) {
        this.assumedRoleId = assumedRoleId2;
    }

    public AssumedRoleUser withAssumedRoleId(String assumedRoleId2) {
        this.assumedRoleId = assumedRoleId2;
        return this;
    }

    public String getArn() {
        return this.arn;
    }

    public void setArn(String arn2) {
        this.arn = arn2;
    }

    public AssumedRoleUser withArn(String arn2) {
        this.arn = arn2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAssumedRoleId() != null) {
            sb.append("AssumedRoleId: " + getAssumedRoleId() + ",");
        }
        if (getArn() != null) {
            sb.append("Arn: " + getArn());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((getAssumedRoleId() == null ? 0 : getAssumedRoleId().hashCode()) + 31) * 31;
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
        if (obj == null || !(obj instanceof AssumedRoleUser)) {
            return false;
        }
        AssumedRoleUser other = (AssumedRoleUser) obj;
        if ((other.getAssumedRoleId() == null) ^ (getAssumedRoleId() == null)) {
            return false;
        }
        if (other.getAssumedRoleId() != null && !other.getAssumedRoleId().equals(getAssumedRoleId())) {
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
