package com.amazonaws.util;

import com.amazonaws.internal.SdkFilterInputStream;
import java.io.IOException;
import java.io.InputStream;

@Deprecated
public class CountingInputStream extends SdkFilterInputStream {
    private long byteCount = 0;

    public CountingInputStream(InputStream in) {
        super(in);
    }

    public long getByteCount() {
        return this.byteCount;
    }

    public int read() throws IOException {
        int tmp = super.read();
        this.byteCount = (tmp >= 0 ? 1 : 0) + this.byteCount;
        return tmp;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int tmp = super.read(b, off, len);
        this.byteCount = (tmp >= 0 ? (long) tmp : 0) + this.byteCount;
        return tmp;
    }
}
