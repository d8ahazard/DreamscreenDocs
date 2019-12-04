package com.amazonaws.regions;

import com.amazonaws.SDKGlobalConfiguration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RegionUtils {
    private static final Log log = LogFactory.getLog("com.amazonaws.request");
    private static List<Region> regions;

    public static synchronized List<Region> getRegions() {
        List<Region> list;
        synchronized (RegionUtils.class) {
            if (regions == null) {
                init();
            }
            list = regions;
        }
        return list;
    }

    public static synchronized List<Region> getRegionsForService(String serviceAbbreviation) {
        List<Region> regions2;
        synchronized (RegionUtils.class) {
            regions2 = new LinkedList<>();
            for (Region r : getRegions()) {
                if (r.isServiceSupported(serviceAbbreviation)) {
                    regions2.add(r);
                }
            }
        }
        return regions2;
    }

    public static Region getRegion(String regionName) {
        for (Region r : getRegions()) {
            if (r.getName().equals(regionName)) {
                return r;
            }
        }
        return null;
    }

    public static Region getRegionByEndpoint(String endpoint) {
        String targetHost = getUriByEndpoint(endpoint).getHost();
        for (Region region : getRegions()) {
            Iterator it = region.getServiceEndpoints().values().iterator();
            while (true) {
                if (it.hasNext()) {
                    if (getUriByEndpoint((String) it.next()).getHost().equals(targetHost)) {
                        return region;
                    }
                }
            }
        }
        throw new IllegalArgumentException("No region found with any service for endpoint " + endpoint);
    }

    public static synchronized void init() {
        synchronized (RegionUtils.class) {
            if (System.getProperty(SDKGlobalConfiguration.REGIONS_FILE_OVERRIDE_SYSTEM_PROPERTY) != null) {
                try {
                    loadRegionsFromOverrideFile();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("Couldn't find regions override file specified", e);
                }
            }
            if (regions == null) {
                initSDKRegions();
            }
            if (regions == null) {
                throw new RuntimeException("Failed to initialize the regions.");
            }
        }
    }

    private static void loadRegionsFromOverrideFile() throws FileNotFoundException {
        String overrideFilePath = System.getProperty(SDKGlobalConfiguration.REGIONS_FILE_OVERRIDE_SYSTEM_PROPERTY);
        if (log.isDebugEnabled()) {
            log.debug("Using local override of the regions file (" + overrideFilePath + ") to initiate regions data...");
        }
        initRegions(new FileInputStream(new File(overrideFilePath)));
    }

    private static void initRegions(InputStream regionsFile) {
        try {
            regions = new RegionMetadataParser().parseRegionMetadata(regionsFile);
        } catch (Exception e) {
            log.warn("Failed to parse regional endpoints", e);
        }
    }

    private static void initSDKRegions() {
        if (log.isDebugEnabled()) {
            log.debug("Initializing the regions with default regions");
        }
        regions = RegionDefaults.getRegions();
    }

    private static URI getUriByEndpoint(String endpoint) {
        try {
            URI targetEndpointUri = new URI(endpoint);
            try {
                if (targetEndpointUri.getHost() == null) {
                    return new URI("http://" + endpoint);
                }
                return targetEndpointUri;
            } catch (URISyntaxException e) {
                e = e;
                URI uri = targetEndpointUri;
                throw new RuntimeException("Unable to parse service endpoint: " + e.getMessage());
            }
        } catch (URISyntaxException e2) {
            e = e2;
            throw new RuntimeException("Unable to parse service endpoint: " + e.getMessage());
        }
    }
}
