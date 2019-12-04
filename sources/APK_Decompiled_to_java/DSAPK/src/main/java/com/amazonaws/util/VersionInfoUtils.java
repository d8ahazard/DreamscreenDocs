package com.amazonaws.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VersionInfoUtils {
    private static final int DEFAULT_STRING_LENGTH = 128;
    private static final Log log = LogFactory.getLog(VersionInfoUtils.class);
    private static volatile String platform = "android";
    private static volatile String userAgent;
    private static volatile String version = "2.6.5";

    public static String getVersion() {
        return version;
    }

    public static String getPlatform() {
        return platform;
    }

    public static String getUserAgent() {
        if (userAgent == null) {
            synchronized (VersionInfoUtils.class) {
                if (userAgent == null) {
                    initializeUserAgent();
                }
            }
        }
        return userAgent;
    }

    private static void initializeUserAgent() {
        userAgent = userAgent();
    }

    static String userAgent() {
        StringBuilder buffer = new StringBuilder(128);
        buffer.append("aws-sdk-");
        buffer.append(StringUtils.lowerCase(getPlatform()));
        buffer.append("/");
        buffer.append(getVersion());
        buffer.append(" ");
        buffer.append(replaceSpaces(System.getProperty("os.name")));
        buffer.append("/");
        buffer.append(replaceSpaces(System.getProperty("os.version")));
        buffer.append(" ");
        buffer.append(replaceSpaces(System.getProperty("java.vm.name")));
        buffer.append("/");
        buffer.append(replaceSpaces(System.getProperty("java.vm.version")));
        buffer.append("/");
        buffer.append(replaceSpaces(System.getProperty("java.version")));
        String language = System.getProperty("user.language");
        String region = System.getProperty("user.region");
        if (!(language == null || region == null)) {
            buffer.append(" ");
            buffer.append(replaceSpaces(language));
            buffer.append("_");
            buffer.append(replaceSpaces(region));
        }
        return buffer.toString();
    }

    private static String replaceSpaces(String input) {
        return input.replace(' ', '_');
    }
}
