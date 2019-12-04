package com.philips.lighting.hue.sdk.wrapper.utilities;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class NetUtil {
    List<NetworkInterface> networkInterfaces;

    NetUtil() {
        this.networkInterfaces = null;
        this.networkInterfaces = new ArrayList();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                this.networkInterfaces.add(interfaces.nextElement());
            }
        } catch (SocketException se) {
            se.printStackTrace();
        }
    }

    private int getInterfaceCount() {
        return this.networkInterfaces.size();
    }

    private NetworkInterface getInterface(int index) {
        return (NetworkInterface) this.networkInterfaces.get(index);
    }

    private byte[] getInterfaceName(int index) {
        return NativeTools.StringToBytes(getInterface(index).getDisplayName());
    }

    private byte[] getInterfaceIPv4Address(int index) {
        Enumeration<InetAddress> addresses = getInterface(index).getInetAddresses();
        while (addresses.hasMoreElements()) {
            InetAddress address = (InetAddress) addresses.nextElement();
            if (address instanceof Inet4Address) {
                String ip = address.toString();
                if (ip.charAt(0) == '/') {
                    ip = ip.substring(1);
                }
                return NativeTools.StringToBytes(ip);
            }
        }
        return null;
    }

    private byte[] getInterfaceIPv6Address(int index) {
        Enumeration<InetAddress> addresses = getInterface(index).getInetAddresses();
        while (addresses.hasMoreElements()) {
            InetAddress address = (InetAddress) addresses.nextElement();
            if (address instanceof Inet6Address) {
                String ip = address.toString();
                if (ip.charAt(0) == '/') {
                    ip = ip.substring(1);
                }
                return NativeTools.StringToBytes(ip);
            }
        }
        return null;
    }

    private boolean getInterfaceUp(int index) {
        try {
            return getInterface(index).isUp();
        } catch (SocketException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean getInterfaceLoopback(int index) {
        try {
            return getInterface(index).isLoopback();
        } catch (SocketException e) {
            e.printStackTrace();
            return false;
        }
    }
}
