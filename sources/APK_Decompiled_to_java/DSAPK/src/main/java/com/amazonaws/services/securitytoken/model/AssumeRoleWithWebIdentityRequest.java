package com.amazonaws.services.securitytoken.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class AssumeRoleWithWebIdentityRequest extends AmazonWebServiceRequest implements Serializable {
    private Integer durationSeconds;
    private String policy;
    private String providerId;
    private String roleArn;
    private String roleSessionName;
    private String webIdentityToken;

    public String getRoleArn() {
        return this.roleArn;
    }

    public void setRoleArn(String roleArn2) {
        this.roleArn = roleArn2;
    }

    public AssumeRoleWithWebIdentityRequest withRoleArn(String roleArn2) {
        this.roleArn = roleArn2;
        return this;
    }

    public String getRoleSessionName() {
        return this.roleSessionName;
    }

    public void setRoleSessionName(String roleSessionName2) {
        this.roleSessionName = roleSessionName2;
    }

    public AssumeRoleWithWebIdentityRequest withRoleSessionName(String roleSessionName2) {
        this.roleSessionName = roleSessionName2;
        return this;
    }

    public String getWebIdentityToken() {
        return this.webIdentityToken;
    }

    public void setWebIdentityToken(String webIdentityToken2) {
        this.webIdentityToken = webIdentityToken2;
    }

    public AssumeRoleWithWebIdentityRequest withWebIdentityToken(String webIdentityToken2) {
        this.webIdentityToken = webIdentityToken2;
        return this;
    }

    public String getProviderId() {
        return this.providerId;
    }

    public void setProviderId(String providerId2) {
        this.providerId = providerId2;
    }

    public AssumeRoleWithWebIdentityRequest withProviderId(String providerId2) {
        this.providerId = providerId2;
        return this;
    }

    public String getPolicy() {
        return this.policy;
    }

    public void setPolicy(String policy2) {
        this.policy = policy2;
    }

    public AssumeRoleWithWebIdentityRequest withPolicy(String policy2) {
        this.policy = policy2;
        return this;
    }

    public Integer getDurationSeconds() {
        return this.durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds2) {
        this.durationSeconds = durationSeconds2;
    }

    public AssumeRoleWithWebIdentityRequest withDurationSeconds(Integer durationSeconds2) {
        this.durationSeconds = durationSeconds2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getRoleArn() != null) {
            sb.append("RoleArn: " + getRoleArn() + ",");
        }
        if (getRoleSessionName() != null) {
            sb.append("RoleSessionName: " + getRoleSessionName() + ",");
        }
        if (getWebIdentityToken() != null) {
            sb.append("WebIdentityToken: " + getWebIdentityToken() + ",");
        }
        if (getProviderId() != null) {
            sb.append("ProviderId: " + getProviderId() + ",");
        }
        if (getPolicy() != null) {
            sb.append("Policy: " + getPolicy() + ",");
        }
        if (getDurationSeconds() != null) {
            sb.append("DurationSeconds: " + getDurationSeconds());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((((getRoleArn() == null ? 0 : getRoleArn().hashCode()) + 31) * 31) + (getRoleSessionName() == null ? 0 : getRoleSessionName().hashCode())) * 31) + (getWebIdentityToken() == null ? 0 : getWebIdentityToken().hashCode())) * 31) + (getProviderId() == null ? 0 : getProviderId().hashCode())) * 31) + (getPolicy() == null ? 0 : getPolicy().hashCode())) * 31;
        if (getDurationSeconds() != null) {
            i = getDurationSeconds().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AssumeRoleWithWebIdentityRequest)) {
            return false;
        }
        AssumeRoleWithWebIdentityRequest other = (AssumeRoleWithWebIdentityRequest) obj;
        if ((other.getRoleArn() == null) ^ (getRoleArn() == null)) {
            return false;
        }
        if (other.getRoleArn() != null && !other.getRoleArn().equals(getRoleArn())) {
            return false;
        }
        if (other.getRoleSessionName() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getRoleSessionName() == null)) {
            return false;
        }
        if (other.getRoleSessionName() != null && !other.getRoleSessionName().equals(getRoleSessionName())) {
            return false;
        }
        if (other.getWebIdentityToken() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getWebIdentityToken() == null)) {
            return false;
        }
        if (other.getWebIdentityToken() != null && !other.getWebIdentityToken().equals(getWebIdentityToken())) {
            return false;
        }
        if (other.getProviderId() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3 ^ (getProviderId() == null)) {
            return false;
        }
        if (other.getProviderId() != null && !other.getProviderId().equals(getProviderId())) {
            return false;
        }
        if (other.getPolicy() == null) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (z4 ^ (getPolicy() == null)) {
            return false;
        }
        if (other.getPolicy() != null && !other.getPolicy().equals(getPolicy())) {
            return false;
        }
        if (other.getDurationSeconds() == null) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (z5 ^ (getDurationSeconds() == null)) {
            return false;
        }
        if (other.getDurationSeconds() == null || other.getDurationSeconds().equals(getDurationSeconds())) {
            return true;
        }
        return false;
    }
}
