package com.amazonaws.auth.policy;

@Deprecated
public enum STSActions implements Action {
    AssumeRole("sts:AssumeRole"),
    AssumeRoleWithWebIdentity("sts:AssumeRoleWithWebIdentity");
    
    private final String action;

    private STSActions(String action2) {
        this.action = action2;
    }

    public String getActionName() {
        return this.action;
    }
}
