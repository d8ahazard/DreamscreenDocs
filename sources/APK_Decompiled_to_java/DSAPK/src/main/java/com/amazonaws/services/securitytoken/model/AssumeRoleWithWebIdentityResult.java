package com.amazonaws.services.securitytoken.model;

import java.io.Serializable;

public class AssumeRoleWithWebIdentityResult implements Serializable {
    private AssumedRoleUser assumedRoleUser;
    private String audience;
    private Credentials credentials;
    private Integer packedPolicySize;
    private String provider;
    private String subjectFromWebIdentityToken;

    public Credentials getCredentials() {
        return this.credentials;
    }

    public void setCredentials(Credentials credentials2) {
        this.credentials = credentials2;
    }

    public AssumeRoleWithWebIdentityResult withCredentials(Credentials credentials2) {
        this.credentials = credentials2;
        return this;
    }

    public String getSubjectFromWebIdentityToken() {
        return this.subjectFromWebIdentityToken;
    }

    public void setSubjectFromWebIdentityToken(String subjectFromWebIdentityToken2) {
        this.subjectFromWebIdentityToken = subjectFromWebIdentityToken2;
    }

    public AssumeRoleWithWebIdentityResult withSubjectFromWebIdentityToken(String subjectFromWebIdentityToken2) {
        this.subjectFromWebIdentityToken = subjectFromWebIdentityToken2;
        return this;
    }

    public AssumedRoleUser getAssumedRoleUser() {
        return this.assumedRoleUser;
    }

    public void setAssumedRoleUser(AssumedRoleUser assumedRoleUser2) {
        this.assumedRoleUser = assumedRoleUser2;
    }

    public AssumeRoleWithWebIdentityResult withAssumedRoleUser(AssumedRoleUser assumedRoleUser2) {
        this.assumedRoleUser = assumedRoleUser2;
        return this;
    }

    public Integer getPackedPolicySize() {
        return this.packedPolicySize;
    }

    public void setPackedPolicySize(Integer packedPolicySize2) {
        this.packedPolicySize = packedPolicySize2;
    }

    public AssumeRoleWithWebIdentityResult withPackedPolicySize(Integer packedPolicySize2) {
        this.packedPolicySize = packedPolicySize2;
        return this;
    }

    public String getProvider() {
        return this.provider;
    }

    public void setProvider(String provider2) {
        this.provider = provider2;
    }

    public AssumeRoleWithWebIdentityResult withProvider(String provider2) {
        this.provider = provider2;
        return this;
    }

    public String getAudience() {
        return this.audience;
    }

    public void setAudience(String audience2) {
        this.audience = audience2;
    }

    public AssumeRoleWithWebIdentityResult withAudience(String audience2) {
        this.audience = audience2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getCredentials() != null) {
            sb.append("Credentials: " + getCredentials() + ",");
        }
        if (getSubjectFromWebIdentityToken() != null) {
            sb.append("SubjectFromWebIdentityToken: " + getSubjectFromWebIdentityToken() + ",");
        }
        if (getAssumedRoleUser() != null) {
            sb.append("AssumedRoleUser: " + getAssumedRoleUser() + ",");
        }
        if (getPackedPolicySize() != null) {
            sb.append("PackedPolicySize: " + getPackedPolicySize() + ",");
        }
        if (getProvider() != null) {
            sb.append("Provider: " + getProvider() + ",");
        }
        if (getAudience() != null) {
            sb.append("Audience: " + getAudience());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        int hashCode2 = ((getCredentials() == null ? 0 : getCredentials().hashCode()) + 31) * 31;
        if (getSubjectFromWebIdentityToken() == null) {
            hashCode = 0;
        } else {
            hashCode = getSubjectFromWebIdentityToken().hashCode();
        }
        int hashCode3 = (((((((hashCode2 + hashCode) * 31) + (getAssumedRoleUser() == null ? 0 : getAssumedRoleUser().hashCode())) * 31) + (getPackedPolicySize() == null ? 0 : getPackedPolicySize().hashCode())) * 31) + (getProvider() == null ? 0 : getProvider().hashCode())) * 31;
        if (getAudience() != null) {
            i = getAudience().hashCode();
        }
        return hashCode3 + i;
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
        if (obj == null || !(obj instanceof AssumeRoleWithWebIdentityResult)) {
            return false;
        }
        AssumeRoleWithWebIdentityResult other = (AssumeRoleWithWebIdentityResult) obj;
        if ((other.getCredentials() == null) ^ (getCredentials() == null)) {
            return false;
        }
        if (other.getCredentials() != null && !other.getCredentials().equals(getCredentials())) {
            return false;
        }
        if (other.getSubjectFromWebIdentityToken() == null) {
            z = true;
        } else {
            z = false;
        }
        if (getSubjectFromWebIdentityToken() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z ^ z2) {
            return false;
        }
        if (other.getSubjectFromWebIdentityToken() != null && !other.getSubjectFromWebIdentityToken().equals(getSubjectFromWebIdentityToken())) {
            return false;
        }
        if (other.getAssumedRoleUser() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3 ^ (getAssumedRoleUser() == null)) {
            return false;
        }
        if (other.getAssumedRoleUser() != null && !other.getAssumedRoleUser().equals(getAssumedRoleUser())) {
            return false;
        }
        if (other.getPackedPolicySize() == null) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (z4 ^ (getPackedPolicySize() == null)) {
            return false;
        }
        if (other.getPackedPolicySize() != null && !other.getPackedPolicySize().equals(getPackedPolicySize())) {
            return false;
        }
        if (other.getProvider() == null) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (z5 ^ (getProvider() == null)) {
            return false;
        }
        if (other.getProvider() != null && !other.getProvider().equals(getProvider())) {
            return false;
        }
        if (other.getAudience() == null) {
            z6 = true;
        } else {
            z6 = false;
        }
        if (z6 ^ (getAudience() == null)) {
            return false;
        }
        if (other.getAudience() == null || other.getAudience().equals(getAudience())) {
            return true;
        }
        return false;
    }
}
