package com.philips.lighting.hue.sdk.wrapper.domain.device.sensor;

import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.Alert;
import com.philips.lighting.hue.sdk.wrapper.domain.device.DeviceConfiguration;
import com.philips.lighting.hue.sdk.wrapper.utilities.NativeTools;
import java.util.ArrayList;
import java.util.List;

public class SensorConfiguration implements DeviceConfiguration {
    protected Alert alert;
    protected Integer battery;
    protected Boolean ledIndication;
    protected byte[] manufacturerName;
    protected byte[] modelIdentifier;
    protected byte[] name;
    protected Boolean on;
    protected byte[][] pending;
    protected Boolean reachable;
    protected Boolean recycle;
    protected DomainType sensorType;
    protected byte[] swVersion;
    protected byte[] uniqueId;
    protected byte[] url;
    protected Boolean usertest;

    protected SensorConfiguration(DomainType type) {
        this.alert = null;
        this.pending = null;
        this.sensorType = type;
    }

    public SensorConfiguration(DomainType type, SensorConfiguration config) {
        this(type);
        this.name = config.name;
        this.modelIdentifier = config.modelIdentifier;
        this.swVersion = config.swVersion;
        this.manufacturerName = config.manufacturerName;
        this.uniqueId = config.uniqueId;
        this.recycle = config.recycle;
        this.on = config.on;
        this.reachable = config.reachable;
        this.battery = config.battery;
        this.alert = config.alert;
        this.usertest = config.usertest;
        this.url = config.url;
        this.pending = config.pending;
        this.ledIndication = config.ledIndication;
    }

    public SensorConfiguration() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public SensorConfiguration(String name2, String modelIdentifier2, String swVersion2, String manufacturerName2, String uniqueId2, Boolean recycle2, Boolean on2, Boolean reachable2, Integer battery2, Alert alert2, Boolean usertest2, String url2, Boolean ledIndication2) {
        this(DomainType.SENSOR);
        setName(name2);
        setModelIdentifier(modelIdentifier2);
        setSwVersion(swVersion2);
        setManufacturerName(manufacturerName2);
        setUniqueIdentifier(uniqueId2);
        this.recycle = recycle2;
        this.on = on2;
        this.reachable = reachable2;
        this.battery = battery2;
        this.alert = alert2;
        this.usertest = usertest2;
        setUrl(url2);
        this.ledIndication = ledIndication2;
    }

    public String getName() {
        return NativeTools.BytesToString(this.name);
    }

    public void setName(String name2) {
        this.name = NativeTools.StringToBytes(name2);
    }

    public String getModelIdentifier() {
        return NativeTools.BytesToString(this.modelIdentifier);
    }

    public String getSwVersion() {
        return NativeTools.BytesToString(this.swVersion);
    }

    public Boolean getRecycle() {
        return this.recycle;
    }

    public void setRecycle(Boolean recycle2) {
        this.recycle = recycle2;
    }

    public Boolean getOn() {
        return this.on;
    }

    public void setOn(Boolean on2) {
        this.on = on2;
    }

    public Alert getAlert() {
        return this.alert;
    }

    public void setAlert(Alert alert2) {
        this.alert = alert2;
    }

    public Boolean getUsertest() {
        return this.usertest;
    }

    public void setUsertest(Boolean usertest2) {
        this.usertest = usertest2;
    }

    public List<String> getPending() {
        if (this.pending == null) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (byte[] pending_item : this.pending) {
            arrayList.add(NativeTools.BytesToString(pending_item));
        }
        return arrayList;
    }

    public Boolean getLedIndication() {
        return this.ledIndication;
    }

    public void setLedIndication(Boolean ledIndication2) {
        this.ledIndication = ledIndication2;
    }

    public String getUrl() {
        return NativeTools.BytesToString(this.url);
    }

    public void setUrl(String url2) {
        this.url = NativeTools.StringToBytes(url2);
    }

    public String getManufacturerName() {
        return NativeTools.BytesToString(this.manufacturerName);
    }

    public String getUniqueIdentifier() {
        return NativeTools.BytesToString(this.uniqueId);
    }

    public Boolean getReachable() {
        return this.reachable;
    }

    public Integer getBattery() {
        return this.battery;
    }

    public void setBattery(Integer battery2) {
        this.battery = battery2;
    }

    public boolean isOfType(DomainType type) {
        if (type == getType() || type == DomainType.SENSOR_CONFIGURATION) {
            return true;
        }
        return false;
    }

    public DomainType getType() {
        return this.sensorType;
    }

    public void setModelIdentifier(String modelIdentifier2) {
        this.modelIdentifier = NativeTools.StringToBytes(modelIdentifier2);
    }

    public void setSwVersion(String swVersion2) {
        this.swVersion = NativeTools.StringToBytes(swVersion2);
    }

    public void setManufacturerName(String manufacturerName2) {
        this.manufacturerName = NativeTools.StringToBytes(manufacturerName2);
    }

