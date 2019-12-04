package com.lab714.dreamscreenv2.layout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lab714.dreamscreenv2.R;
import com.lab714.dreamscreenv2.devices.Connect;
import com.lab714.dreamscreenv2.devices.DreamScreen;
import com.lab714.dreamscreenv2.devices.DreamScreen4K;
import com.lab714.dreamscreenv2.devices.DreamScreenSolo;
import com.lab714.dreamscreenv2.devices.Light;
import com.lab714.dreamscreenv2.devices.SideKick;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.LinkedList;

public class SplashFragment extends Fragment {
    private static final int connectToWifiTimeout = 15000;
    private static final int dreamScreenPort = 8888;
    private static final int permissionRequestCode = 33030;
    private static final String tag = "SplashFrag";
    /* access modifiers changed from: private */
    public AlertDialog addNewDialog;
    /* access modifiers changed from: private */
    public InetAddress broadcastIP;
    /* access modifiers changed from: private */
    public InetAddress broadcastIPCompat;
    private ConnectivityManager connectivityManager;
    /* access modifiers changed from: private */
    public InetAddress deviceIP;
    private LinearLayout errorLayouts;
    /* access modifiers changed from: private */
    public final Handler handler = new Handler();
    public boolean isSearching = false;
    /* access modifiers changed from: private */
    public AlertDialog permissionsDialog;
    private RelativeLayout progressLayout;
    private int searchCount = 0;
    /* access modifiers changed from: private */
    public SplashFragmentListener splashFragmentListener;
    /* access modifiers changed from: private */
    public TextView ssidText;
    private TextView statusText;
    private LinearLayout wifiLayout;
    /* access modifiers changed from: private */
    public WifiManager wifiManager;
    /* access modifiers changed from: private */
    public WifiReceiver wifiReceiver = null;

    public interface SplashFragmentListener {
        void addLights(LinkedList<Light> linkedList);

        boolean finishedSearching();

        String getBroadcastIpString();

        String getDeviceIpString();

        void startNewDeviceFragment();

        void wifiNetworkChanged();
    }

    private class UDPBroadcast extends AsyncTask<byte[], Void, Void> {
        private UDPBroadcast() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(byte[]... bytes) {
            try {
                DatagramSocket s = new DatagramSocket();
                s.setBroadcast(true);
                byte[] command = bytes[0];
                s.send(new DatagramPacket(command, command.length, SplashFragment.this.broadcastIP, SplashFragment.dreamScreenPort));
                s.close();
            } catch (Exception e) {
                Log.i(SplashFragment.tag, "sending error-" + e.toString());
            }
            return null;
        }
    }

    private class UDPBroadcastCompat extends AsyncTask<byte[], Void, Void> {
        private UDPBroadcastCompat() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(byte[]... bytes) {
            try {
                DatagramSocket s = new DatagramSocket();
                s.setBroadcast(true);
                byte[] command = bytes[0];
                s.send(new DatagramPacket(command, command.length, SplashFragment.this.broadcastIPCompat, SplashFragment.dreamScreenPort));
                s.close();
            } catch (Exception e) {
                Log.i(SplashFragment.tag, "sending error-" + e.toString());
            }
            return null;
        }
    }

    private class UDPUnicast extends AsyncTask<byte[], Void, Void> {
        InetAddress unicastIp;

        public UDPUnicast(InetAddress unicastIp2) {
            this.unicastIp = unicastIp2;
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(byte[]... bytes) {
            try {
                DatagramSocket s = new DatagramSocket();
                byte[] command = bytes[0];
                s.send(new DatagramPacket(command, command.length, this.unicastIp, SplashFragment.dreamScreenPort));
                s.close();
            } catch (SocketException socketException) {
                Log.i(SplashFragment.tag, "sending unicast socketException-" + socketException.toString());
            } catch (Exception e) {
                Log.i(SplashFragment.tag, "sending unicast error-" + e.toString());
            }
            return null;
        }
    }

