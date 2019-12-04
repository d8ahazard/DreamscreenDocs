package com.philips.lighting.hue.sdk.wrapper.knowledgebase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LightsKnowledgeBase implements KnowledgeBase {
    private List<Gamut> gamuts = new ArrayList();
    private List<LightInfo> lights = new ArrayList();

    private LightsKnowledgeBase(Gamut[] gamuts2, LightInfo[] lights2) {
        this.gamuts = new ArrayList(Arrays.asList(gamuts2));
        this.lights = new ArrayList(Arrays.asList(lights2));
    }

    public List<Gamut> getGamuts() {
        return this.gamuts;
    }

    public List<LightInfo> getLights() {
        return this.lights;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((this.gamuts == null ? 0 : this.gamuts.hashCode()) + 31) * 31;
        if (this.lights != null) {
            i = this.lights.hashCode();
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
        LightsKnowledgeBase other = (LightsKnowledgeBase) obj;
        if (this.gamuts == null) {
            if (other.gamuts != null) {
                return false;
            }
        } else if (!this.gamuts.equals(other.gamuts)) {
            return false;
        }
        if (this.lights == null) {
            if (other.lights != null) {
                return false;
            }
            return true;
        } else if (!this.lights.equals(other.lights)) {
            return false;
        } else {
            return true;
        }
    }
}
