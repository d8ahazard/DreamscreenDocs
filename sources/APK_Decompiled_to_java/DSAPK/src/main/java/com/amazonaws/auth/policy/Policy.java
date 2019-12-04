package com.amazonaws.auth.policy;

import com.amazonaws.auth.policy.internal.JsonPolicyReader;
import com.amazonaws.auth.policy.internal.JsonPolicyWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Policy {
    private static final String DEFAULT_POLICY_VERSION = "2012-10-17";
    private String id;
    private List<Statement> statements;
    private String version;

    public Policy() {
        this.version = DEFAULT_POLICY_VERSION;
        this.statements = new ArrayList();
    }

    public Policy(String id2) {
        this.version = DEFAULT_POLICY_VERSION;
        this.statements = new ArrayList();
        this.id = id2;
    }

    public Policy(String id2, Collection<Statement> statements2) {
        this(id2);
        setStatements(statements2);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public Policy withId(String id2) {
        setId(id2);
        return this;
    }

    public String getVersion() {
        return this.version;
    }

    public Collection<Statement> getStatements() {
        return this.statements;
    }

    public void setStatements(Collection<Statement> statements2) {
        this.statements = new ArrayList(statements2);
        assignUniqueStatementIds();
    }

    public Policy withStatements(Statement... statements2) {
        setStatements(Arrays.asList(statements2));
        return this;
    }

    public String toJson() {
        return new JsonPolicyWriter().writePolicyToString(this);
    }

    public static Policy fromJson(String jsonString) {
        return new JsonPolicyReader().createPolicyFromJsonString(jsonString);
    }

    private void assignUniqueStatementIds() {
        Set<String> usedStatementIds = new HashSet<>();
        for (Statement statement : this.statements) {
            if (statement.getId() != null) {
                usedStatementIds.add(statement.getId());
            }
        }
        int counter = 0;
        for (Statement statement2 : this.statements) {
            if (statement2.getId() == null) {
                do {
                    counter++;
                } while (usedStatementIds.contains(Integer.toString(counter)));
                statement2.setId(Integer.toString(counter));
            }
        }
    }
}
