package com.amazonaws.util;

import com.amazonaws.internal.SdkFilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;

public class CRC32ChecksumCalculatingInputStream extends SdkFilterInputStream {
    private CRC32 crc32 = new CRC32();

    public CRC32ChecksumCalculatingInputStream(InputStream in) {
        super(in);
    }

    public long getCRC32Checksum() {
        return this.crc32.getValue();
    }

    public synchronized void reset() throws IOException {
        abortIfNeeded();
        this.crc32.reset();
        this.in.reset();
    }

    public int read() throws IOException {
        abortIfNeeded();
        int ch = this.in.read();
        if (ch != -1) {
            this.crc32.update(ch);
        }
        return ch;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        abortIfNeeded();
        int result = this.in.read(b, off, len);
        if (result != -1) {
            this.crc32.update(b, off, result);
        }
        return result;
    }
}
