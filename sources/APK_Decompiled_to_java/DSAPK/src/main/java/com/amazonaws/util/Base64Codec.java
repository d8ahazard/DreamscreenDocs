package com.amazonaws.util;

class Base64Codec implements Codec {
    private static final int BITS_3 = 3;
    private static final int BITS_4 = 4;
    private static final int BITS_6 = 6;
    private static final int MASK_2BITS = 3;
    private static final int MASK_4BITS = 15;
    private static final int MASK_6BITS = 63;
    private static final int OFFSET_0_VALUE = 52;
    private static final int OFFSET_OF_0 = -4;
    private static final int OFFSET_OF_PLUS = -19;
    private static final int OFFSET_OF_SLASH = -16;
    private static final int OFFSET_OF_a = 71;
    private static final int OFFSET_PLUS_VALUE = 62;
    private static final int OFFSET_SLASH_VALUE = 63;
    private static final int OFFSET_a_VALUE = 26;
    private static final byte PAD = 61;
    private final byte[] alpahbets;

    private static class LazyHolder {
        /* access modifiers changed from: private */
        public static final byte[] DECODED = decodeTable();

        private LazyHolder() {
        }

        private static byte[] decodeTable() {
            byte[] dest = new byte[123];
            for (int i = 0; i <= 122; i++) {
                if (i >= 65 && i <= 90) {
                    dest[i] = (byte) (i - 65);
                } else if (i >= 48 && i <= 57) {
                    dest[i] = (byte) (i + 4);
                } else if (i == 43) {
                    dest[i] = (byte) (i + 19);
                } else if (i == 47) {
                    dest[i] = (byte) (i + 16);
                } else if (i < 97 || i > 122) {
                    dest[i] = -1;
                } else {
                    dest[i] = (byte) (i - 71);
                }
            }
            return dest;
        }
    }