    class WifiReceiver extends BroadcastReceiver {
        WifiReceiver() {
        }

        public void onReceive(Context c, Intent intent) {
            if (intent.getAction().equals("android.net.wifi.STATE_CHANGE")) {
                Log.i(SplashFragment.tag, "NETWORK_STATE_CHANGED_ACTION");
                NetworkInfo info = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                if (info != null && info.isConnected()) {
                    String ssid = SplashFragment.this.wifiManager.getConnectionInfo().getSSID();
                    Log.i(SplashFragment.tag, "Connected to network: " + ssid);
                    SplashFragment.this.ssidText.setText(ssid.replaceAll("^\"|\"$", ""));
                    SplashFragment.this.handler.removeCallbacksAndMessages(null);
                    if (SplashFragment.this.wifiReceiver != null) {
                        SplashFragment.this.getActivity().unregisterReceiver(SplashFragment.this.wifiReceiver);
                        SplashFragment.this.wifiReceiver = null;
                    }
                    if (ssid.contains("DreamScreen") || ssid.contains("SideKick")) {
                        SplashFragment.this.handler.post(new Runnable() {
                            public void run() {
                                Log.i(SplashFragment.tag, "wifireceiver still connected to a dreamscreen, startWiFiError");
                                SplashFragment.this.startWiFiError();
                            }
                        });
                    } else {
                        SplashFragment.this.handler.postDelayed(new Runnable() {
                            public void run() {
                                Log.i(SplashFragment.tag, "wifiReceiver startSearching");
                                SplashFragment.this.startSearching();
                            }
                        }, 1000);
                    }
                }
            }
        }
    }

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(tag, "onRequestPermissionsResult");
        if (requestCode == permissionRequestCode) {
            onStart();
        }
    }

