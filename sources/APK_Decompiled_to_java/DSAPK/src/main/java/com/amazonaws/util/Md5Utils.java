package com.amazonaws.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.logging.LogFactory;

public class Md5Utils {
    private static final int FOURTEEN = 14;
    private static final int SIXTEEN_K = 16384;

    public static byte[] computeMD5Hash(InputStream is) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[16384];
            while (true) {
                int bytesRead = bis.read(buffer, 0, buffer.length);
                if (bytesRead == -1) {
                    break;
                }
                messageDigest.update(buffer, 0, bytesRead);
            }
            byte[] digest = messageDigest.digest();
            try {
                bis.close();
            } catch (Exception e) {
                LogFactory.getLog(Md5Utils.class).debug("Unable to close input stream of hash candidate: " + e);
            }
            return digest;
        } catch (NoSuchAlgorithmException e2) {
            throw new IllegalStateException(e2);
        } catch (Throwable th) {
            try {
                bis.close();
            } catch (Exception e3) {
                LogFactory.getLog(Md5Utils.class).debug("Unable to close input stream of hash candidate: " + e3);
            }
            throw th;
        }
    }

    public static String md5AsBase64(InputStream is) throws IOException {
        return Base64.encodeAsString(computeMD5Hash(is));
    }

    public static byte[] computeMD5Hash(byte[] input) {
        try {
            return MessageDigest.getInstance("MD5").digest(input);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String md5AsBase64(byte[] input) {
        return Base64.encodeAsString(computeMD5Hash(input));
    }

    public static byte[] computeMD5Hash(File file) throws IOException {
        return computeMD5Hash((InputStream) new FileInputStream(file));
    }

    public static String md5AsBase64(File file) throws IOException {
        return Base64.encodeAsString(computeMD5Hash(file));
    }
}
