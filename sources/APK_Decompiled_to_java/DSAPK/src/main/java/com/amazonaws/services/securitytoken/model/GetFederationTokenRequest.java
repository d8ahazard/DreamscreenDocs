package com.amazonaws.services.securitytoken.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class GetFederationTokenRequest extends AmazonWebServiceRequest implements Serializable {
    private Integer durationSeconds;
    private String name;
    private String policy;

    public GetFederationTokenRequest() {
    }

    public GetFederationTokenRequest(String name2) {
        setName(name2);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public GetFederationTokenRequest withName(String name2) {
        this.name = name2;
        return this;
    }

    public String getPolicy() {
        return this.policy;
    }

    public void setPolicy(String policy2) {
        this.policy = policy2;
    }

    public GetFederationTokenRequest withPolicy(String policy2) {
        this.policy = policy2;
        return this;
    }

    public Integer getDurationSeconds() {
        return this.durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds2) {
        this.durationSeconds = durationSeconds2;
    }

    public GetFederationTokenRequest withDurationSeconds(Integer durationSeconds2) {
        this.durationSeconds = durationSeconds2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getName() != null) {
            sb.append("Name: " + getName() + ",");
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
        int hashCode = ((((getName() == null ? 0 : getName().hashCode()) + 31) * 31) + (getPolicy() == null ? 0 : getPolicy().hashCode())) * 31;
        if (getDurationSeconds() != null) {
            i = getDurationSeconds().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetFederationTokenRequest)) {
            return false;
        }
        GetFederationTokenRequest other = (GetFederationTokenRequest) obj;
        if ((other.getName() == null) ^ (getName() == null)) {
            return false;
        }
        if (other.getName() != null && !other.getName().equals(getName())) {
            return false;
        }
        if (other.getPolicy() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getPolicy() == null)) {
            return false;
        }
        if (other.getPolicy() != null && !other.getPolicy().equals(getPolicy())) {
            return false;
        }
        if (other.getDurationSeconds() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getDurationSeconds() == null)) {
            return false;
        }
        if (other.getDurationSeconds() == null || other.getDurationSeconds().equals(getDurationSeconds())) {
            return true;
        }
        return false;
    }
}
