package com.amazonaws.util;

import com.amazonaws.internal.config.HostRegexToRegionMapping;
import com.amazonaws.internal.config.InternalConfig.Factory;
import com.amazonaws.regions.ServiceAbbreviations;
import java.net.InetAddress;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.LogFactory;

public class AwsHostNameUtils {
    private static final Pattern S3_ENDPOINT_PATTERN = Pattern.compile("^(?:.+\\.)?s3[.-]([a-z0-9-]+)$");

    @Deprecated
    public static String parseRegionName(URI endpoint) {
        return parseRegionName(endpoint.getHost(), null);
    }

    public static String parseRegionName(String host, String serviceHint) {
        if (host == null) {
            throw new IllegalArgumentException("hostname cannot be null");
        }
        String regionNameInInternalConfig = parseRegionNameByInternalConfig(host);
        if (regionNameInInternalConfig != null) {
            return regionNameInInternalConfig;
        }
        if (host.endsWith(".amazonaws.com")) {
            return parseStandardRegionName(host.substring(0, host.length() - ".amazonaws.com".length()));
        }
        if (serviceHint != null) {
            Matcher matcher = Pattern.compile("^(?:.+\\.)?" + Pattern.quote(serviceHint) + "[.-]([a-z0-9-]+)\\.").matcher(host);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return "us-east-1";
    }

    private static String parseStandardRegionName(String fragment) {
        Matcher matcher = S3_ENDPOINT_PATTERN.matcher(fragment);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        int index = fragment.lastIndexOf(46);
        if (index == -1) {
            return "us-east-1";
        }
        String region = fragment.substring(index + 1);
        if ("us-gov".equals(region)) {
            return "us-gov-west-1";
        }
        return region;
    }

    private static String parseRegionNameByInternalConfig(String host) {
        for (HostRegexToRegionMapping mapping : Factory.getInternalConfig().getHostRegexToRegionMappings()) {
            if (host.matches(mapping.getHostNameRegex())) {
                return mapping.getRegionName();
            }
        }
        return null;
    }

    @Deprecated
    public static String parseServiceName(URI endpoint) {
        String host = endpoint.getHost();
        if (!host.endsWith(".amazonaws.com")) {
            throw new IllegalArgumentException("Cannot parse a service name from an unrecognized endpoint (" + host + ").");
        }
        String serviceAndRegion = host.substring(0, host.indexOf(".amazonaws.com"));
        if (serviceAndRegion.endsWith(".s3") || S3_ENDPOINT_PATTERN.matcher(serviceAndRegion).matches()) {
            return ServiceAbbreviations.S3;
        }
        if (serviceAndRegion.indexOf(46) != -1) {
            return serviceAndRegion.substring(0, serviceAndRegion.indexOf(46));
        }
        return serviceAndRegion;
    }

    public static String localHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            LogFactory.getLog(AwsHostNameUtils.class).debug("Failed to determine the local hostname; fall back to use \"localhost\".", e);
            return "localhost";
        }
    }
}
