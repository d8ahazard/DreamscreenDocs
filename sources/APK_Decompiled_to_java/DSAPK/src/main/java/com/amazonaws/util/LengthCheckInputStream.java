package com.amazonaws.util;

import com.amazonaws.AmazonClientException;
import com.amazonaws.internal.SdkFilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LengthCheckInputStream extends SdkFilterInputStream {
    public static final boolean EXCLUDE_SKIPPED_BYTES = false;
    public static final boolean INCLUDE_SKIPPED_BYTES = true;
    private long dataLength;
    private final long expectedLength;
    private final boolean includeSkipped;
    private long marked;

    public LengthCheckInputStream(InputStream in, long expectedLength2, boolean includeSkipped2) {
        super(in);
        if (expectedLength2 < 0) {
            throw new IllegalArgumentException();
        }
        this.expectedLength = expectedLength2;
        this.includeSkipped = includeSkipped2;
    }

    public int read() throws IOException {
        int c = super.read();
        if (c >= 0) {
            this.dataLength++;
        }
        checkLength(c == -1);
        return c;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int readLen = super.read(b, off, len);
        this.dataLength = (readLen >= 0 ? (long) readLen : 0) + this.dataLength;
        checkLength(readLen == -1);
        return readLen;
    }

    public void mark(int readlimit) {
        super.mark(readlimit);
        this.marked = this.dataLength;
    }

    public void reset() throws IOException {
        super.reset();
        if (super.markSupported()) {
            this.dataLength = this.marked;
        }
    }

    private void checkLength(boolean eof) {
        if (eof) {
            if (this.dataLength != this.expectedLength) {
                throw new AmazonClientException("Data read (" + this.dataLength + ") has a different length than the expected (" + this.expectedLength + ")");
            }
        } else if (this.dataLength > this.expectedLength) {
            throw new AmazonClientException("More data read (" + this.dataLength + ") than expected (" + this.expectedLength + ")");
        }
    }

    public long skip(long n) throws IOException {
        long skipped = super.skip(n);
        if (this.includeSkipped && skipped > 0) {
            this.dataLength += skipped;
            checkLength(false);
        }
        return skipped;
    }
}
