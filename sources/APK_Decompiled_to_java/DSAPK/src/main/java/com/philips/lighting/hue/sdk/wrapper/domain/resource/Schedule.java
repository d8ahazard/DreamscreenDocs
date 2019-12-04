package com.philips.lighting.hue.sdk.wrapper.domain.resource;

import com.philips.lighting.hue.sdk.wrapper.SessionObject;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseDispatcher;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ClipAction;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.timepattern.TimePattern;
import com.philips.lighting.hue.sdk.wrapper.utilities.Executor;
import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.Arrays;
import java.util.Date;

public class Schedule extends SessionObject implements BridgeResource {
    private Boolean autoDelete;
    private ClipAction clipAction;
    private Date creationTime;
    private byte[] description;
    private byte[] identifier;
    private TimePattern localTime;
    private byte[] name;
    private Boolean recycle;
    private ScheduleStatus status;
    private TimePattern utcTime;

    /* access modifiers changed from: private */
    public native void fetchNative(int i, BridgeResponseCallback bridgeResponseCallback);

    protected Schedule(long sessionKey) {
        super(sessionKey);
    }

    public Schedule() {
    }

    protected Schedule(Schedule schedule) {
        super(schedule.getSessionKey());
        this.identifier = schedule.identifier;
        this.name = schedule.name;
        this.description = schedule.description;
        this.utcTime = schedule.utcTime;
        this.localTime = schedule.localTime;
        this.creationTime = schedule.creationTime;
        this.autoDelete = schedule.autoDelete;
        this.status = schedule.status;
        this.clipAction = schedule.clipAction;
        this.recycle = schedule.recycle;
    }

    public Schedule(String name2, String description2, TimePattern utcTime2, TimePattern localTime2, Boolean autoDelete2, ScheduleStatus status2, ClipAction clipAction2) {
        setName(name2);
        setDescription(description2);
        this.utcTime = utcTime2;
        this.localTime = localTime2;
        this.autoDelete = autoDelete2;
        this.status = status2;
        this.clipAction = clipAction2;
        this.recycle = null;
    }

    public Schedule(String name2, String description2, TimePattern utcTime2, TimePattern localTime2, Boolean autoDelete2, ScheduleStatus status2, ClipAction clipAction2, Boolean recycle2) {
        setName(name2);
        setDescription(description2);
        this.utcTime = utcTime2;
        this.localTime = localTime2;
        this.autoDelete = autoDelete2;
        this.status = status2;
        this.clipAction = clipAction2;
        this.recycle = recycle2;
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

    public int getDomainType() {
        return getType().getValue();
    }

    public String getDescription() {
        return NativeTools.BytesToString(this.description);
    }

    public void setDescription(String description2) {
        this.description = NativeTools.StringToBytes(description2);
    }

    public Date getCreationTime() {
        return this.creationTime;
    }

    public Boolean getAutoDelete() {
        return this.autoDelete;
    }

    public void setAutoDelete(Boolean autoDelete2) {
        this.autoDelete = autoDelete2;
    }

    public ScheduleStatus getStatus() {
        return this.status;
    }

    public void setStatus(ScheduleStatus status2) {
        this.status = status2;
    }

    /* access modifiers changed from: protected */
    public int getStatusAsInt() {
        return this.status.getValue();
    }

    /* access modifiers changed from: protected */
    public void setStatusFromInt(int value) {
        this.status = ScheduleStatus.fromValue(value);
    }

    public TimePattern getUtcTime() {
        return this.utcTime;
    }

    public void setUtcTime(TimePattern utcTime2) {
        this.utcTime = utcTime2;
    }

    public TimePattern getLocalTime() {
        return this.localTime;
    }

    public void setLocalTime(TimePattern localTime2) {
        this.localTime = localTime2;
    }

    public ClipAction getClipAction() {
        return this.clipAction;
    }

    public void setClipAction(ClipAction clipAction2) {
        this.clipAction = clipAction2;
    }

    public void setCreationTime(Date creationTime2) {
        this.creationTime = creationTime2;
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

    public DomainType getType() {
        return DomainType.SCHEDULE;
    }

    public boolean isOfType(DomainType type) {
        if (type == DomainType.RESOURCE || getType() == type) {
            return true;
        }
        return false;
    }

    public boolean isOfType(int typeAsInt) {
        return isOfType(DomainType.fromValue(typeAsInt));
    }

    public void setBridge(Bridge bridge) {
        setSessionKey(((SessionObject) bridge).getSessionKey());
    }

    public void syncNative() {
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
                    Schedule.this.fetchNative(allowedConnectionType.getValue(), callback);
                }
            });
        }
    }

    public int hashCode() {
        int hashCode;
        int hashCode2;
        int hashCode3;
        int hashCode4;
        int i = 0;
        int hashCode5 = super.hashCode() * 31;
        if (this.autoDelete == null) {
            hashCode = 0;
        } else {
            hashCode = this.autoDelete.hashCode();
        }
        int i2 = (hashCode5 + hashCode) * 31;
        if (this.clipAction == null) {
            hashCode2 = 0;
        } else {
            hashCode2 = this.clipAction.hashCode();
        }
        int i3 = (i2 + hashCode2) * 31;
        if (this.creationTime == null) {
            hashCode3 = 0;
        } else {
            hashCode3 = this.creationTime.hashCode();
        }
        int hashCode6 = (((((i3 + hashCode3) * 31) + Arrays.hashCode(this.description)) * 31) + Arrays.hashCode(this.identifier)) * 31;
        if (this.localTime == null) {
            hashCode4 = 0;
        } else {
            hashCode4 = this.localTime.hashCode();
        }
        int hashCode7 = (((((((hashCode6 + hashCode4) * 31) + Arrays.hashCode(this.name)) * 31) + (this.recycle == null ? 0 : this.recycle.hashCode())) * 31) + (this.status == null ? 0 : this.status.hashCode())) * 31;
        if (this.utcTime != null) {
            i = this.utcTime.hashCode();
        }
        return hashCode7 + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Schedule other = (Schedule) obj;
        if (this.autoDelete == null) {
            if (other.autoDelete != null) {
                return false;
            }
        } else if (!this.autoDelete.equals(other.autoDelete)) {
            return false;
        }
        if (this.clipAction == null) {
            if (other.clipAction != null) {
                return false;
            }
        } else if (!this.clipAction.equals(other.clipAction)) {
            return false;
        }
        if (this.creationTime == null) {
            if (other.creationTime != null) {
                return false;
            }
        } else if (!this.creationTime.equals(other.creationTime)) {
            return false;
        }
        if (!Arrays.equals(this.description, other.description)) {
            return false;
        }
        if (!Arrays.equals(this.identifier, other.identifier)) {
            return false;
        }
        if (this.localTime == null) {
            if (other.localTime != null) {
                return false;
            }
        } else if (!this.localTime.equals(other.localTime)) {
            return false;
        }
        if (!Arrays.equals(this.name, other.name)) {
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
        if (this.utcTime == null) {
            if (other.utcTime != null) {
                return false;
            }
            return true;
        } else if (!this.utcTime.equals(other.utcTime)) {
            return false;
        } else {
            return true;
        }
    }
}