    Base64Codec() {
        this.alpahbets = CodecUtils.toBytesDirect("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
    }

    protected Base64Codec(byte[] alphabets) {
        this.alpahbets = alphabets;
    }

    public byte[] encode(byte[] src) {
        int num3bytes = src.length / 3;
        int remainder = src.length % 3;
        if (remainder == 0) {
            byte[] dest = new byte[(num3bytes * 4)];
            int s = 0;
            int d = 0;
            while (s < src.length) {
                encode3bytes(src, s, dest, d);
                s += 3;
                d += 4;
            }
            return dest;
        }
        byte[] dest2 = new byte[((num3bytes + 1) * 4)];
        int s2 = 0;
        int d2 = 0;
        while (s2 < src.length - remainder) {
            encode3bytes(src, s2, dest2, d2);
            s2 += 3;
            d2 += 4;
        }
        switch (remainder) {
            case 1:
                encode1byte(src, s2, dest2, d2);
                break;
            case 2:
                encode2bytes(src, s2, dest2, d2);
                break;
        }
        return dest2;
    }

    /* access modifiers changed from: 0000 */
    public void encode3bytes(byte[] src, int s, byte[] dest, int d) {
        int d2 = d + 1;
        byte[] bArr = this.alpahbets;
        int s2 = s + 1;
        byte p = src[s];
        dest[d] = bArr[(p >>> 2) & 63];
        int d3 = d2 + 1;
        byte[] bArr2 = this.alpahbets;
        int i = (p & 3) << 4;
        int s3 = s2 + 1;
        byte p2 = src[s2];
        dest[d2] = bArr2[i | ((p2 >>> 4) & 15)];
        int d4 = d3 + 1;
        byte[] bArr3 = this.alpahbets;
        int i2 = (p2 & 15) << 2;
        byte p3 = src[s3];
        dest[d3] = bArr3[i2 | ((p3 >>> 6) & 3)];
        dest[d4] = this.alpahbets[p3 & 63];
    }

    /* access modifiers changed from: 0000 */
    public void encode2bytes(byte[] src, int s, byte[] dest, int d) {
        int d2 = d + 1;
        byte[] bArr = this.alpahbets;
        int s2 = s + 1;
        byte p = src[s];
        dest[d] = bArr[(p >>> 2) & 63];
        int d3 = d2 + 1;
        byte[] bArr2 = this.alpahbets;
        int i = (p & 3) << 4;
        byte p2 = src[s2];
        dest[d2] = bArr2[i | ((p2 >>> 4) & 15)];
        int d4 = d3 + 1;
        dest[d3] = this.alpahbets[(p2 & 15) << 2];
        dest[d4] = PAD;
    }

    /* access modifiers changed from: 0000 */
    public void encode1byte(byte[] src, int s, byte[] dest, int d) {
        int d2 = d + 1;
        byte[] bArr = this.alpahbets;
        byte p = src[s];
        dest[d] = bArr[(p >>> 2) & 63];
        int d3 = d2 + 1;
        dest[d2] = this.alpahbets[(p & 3) << 4];
        int d4 = d3 + 1;
        dest[d3] = PAD;
        dest[d4] = PAD;
    }

    /* access modifiers changed from: 0000 */
    public void decode4bytes(byte[] src, int s, byte[] dest, int d) {
        int d2 = d + 1;
        int s2 = s + 1;
        int pos = pos(src[s]) << 2;
        int s3 = s2 + 1;
        int p = pos(src[s2]);
        dest[d] = (byte) (pos | ((p >>> 4) & 3));
        int d3 = d2 + 1;
        int i = (p & 15) << 4;
        int s4 = s3 + 1;
        int p2 = pos(src[s3]);
        dest[d2] = (byte) (i | ((p2 >>> 2) & 15));
        dest[d3] = (byte) (((p2 & 3) << 6) | pos(src[s4]));
    }

    /* access modifiers changed from: 0000 */
    public void decode1to3bytes(int n, byte[] src, int s, byte[] dest, int d) {
        int d2 = d + 1;
        int s2 = s + 1;
        int pos = pos(src[s]) << 2;
        int s3 = s2 + 1;
        int p = pos(src[s2]);
        dest[d] = (byte) (pos | ((p >>> 4) & 3));
        if (n == 1) {
            CodecUtils.sanityCheckLastPos(p, 15);
            int i = d2;
            return;
        }
        int d3 = d2 + 1;
        int i2 = (p & 15) << 4;
        int s4 = s3 + 1;
        int p2 = pos(src[s3]);
        dest[d2] = (byte) (i2 | ((p2 >>> 2) & 15));
        if (n == 2) {
            CodecUtils.sanityCheckLastPos(p2, 3);
            int i3 = s4;
            return;
        }
        dest[d3] = (byte) (((p2 & 3) << 6) | pos(src[s4]));
        int i4 = s4;
    }

    public byte[] decode(byte[] src, int length) {
        int fq;
        if (length % 4 != 0) {
            throw new IllegalArgumentException("Input is expected to be encoded in multiple of 4 bytes but found: " + length);
        }
        int pads = 0;
        int last = length - 1;
        while (pads < 2 && last > -1 && src[last] == 61) {
            last--;
            pads++;
        }
        switch (pads) {
            case 0:
                fq = 3;
                break;
            case 1:
                fq = 2;
                break;
            case 2:
                fq = 1;
                break;
            default:
                throw new Error("Impossible");
        }
        byte[] dest = new byte[(((length / 4) * 3) - (3 - fq))];
        int s = 0;
        int d = 0;
        while (d < dest.length - (fq % 3)) {
            decode4bytes(src, s, dest, d);
            s += 4;
            d += 3;
        }
        if (fq < 3) {
            decode1to3bytes(fq, src, s, dest, d);
        }
        return dest;
    }

    /* access modifiers changed from: protected */
    public int pos(byte in) {
        byte pos = LazyHolder.DECODED[in];
        if (pos > -1) {
            return pos;
        }
        throw new IllegalArgumentException("Invalid base 64 character: '" + ((char) in) + "'");
    }
}
