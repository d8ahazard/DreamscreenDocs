package com.amazonaws.services.securitytoken.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class GetSessionTokenRequest extends AmazonWebServiceRequest implements Serializable {
    private Integer durationSeconds;
    private String serialNumber;
    private String tokenCode;

    public Integer getDurationSeconds() {
        return this.durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds2) {
        this.durationSeconds = durationSeconds2;
    }

    public GetSessionTokenRequest withDurationSeconds(Integer durationSeconds2) {
        this.durationSeconds = durationSeconds2;
        return this;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber2) {
        this.serialNumber = serialNumber2;
    }

    public GetSessionTokenRequest withSerialNumber(String serialNumber2) {
        this.serialNumber = serialNumber2;
        return this;
    }

    public String getTokenCode() {
        return this.tokenCode;
    }

    public void setTokenCode(String tokenCode2) {
        this.tokenCode = tokenCode2;
    }

    public GetSessionTokenRequest withTokenCode(String tokenCode2) {
        this.tokenCode = tokenCode2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getDurationSeconds() != null) {
            sb.append("DurationSeconds: " + getDurationSeconds() + ",");
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
        int hashCode = ((((getDurationSeconds() == null ? 0 : getDurationSeconds().hashCode()) + 31) * 31) + (getSerialNumber() == null ? 0 : getSerialNumber().hashCode())) * 31;
        if (getTokenCode() != null) {
            i = getTokenCode().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetSessionTokenRequest)) {
            return false;
        }
        GetSessionTokenRequest other = (GetSessionTokenRequest) obj;
        if ((other.getDurationSeconds() == null) ^ (getDurationSeconds() == null)) {
            return false;
        }
        if (other.getDurationSeconds() != null && !other.getDurationSeconds().equals(getDurationSeconds())) {
            return false;
        }
        if (other.getSerialNumber() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getSerialNumber() == null)) {
            return false;
        }
        if (other.getSerialNumber() != null && !other.getSerialNumber().equals(getSerialNumber())) {
            return false;
        }
        if (other.getTokenCode() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getTokenCode() == null)) {
            return false;
        }
        if (other.getTokenCode() == null || other.getTokenCode().equals(getTokenCode())) {
            return true;
        }
        return false;
    }
}
