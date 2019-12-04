package com.amazonaws.internal;

import com.amazonaws.AmazonClientException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ResettableInputStream extends ReleasableInputStream {
    private static final Log log = LogFactory.getLog(ResettableInputStream.class);
    private final File file;
    private final FileChannel fileChannel;
    private final FileInputStream fis;
    private long markPos;

    public ResettableInputStream(File file2) throws IOException {
        this(new FileInputStream(file2), file2);
    }

    public ResettableInputStream(FileInputStream fis2) throws IOException {
        this(fis2, null);
    }

    private ResettableInputStream(FileInputStream fis2, File file2) throws IOException {
        super(fis2);
        this.file = file2;
        this.fis = fis2;
        this.fileChannel = fis2.getChannel();
        this.markPos = this.fileChannel.position();
    }

    public final boolean markSupported() {
        return true;
    }

    public void mark(int _) {
        abortIfNeeded();
        try {
            this.markPos = this.fileChannel.position();
            if (log.isTraceEnabled()) {
                log.trace("File input stream marked at position " + this.markPos);
            }
        } catch (IOException e) {
            throw new AmazonClientException("Failed to mark the file position", e);
        }
    }

    public void reset() throws IOException {
        abortIfNeeded();
        this.fileChannel.position(this.markPos);
        if (log.isTraceEnabled()) {
            log.trace("Reset to position " + this.markPos);
        }
    }

    public int available() throws IOException {
        abortIfNeeded();
        return this.fis.available();
    }

    public int read() throws IOException {
        abortIfNeeded();
        return this.fis.read();
    }

    public long skip(long n) throws IOException {
        abortIfNeeded();
        return this.fis.skip(n);
    }

    public int read(byte[] arg0, int arg1, int arg2) throws IOException {
        abortIfNeeded();
        return this.fis.read(arg0, arg1, arg2);
    }

    public File getFile() {
        return this.file;
    }

    public static ResettableInputStream newResettableInputStream(File file2) {
        return newResettableInputStream(file2, (String) null);
    }

    public static ResettableInputStream newResettableInputStream(File file2, String errmsg) {
        try {
            return new ResettableInputStream(file2);
        } catch (IOException e) {
            throw (errmsg == null ? new AmazonClientException((Throwable) e) : new AmazonClientException(errmsg, e));
        }
    }

    public static ResettableInputStream newResettableInputStream(FileInputStream fis2) {
        return newResettableInputStream(fis2, (String) null);
    }

    public static ResettableInputStream newResettableInputStream(FileInputStream fis2, String errmsg) {
        try {
            return new ResettableInputStream(fis2);
        } catch (IOException e) {
            throw new AmazonClientException(errmsg, e);
        }
    }
}
