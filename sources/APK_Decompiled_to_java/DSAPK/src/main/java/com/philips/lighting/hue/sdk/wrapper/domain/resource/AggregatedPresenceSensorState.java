package com.philips.lighting.hue.sdk.wrapper.domain.resource;

import java.util.Date;
import java.util.Objects;
import javax.annotation.Nullable;

public final class AggregatedPresenceSensorState {
    public Date lastUpdated;
    public Boolean presence;
    public Boolean presenceAll;

    public AggregatedPresenceSensorState() {
    }

    public AggregatedPresenceSensorState(Date lastUpdated2, Boolean presence2, Boolean presenceAll2) {
        this.lastUpdated = lastUpdated2;
        this.presence = presence2;
        this.presenceAll = presenceAll2;
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AggregatedPresenceSensorState other = (AggregatedPresenceSensorState) o;
        if (!Objects.equals(this.lastUpdated, other.lastUpdated) || !Objects.equals(this.presence, other.presence) || !Objects.equals(this.presenceAll, other.presenceAll)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.lastUpdated, this.presence, this.presenceAll});
    }
}
