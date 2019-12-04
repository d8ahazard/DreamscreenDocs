package com.amazonaws.util;

import org.apache.commons.logging.LogFactory;

public enum Throwables {
    ;
    
    private static final int MAX_RETRY = 1000;

    public static Throwable getRootCause(Throwable orig) {
        if (orig == null) {
            return orig;
        }
        Throwable t = orig;
        for (int i = 0; i < 1000; i++) {
            Throwable cause = t.getCause();
            if (cause == null) {
                return t;
            }
            t = cause;
        }
        LogFactory.getLog(Throwables.class).debug("Possible circular reference detected on " + orig.getClass() + ": [" + orig + "]");
        return orig;
    }
}
