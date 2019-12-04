package com.amazonaws.util;

public enum Base32 {
    ;
    
    private static final Base32Codec CODEC = null;

    static {
        CODEC = new Base32Codec();
    }

    public static String encodeAsString(byte... bytes) {
        if (bytes == null) {
            return null;
        }
        return bytes.length == 0 ? "" : CodecUtils.toStringDirect(CODEC.encode(bytes));
    }

    public static byte[] encode(byte[] bytes) {
        return (bytes == null || bytes.length == 0) ? bytes : CODEC.encode(bytes);
    }

    public static byte[] decode(String b32) {
        if (b32 == null) {
            return null;
        }
        if (b32.length() == 0) {
            return new byte[0];
        }
        byte[] buf = new byte[b32.length()];
        return CODEC.decode(buf, CodecUtils.sanitize(b32, buf));
    }

    public static byte[] decode(byte[] b32) {
        return (b32 == null || b32.length == 0) ? b32 : CODEC.decode(b32, b32.length);
    }
}
