package com.amazonaws.util;

interface Codec {
    byte[] decode(byte[] bArr, int i);

    byte[] encode(byte[] bArr);
}
