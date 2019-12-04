package com.philips.lighting.hue.sdk.wrapper.domain.resource.rule;

import com.philips.lighting.hue.sdk.wrapper.SessionObject;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ClipAction;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ClipCondition;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.BridgeResource;
import com.philips.lighting.hue.sdk.wrapper.utilities.Executor;
import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Rule extends SessionObject implements BridgeResource {
    private List<ClipAction> actions = null;
    private List<ClipCondition> conditions = null;
    private Date created = null;
    private byte[] identifier = null;
    private Date lastTriggered = null;
    private byte[] name = null;
    private byte[] owner = null;
    private Boolean recycle = null;
    private RuleStatus status = null;
    private Integer timesTriggered = null;

    /* access modifiers changed from: private */
    public native void fetchNative(int i, BridgeResponseCallback bridgeResponseCallback);

    protected Rule(long sessionKey) {
        super(sessionKey);
    }

    public Rule() {
    }

    public String getIdentifier() {
        return NativeTools.BytesToString(this.identifier);
    }

    public void setIdentifier(String identifier2) {
        this.identifier = NativeTools.StringToBytes(identifier2);
    }

    public String getName() {
        return NativeTools.BytesToString(this.name);
    }

    public void setName(String name2) {
        this.name = NativeTools.StringToBytes(name2);
    }

    public String getOwner() {
        return NativeTools.BytesToString(this.owner);
    }

    public RuleStatus getStatus() {
        return this.status;
    }

    public void setStatus(RuleStatus status2) {
        this.status = status2;
    }

    public Date getCreated() {
        return this.created;
    }

    /* access modifiers changed from: protected */
    public int getStatusAsInt() {
        if (this.status != null) {
            return this.status.getValue();
        }
        return RuleStatus.UNKNOWN.getValue();
    }

    private void setStatusFromInt(int value) {
        this.status = RuleStatus.fromValue(value);
    }

    public Date getLastTriggered() {
        return this.lastTriggered;
    }

    public Integer getTimesTriggered() {
        return this.timesTriggered;
    }

    public Boolean getRecycle() {
        return this.recycle;
    }

    public void setRecycle(Boolean recycle2) {
        this.recycle = recycle2;
    }

    public void setRecycle(boolean recycle2) {
        this.recycle = new Boolean(recycle2);
    }

    public int getDomainType() {
        return getType().getValue();
    }

    public DomainType getType() {
        return DomainType.RULE;
    }

    public boolean isOfType(DomainType type) {
        return getType() == type;
    }

    public boolean isOfType(int typeAsInt) {
        return isOfType(DomainType.fromValue(typeAsInt));
    }

    public List<ClipCondition> getConditions() {
        return this.conditions;
    }

    /* access modifiers changed from: protected */
    public ClipCondition[] getConditionsArray() {
        if (this.conditions == null) {
            return null;
        }
        ClipCondition[] result = new ClipCondition[this.conditions.size()];
        for (int i = 0; i < this.conditions.size(); i++) {
            result[i] = (ClipCondition) this.conditions.get(i);
        }
        return result;
    }

    public void setConditions(List<ClipCondition> conditions2) {
        this.conditions = conditions2;
    }

    public List<ClipAction> getActions() {
        return this.actions;
    }

    /* access modifiers changed from: protected */
    public ClipAction[] getActionsArray() {
        if (this.actions == null) {
            return null;
        }
        ClipAction[] result = new ClipAction[this.actions.size()];
        for (int i = 0; i < this.actions.size(); i++) {
            result[i] = (ClipAction) this.actions.get(i);
        }
        return result;
    }

    public void setActions(List<ClipAction> actions2) {
        this.actions = actions2;
    }

    public void addCondition(ClipCondition condition) {
        if (this.conditions == null) {
            this.conditions = new ArrayList();
        }
        this.conditions.add(condition);
    }

    public void addAction(ClipAction action) {
        if (this.actions == null) {
            this.actions = new ArrayList();
        }
        this.actions.add(action);
    }

    public void setBridge(Bridge bridge) {
        setSessionKey(((SessionObject) bridge).getSessionKey());
    }

    public void fetch(BridgeResponseCallback callback) {
        fetch(BridgeConnectionType.LOCAL_REMOTE, callback);
    }

    public void fetch(final BridgeConnectionType allowedConnectionType, final BridgeResponseCallback callback) {
        if (allowedConnectionType == null) {
            BridgeResponseDispatcher.dispatch(callback, null, ReturnCode.NOT_ALLOWED);
        } else {
            Executor.submit(new Runnable() {
                public void run() {
                    Rule.this.fetchNative(allowedConnectionType.getValue(), callback);
                }
            });
        }
    }

    public int hashCode() {
        int hashCode;
        int hashCode2;
        int i = 0;
        int hashCode3 = ((this.actions == null ? 0 : this.actions.hashCode()) + 31) * 31;
        if (this.conditions == null) {
            hashCode = 0;
        } else {
            hashCode = this.conditions.hashCode();
        }
        int hashCode4 = (((((hashCode3 + hashCode) * 31) + (this.created == null ? 0 : this.created.hashCode())) * 31) + Arrays.hashCode(this.identifier)) * 31;
        if (this.lastTriggered == null) {
            hashCode2 = 0;
        } else {
            hashCode2 = this.lastTriggered.hashCode();
        }
        int hashCode5 = (((((((((hashCode4 + hashCode2) * 31) + Arrays.hashCode(this.name)) * 31) + Arrays.hashCode(this.owner)) * 31) + (this.recycle == null ? 0 : this.recycle.hashCode())) * 31) + (this.status == null ? 0 : this.status.hashCode())) * 31;
        if (this.timesTriggered != null) {
            i = this.timesTriggered.hashCode();
        }
        return hashCode5 + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Rule other = (Rule) obj;
        if (this.actions == null) {
            if (other.actions != null) {
                return false;
            }
        } else if (!this.actions.equals(other.actions)) {
            return false;
        }
        if (this.conditions == null) {
            if (other.conditions != null) {
                return false;
            }
        } else if (!this.conditions.equals(other.conditions)) {
            return false;
        }
        if (this.created == null) {
            if (other.created != null) {
                return false;
            }
        } else if (!this.created.equals(other.created)) {
            return false;
        }
        if (!Arrays.equals(this.identifier, other.identifier)) {
            return false;
        }
        if (this.lastTriggered == null) {
            if (other.lastTriggered != null) {
                return false;
            }
        } else if (!this.lastTriggered.equals(other.lastTriggered)) {
            return false;
        }
        if (!Arrays.equals(this.name, other.name)) {
            return false;
        }
        if (!Arrays.equals(this.owner, other.owner)) {
            return false;
        }
        if (this.recycle == null) {
            if (other.recycle != null) {
                return false;
            }
        } else if (!this.recycle.equals(other.recycle)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (this.timesTriggered == null) {
            if (other.timesTriggered != null) {
                return false;
            }
            return true;
        } else if (!this.timesTriggered.equals(other.timesTriggered)) {
            return false;
        } else {
            return true;
        }
    }

    public void syncNative() {
    }
}
