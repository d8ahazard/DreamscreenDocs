package com.amazonaws.services.securitytoken.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class AssumeRoleRequest extends AmazonWebServiceRequest implements Serializable {
    private Integer durationSeconds;
    private String externalId;
    private String policy;
    private String roleArn;
    private String roleSessionName;
    private String serialNumber;
    private String tokenCode;

    public String getRoleArn() {
        return this.roleArn;
    }

    public void setRoleArn(String roleArn2) {
        this.roleArn = roleArn2;
    }

    public AssumeRoleRequest withRoleArn(String roleArn2) {
        this.roleArn = roleArn2;
        return this;
    }

    public String getRoleSessionName() {
        return this.roleSessionName;
    }

    public void setRoleSessionName(String roleSessionName2) {
        this.roleSessionName = roleSessionName2;
    }

    public AssumeRoleRequest withRoleSessionName(String roleSessionName2) {
        this.roleSessionName = roleSessionName2;
        return this;
    }

    public String getPolicy() {
        return this.policy;
    }

    public void setPolicy(String policy2) {
        this.policy = policy2;
    }

    public AssumeRoleRequest withPolicy(String policy2) {
        this.policy = policy2;
        return this;
    }

    public Integer getDurationSeconds() {
        return this.durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds2) {
        this.durationSeconds = durationSeconds2;
    }

    public AssumeRoleRequest withDurationSeconds(Integer durationSeconds2) {
        this.durationSeconds = durationSeconds2;
        return this;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public void setExternalId(String externalId2) {
        this.externalId = externalId2;
    }

    public AssumeRoleRequest withExternalId(String externalId2) {
        this.externalId = externalId2;
        return this;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber2) {
        this.serialNumber = serialNumber2;
    }

    public AssumeRoleRequest withSerialNumber(String serialNumber2) {
        this.serialNumber = serialNumber2;
        return this;
    }

    public String getTokenCode() {
        return this.tokenCode;
    }

    public void setTokenCode(String tokenCode2) {
        this.tokenCode = tokenCode2;
    }

    public AssumeRoleRequest withTokenCode(String tokenCode2) {
        this.tokenCode = tokenCode2;
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
        if (getPolicy() != null) {
            sb.append("Policy: " + getPolicy() + ",");
        }
        if (getDurationSeconds() != null) {
            sb.append("DurationSeconds: " + getDurationSeconds() + ",");
        }
        if (getExternalId() != null) {
            sb.append("ExternalId: " + getExternalId() + ",");
        }
        if (getSerialNumber() != null) {
            sb.append("SerialNumber: " + getSerialNumber() + ",");
        }
        if (getTokenCode() != null) {
            sb.append("TokenCode: " + getTokenCode());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((((((getRoleArn() == null ? 0 : getRoleArn().hashCode()) + 31) * 31) + (getRoleSessionName() == null ? 0 : getRoleSessionName().hashCode())) * 31) + (getPolicy() == null ? 0 : getPolicy().hashCode())) * 31) + (getDurationSeconds() == null ? 0 : getDurationSeconds().hashCode())) * 31) + (getExternalId() == null ? 0 : getExternalId().hashCode())) * 31) + (getSerialNumber() == null ? 0 : getSerialNumber().hashCode())) * 31;
        if (getTokenCode() != null) {
            i = getTokenCode().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AssumeRoleRequest)) {
            return false;
        }
        AssumeRoleRequest other = (AssumeRoleRequest) obj;
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
        if (other.getPolicy() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getPolicy() == null)) {
            return false;
        }
        if (other.getPolicy() != null && !other.getPolicy().equals(getPolicy())) {
            return false;
        }
        if (other.getDurationSeconds() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3 ^ (getDurationSeconds() == null)) {
            return false;
        }
        if (other.getDurationSeconds() != null && !other.getDurationSeconds().equals(getDurationSeconds())) {
            return false;
        }
        if (other.getExternalId() == null) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (z4 ^ (getExternalId() == null)) {
            return false;
        }
        if (other.getExternalId() != null && !other.getExternalId().equals(getExternalId())) {
            return false;
        }
        if (other.getSerialNumber() == null) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (z5 ^ (getSerialNumber() == null)) {
            return false;
        }
        if (other.getSerialNumber() != null && !other.getSerialNumber().equals(getSerialNumber())) {
            return false;
        }
        if (other.getTokenCode() == null) {
            z6 = true;
        } else {
            z6 = false;
        }
        if (z6 ^ (getTokenCode() == null)) {
            return false;
        }
        if (other.getTokenCode() == null || other.getTokenCode().equals(getTokenCode())) {
            return true;
        }
        return false;
    }
}
