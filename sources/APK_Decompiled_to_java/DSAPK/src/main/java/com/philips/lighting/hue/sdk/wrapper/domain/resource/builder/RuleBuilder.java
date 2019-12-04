package com.philips.lighting.hue.sdk.wrapper.domain.resource.builder;

import com.philips.lighting.hue.sdk.wrapper.domain.clip.ClipAction;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ClipCondition;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.rule.Rule;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.rule.RuleStatus;
import java.util.List;

public class RuleBuilder {
    private Rule rule;

    public RuleBuilder() {
        this.rule = null;
        this.rule = new Rule();
    }

    public RuleBuilder setName(String name) {
        if (name != null) {
            this.rule.setName(name);
        }
        return this;
    }

    public RuleBuilder setStatus(RuleStatus status) {
        if (status != null) {
            this.rule.setStatus(status);
        }
        return this;
    }

    public RuleBuilder setRecycle(boolean recycle) {
        this.rule.setRecycle(recycle);
        return this;
    }

    public RuleBuilder ifCondition(ClipCondition condition) {
        if (condition != null) {
            this.rule.setConditions(null);
            this.rule.addCondition(condition);
        }
        return this;
    }

    public RuleBuilder ifConditions(List<ClipCondition> conditions) {
        if (conditions != null) {
            this.rule.setConditions(conditions);
        }
        return this;
    }

    public RuleBuilder andCondition(ClipCondition condition) {
        if (condition != null) {
            this.rule.addCondition(condition);
        }
        return this;
    }

    public RuleBuilder thenAction(ClipAction action) {
        if (action != null) {
            this.rule.setActions(null);
            this.rule.addAction(action);
        }
        return this;
    }

    public RuleBuilder thenActions(List<ClipAction> actions) {
        if (actions != null) {
            this.rule.setActions(actions);
        }
        return this;
    }

    public RuleBuilder andAction(ClipAction action) {
        if (action != null) {
            this.rule.addAction(action);
        }
        return this;
    }

    public Rule build() {
        return this.rule;
    }
}
