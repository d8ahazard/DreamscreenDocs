package com.amazonaws.internal;

import java.io.IOException;

public class CRC32MismatchException extends IOException {
    private static final long serialVersionUID = 1;

    public CRC32MismatchException(String message, Throwable t) {
        super(message, t);
    }

    public CRC32MismatchException(String message) {
        super(message);
    }
}
