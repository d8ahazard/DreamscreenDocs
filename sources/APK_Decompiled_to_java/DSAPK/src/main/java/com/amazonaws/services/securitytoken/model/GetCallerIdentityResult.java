package com.amazonaws.services.securitytoken.model;

import java.io.Serializable;

public class GetCallerIdentityResult implements Serializable {
    private String account;
    private String arn;
    private String userId;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId2) {
        this.userId = userId2;
    }

    public GetCallerIdentityResult withUserId(String userId2) {
        this.userId = userId2;
        return this;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account2) {
        this.account = account2;
    }

    public GetCallerIdentityResult withAccount(String account2) {
        this.account = account2;
        return this;
    }

    public String getArn() {
        return this.arn;
    }

    public void setArn(String arn2) {
        this.arn = arn2;
    }

    public GetCallerIdentityResult withArn(String arn2) {
        this.arn = arn2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getUserId() != null) {
            sb.append("UserId: " + getUserId() + ",");
        }
        if (getAccount() != null) {
            sb.append("Account: " + getAccount() + ",");
        }
        if (getArn() != null) {
            sb.append("Arn: " + getArn());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((getUserId() == null ? 0 : getUserId().hashCode()) + 31) * 31) + (getAccount() == null ? 0 : getAccount().hashCode())) * 31;
        if (getArn() != null) {
            i = getArn().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetCallerIdentityResult)) {
            return false;
        }
        GetCallerIdentityResult other = (GetCallerIdentityResult) obj;
        if ((other.getUserId() == null) ^ (getUserId() == null)) {
            return false;
        }
        if (other.getUserId() != null && !other.getUserId().equals(getUserId())) {
            return false;
        }
        if (other.getAccount() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getAccount() == null)) {
            return false;
        }
        if (other.getAccount() != null && !other.getAccount().equals(getAccount())) {
            return false;
        }
        if (other.getArn() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getArn() == null)) {
            return false;
        }
        if (other.getArn() == null || other.getArn().equals(getArn())) {
            return true;
        }
        return false;
    }
}
