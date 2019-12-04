package com.amazonaws.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Locale;

public class StringUtils {
    private static final String DEFAULT_ENCODING = "UTF-8";
    public static final Charset UTF8 = Charset.forName(DEFAULT_ENCODING);

    public static Integer toInteger(StringBuilder value) {
        return Integer.valueOf(Integer.parseInt(value.toString()));
    }

    public static String toString(StringBuilder value) {
        return value.toString();
    }

    public static Boolean toBoolean(StringBuilder value) {
        return Boolean.valueOf(Boolean.getBoolean(value.toString()));
    }

    public static String fromInteger(Integer value) {
        return Integer.toString(value.intValue());
    }

    public static String fromLong(Long value) {
        return Long.toString(value.longValue());
    }

    public static String fromString(String value) {
        return value;
    }

    public static String fromBoolean(Boolean value) {
        return Boolean.toString(value.booleanValue());
    }

    public static String fromBigInteger(BigInteger value) {
        return value.toString();
    }

    public static String fromBigDecimal(BigDecimal value) {
        return value.toString();
    }

    public static BigInteger toBigInteger(String s) {
        return new BigInteger(s);
    }

    public static BigDecimal toBigDecimal(String s) {
        return new BigDecimal(s);
    }

    public static String fromFloat(Float value) {
        return Float.toString(value.floatValue());
    }

    public static String fromDate(Date value) {
        return DateUtils.formatISO8601Date(value);
    }

    public static String fromDouble(Double d) {
        return Double.toString(d.doubleValue());
    }

    public static String fromByte(Byte b) {
        return Byte.toString(b.byteValue());
    }

    public static String fromByteBuffer(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            return Base64.encodeAsString(byteBuffer.array());
        }
        byte[] binaryData = new byte[byteBuffer.limit()];
        byteBuffer.get(binaryData);
        return Base64.encodeAsString(binaryData);
    }

    public static String replace(String originalString, String partToMatch, String replacement) {
        StringBuffer buffer = new StringBuffer(originalString.length());
        buffer.append(originalString);
        int indexOf = buffer.indexOf(partToMatch);
        while (indexOf != -1) {
            buffer = buffer.replace(indexOf, partToMatch.length() + indexOf, replacement);
            indexOf = buffer.indexOf(partToMatch);
        }
        return buffer.toString();
    }

    public static String join(String joiner, String... parts) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            builder.append(parts[i]);
            if (i < parts.length - 1) {
                builder.append(joiner);
            }
        }
        return builder.toString();
    }

    public static String lowerCase(String str) {
        if (str == null) {
            return null;
        }
        if (str.isEmpty()) {
            return "";
        }
        return str.toLowerCase(Locale.ENGLISH);
    }

    public static String upperCase(String str) {
        if (str == null) {
            return null;
        }
        if (str.isEmpty()) {
            return "";
        }
        return str.toUpperCase(Locale.ENGLISH);
    }

    public static boolean isBlank(CharSequence cs) {
        if (cs == null) {
            return true;
        }
        int strLen = cs.length();
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
