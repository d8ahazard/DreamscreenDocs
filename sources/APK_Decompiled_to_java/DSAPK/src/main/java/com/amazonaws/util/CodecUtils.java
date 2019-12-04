package com.amazonaws.util;

public enum CodecUtils {
    ;

    static int sanitize(String singleOctets, byte[] dest) {
        int limit;
        int capacity = dest.length;
        char[] src = singleOctets.toCharArray();
        int i = 0;
        int limit2 = 0;
        while (i < capacity) {
            char c = src[i];
            if (c == 13 || c == 10) {
                limit = limit2;
            } else if (c == ' ') {
                limit = limit2;
            } else if (c > 127) {
                throw new IllegalArgumentException("Invalid character found at position " + i + " for " + singleOctets);
            } else {
                limit = limit2 + 1;
                dest[limit2] = (byte) c;
            }
            i++;
            limit2 = limit;
        }
        return limit2;
    }

    public static byte[] toBytesDirect(String singleOctets) {
        char[] src = singleOctets.toCharArray();
        byte[] dest = new byte[src.length];
        for (int i = 0; i < dest.length; i++) {
            char c = src[i];
            if (c > 127) {
                throw new IllegalArgumentException("Invalid character found at position " + i + " for " + singleOctets);
            }
            dest[i] = (byte) c;
        }
        return dest;
    }

    public static String toStringDirect(byte[] bytes) {
        char[] dest = new char[bytes.length];
        int length = bytes.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = i2 + 1;
            dest[i2] = (char) bytes[i];
            i++;
            i2 = i3;
        }
        return new String(dest);
    }

    static void sanityCheckLastPos(int pos, int mask) {
        if ((pos & mask) != 0) {
            throw new IllegalArgumentException("Invalid last non-pad character detected");
        }
    }
}
