package com.philips.lighting.hue.sdk.wrapper.knowledgebase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManufacturersKnowledgeBase implements KnowledgeBase {
    private List<Manufacturer> manufacturers = new ArrayList();

    private ManufacturersKnowledgeBase(Manufacturer[] manufacturers2) {
        this.manufacturers = new ArrayList(Arrays.asList(manufacturers2));
    }

    public Manufacturer getManufacturer(String identifier) {
        for (Manufacturer m : this.manufacturers) {
            if (m.getIdentifier().equals(identifier)) {
                return m;
            }
        }
        return null;
    }

    public List<Manufacturer> getManufacturers() {
        return this.manufacturers;
    }

    public int hashCode() {
        int hashCode;
        if (this.manufacturers == null) {
            hashCode = 0;
        } else {
            hashCode = this.manufacturers.hashCode();
        }
        return hashCode + 31;
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
        ManufacturersKnowledgeBase other = (ManufacturersKnowledgeBase) obj;
        if (this.manufacturers == null) {
            if (other.manufacturers != null) {
                return false;
            }
            return true;
        } else if (!this.manufacturers.equals(other.manufacturers)) {
            return false;
        } else {
            return true;
        }
    }
}
