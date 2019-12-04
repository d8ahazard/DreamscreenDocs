package com.amazonaws.services.lambda.model;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class InvokeResult implements Serializable {
    private String functionError;
    private String logResult;
    private ByteBuffer payload;
    private Integer statusCode;

    public Integer getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(Integer statusCode2) {
        this.statusCode = statusCode2;
    }

    public InvokeResult withStatusCode(Integer statusCode2) {
        this.statusCode = statusCode2;
        return this;
    }

    public String getFunctionError() {
        return this.functionError;
    }

    public void setFunctionError(String functionError2) {
        this.functionError = functionError2;
    }

    public InvokeResult withFunctionError(String functionError2) {
        this.functionError = functionError2;
        return this;
    }

    public String getLogResult() {
        return this.logResult;
    }

    public void setLogResult(String logResult2) {
        this.logResult = logResult2;
    }

    public InvokeResult withLogResult(String logResult2) {
        this.logResult = logResult2;
        return this;
    }

    public ByteBuffer getPayload() {
        return this.payload;
    }

    public void setPayload(ByteBuffer payload2) {
        this.payload = payload2;
    }

    public InvokeResult withPayload(ByteBuffer payload2) {
        this.payload = payload2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getStatusCode() != null) {
            sb.append("StatusCode: " + getStatusCode() + ",");
        }
        if (getFunctionError() != null) {
            sb.append("FunctionError: " + getFunctionError() + ",");
        }
        if (getLogResult() != null) {
            sb.append("LogResult: " + getLogResult() + ",");
        }
        if (getPayload() != null) {
            sb.append("Payload: " + getPayload());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((getStatusCode() == null ? 0 : getStatusCode().hashCode()) + 31) * 31) + (getFunctionError() == null ? 0 : getFunctionError().hashCode())) * 31) + (getLogResult() == null ? 0 : getLogResult().hashCode())) * 31;
        if (getPayload() != null) {
            i = getPayload().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        boolean z3;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof InvokeResult)) {
            return false;
        }
        InvokeResult other = (InvokeResult) obj;
        if ((other.getStatusCode() == null) ^ (getStatusCode() == null)) {
            return false;
        }
        if (other.getStatusCode() != null && !other.getStatusCode().equals(getStatusCode())) {
            return false;
        }
        if (other.getFunctionError() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getFunctionError() == null)) {
            return false;
        }
        if (other.getFunctionError() != null && !other.getFunctionError().equals(getFunctionError())) {
            return false;
        }
        if (other.getLogResult() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getLogResult() == null)) {
            return false;
        }
        if (other.getLogResult() != null && !other.getLogResult().equals(getLogResult())) {
            return false;
        }
        if (other.getPayload() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3 ^ (getPayload() == null)) {
            return false;
        }
        if (other.getPayload() == null || other.getPayload().equals(getPayload())) {
            return true;
        }
        return false;
    }
}
