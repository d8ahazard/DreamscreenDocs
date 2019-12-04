package com.amazonaws.internal;

import java.util.ArrayList;
import java.util.Collection;

public class ListWithAutoConstructFlag<T> extends ArrayList<T> {
    private static final long serialVersionUID = 1;
    private boolean autoConstruct;

    public ListWithAutoConstructFlag() {
    }

    public ListWithAutoConstructFlag(Collection<? extends T> c) {
        super(c);
    }

    public ListWithAutoConstructFlag(int initialCapacity) {
        super(initialCapacity);
    }

    public void setAutoConstruct(boolean autoConstruct2) {
        this.autoConstruct = autoConstruct2;
    }

    public boolean isAutoConstruct() {
        return this.autoConstruct;
    }
}
