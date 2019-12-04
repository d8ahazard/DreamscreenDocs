package com.amazonaws.regions;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Deprecated
public class RegionMetadataParser {
    private static final String DOMAIN_TAG = "Domain";
    private static final String ENDPOINT_TAG = "Endpoint";
    private static final String HOSTNAME_TAG = "Hostname";
    private static final String HTTPS_TAG = "Https";
    private static final String HTTP_TAG = "Http";
    private static final String REGION_ID_TAG = "Name";
    private static final String REGION_TAG = "Region";
    private static final String SERVICE_TAG = "ServiceName";

    public static RegionMetadata parse(InputStream input) throws IOException {
        return new RegionMetadata(internalParse(input, false));
    }

    @Deprecated
    public List<Region> parseRegionMetadata(InputStream input) throws IOException {
        return internalParse(input, false);
    }

    @Deprecated
    public List<Region> parseRegionMetadata(InputStream input, boolean endpointVerification) throws IOException {
        return internalParse(input, endpointVerification);
    }

    private static List<Region> internalParse(InputStream input, boolean endpointVerification) throws IOException {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
            try {
                input.close();
            } catch (IOException e) {
            }
            NodeList regionNodes = document.getElementsByTagName(REGION_TAG);
            List<Region> regions = new ArrayList<>();
            for (int i = 0; i < regionNodes.getLength(); i++) {
                Node node = regionNodes.item(i);
                if (node.getNodeType() == 1) {
                    regions.add(parseRegionElement((Element) node, endpointVerification));
                }
            }
            return regions;
        } catch (IOException exception) {
            throw exception;
        } catch (Exception exception2) {
            throw new IOException("Unable to parse region metadata file: " + exception2.getMessage(), exception2);
        } catch (Throwable th) {
            try {
                input.close();
            } catch (IOException e2) {
            }
            throw th;
        }
    }

    private static Region parseRegionElement(Element regionElement, boolean endpointVerification) {
        Region region = new Region(getChildElementValue(REGION_ID_TAG, regionElement), getChildElementValue(DOMAIN_TAG, regionElement));
        NodeList endpointNodes = regionElement.getElementsByTagName(ENDPOINT_TAG);
        for (int i = 0; i < endpointNodes.getLength(); i++) {
            addRegionEndpoint(region, (Element) endpointNodes.item(i), endpointVerification);
        }
        return region;
    }

    private static void addRegionEndpoint(Region region, Element endpointElement, boolean endpointVerification) {
        String serviceName = getChildElementValue(SERVICE_TAG, endpointElement);
        String hostname = getChildElementValue(HOSTNAME_TAG, endpointElement);
        String http = getChildElementValue(HTTP_TAG, endpointElement);
        String https = getChildElementValue(HTTPS_TAG, endpointElement);
        if (!endpointVerification || verifyLegacyEndpoint(hostname)) {
            region.getServiceEndpoints().put(serviceName, hostname);
            region.getHttpSupport().put(serviceName, Boolean.valueOf("true".equals(http)));
            region.getHttpsSupport().put(serviceName, Boolean.valueOf("true".equals(https)));
            return;
        }
        throw new IllegalStateException("Invalid service endpoint (" + hostname + ") is detected.");
    }

    private static String getChildElementValue(String tagName, Element element) {
        Node tagNode = element.getElementsByTagName(tagName).item(0);
        if (tagNode == null) {
            return null;
        }
        return tagNode.getChildNodes().item(0).getNodeValue();
    }

    private static boolean verifyLegacyEndpoint(String endpoint) {
        return endpoint.endsWith(".amazonaws.com");
    }
}
