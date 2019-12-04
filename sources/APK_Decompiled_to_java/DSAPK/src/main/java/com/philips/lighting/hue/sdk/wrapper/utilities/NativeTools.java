package com.philips.lighting.hue.sdk.wrapper.utilities;

import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NativeTools {
    private static <T> List<T> arrayToList(T[] array) {
        return new ArrayList(Arrays.asList(array));
    }

    private static <T> Object[] listToArray(List<T> list) {
        return list.toArray();
    }

    public static String BytesToString(byte[] bytes) {
        if (bytes != null) {
            try {
                return new String(bytes, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static byte[] StringToBytes(String string) {
        if (string != null) {
            try {
                return string.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static ReturnCode resolveReturnCode(int value) {
        return ReturnCode.fromValue(value);
    }

    private static NetUtil getNetUtil() {
        return new NetUtil();
    }
}
