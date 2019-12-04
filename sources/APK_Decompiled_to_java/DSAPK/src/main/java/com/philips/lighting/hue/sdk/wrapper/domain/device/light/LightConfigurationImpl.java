package com.philips.lighting.hue.sdk.wrapper.domain.device.light;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;
import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.LightArchetype;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.LightDirection;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.LightFunction;
import java.util.Objects;

public final class LightConfigurationImpl extends WrapperObject implements LightConfiguration {
    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void create(String str);

    /* access modifiers changed from: protected */
    public native void create(String str, String str2, String str3, String str4, String str5, String str6, LightArchetype lightArchetype, LightDirection lightDirection, LightFunction lightFunction, LightStartUpState lightStartUpState);

    /* access modifiers changed from: protected */
    public native void delete();

    public final native LightArchetype getArchetype();

    public final native LightDirection getDirection();

    public final native LightFunction getFunction();

    public final native LightStartUpState getLightStartUpState();

    public final native String getLuminaireUniqueId();

    public final native String getManufacturerName();

    public final native String getModelId();

    public final native String getName();

    public final native String getSwVersion();

    public final native String getUniqueId();

    public final native void setArchetype(LightArchetype lightArchetype);

    public final native void setDirection(LightDirection lightDirection);

    public final native void setFunction(LightFunction lightFunction);

    public final native void setLightStartUpState(LightStartUpState lightStartUpState);

    public final native void setLuminaireUniqueId(String str);

    public final native void setName(String str);

    public LightConfigurationImpl() {
        create();
    }

    public LightConfigurationImpl(String name, String modelId, String swVersion, String manufacturerName, String uniqueId, String luminaireUniqueId, LightArchetype archetype, LightDirection direction, LightFunction function, LightStartUpState lightStartupState) {
        create(name, modelId, swVersion, manufacturerName, uniqueId, luminaireUniqueId, archetype, direction, function, lightStartupState);
    }

    public LightConfigurationImpl(String name) {
        create(name);
    }

    protected LightConfigurationImpl(Scope scope) {
    }

    public DomainType getType() {
        return DomainType.LIGHT_CONFIGURATION;
    }

    @Deprecated
    public String getModelIdentifier() {
        return getModelId();
    }

    @Deprecated
    public String getUniqueIdentifier() {
        return getUniqueId();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{getLuminaireUniqueId(), getManufacturerName(), getModelId(), getName(), getSwVersion(), getUniqueId(), getArchetype(), getDirection(), getFunction(), getLightStartUpState()});
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
        LightConfiguration other = (LightConfiguration) obj;
        if (!Objects.equals(getLuminaireUniqueId(), other.getLuminaireUniqueId()) || !Objects.equals(getManufacturerName(), other.getManufacturerName()) || !Objects.equals(getModelId(), other.getModelId()) || !Objects.equals(getName(), other.getName()) || !Objects.equals(getSwVersion(), other.getSwVersion()) || !Objects.equals(getUniqueId(), other.getUniqueId()) || !Objects.equals(getArchetype(), other.getArchetype()) || !Objects.equals(getDirection(), other.getDirection()) || !Objects.equals(getFunction(), other.getFunction()) || !Objects.equals(getLightStartUpState(), other.getLightStartUpState())) {
            return false;
        }
        return true;
    }
}
