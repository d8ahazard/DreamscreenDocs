package com.amazonaws.internal.config;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class HostRegexToRegionMapping {
    private final String hostNameRegex;
    private final String regionName;

    public HostRegexToRegionMapping(String hostNameRegex2, String regionName2) {
        if (hostNameRegex2 == null || hostNameRegex2.isEmpty()) {
            throw new IllegalArgumentException("Invalid HostRegexToRegionMapping configuration: hostNameRegex must be non-empty");
        }
        try {
            Pattern.compile(hostNameRegex2);
            if (regionName2 == null || regionName2.isEmpty()) {
                throw new IllegalArgumentException("Invalid HostRegexToRegionMapping configuration: regionName must be non-empty");
            }
            this.hostNameRegex = hostNameRegex2;
            this.regionName = regionName2;
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Invalid HostRegexToRegionMapping configuration: hostNameRegex is not a valid regex", e);
        }
    }

    public String getHostNameRegex() {
        return this.hostNameRegex;
    }

    public String getRegionName() {
        return this.regionName;
    }
}
