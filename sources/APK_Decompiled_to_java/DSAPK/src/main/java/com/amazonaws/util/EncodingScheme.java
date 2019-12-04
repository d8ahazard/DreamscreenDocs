package com.amazonaws.util;

public interface EncodingScheme {
    byte[] decode(String str);

    String encodeAsString(byte[] bArr);
}
