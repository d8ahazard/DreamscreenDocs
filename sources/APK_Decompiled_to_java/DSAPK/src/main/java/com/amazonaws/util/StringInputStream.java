package com.amazonaws.util;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

public class StringInputStream extends ByteArrayInputStream {
    private final String string;

    public StringInputStream(String s) throws UnsupportedEncodingException {
        super(s.getBytes(StringUtils.UTF8));
        this.string = s;
    }

    public String getString() {
        return this.string;
    }
}
