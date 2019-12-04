package com.philips.lighting.hue.sdk.wrapper.domain.resource;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import java.util.Objects;

public class GroupState {
    private Boolean all_on = null;
    private Boolean any_on = null;

    public GroupState() {
    }

    public GroupState(Boolean any_on2, Boolean all_on2) {
        this.any_on = any_on2;
        this.all_on = all_on2;
    }

    public Boolean isAnyOn() {
        return this.any_on;
    }

    public Boolean isAllOn() {
        return this.all_on;
    }

    public DomainType getType() {
        return DomainType.GROUP_STATE;
    }

    public GroupState clone() {
        return new GroupState(this.any_on, this.all_on);
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((this.any_on == null ? 0 : this.any_on.hashCode()) + 31) * 31;
        if (this.all_on != null) {
            i = this.all_on.hashCode();
        }
        return hashCode + i;
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
        GroupState other = (GroupState) obj;
        if (!Objects.equals(this.any_on, other.any_on)) {
            return false;
        }
        if (!Objects.equals(this.all_on, other.all_on)) {
            return false;
        }
        return true;
    }
}
