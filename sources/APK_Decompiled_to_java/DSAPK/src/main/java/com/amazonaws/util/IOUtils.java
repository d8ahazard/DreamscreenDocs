package com.amazonaws.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public enum IOUtils {
    ;
    
    private static final int BUFFER_SIZE = 4096;
    private static final Log logger = null;

    static {
        logger = LogFactory.getLog(IOUtils.class);
    }

    public static byte[] toByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] b = new byte[4096];
            while (true) {
                int n = is.read(b);
                if (n == -1) {
                    return output.toByteArray();
                }
                output.write(b, 0, n);
            }
        } finally {
            output.close();
        }
    }

    public static String toString(InputStream is) throws IOException {
        return new String(toByteArray(is), StringUtils.UTF8);
    }

    public static void closeQuietly(Closeable is, Log log) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException ex) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Ignore failure in closing the Closeable", ex);
                }
            }
        }
    }

    public static void release(Closeable is, Log log) {
        closeQuietly(is, log);
    }

    public static long copy(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[4096];
        long count = 0;
        while (true) {
            int n = in.read(buf);
            if (n <= -1) {
                return count;
            }
            out.write(buf, 0, n);
            count += (long) n;
        }
    }
}
