package com.amazonaws.auth.policy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Statement {
    private List<Action> actions = new ArrayList();
    private List<Condition> conditions = new ArrayList();
    private Effect effect;
    private String id;
    private List<Principal> principals = new ArrayList();
    private List<Resource> resources;

    public enum Effect {
        Allow,
        Deny
    }

    public Statement(Effect effect2) {
        this.effect = effect2;
        this.id = null;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public Statement withId(String id2) {
        setId(id2);
        return this;
    }

    public Effect getEffect() {
        return this.effect;
    }

    public void setEffect(Effect effect2) {
        this.effect = effect2;
    }

    public List<Action> getActions() {
        return this.actions;
    }

    public void setActions(Collection<Action> actions2) {
        this.actions = new ArrayList(actions2);
    }

    public Statement withActions(Action... actions2) {
        setActions(Arrays.asList(actions2));
        return this;
    }

    public List<Resource> getResources() {
        return this.resources;
    }

    public void setResources(Collection<Resource> resources2) {
        this.resources = new ArrayList(resources2);
    }

    public Statement withResources(Resource... resources2) {
        setResources(Arrays.asList(resources2));
        return this;
    }

    public List<Condition> getConditions() {
        return this.conditions;
    }

    public void setConditions(List<Condition> conditions2) {
        this.conditions = conditions2;
    }

    public Statement withConditions(Condition... conditions2) {
        setConditions(Arrays.asList(conditions2));
        return this;
    }

    public List<Principal> getPrincipals() {
        return this.principals;
    }

    public void setPrincipals(Collection<Principal> principals2) {
        this.principals = new ArrayList(principals2);
    }

    public void setPrincipals(Principal... principals2) {
        setPrincipals((Collection<Principal>) new ArrayList<Principal>(Arrays.asList(principals2)));
    }

    public Statement withPrincipals(Principal... principals2) {
        setPrincipals(principals2);
        return this;
    }
}
