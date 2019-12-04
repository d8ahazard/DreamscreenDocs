package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GetCredentialsForIdentityRequest extends AmazonWebServiceRequest implements Serializable {
    private String customRoleArn;
    private String identityId;
    private Map<String, String> logins;

    public String getIdentityId() {
        return this.identityId;
    }

    public void setIdentityId(String identityId2) {
        this.identityId = identityId2;
    }

    public GetCredentialsForIdentityRequest withIdentityId(String identityId2) {
        this.identityId = identityId2;
        return this;
    }

    public Map<String, String> getLogins() {
        return this.logins;
    }

    public void setLogins(Map<String, String> logins2) {
        this.logins = logins2;
    }

    public GetCredentialsForIdentityRequest withLogins(Map<String, String> logins2) {
        this.logins = logins2;
        return this;
    }

    public GetCredentialsForIdentityRequest addLoginsEntry(String key, String value) {
        if (this.logins == null) {
            this.logins = new HashMap();
        }
        if (this.logins.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
        }
        this.logins.put(key, value);
        return this;
    }

    public GetCredentialsForIdentityRequest clearLoginsEntries() {
        this.logins = null;
        return this;
    }

    public String getCustomRoleArn() {
        return this.customRoleArn;
    }

    public void setCustomRoleArn(String customRoleArn2) {
        this.customRoleArn = customRoleArn2;
    }

    public GetCredentialsForIdentityRequest withCustomRoleArn(String customRoleArn2) {
        this.customRoleArn = customRoleArn2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIdentityId() != null) {
            sb.append("IdentityId: " + getIdentityId() + ",");
        }
        if (getLogins() != null) {
            sb.append("Logins: " + getLogins() + ",");
        }
        if (getCustomRoleArn() != null) {
            sb.append("CustomRoleArn: " + getCustomRoleArn());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((getIdentityId() == null ? 0 : getIdentityId().hashCode()) + 31) * 31) + (getLogins() == null ? 0 : getLogins().hashCode())) * 31;
        if (getCustomRoleArn() != null) {
            i = getCustomRoleArn().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetCredentialsForIdentityRequest)) {
            return false;
        }
        GetCredentialsForIdentityRequest other = (GetCredentialsForIdentityRequest) obj;
        if ((other.getIdentityId() == null) ^ (getIdentityId() == null)) {
            return false;
        }
        if (other.getIdentityId() != null && !other.getIdentityId().equals(getIdentityId())) {
            return false;
        }
        if (other.getLogins() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getLogins() == null)) {
            return false;
        }
        if (other.getLogins() != null && !other.getLogins().equals(getLogins())) {
            return false;
        }
        if (other.getCustomRoleArn() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getCustomRoleArn() == null)) {
            return false;
        }
        if (other.getCustomRoleArn() == null || other.getCustomRoleArn().equals(getCustomRoleArn())) {
            return true;
        }
        return false;
    }
}
