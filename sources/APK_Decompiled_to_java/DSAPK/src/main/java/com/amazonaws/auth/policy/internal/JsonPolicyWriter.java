package com.amazonaws.auth.policy.internal;

import com.amazonaws.auth.policy.Action;
import com.amazonaws.auth.policy.Condition;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JsonPolicyWriter {
    private static final Log log = LogFactory.getLog("com.amazonaws.auth.policy");
    private AwsJsonWriter jsonWriter;
    private final Writer writer;

    static class ConditionsByKey {
        private Map<String, List<String>> conditionsByKey = new HashMap();

        public Map<String, List<String>> getConditionsByKey() {
            return this.conditionsByKey;
        }

        public void setConditionsByKey(Map<String, List<String>> conditionsByKey2) {
            this.conditionsByKey = conditionsByKey2;
        }

        public boolean containsKey(String key) {
            return this.conditionsByKey.containsKey(key);
        }

        public List<String> getConditionsByKey(String key) {
            return (List) this.conditionsByKey.get(key);
        }

        public Set<String> keySet() {
            return this.conditionsByKey.keySet();
        }

        public void addValuesToKey(String key, List<String> values) {
            List<String> conditionValues = getConditionsByKey(key);
            if (conditionValues == null) {
                this.conditionsByKey.put(key, new ArrayList(values));
            } else {
                conditionValues.addAll(values);
            }
        }
    }

    public JsonPolicyWriter() {
        this.jsonWriter = null;
        this.writer = new StringWriter();
        this.jsonWriter = JsonUtils.getJsonWriter(this.writer);
    }

    public String writePolicyToString(Policy policy) {
        if (!isNotNull(policy)) {
            throw new IllegalArgumentException("Policy cannot be null");
        }
        try {
            String jsonStringOf = jsonStringOf(policy);
            try {
                this.writer.close();
            } catch (Exception e) {
            }
            return jsonStringOf;
        } catch (Exception e2) {
            throw new IllegalArgumentException("Unable to serialize policy to JSON string: " + e2.getMessage(), e2);
        } catch (Throwable th) {
            try {
                this.writer.close();
            } catch (Exception e3) {
            }
            throw th;
        }
    }

    private String jsonStringOf(Policy policy) throws IOException {
        this.jsonWriter.beginObject();
        writeJsonKeyValue(JsonDocumentFields.VERSION, policy.getVersion());
        if (isNotNull(policy.getId())) {
            writeJsonKeyValue(JsonDocumentFields.POLICY_ID, policy.getId());
        }
        writeJsonArrayStart(JsonDocumentFields.STATEMENT);
        for (Statement statement : policy.getStatements()) {
            this.jsonWriter.beginObject();
            if (isNotNull(statement.getId())) {
                writeJsonKeyValue(JsonDocumentFields.STATEMENT_ID, statement.getId());
            }
            writeJsonKeyValue(JsonDocumentFields.STATEMENT_EFFECT, statement.getEffect().toString());
            List<Principal> principals = statement.getPrincipals();
            if (isNotNull(principals) && !principals.isEmpty()) {
                writePrincipals(principals);
            }
            List<Action> actions = statement.getActions();
            if (isNotNull(actions) && !actions.isEmpty()) {
                writeActions(actions);
            }
            List<Resource> resources = statement.getResources();
            if (isNotNull(resources) && !resources.isEmpty()) {
                writeResources(resources);
            }
            List<Condition> conditions = statement.getConditions();
            if (isNotNull(conditions) && !conditions.isEmpty()) {
                writeConditions(conditions);
            }
            this.jsonWriter.endObject();
        }
        writeJsonArrayEnd();
        this.jsonWriter.endObject();
        this.jsonWriter.flush();
        return this.writer.toString();
    }

    private void writeConditions(List<Condition> conditions) throws IOException {
        Map<String, ConditionsByKey> conditionsByType = groupConditionsByTypeAndKey(conditions);
        writeJsonObjectStart(JsonDocumentFields.CONDITION);
        for (Entry<String, ConditionsByKey> entry : conditionsByType.entrySet()) {
            ConditionsByKey conditionsByKey = (ConditionsByKey) conditionsByType.get(entry.getKey());
            writeJsonObjectStart((String) entry.getKey());
            for (String key : conditionsByKey.keySet()) {
                writeJsonArray(key, conditionsByKey.getConditionsByKey(key));
            }
            writeJsonObjectEnd();
        }
        writeJsonObjectEnd();
    }

    private void writeResources(List<Resource> resources) throws IOException {
        List<String> resourceStrings = new ArrayList<>();
        for (Resource resource : resources) {
            resourceStrings.add(resource.getId());
        }
        writeJsonArray(JsonDocumentFields.RESOURCE, resourceStrings);
    }

    private void writeActions(List<Action> actions) throws IOException {
        List<String> actionStrings = new ArrayList<>();
        for (Action action : actions) {
            actionStrings.add(action.getActionName());
        }
        writeJsonArray(JsonDocumentFields.ACTION, actionStrings);
    }

    private void writePrincipals(List<Principal> principals) throws IOException {
        if (principals.size() != 1 || !((Principal) principals.get(0)).equals(Principal.All)) {
            writeJsonObjectStart(JsonDocumentFields.PRINCIPAL);
            Map<String, List<String>> principalsByScheme = groupPrincipalByScheme(principals);
            for (Entry<String, List<String>> entry : principalsByScheme.entrySet()) {
                List<String> principalValues = (List) principalsByScheme.get(entry.getKey());
                if (principalValues.size() == 1) {
                    writeJsonKeyValue((String) entry.getKey(), (String) principalValues.get(0));
                } else {
                    writeJsonArray((String) entry.getKey(), principalValues);
                }
            }
            writeJsonObjectEnd();
            return;
        }
        writeJsonKeyValue(JsonDocumentFields.PRINCIPAL, Principal.All.getId());
    }

    private Map<String, List<String>> groupPrincipalByScheme(List<Principal> principals) {
        Map<String, List<String>> principalsByScheme = new HashMap<>();
        for (Principal principal : principals) {
            String provider = principal.getProvider();
            if (!principalsByScheme.containsKey(provider)) {
                principalsByScheme.put(provider, new ArrayList());
            }
            ((List) principalsByScheme.get(provider)).add(principal.getId());
        }
        return principalsByScheme;
    }

    private Map<String, ConditionsByKey> groupConditionsByTypeAndKey(List<Condition> conditions) {
        Map<String, ConditionsByKey> conditionsByType = new HashMap<>();
        for (Condition condition : conditions) {
            String type = condition.getType();
            String key = condition.getConditionKey();
            if (!conditionsByType.containsKey(type)) {
                conditionsByType.put(type, new ConditionsByKey());
            }
            ((ConditionsByKey) conditionsByType.get(type)).addValuesToKey(key, condition.getValues());
        }
        return conditionsByType;
    }

    private void writeJsonArray(String arrayName, List<String> values) throws IOException {
        writeJsonArrayStart(arrayName);
        for (String value : values) {
            this.jsonWriter.value(value);
        }
        writeJsonArrayEnd();
    }

    private void writeJsonObjectStart(String fieldName) throws IOException {
        this.jsonWriter.name(fieldName);
        this.jsonWriter.beginObject();
    }

    private void writeJsonObjectEnd() throws IOException {
        this.jsonWriter.endObject();
    }

    private void writeJsonArrayStart(String fieldName) throws IOException {
        this.jsonWriter.name(fieldName);
        this.jsonWriter.beginArray();
    }

    private void writeJsonArrayEnd() throws IOException {
        this.jsonWriter.endArray();
    }

    private void writeJsonKeyValue(String fieldName, String value) throws IOException {
        this.jsonWriter.name(fieldName);
        this.jsonWriter.value(value);
    }

    private boolean isNotNull(Object object) {
        return object != null;
    }
}
