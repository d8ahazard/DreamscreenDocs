package com.amazonaws.util;

import com.amazonaws.internal.SdkFilterInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

class NamespaceRemovingInputStream extends SdkFilterInputStream {
    private static final int BUFFER_SIZE = 200;
    private boolean hasRemovedNamespace = false;
    private final byte[] lookAheadData = new byte[200];

    private static final class StringPrefixSlicer {
        private String s;

        public StringPrefixSlicer(String s2) {
            this.s = s2;
        }

        public String getString() {
            return this.s;
        }

        public boolean removePrefix(String prefix) {
            if (!this.s.startsWith(prefix)) {
                return false;
            }
            this.s = this.s.substring(prefix.length());
            return true;
        }

        public boolean removeRepeatingPrefix(String prefix) {
            if (!this.s.startsWith(prefix)) {
                return false;
            }
            while (this.s.startsWith(prefix)) {
                this.s = this.s.substring(prefix.length());
            }
            return true;
        }

        public boolean removePrefixEndingWith(String marker) {
            int i = this.s.indexOf(marker);
            if (i < 0) {
                return false;
            }
            this.s = this.s.substring(marker.length() + i);
            return true;
        }
    }

    public NamespaceRemovingInputStream(InputStream in) {
        super(new BufferedInputStream(in));
    }

    public int read() throws IOException {
        abortIfNeeded();
        int b = this.in.read();
        if (b != 120 || this.hasRemovedNamespace) {
            return b;
        }
        this.lookAheadData[0] = (byte) b;
        this.in.mark(this.lookAheadData.length);
        int bytesRead = this.in.read(this.lookAheadData, 1, this.lookAheadData.length - 1);
        this.in.reset();
        int numberCharsMatched = matchXmlNamespaceAttribute(new String(this.lookAheadData, 0, bytesRead + 1, StringUtils.UTF8));
        if (numberCharsMatched <= 0) {
            return b;
        }
        for (int i = 0; i < numberCharsMatched - 1; i++) {
            this.in.read();
        }
        int b2 = this.in.read();
        this.hasRemovedNamespace = true;
        return b2;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int i = 0;
        while (i < len) {
            int j = read();
            if (j != -1) {
                b[i + off] = (byte) j;
                i++;
            } else if (i == 0) {
                return -1;
            } else {
                return i;
            }
        }
        return len;
    }

    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    private int matchXmlNamespaceAttribute(String s) {
        StringPrefixSlicer stringSlicer = new StringPrefixSlicer(s);
        if (!stringSlicer.removePrefix("xmlns")) {
            return -1;
        }
        stringSlicer.removeRepeatingPrefix(" ");
        if (!stringSlicer.removePrefix("=")) {
            return -1;
        }
        stringSlicer.removeRepeatingPrefix(" ");
        if (!stringSlicer.removePrefix("\"") || !stringSlicer.removePrefixEndingWith("\"")) {
            return -1;
        }
        return s.length() - stringSlicer.getString().length();
    }
}
