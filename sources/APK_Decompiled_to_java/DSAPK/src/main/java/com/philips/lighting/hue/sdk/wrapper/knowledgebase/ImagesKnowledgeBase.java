package com.philips.lighting.hue.sdk.wrapper.knowledgebase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImagesKnowledgeBase implements KnowledgeBase {
    private List<Image> images = new ArrayList();

    private ImagesKnowledgeBase(Image[] images2) {
        this.images = new ArrayList(Arrays.asList(images2));
    }

    public Image getImage(String identifier) {
        for (Image i : this.images) {
            if (i.getIdentifier().equals(identifier)) {
                return i;
            }
        }
        return null;
    }

    public List<Image> getImages() {
        return this.images;
    }

    public int hashCode() {
        return (this.images == null ? 0 : this.images.hashCode()) + 31;
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
        ImagesKnowledgeBase other = (ImagesKnowledgeBase) obj;
        if (this.images == null) {
            if (other.images != null) {
                return false;
            }
            return true;
        } else if (!this.images.equals(other.images)) {
            return false;
        } else {
            return true;
        }
    }
}
