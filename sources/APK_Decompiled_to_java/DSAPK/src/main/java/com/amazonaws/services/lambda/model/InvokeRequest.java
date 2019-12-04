package com.amazonaws.services.lambda.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class InvokeRequest extends AmazonWebServiceRequest implements Serializable {
    private String clientContext;
    private String functionName;
    private String invocationType;
    private String logType;
    private ByteBuffer payload;
    private String qualifier;

    public String getFunctionName() {
        return this.functionName;
    }

    public void setFunctionName(String functionName2) {
        this.functionName = functionName2;
    }

    public InvokeRequest withFunctionName(String functionName2) {
        this.functionName = functionName2;
        return this;
    }

    public String getInvocationType() {
        return this.invocationType;
    }

    public void setInvocationType(String invocationType2) {
        this.invocationType = invocationType2;
    }

    public InvokeRequest withInvocationType(String invocationType2) {
        this.invocationType = invocationType2;
        return this;
    }

    public void setInvocationType(InvocationType invocationType2) {
        this.invocationType = invocationType2.toString();
    }

    public InvokeRequest withInvocationType(InvocationType invocationType2) {
        this.invocationType = invocationType2.toString();
        return this;
    }

    public String getLogType() {
        return this.logType;
    }

    public void setLogType(String logType2) {
        this.logType = logType2;
    }

    public InvokeRequest withLogType(String logType2) {
        this.logType = logType2;
        return this;
    }

    public void setLogType(LogType logType2) {
        this.logType = logType2.toString();
    }

    public InvokeRequest withLogType(LogType logType2) {
        this.logType = logType2.toString();
        return this;
    }

    public String getClientContext() {
        return this.clientContext;
    }

    public void setClientContext(String clientContext2) {
        this.clientContext = clientContext2;
    }

    public InvokeRequest withClientContext(String clientContext2) {
        this.clientContext = clientContext2;
        return this;
    }

    public ByteBuffer getPayload() {
        return this.payload;
    }

    public void setPayload(ByteBuffer payload2) {
        this.payload = payload2;
    }

    public InvokeRequest withPayload(ByteBuffer payload2) {
        this.payload = payload2;
        return this;
    }

    public String getQualifier() {
        return this.qualifier;
    }

    public void setQualifier(String qualifier2) {
        this.qualifier = qualifier2;
    }

    public InvokeRequest withQualifier(String qualifier2) {
        this.qualifier = qualifier2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getFunctionName() != null) {
            sb.append("FunctionName: " + getFunctionName() + ",");
        }
        if (getInvocationType() != null) {
            sb.append("InvocationType: " + getInvocationType() + ",");
        }
        if (getLogType() != null) {
            sb.append("LogType: " + getLogType() + ",");
        }
        if (getClientContext() != null) {
            sb.append("ClientContext: " + getClientContext() + ",");
        }
        if (getPayload() != null) {
            sb.append("Payload: " + getPayload() + ",");
        }
        if (getQualifier() != null) {
            sb.append("Qualifier: " + getQualifier());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((((getFunctionName() == null ? 0 : getFunctionName().hashCode()) + 31) * 31) + (getInvocationType() == null ? 0 : getInvocationType().hashCode())) * 31) + (getLogType() == null ? 0 : getLogType().hashCode())) * 31) + (getClientContext() == null ? 0 : getClientContext().hashCode())) * 31) + (getPayload() == null ? 0 : getPayload().hashCode())) * 31;
        if (getQualifier() != null) {
            i = getQualifier().hashCode();
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
        if (obj == null || !(obj instanceof InvokeRequest)) {
            return false;
        }
        InvokeRequest other = (InvokeRequest) obj;
        if ((other.getFunctionName() == null) ^ (getFunctionName() == null)) {
            return false;
        }
        if (other.getFunctionName() != null && !other.getFunctionName().equals(getFunctionName())) {
            return false;
        }
        if (other.getInvocationType() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getInvocationType() == null)) {
            return false;
        }
        if (other.getInvocationType() != null && !other.getInvocationType().equals(getInvocationType())) {
            return false;
        }
        if (other.getLogType() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getLogType() == null)) {
            return false;
        }
        if (other.getLogType() != null && !other.getLogType().equals(getLogType())) {
            return false;
        }
        if (other.getClientContext() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3 ^ (getClientContext() == null)) {
            return false;
        }
        if (other.getClientContext() != null && !other.getClientContext().equals(getClientContext())) {
            return false;
        }
        if (other.getPayload() == null) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (z4 ^ (getPayload() == null)) {
            return false;
        }
        if (other.getPayload() != null && !other.getPayload().equals(getPayload())) {
            return false;
        }
        if (other.getQualifier() == null) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (z5 ^ (getQualifier() == null)) {
            return false;
        }
        if (other.getQualifier() == null || other.getQualifier().equals(getQualifier())) {
            return true;
        }
        return false;
    }
}
