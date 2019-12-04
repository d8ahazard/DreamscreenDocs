package com.amazonaws.util;

public enum EncodingSchemeEnum implements EncodingScheme {
    BASE16 {
        public String encodeAsString(byte[] bytes) {
            return Base16.encodeAsString(bytes);
        }

        public byte[] decode(String encoded) {
            return Base16.decode(encoded);
        }
    },
    BASE32 {
        public String encodeAsString(byte[] bytes) {
            return Base32.encodeAsString(bytes);
        }

        public byte[] decode(String encoded) {
            return Base32.decode(encoded);
        }
    },
    BASE64 {
        public String encodeAsString(byte[] bytes) {
            return Base64.encodeAsString(bytes);
        }

        public byte[] decode(String encoded) {
            return Base64.decode(encoded);
        }
    };

    public abstract String encodeAsString(byte[] bArr);
}
