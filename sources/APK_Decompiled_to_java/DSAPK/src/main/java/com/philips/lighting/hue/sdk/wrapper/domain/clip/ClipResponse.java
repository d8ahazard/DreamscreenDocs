package com.philips.lighting.hue.sdk.wrapper.domain.clip;

import com.philips.lighting.hue.sdk.wrapper.domain.BridgeBackupStatus;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.StartUpMode;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.switches.SwitchButtonEvent;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.GroupType;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.ScheduleStatus;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.rule.RuleStatus;
import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ClipResponse {
    private Alert alertValue = null;
    private Boolean booleanValue = null;
    private BridgeBackupStatus bridgeBackupStatusValue = null;
    private ClipConditionOperator clipOperatorValue = null;
    private ColorMode colorModeValue = null;
    private Date dateValue = null;
    private DoublePair doublePairValue = null;
    private Double doubleValue = null;
    private Effect effectValue = null;
    private GroupType groupTypeValue = null;
    private Integer integerValue = null;
    private LightArchetype lightArchetype = null;
    private LightDirection lightDirection = null;
    private LightFunction lightFunction = null;
    private PortalConnectionState portalConnectionStateValue = null;
    private byte[] resourceName = null;
    private RuleStatus ruleStatusValue = null;
    private ScheduleStatus scheduleStatusValue = null;
    private StartUpMode startUpMode = null;
    private byte[][] stringArrayValue = null;
    private byte[] stringValue = null;
    private SwitchButtonEvent switchButtonEventValue = null;

    protected ClipResponse(byte[] resourceName2) {
        this.resourceName = resourceName2;
    }

    public ClipResponse(String resourceName2) {
        this.resourceName = NativeTools.StringToBytes(resourceName2);
    }

    private void setAlertValue(Alert value) {
        this.alertValue = value;
    }

    private void setColorModeValueFromInt(int value) {
        this.colorModeValue = ColorMode.fromValue(value);
    }

    private void setSwitchButtonEventValueFromInt(int value) {
        this.switchButtonEventValue = SwitchButtonEvent.fromValue(value);
    }

    private void setEffectValueFromInt(int value) {
        this.effectValue = Effect.fromValue(value);
    }

    private void setGroupTypeValueFromInt(int value) {
        this.groupTypeValue = GroupType.fromValue(value);
    }

    private void setPortalConnectionStateValueFromInt(int value) {
        this.portalConnectionStateValue = PortalConnectionState.fromValue(value);
    }

    private void setScheduleStatusFromInt(int value) {
        this.scheduleStatusValue = ScheduleStatus.fromValue(value);
    }

    private void setRuleStatusFromInt(int value) {
        this.ruleStatusValue = RuleStatus.fromValue(value);
    }

    private void setClipOperatorFromInt(int value) {
        this.clipOperatorValue = ClipConditionOperator.fromValue(value);
    }

    private void setBridgeBackupStatusValueFromInt(int value) {
        this.bridgeBackupStatusValue = BridgeBackupStatus.fromValue(value);
    }

    private void setLightArchetype(LightArchetype lightArchetype2) {
        this.lightArchetype = lightArchetype2;
    }

    private void setLightDirection(LightDirection lightDirection2) {
        this.lightDirection = lightDirection2;
    }

    private void setLightFunction(LightFunction lightFunction2) {
        this.lightFunction = lightFunction2;
    }

    private void setStartUpMode(StartUpMode startUpMode2) {
        this.startUpMode = startUpMode2;
    }

    public String getResourceName() {
        return NativeTools.BytesToString(this.resourceName);
    }

    public Alert getAlertValue() {
        return this.alertValue;
    }

    public Boolean getBooleanValue() {
        return this.booleanValue;
    }

    public ColorMode getColorModeValue() {
        return this.colorModeValue;
    }

    public SwitchButtonEvent getSwitchButtonEventValue() {
        return this.switchButtonEventValue;
    }

    public Date getDateValue() {
        return this.dateValue;
    }

    public Double getDoubleValue() {
        return this.doubleValue;
    }

    public DoublePair getDoublePairValue() {
        return this.doublePairValue;
    }

    public Effect getEffectValue() {
        return this.effectValue;
    }

    public GroupType getGroupTypeValue() {
        return this.groupTypeValue;
    }

    public Integer getIntegerValue() {
        return this.integerValue;
    }

    public String getStringValue() {
        return NativeTools.BytesToString(this.stringValue);
    }

    public List<String> getStringArrayValue() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (byte[] string : this.stringArrayValue) {
            arrayList.add(NativeTools.BytesToString(string));
        }
        return arrayList;
    }

    public PortalConnectionState getPortalConnectionStateValue() {
        return this.portalConnectionStateValue;
    }

    public ScheduleStatus getScheduleStatusValue() {
        return this.scheduleStatusValue;
    }

    public RuleStatus getRuleStatusValue() {
        return this.ruleStatusValue;
    }

    public ClipConditionOperator getClipOperatorValue() {
        return this.clipOperatorValue;
    }

    public LightArchetype getLightArchetype() {
        return this.lightArchetype;
    }

    public LightDirection getLightDirection() {
        return this.lightDirection;
    }

    public LightFunction getLightFunction() {
        return this.lightFunction;
    }

    public StartUpMode getStartUpMode() {
        return this.startUpMode;
    }

    public int hashCode() {
        int hashCode;
        int hashCode2;
        int hashCode3;
        int hashCode4;
        int hashCode5;
        int hashCode6;
        int hashCode7;
        int hashCode8;
        int hashCode9;
        int hashCode10;
        int hashCode11;
        int hashCode12;
        int hashCode13;
        int hashCode14;
        int hashCode15;
        int i = 0;
        if (this.alertValue == null) {
            hashCode = 0;
        } else {
            hashCode = this.alertValue.hashCode();
        }
        int i2 = (hashCode + 31) * 31;
        if (this.booleanValue == null) {
            hashCode2 = 0;
        } else {
            hashCode2 = this.booleanValue.hashCode();
        }
        int i3 = (i2 + hashCode2) * 31;
        if (this.bridgeBackupStatusValue == null) {
            hashCode3 = 0;
        } else {
            hashCode3 = this.bridgeBackupStatusValue.hashCode();
        }
        int i4 = (i3 + hashCode3) * 31;
        if (this.clipOperatorValue == null) {
            hashCode4 = 0;
        } else {
            hashCode4 = this.clipOperatorValue.hashCode();
        }
        int i5 = (i4 + hashCode4) * 31;
        if (this.colorModeValue == null) {
            hashCode5 = 0;
        } else {
            hashCode5 = this.colorModeValue.hashCode();
        }
        int i6 = (i5 + hashCode5) * 31;
        if (this.dateValue == null) {
            hashCode6 = 0;
        } else {
            hashCode6 = this.dateValue.hashCode();
        }
        int i7 = (i6 + hashCode6) * 31;
        if (this.doublePairValue == null) {
            hashCode7 = 0;
        } else {
            hashCode7 = this.doublePairValue.hashCode();
        }
        int i8 = (i7 + hashCode7) * 31;
        if (this.doubleValue == null) {
            hashCode8 = 0;
        } else {
            hashCode8 = this.doubleValue.hashCode();
        }
        int i9 = (i8 + hashCode8) * 31;
        if (this.effectValue == null) {
            hashCode9 = 0;
        } else {
            hashCode9 = this.effectValue.hashCode();
        }
        int i10 = (i9 + hashCode9) * 31;
        if (this.groupTypeValue == null) {
            hashCode10 = 0;
        } else {
            hashCode10 = this.groupTypeValue.hashCode();
        }
        int i11 = (i10 + hashCode10) * 31;
        if (this.integerValue == null) {
            hashCode11 = 0;
        } else {
            hashCode11 = this.integerValue.hashCode();
        }
        int i12 = (i11 + hashCode11) * 31;
        if (this.portalConnectionStateValue == null) {
            hashCode12 = 0;
        } else {
            hashCode12 = this.portalConnectionStateValue.hashCode();
        }
        int hashCode16 = (((i12 + hashCode12) * 31) + Arrays.hashCode(this.resourceName)) * 31;
        if (this.ruleStatusValue == null) {
            hashCode13 = 0;
        } else {
            hashCode13 = this.ruleStatusValue.hashCode();
        }
        int i13 = (hashCode16 + hashCode13) * 31;
        if (this.scheduleStatusValue == null) {
            hashCode14 = 0;
        } else {
            hashCode14 = this.scheduleStatusValue.hashCode();
        }
        int hashCode17 = (((((i13 + hashCode14) * 31) + Arrays.hashCode(this.stringArrayValue)) * 31) + Arrays.hashCode(this.stringValue)) * 31;
        if (this.switchButtonEventValue == null) {
            hashCode15 = 0;
        } else {
            hashCode15 = this.switchButtonEventValue.hashCode();
        }
        int hashCode18 = (((((((hashCode17 + hashCode15) * 31) + (this.lightArchetype == null ? 0 : this.lightArchetype.hashCode())) * 31) + (this.lightDirection == null ? 0 : this.lightDirection.hashCode())) * 31) + (this.lightFunction == null ? 0 : this.lightFunction.hashCode())) * 31;
        if (this.startUpMode != null) {
            i = this.startUpMode.hashCode();
        }
        return hashCode18 + i;
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
        ClipResponse other = (ClipResponse) obj;
        if (this.alertValue != other.alertValue) {
            return false;
        }
        if (this.booleanValue == null) {
            if (other.booleanValue != null) {
                return false;
            }
        } else if (!this.booleanValue.equals(other.booleanValue)) {
            return false;
        }
        if (this.bridgeBackupStatusValue != other.bridgeBackupStatusValue) {
            return false;
        }
        if (this.clipOperatorValue != other.clipOperatorValue) {
            return false;
        }
        if (this.colorModeValue != other.colorModeValue) {
            return false;
        }
        if (this.dateValue == null) {
            if (other.dateValue != null) {
                return false;
            }
        } else if (!this.dateValue.equals(other.dateValue)) {
            return false;
        }
        if (this.doublePairValue == null) {
            if (other.doublePairValue != null) {
                return false;
            }
        } else if (!this.doublePairValue.equals(other.doublePairValue)) {
            return false;
        }
        if (this.doubleValue == null) {
            if (other.doubleValue != null) {
                return false;
            }
        } else if (!this.doubleValue.equals(other.doubleValue)) {
            return false;
        }
        if (this.effectValue != other.effectValue) {
            return false;
        }
        if (this.groupTypeValue != other.groupTypeValue) {
            return false;
        }
        if (this.integerValue == null) {
            if (other.integerValue != null) {
                return false;
            }
        } else if (!this.integerValue.equals(other.integerValue)) {
            return false;
        }
        if (this.portalConnectionStateValue != other.portalConnectionStateValue) {
            return false;
        }
        if (!Arrays.equals(this.resourceName, other.resourceName)) {
            return false;
        }
        if (this.ruleStatusValue != other.ruleStatusValue) {
            return false;
        }
        if (this.scheduleStatusValue != other.scheduleStatusValue) {
            return false;
        }
        if (!Arrays.deepEquals(this.stringArrayValue, other.stringArrayValue)) {
            return false;
        }
        if (!Arrays.equals(this.stringValue, other.stringValue)) {
            return false;
        }
        if (this.switchButtonEventValue != other.switchButtonEventValue) {
            return false;
        }
        return true;
    }

    public BridgeBackupStatus getBridgeBackupStatusValue() {
        return this.bridgeBackupStatusValue;
    }
}
