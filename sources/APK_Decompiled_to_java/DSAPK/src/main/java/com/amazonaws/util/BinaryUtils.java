package com.amazonaws.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class BinaryUtils {
    private static final int FF_LOCATION = 6;
    private static final int HEX_LENGTH_8 = 8;
    private static final int HEX_PARSE_16 = 16;

    public static String toHex(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte hexString : data) {
            String hex = Integer.toHexString(hexString);
            if (hex.length() == 1) {
                sb.append("0");
            } else if (hex.length() == 8) {
                hex = hex.substring(6);
            }
            sb.append(hex);
        }
        return StringUtils.lowerCase(sb.toString());
    }

    public static byte[] fromHex(String hexData) {
        byte[] result = new byte[((hexData.length() + 1) / 2)];
        int stringOffset = 0;
        int byteOffset = 0;
        while (stringOffset < hexData.length()) {
            String hexNumber = hexData.substring(stringOffset, stringOffset + 2);
            stringOffset += 2;
            int byteOffset2 = byteOffset + 1;
            result[byteOffset] = (byte) Integer.parseInt(hexNumber, 16);
            byteOffset = byteOffset2;
        }
        return result;
    }

    public static String toBase64(byte[] data) {
        return Base64.encodeAsString(data);
    }

    public static byte[] fromBase64(String b64Data) {
        if (b64Data == null) {
            return null;
        }
        return Base64.decode(b64Data);
    }

    public static InputStream toStream(ByteBuffer byteBuffer) {
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        return new ByteArrayInputStream(bytes);
    }

    public static byte[] copyAllBytesFrom(ByteBuffer bb) {
        if (bb == null) {
            return null;
        }
        if (bb.hasArray()) {
            return Arrays.copyOfRange(bb.array(), bb.arrayOffset(), bb.arrayOffset() + bb.limit());
        }
        ByteBuffer copy = bb.asReadOnlyBuffer();
        copy.rewind();
        byte[] dst = new byte[copy.remaining()];
        copy.get(dst);
        return dst;
    }
}
