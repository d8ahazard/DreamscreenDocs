package com.amazonaws.auth.policy;

public class Resource {
    private final String resource;

    public Resource(String resource2) {
        this.resource = resource2;
    }

    public String getId() {
        return this.resource;
    }
}