    public void setUniqueIdentifier(String uniqueId2) {
        this.uniqueId = NativeTools.StringToBytes(uniqueId2);
    }

    /* access modifiers changed from: protected */
    public int getTypeAsInt() {
        if (this.sensorType != null) {
            return this.sensorType.getValue();
        }
        return DomainType.UNKNOWN.getValue();
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
        int i = 0;
        int hashCode9 = ((((this.alert == null ? 0 : this.alert.hashCode()) + 31) * 31) + (this.battery == null ? 0 : this.battery.hashCode())) * 31;
        if (this.manufacturerName == null) {
            hashCode = 0;
        } else {
            hashCode = this.manufacturerName.hashCode();
        }
        int i2 = (hashCode9 + hashCode) * 31;
        if (this.modelIdentifier == null) {
            hashCode2 = 0;
        } else {
            hashCode2 = this.modelIdentifier.hashCode();
        }
        int hashCode10 = (((((i2 + hashCode2) * 31) + (this.name == null ? 0 : this.name.hashCode())) * 31) + (this.on == null ? 0 : this.on.hashCode())) * 31;
        if (this.reachable == null) {
            hashCode3 = 0;
        } else {
            hashCode3 = this.reachable.hashCode();
        }
        int hashCode11 = (((hashCode10 + hashCode3) * 31) + (this.recycle == null ? 0 : this.recycle.hashCode())) * 31;
        if (this.sensorType == null) {
            hashCode4 = 0;
        } else {
            hashCode4 = this.sensorType.hashCode();
        }
        int i3 = (hashCode11 + hashCode4) * 31;
        if (this.swVersion == null) {
            hashCode5 = 0;
        } else {
            hashCode5 = this.swVersion.hashCode();
        }
        int i4 = (i3 + hashCode5) * 31;
        if (this.uniqueId == null) {
            hashCode6 = 0;
        } else {
            hashCode6 = this.uniqueId.hashCode();
        }
        int hashCode12 = (((i4 + hashCode6) * 31) + (this.url == null ? 0 : this.url.hashCode())) * 31;
        if (this.usertest == null) {
            hashCode7 = 0;
        } else {
            hashCode7 = this.usertest.hashCode();
        }
        int i5 = (hashCode12 + hashCode7) * 31;
        if (this.pending == null) {
            hashCode8 = 0;
        } else {
            hashCode8 = this.pending.hashCode();
        }
        int i6 = (i5 + hashCode8) * 31;
        if (this.ledIndication != null) {
            i = this.ledIndication.hashCode();
        }
        return i6 + i;
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
        SensorConfiguration other = (SensorConfiguration) obj;
        if (this.alert != other.alert) {
            return false;
        }
        if (this.battery == null) {
            if (other.battery != null) {
                return false;
            }
        } else if (!this.battery.equals(other.battery)) {
            return false;
        }
        if (this.manufacturerName == null) {
            if (other.manufacturerName != null) {
                return false;
            }
        } else if (!this.manufacturerName.equals(other.manufacturerName)) {
            return false;
        }
        if (this.modelIdentifier == null) {
            if (other.modelIdentifier != null) {
                return false;
            }
        } else if (!this.modelIdentifier.equals(other.modelIdentifier)) {
            return false;
        }
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        if (this.on == null) {
            if (other.on != null) {
                return false;
            }
        } else if (!this.on.equals(other.on)) {
            return false;
        }
        if (this.reachable == null) {
            if (other.reachable != null) {
                return false;
            }
        } else if (!this.reachable.equals(other.reachable)) {
            return false;
        }
        if (this.recycle == null) {
            if (other.recycle != null) {
                return false;
            }
        } else if (!this.recycle.equals(other.recycle)) {
            return false;
        }
        if (this.sensorType != other.sensorType) {
            return false;
        }
        if (this.swVersion == null) {
            if (other.swVersion != null) {
                return false;
            }
        } else if (!this.swVersion.equals(other.swVersion)) {
            return false;
        }
        if (this.uniqueId == null) {
            if (other.uniqueId != null) {
                return false;
            }
        } else if (!this.uniqueId.equals(other.uniqueId)) {
            return false;
        }
        if (this.url == null) {
            if (other.url != null) {
                return false;
            }
        } else if (!this.url.equals(other.url)) {
            return false;
        }
        if (this.usertest == null) {
            if (other.usertest != null) {
                return false;
            }
        } else if (!this.usertest.equals(other.usertest)) {
            return false;
        }
        if (this.pending == null) {
            if (other.pending != null) {
                return false;
            }
        } else if (!this.pending.equals(other.pending)) {
            return false;
        }
        if (this.ledIndication == null) {
            if (other.ledIndication != null) {
                return false;
            }
            return true;
        } else if (!this.ledIndication.equals(other.ledIndication)) {
            return false;
        } else {
            return true;
        }
    }
}