    public void onPause() {
        super.onPause();
        Log.i(tag, "onPause");
        if (this.addNewDialog != null && this.addNewDialog.isShowing()) {
            this.addNewDialog.dismiss();
        }
        if (this.permissionsDialog != null && this.permissionsDialog.isShowing()) {
            this.permissionsDialog.dismiss();
        }
        if (this.wifiReceiver != null) {
            getActivity().unregisterReceiver(this.wifiReceiver);
            this.wifiReceiver = null;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        this.statusText = (TextView) view.findViewById(R.id.statusText);
        TextView appVersionText = (TextView) view.findViewById(R.id.appVersionText);
        this.ssidText = (TextView) view.findViewById(R.id.ssidText);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf");
        this.statusText.setTypeface(font);
        ((TextView) view.findViewById(R.id.addNewDeviceText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.refreshText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.demoText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.demoText2)).setTypeface(font);
        ((TextView) view.findViewById(R.id.wifiText)).setTypeface(font);
        ((TextView) view.findViewById(R.id.wifiErrorText)).setTypeface(font);
        appVersionText.setTypeface(font);
        this.ssidText.setTypeface(font);
        this.ssidText.setText("");
        appVersionText.setText("v2.2.2.42");
        this.progressLayout = (RelativeLayout) view.findViewById(R.id.progressLayout);
        this.errorLayouts = (LinearLayout) view.findViewById(R.id.errorLayouts);
        this.wifiLayout = (LinearLayout) view.findViewById(R.id.wifiLayout);
        view.findViewById(R.id.addNewDeviceButton).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SplashFragment.this.addNewDialog != null && SplashFragment.this.addNewDialog.isShowing()) {
                    SplashFragment.this.addNewDialog.dismiss();
                }
                View addNewDialogView = SplashFragment.this.getActivity().getLayoutInflater().inflate(R.layout.new_device_dialog, null);
                TextView continueTextButton = (TextView) addNewDialogView.findViewById(R.id.continueTextButton);
                TextView cancelTextButton = (TextView) addNewDialogView.findViewById(R.id.cancelTextButton);
                Builder builder = new Builder(SplashFragment.this.getActivity());
                builder.setView(addNewDialogView).setCancelable(false);
                SplashFragment.this.addNewDialog = builder.create();
                SplashFragment.this.addNewDialog.show();
                continueTextButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        SplashFragment.this.addNewDialog.dismiss();
                        if (SplashFragment.this.splashFragmentListener != null) {
                            SplashFragment.this.splashFragmentListener.startNewDeviceFragment();
                        }
                    }
                });
                cancelTextButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        SplashFragment.this.addNewDialog.dismiss();
                    }
                });
            }
        });
        view.findViewById(R.id.wifiButton).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SplashFragment.this.startActivity(new Intent("android.settings.WIFI_SETTINGS"));
            }
        });
        view.findViewById(R.id.refreshButton).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SplashFragment.this.onStart();
            }
        });
        OnClickListener demoClicked = new OnClickListener() {
            public void onClick(View view) {
                Log.i(SplashFragment.tag, "initiate app demo with fake lights");
                LinkedList<Light> lights = new LinkedList<>();
                try {
                    DreamScreen d1 = new DreamScreen4K("192.168.4.101", "192.168.4.100");
                    d1.setAsDemo();
                    d1.initGroupNumber(1);
                    d1.initGroupName("Living Room");
                    lights.add(d1);
                    SideKick s1 = new SideKick("192.168.4.102", "192.168.4.100");
                    s1.setAsDemo();
                    s1.initName("SideKick Left");
                    s1.initGroupNumber(1);
                    s1.initGroupName("Living Room");
                    lights.add(s1);
                    SideKick s2 = new SideKick("192.168.4.103", "192.168.4.100");
                    s2.setAsDemo();
                    s2.initName("SideKick Right");
                    s2.initGroupNumber(1);
                    s2.initGroupName("Living Room");
                    lights.add(s2);
                    DreamScreen d2 = new DreamScreen("192.168.4.104", "192.168.4.100");
                    d2.setAsDemo();
                    d2.initGroupNumber(2);
                    d2.initGroupName("Bed Room");
                    lights.add(d2);
                    SideKick s3 = new SideKick("192.168.4.105", "192.168.4.100");
                    s3.setAsDemo();
                    s3.initName("SideKick Left");
                    s3.initGroupNumber(2);
                    s3.initGroupName("Bed Room");
                    lights.add(s3);
                    SideKick s4 = new SideKick("192.168.4.106", "192.168.4.100");
                    s4.setAsDemo();
                    s4.initName("SideKick Right");
                    s4.initGroupNumber(2);
                    s4.initGroupName("Bed Room");
                    lights.add(s4);
                    Connect c1 = new Connect("192.168.4.107", "192.168.4.100");
                    c1.setAsDemo();
                    c1.initGroupNumber(2);
                    c1.initGroupName("Bed Room");
                    lights.add(c1);
                    SideKick s5 = new SideKick("192.168.4.107", "192.168.4.100");
                    s5.setAsDemo();
                    s5.initName("Kitchen Accent");
                    lights.add(s5);
                    DreamScreen d3 = new DreamScreenSolo("192.168.4.108", "192.168.4.100");
                    d3.setAsDemo();
                    lights.add(d3);
                    if (SplashFragment.this.splashFragmentListener != null) {
                        SplashFragment.this.splashFragmentListener.addLights(lights);
                    }
                } catch (Exception e) {
                    Log.i(SplashFragment.tag, e.toString());
                }
            }
        };
        view.findViewById(R.id.demoButton).setOnClickListener(demoClicked);
        view.findViewById(R.id.demoButton2).setOnClickListener(demoClicked);
        return view;
    }

    public void onStart() {
        super.onStart();
        Log.i(tag, "SplashFragment onStart");
        if (this.addNewDialog != null && this.addNewDialog.isShowing()) {
            this.addNewDialog.dismiss();
        }
        if (this.permissionsDialog != null && this.permissionsDialog.isShowing()) {
            this.permissionsDialog.dismiss();
        }
        this.errorLayouts.setVisibility(8);
        this.wifiLayout.setVisibility(8);
        this.progressLayout.setVisibility(0);
        this.statusText.setText("");
        if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.ACCESS_COARSE_LOCATION") != 0) {
            Log.i(tag, "ACCESS_COARSE_LOCATION not granted");
            View locationPermissionDialog = getActivity().getLayoutInflater().inflate(R.layout.location_permission_dialog, null);
            TextView continueText = (TextView) locationPermissionDialog.findViewById(R.id.continueText);
            TextView continueTextButton = (TextView) locationPermissionDialog.findViewById(R.id.continueTextButton);
            Builder builder = new Builder(getActivity());
            builder.setView(locationPermissionDialog).setCancelable(false);
            this.permissionsDialog = builder.create();
            this.permissionsDialog.show();
            Log.i(tag, "permissionsDialog.show()");
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), "android.permission.ACCESS_COARSE_LOCATION")) {
                continueText.setText(getContext().getResources().getString(R.string.locationSettings1));
                continueTextButton.setText(getContext().getResources().getString(R.string.locationSettings2));
                continueTextButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        SplashFragment.this.permissionsDialog.dismiss();
                        if (SplashFragment.this.getActivity() == null) {
                            Log.i(SplashFragment.tag, "Open App Settings, location permission, getActivity null. return?");
                            return;
                        }
                        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", SplashFragment.this.getActivity().getPackageName(), null));
                        intent.addFlags(268435456);
                        SplashFragment.this.startActivity(intent);
                    }
                });
                return;
            }
            continueText.setText(getContext().getResources().getString(R.string.locationPermissions1));
            continueTextButton.setText(getContext().getResources().getString(R.string.locationPermissions2));
            continueTextButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SplashFragment.this.permissionsDialog.dismiss();
                    if (SplashFragment.this.splashFragmentListener == null) {
                        Log.i(SplashFragment.tag, "special case, cancelling");
                        return;
                    }
                    Log.i(SplashFragment.tag, "ACCESS_COARSE_LOCATION start request");
                    SplashFragment.this.requestPermissions(new String[]{"android.permission.ACCESS_COARSE_LOCATION"}, SplashFragment.permissionRequestCode);
                }
            });
            return;
        }
        Log.i(tag, "ACCESS_COARSE_LOCATION granted");
        this.wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService("wifi");
        String currentNetwork = this.wifiManager.getConnectionInfo().getSSID();
        if (currentNetwork == null || (!currentNetwork.contains("DreamScreen") && !currentNetwork.contains("SideKick"))) {
            this.connectivityManager = (ConnectivityManager) getActivity().getSystemService("connectivity");
            if (wifiConnected()) {
                this.ssidText.setText(currentNetwork.replaceAll("^\"|\"$", ""));
                startSearching();
                return;
            }
            this.ssidText.setText("");
            if (this.wifiManager != null) {
                Log.i(tag, "not connected to wifi ");
                this.wifiManager.setWifiEnabled(true);
                this.statusText.setText(getContext().getResources().getString(R.string.ConnectingToNetwork));
                this.wifiReceiver = new WifiReceiver();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.net.wifi.STATE_CHANGE");
                getActivity().registerReceiver(this.wifiReceiver, intentFilter);
                this.handler.postDelayed(new Runnable() {
                    public void run() {
                        if (SplashFragment.this.wifiReceiver != null) {
                            SplashFragment.this.startWiFiError();
                        }
                    }
                }, 15000);
                return;
            }
            return;
        }
        this.ssidText.setText(currentNetwork.replaceAll("^\"|\"$", ""));
        int id = this.wifiManager.getConnectionInfo().getNetworkId();
        Log.i(tag, "connected to dreamscreen directly " + id);
        Log.i(tag, "removeNetwork " + this.wifiManager.removeNetwork(id));
        this.wifiManager.saveConfiguration();
        this.statusText.setText(getContext().getResources().getString(R.string.ConnectingToNetwork));
        this.wifiReceiver = new WifiReceiver();
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.net.wifi.STATE_CHANGE");
        getActivity().registerReceiver(this.wifiReceiver, intentFilter2);
        this.handler.postDelayed(new Runnable() {
            public void run() {
                if (SplashFragment.this.wifiReceiver != null) {
                    SplashFragment.this.startWiFiError();
                }
            }
        }, 15000);
    }

    /* access modifiers changed from: private */
    public void startSearching() {
        Log.i(tag, "startSearching");
        if (VERSION.SDK_INT >= 23 && this.connectivityManager != null && this.connectivityManager.getAllNetworks() != null) {
            Network[] networks = this.connectivityManager.getAllNetworks();
            int length = networks.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Network network = networks[i];
                if (this.connectivityManager != null) {
                    if (network != null && this.connectivityManager.getNetworkInfo(network) != null && this.connectivityManager.getNetworkInfo(network).getType() == 1) {
                        this.connectivityManager.bindProcessToNetwork(network);
                        Log.i(tag, "android M bound to wifinetwork " + network.toString());
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
        }
        if (this.wifiManager != null) {
            try {
                if (this.splashFragmentListener != null) {
                    this.splashFragmentListener.wifiNetworkChanged();
                    this.broadcastIP = InetAddress.getByName(this.splashFragmentListener.getBroadcastIpString());
                    this.broadcastIPCompat = InetAddress.getByName("255.255.255.255");
                    this.deviceIP = InetAddress.getByName(this.splashFragmentListener.getDeviceIpString());
                }
            } catch (Exception e) {
                Log.i(tag, e.toString());
            }
            this.searchCount++;
            if (this.searchCount % 3 != 0) {
                discoverAllDevicesGetState();
            } else {
                discoverAllDevicesGetState_UnicastAll();
            }
        }
    }

    private void discoverAllDevicesGetState() {
        this.isSearching = true;
        new Thread(new Runnable() {
            public void run() {
                ByteArrayOutputStream discoveryPacket = new ByteArrayOutputStream();
                discoveryPacket.write(252);
                discoveryPacket.write(5);
                discoveryPacket.write(255);
                discoveryPacket.write(48);
                discoveryPacket.write(1);
                discoveryPacket.write(10);
                discoveryPacket.write(42);
                ByteArrayOutputStream newDevicePacket = new ByteArrayOutputStream();
                newDevicePacket.write(252);
                newDevicePacket.write(5);
                newDevicePacket.write(255);
                newDevicePacket.write(48);
                newDevicePacket.write(1);
                newDevicePacket.write(19);
                newDevicePacket.write(101);
                try {
                    new UDPBroadcast().execute(new byte[][]{discoveryPacket.toByteArray()});
                    Log.i(SplashFragment.tag, "sent discovery, get state, udp broadcast");
                    Thread.sleep(430);
                    new UDPBroadcast().execute(new byte[][]{newDevicePacket.toByteArray()});
                    Log.i(SplashFragment.tag, "sent get new devices, udp broadcast");
                    Thread.sleep(430);
                    new UDPBroadcastCompat().execute(new byte[][]{discoveryPacket.toByteArray()});
                    Log.i(SplashFragment.tag, "compat: sent discovery, get state, udp broadcast");
                    Thread.sleep(430);
                    new UDPBroadcastCompat().execute(new byte[][]{newDevicePacket.toByteArray()});
                    Log.i(SplashFragment.tag, "compat: sent get new devices, udp broadcast");
                    Thread.sleep(430);
                    new UDPBroadcast().execute(new byte[][]{discoveryPacket.toByteArray()});
                    Log.i(SplashFragment.tag, "sent discovery, get state, udp broadcast");
                    Thread.sleep(430);
                    new UDPBroadcast().execute(new byte[][]{newDevicePacket.toByteArray()});
                    Log.i(SplashFragment.tag, "sent get new devices, udp broadcast");
                    Thread.sleep(430);
                    new UDPBroadcastCompat().execute(new byte[][]{discoveryPacket.toByteArray()});
                    Log.i(SplashFragment.tag, "compat: sent discovery, get state, udp broadcast");
                    Thread.sleep(430);
                } catch (Exception e) {
                    Log.i(SplashFragment.tag, "send discovery failed :" + e.toString());
                }
                SplashFragment.this.handler.post(new Runnable() {
                    public void run() {
                        if (SplashFragment.this.splashFragmentListener != null && !SplashFragment.this.splashFragmentListener.finishedSearching()) {
                            SplashFragment.this.startNoLightsFound();
                        }
                    }
                });
            }
        }).start();
    }

    private void discoverAllDevicesGetState_UnicastAll() {
        Log.i(tag, "discoverAllDevicesGetState_UnicastAll");
        this.isSearching = true;
        new Thread(new Runnable() {
            public void run() {
                ByteArrayOutputStream discoveryPacket = new ByteArrayOutputStream();
                discoveryPacket.write(252);
                discoveryPacket.write(5);
                discoveryPacket.write(255);
                discoveryPacket.write(48);
                discoveryPacket.write(1);
                discoveryPacket.write(10);
                discoveryPacket.write(Light.uartComm_calculate_crc8(discoveryPacket.toByteArray()));
                try {
                    byte[] addressBytes = SplashFragment.this.deviceIP.getAddress();
                    for (int i = 0; i < 255; i++) {
                        addressBytes[3] = (byte) i;
                        new UDPUnicast(InetAddress.getByAddress(addressBytes)).execute(new byte[][]{discoveryPacket.toByteArray()});
                        Thread.sleep(60);
                    }
                } catch (Exception e) {
                    Log.i(SplashFragment.tag, "send discovery unicastAll failed :" + e.toString());
                }
                SplashFragment.this.handler.post(new Runnable() {
                    public void run() {
                        if (SplashFragment.this.splashFragmentListener != null && !SplashFragment.this.splashFragmentListener.finishedSearching()) {
                            SplashFragment.this.startNoLightsFound();
                        }
                    }
                });
            }
        }).start();
    }

    /* access modifiers changed from: private */
    public void startNoLightsFound() {
        this.isSearching = false;
        this.progressLayout.setVisibility(8);
        this.wifiLayout.setVisibility(8);
        this.errorLayouts.setVisibility(0);
        this.statusText.setText(getContext().getResources().getString(R.string.NoDevicesFound));
    }

    /* access modifiers changed from: private */
    public void startWiFiError() {
        Log.i(tag, "startWiFiError");
        this.progressLayout.setVisibility(8);
        this.errorLayouts.setVisibility(8);
        this.wifiLayout.setVisibility(0);
    }

    private boolean wifiConnected() {
        if (!this.wifiManager.isWifiEnabled() || this.wifiManager.getConnectionInfo().getNetworkId() == -1) {
            return false;
        }
        return true;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SplashFragmentListener) {
            this.splashFragmentListener = (SplashFragmentListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
    }

    public void onDetach() {
        if (this.addNewDialog != null && this.addNewDialog.isShowing()) {
            this.addNewDialog.dismiss();
        }
        if (this.permissionsDialog != null && this.permissionsDialog.isShowing()) {
            this.permissionsDialog.dismiss();
        }
        if (this.wifiReceiver != null) {
            getActivity().unregisterReceiver(this.wifiReceiver);
            this.wifiReceiver = null;
        }
        super.onDetach();
        this.splashFragmentListener = null;
    }
}
