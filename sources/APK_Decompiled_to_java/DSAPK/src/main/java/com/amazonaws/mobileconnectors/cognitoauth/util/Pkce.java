package com.amazonaws.mobileconnectors.cognitoauth.util;

import android.util.Base64;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.SecureRandom;

public final class Pkce {
    public static final String generateRandom() {
        byte[] randBytes = new byte[32];
        new SecureRandom().nextBytes(randBytes);
        return Base64.encodeToString(randBytes, 11);
    }

    public static final String generateHash(String data) throws Exception {
        String str = data;
        try {
            byte[] bytes = data.getBytes("US-ASCII");
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(bytes, 0, bytes.length);
            return Base64.encodeToString(digest.digest(), 11);
        } catch (Exception e) {
            throw e;
        }
    }

    public static final String encodeBase64(String str) {
        if (str == null) {
            return null;
        }
        return Base64.encodeToString(str.getBytes(Charset.forName("ISO-8859-1")), 1);
    }
}
